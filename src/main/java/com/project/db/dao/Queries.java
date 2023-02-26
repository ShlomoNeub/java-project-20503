package com.project.db.dao;


public class Queries {
    public static class UserQueries{
        /**
         * Get all the users without any parameters
         */
        public final static String GET_ALL = "User.GetAll";
        /**
         * Retrieve Profile of the user.
         * notice this query need id of the user
         */
        public static final String GET_PROFILE_BY_ID = "User.GetProfile";
        public static final String GET_BY_ID = "User.GetById";
    }

    public static class ProfileQueries{
        /**
         * Get all profiles
         */
        public final static String GET_ALL = "Profile.GetAll";

    }

}
