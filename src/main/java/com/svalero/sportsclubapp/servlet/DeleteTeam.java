package com.svalero.sportsclubapp.servlet;

import com.svalero.sportsclubapp.dao.Database;
import com.svalero.sportsclubapp.dao.TeamDao;
import com.svalero.sportsclubapp.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/deleteTeam")
public class DeleteTeam extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //SINO ESTA LOGEADO LO MANDO DE NUEVO AL INICIO DE SESIÓN
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
        }

        String idTeam = request.getParameter("id_team");

        Database database = new Database();
        TeamDao teamDao = new TeamDao(database.getConnection());
        try {
            teamDao.deleteById(Integer.parseInt(idTeam));
            out.println("<div class='alert alert-success' role='alert'>Equipo Borrado de la BBDD correctamente</div>");
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al conectar con la BBDD</div>");
            sqle.printStackTrace(); //PINTAMOS LAS TRAZAS DEL ERROR
        }
    }
}
