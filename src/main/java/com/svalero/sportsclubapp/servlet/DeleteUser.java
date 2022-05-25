package com.svalero.sportsclubapp.servlet;

import com.svalero.sportsclubapp.dao.Database;
import com.svalero.sportsclubapp.dao.PlayerDao;
import com.svalero.sportsclubapp.dao.TeamDao;
import com.svalero.sportsclubapp.dao.UserDao;
import com.svalero.sportsclubapp.domain.Player;
import com.svalero.sportsclubapp.domain.Team;
import com.svalero.sportsclubapp.domain.User;
import com.svalero.sportsclubapp.exception.UserExistTablesException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/deleteUser")
public class DeleteUser extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //SINO ESTA LOGEADO LO MANDO DE NUEVO AL INICIO DE SESIÓN
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
        }

        String idUser = request.getParameter("id_user");
        int idUserCero = 0;

        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        TeamDao teamDao = new TeamDao(database.getConnection());
        PlayerDao playerDao = new PlayerDao(database.getConnection());
        try {
            if (teamDao.findById(Integer.parseInt(idUser)) != null) {
                teamDao.deleteById(Integer.parseInt(idUser))
                teamDao.modifyByIdUser(Integer.parseInt(idUser));
                out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Borrado Correctamente</a>");
            }

        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'><Se ha producido un error al conectar con la BBDD</div>");
            sqle.printStackTrace(); //PINTAMOS LAS TRAZAS DEL ERROR
        }
    }
}
