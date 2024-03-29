package com.example.demo.controllers.rest;

import com.example.demo.config.annotation.Auth;
import com.example.demo.config.annotation.AuthPayload;
import com.example.demo.config.records.AuthInfo;
import com.example.demo.db.entities.JsonWebToken;
import com.example.demo.db.entities.Profile;
import com.example.demo.db.entities.Role;
import com.example.demo.db.entities.User;
import com.example.demo.db.repo.JwtRepo;
import com.example.demo.db.repo.ProfileRepo;
import com.example.demo.db.repo.RoleRepo;
import com.example.demo.db.repo.UserRepo;
import com.example.demo.scheduler.AutoScheduleMonitor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Objects;

/**
 * Controller that implements the Users REST API
 */
@RestController
@RequestMapping(path = "/users")
public class UserController extends RestApiAbstract<User, UserRepo, Integer> {
    final static private String USERNAME_KEY = "username";
    final static private String PASSWORD_KEY = "password";

    final Logger logger = LogManager.getLogger(UserController.class);
    final UserRepo userRepo;
    final ProfileRepo profileRepo;
    final JwtRepo jwtRepo;

    final RoleRepo roleRepo;

    final AutoScheduleMonitor autoScheduleMonitor;

    public UserController(UserRepo repo, JwtRepo jwtRepo, ProfileRepo profileRepo, RoleRepo roleRepo, AutoScheduleMonitor autoScheduleMonitor) {
        this.userRepo = repo;
        this.jwtRepo = jwtRepo;
        this.profileRepo = profileRepo;
        this.roleRepo = roleRepo;
        this.autoScheduleMonitor = autoScheduleMonitor;
    }


    /**
     * <b>Post /login</b>
     * <p>Login route</p>
     *
     * @param request stringified json with username and password
     * @return Json stringified with role,jwt,id
     * @throws ResponseStatusException when cannot execute correctly
     */
    @RequestMapping(method = RequestMethod.POST, path = "/login", produces = "application/json")
    public String login(@RequestBody String request) {
        JsonObject jsonRequest = JsonParser.parseString(request).getAsJsonObject();
        if (!jsonRequest.has(USERNAME_KEY) || !jsonRequest.has(PASSWORD_KEY)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing username or password");
        }

        String username = jsonRequest.get(USERNAME_KEY).getAsString();
        String password = jsonRequest.get(PASSWORD_KEY).getAsString();
        User u = userRepo.findFirstByUsernameAndPasswordOrderByUsernameAsc(username, password);
        if (u == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        jwtRepo.updateInvalidOldJwt(u.getId());
        JsonWebToken jwt = new JsonWebToken();
        jwt.setUid(u.getId());
        try {
            jwt = jwtRepo.save(jwt);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error while handling insert reason:" + e.getMostSpecificCause());
            logger.debug(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid properties");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to handle request");
        }
        JsonObject response = new JsonObject();
        response.addProperty("jwt", jwt.getJwt().toString());
        response.addProperty("role", u.getRole().getRoleLevel());
        response.addProperty("id", u.getId());
        response.add("profile", JsonParser.parseString(privateGson.toJson(u.getProfile())));
        return response.toString();
    }

    /**
     * <b>Post /signup</b>
     * <p>signup route for the system</p>
     *
     * @param request stringified json with flattened profile and user
     * @return Json stringified with username, nad if it was created.
     * @throws ResponseStatusException when cannot execute correctly
     */

    @RequestMapping(method = RequestMethod.POST, path = "/signup", produces = "application/json")
    public String signup(@RequestBody String request) {
        JsonObject jsonRequest;
        String username;
        String password;
        Profile profile = null;
        User user = null;
        try {
            jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            user = new User();
            profile = gson.fromJson(jsonRequest, Profile.class);
            username = jsonRequest.get(USERNAME_KEY).getAsString();
            password = jsonRequest.get(PASSWORD_KEY).getAsString();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole_id(roleRepo.getRoleByLevel(0).orElse(new Role()).getId());

            profile = profileRepo.save(profile);
            user.setPid(profile.getId());
            userRepo.save(user);
        } catch (Exception e) {
            logger.error(e);
            if (profile != null && profile.getId() != null) profileRepo.delete(profile);
            if (user != null && user.getId() != null) userRepo.delete(user);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        JsonObject response = new JsonObject();
        response.addProperty("username", username);
        response.addProperty("created", true);

        return response.toString();
    }

    /**
     * <b>GET /logout</b>
     * <p>logout route for the system</p>
     *
     * @param authInfo the information of the user that enter to this call
     * @return Json stringified with logout flag {@code true} if success otherwise {@code false}
     * @throws ResponseStatusException when cannot execute correctly
     */
    @RequestMapping(path = "/logout", method = RequestMethod.GET, produces = "application/json")
    @Auth
    public String logout(@AuthPayload AuthInfo authInfo) {
        JsonObject object = new JsonObject();
        if (authInfo == null || !Objects.equals(authInfo.user(), authInfo.token().getUser()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        long count = jwtRepo.deleteByUid(authInfo.user().getId());
        if (count == 0) {
            logger.warn("Can not log out from logged out user");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already logged out");
        }
        object.addProperty("logout", true);
        return object.toString();
    }

    /**
     * <b>GET /users-id/{id}</b>
     * <p>Manger call to get all users of profile</p>
     *
     * @param id of the profile
     * @throws ResponseStatusException when cannot execute correctly
     */
    @Auth(minLevel = 1)
    @GetMapping("/users-id/{id}")
    public String getUserProfileById(@PathVariable Integer id) {
        Collection<User> users = userRepo.findByProfile(id);
        if (users.size() == 0) {
            logger.warn("No user with id " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        JsonArray array = new JsonArray();
        users.forEach(user -> array.add(JsonParser.parseString(privateGson.toJson(user))));
        return array.toString();
    }

    /**
     * <b>Post /users-id/{id}</b>
     * <p>Super Admin call to promote user</p>
     *
     * @param id user to promote
     * @throws ResponseStatusException when cannot execute correctly
     */
    @Auth(minLevel = 2)
    @PostMapping("/add-admin/{id}")
    public String setAsAdmin(@PathVariable Integer id) {
        User u = userRepo.findById(id).orElse(null);
        Role role = roleRepo.getRoleByLevel(Role.MANAGER_ROLE_LEVEL).orElse(null);
        changeUserRole(u, role);
        return JsonParser.parseString(privateGson.toJson(u)).getAsJsonObject().toString();
    }

    /**
     * <b>DELETE /users-id/{id}</b>
     * <p>Super Admin call to demote user</p>
     *
     * @param id user to promote
     * @throws ResponseStatusException when cannot execute correctly
     */
    @Auth(minLevel = 2)
    @DeleteMapping("/remove-admin/{id}")
    public String unsetAsAdmin(@PathVariable Integer id) {
        User u = userRepo.findById(id).orElse(null);
        Role role = roleRepo.getRoleByLevel(Role.STANDARD_ROLE_LEVEL).orElse(null);
        changeUserRole(u, role);
        return JsonParser.parseString(privateGson.toJson(u)).getAsJsonObject().toString();
    }

    /**
     * Helper function to change the role of user
     *
     * @param u    target user
     * @param role target role
     */
    private void changeUserRole(User u, Role role) {
        if (u == null || role == null) {
            logger.warn("Send 404 reason user or role were not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        u.setRole_id(role.getId());
        userRepo.save(u);
    }

    @Override
    public UserRepo getAvailableShiftsRepo() {
        return userRepo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
