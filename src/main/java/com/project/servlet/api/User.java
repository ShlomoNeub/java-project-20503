package com.project.servlet.api;

import com.project.db.dao.UserDao;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/api/user")
public class User extends HttpServlet {
    @Inject
    UserDao userDao;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getQueryString());

        request.setAttribute("users",userDao.getAll());
        request.getRequestDispatcher("/list/user.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
