package com.example.demo.controllers.rest;

import com.example.demo.config.interfaces.Auth;
import com.example.demo.config.interfaces.AuthPayload;
import com.example.demo.db.entities.JsonWebToken;
import com.example.demo.db.entities.Profile;
import com.example.demo.db.entities.Role;
import com.example.demo.db.entities.Users;
import com.example.demo.db.repo.JwtRepo;
import com.example.demo.db.repo.ProfileRepo;
import com.example.demo.db.repo.RoleRepo;
import com.example.demo.db.repo.UserRepo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Controller that implements the Users REST API
 */
@RestController
@RequestMapping(path = "/users")
public class UserController extends RestApiAbstract<Users, UserRepo, Integer> {
    final static private String USERNAME_KEY = "username";
    final static private String PASSWORD_KEY = "username";

    final Logger logger = LogManager.getLogger(UserController.class);
    final UserRepo userRepo;
    final ProfileRepo profileRepo;
    final JwtRepo jwtRepo;

    final RoleRepo roleRepo;
    final Gson gson = new Gson();

    public UserController(UserRepo repo, JwtRepo jwtRepo, ProfileRepo profileRepo, RoleRepo roleRepo) {
        this.userRepo = repo;
        this.jwtRepo = jwtRepo;
        this.profileRepo = profileRepo;
        this.roleRepo = roleRepo;
    }

    /**
     * Login route
     * @param request expects JsonString with the keys username and passwrod
     * @return JsonString with jwt,id,role;
     */
    @RequestMapping(method = RequestMethod.POST, path = "/login", produces = "application/json")
    public String login(@RequestBody String request) {
        JsonObject jsonRequest = JsonParser.parseString(request).getAsJsonObject();
        if (!jsonRequest.has(USERNAME_KEY) || !jsonRequest.has(PASSWORD_KEY)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing username or password");
        }

        String username = jsonRequest.get("username").getAsString();
        String password = jsonRequest.get("password").getAsString();
        Users u = userRepo.findFirstByUsernameAndPasswordOrderByUsernameAsc(username, password);
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
        return response.toString();
    }

    /**
     * signup route for the system
     * @param request JsonString with flat representation of {@link Profile} and username and password
     * @return JsonString with username and created flag
     */
    @RequestMapping(method = RequestMethod.POST, path = "/signup", produces = "application/json")
    public String signup(@RequestBody String request) {
        JsonObject jsonRequest;
        String username;
        String password;
        Profile profile = null;
        Users user = null;
        try {
            jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            user = new Users();
            profile = Profile.fromJson(jsonRequest);
            username = jsonRequest.get("username").getAsString();
            password = jsonRequest.get("password").getAsString();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole_id(roleRepo.getRoleByLevel(0).orElse(new Role()).getId());

            profile = profileRepo.save(profile);
            user.setPid(profile.getId());
            user = userRepo.save(user);
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

    @Auth
    @RequestMapping(path = "/logout", method = RequestMethod.GET, produces = "application/json")
    public String logout(@AuthPayload String jwt, @AuthPayload(jwt = false) String uid) {
        JsonObject object = new JsonObject();
        object.addProperty("logout", true);
        return object.toString();
    }


    @RequestMapping(path = "/{id}/user-profiles", method = RequestMethod.GET)
    public Collection<String> getProfiles(@PathVariable Integer id) {
        Optional<Users> user = userRepo.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with id" + id);
        }
        ArrayList<String> usersStrings = new ArrayList<>();
        for (Users _user : user.get().getProfile().getUsers()) {
            usersStrings.add(_user.getUsername());
        }

        return usersStrings;
    }


    @Override
    public UserRepo getRepo() {
        return userRepo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
