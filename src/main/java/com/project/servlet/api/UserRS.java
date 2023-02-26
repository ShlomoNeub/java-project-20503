package com.project.servlet.api;

import com.project.db.dao.ProfileDao;
import com.project.db.services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;
import org.apache.logging.log4j.*;
import java.util.List;

@Path("/user")
@Stateless
public class UserRS {
    static Logger logger = LogManager.getRootLogger();
    @Inject
    private UserService userDao;
    @Inject
    private ProfileDao profileDao;

    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<com.project.db.entities.User> listUser(){
        logger.debug("A request for all user entered");
        return userDao.getAllUsers();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("{id}")
    public com.project.db.entities.User getUserById(@PathParam("id") long id){
        return userDao.getUserById(id);
    }


    @POST
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response addUser(com.project.db.entities.User user ){
        try {
            userDao.addUser(user);
            return Response.ok().entity(user).build();
        }catch (Exception e){
            e.printStackTrace(System.out);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response updateUser(@PathParam("id") long id, com.project.db.entities.User changedUser){
        try{
            com.project.db.entities.User user = userDao.getUserById(id);
            if(user != null){
                userDao.updateUser(changedUser);
                return Response.ok().entity(changedUser).build();
            }
            return Response.status(Status.NOT_FOUND).build();
        }catch (Exception e){
            e.printStackTrace(System.out);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteUserById(@PathParam("id") long id){
        try{
            User user = userDao.getUserById(id);
            if(user != null){
                userDao.deleteUser(user);
                return Response.ok().build();
            }
            return Response.status(Status.NOT_FOUND).build();
        }catch (Exception e){
            e.printStackTrace(System.out);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
