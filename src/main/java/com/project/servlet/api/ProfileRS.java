package com.project.servlet.api;

import com.project.db.dao.ProfileDao;
import com.project.db.entities.Profile;
import com.project.db.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("/profile")
@Stateless
public class ProfileRS {
    static Logger logger = LogManager.getRootLogger();
    @Inject
    private ProfileDao profileDao;

    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<Profile> listUser(){
        logger.debug("A request for all user entered");
        return profileDao.getAll();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Profile getUserById(@PathParam("id") long id){
        return profileDao.getById(id);
    }


    @POST
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response addUser(Profile user ){
        try {
            profileDao.insert(user);
            return Response.ok().entity(user).build();
        }catch (Exception e){
            e.printStackTrace(System.out);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @PUT
//    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//    @Path("{id}")
//    public Response updateUser(@PathParam("id") long id, User changedUser){
//        try{
//            User user = userDao.getUserById(id);
//            if(user != null){
//                userDao.updateUser(changedUser);
//                return Response.ok().entity(changedUser).build();
//            }
//            return Response.status(Status.NOT_FOUND).build();
//        }catch (Exception e){
//            e.printStackTrace(System.out);
//            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @DELETE
//    @Path("{id}")
//    public Response deleteUserById(@PathParam("id") long id){
//        try{
//            User user = userDao.getUserById(id);
//            if(user != null){
//                userDao.deleteUser(user);
//                return Response.ok().build();
//            }
//            return Response.status(Status.NOT_FOUND).build();
//        }catch (Exception e){
//            e.printStackTrace(System.out);
//            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//        }
//    }

}
