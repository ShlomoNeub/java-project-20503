package com.project.servlet.api;

import com.project.db.dao.ProfileDao;
import com.project.db.services.ProfileService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/profile")
public class Profile extends HttpServlet {
    @Inject
    ProfileDao profileDao;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getQueryString());

        request.setAttribute("profiles",profileDao.getAll());
        request.getRequestDispatcher("/list/profile.jsp").forward(request,response);
    }
}
