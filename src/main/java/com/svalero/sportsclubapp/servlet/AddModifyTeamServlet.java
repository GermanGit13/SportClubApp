package com.svalero.sportsclubapp.servlet;

import com.svalero.sportsclubapp.dao.Database;
import com.svalero.sportsclubapp.dao.TeamDao;
import com.svalero.sportsclubapp.domain.Team;
import com.svalero.sportsclubapp.domain.User;
import com.svalero.sportsclubapp.exception.TeamAlreadyExistException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static com.svalero.sportsclubapp.util.Constants.QUOTA;

@WebServlet("/add-modify-team")
public class AddModifyTeamServlet extends HttpServlet {

    /**
     * doPost PORQUE ESTOY DANDO DE ALTA DESDE UN FOMULARIO DESDE ADDPLAYER.JSP
     * response.setContentType("text/html"); PONERLO SIEMPRE PARA QUE NOS DEVUELVA COMO HTML Y NO TEXTO SIMPLE
     * PrintWriter out: CÓMODO PINTAR POR PANTALLA SOLO USANDO out.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        /**
         * SINO ESTA LOGEADO LO MANDO DE NUEVO AL INICIO DE SESIÓN
         */
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
        }

        /**
         * REQUEST PARA RECOGER LO QUE PROVIENE DEL USUARIO --> input name del formulario
         * String action = request.getParameter("action"); //PARA INDICARLE SI QUEREMOS REGISTRAR O MODIFICAR
         */
        String name = request.getParameter("name");
        name = name.toUpperCase();
        String category = request.getParameter("category");
        category = category.toUpperCase();
        String action = request.getParameter("action");
        String idUser = request.getParameter("coach");
        String idTeam = request.getParameter("idTeam");
        Team team = new Team(name, category);

        Database database = new Database();
        TeamDao teamDao = new TeamDao(database.getConnection());
        try {
            if (action.equals("register")) {
                teamDao.add(team);
                out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Equipo Registrado Correctamente</a>");
            } else {
                teamDao.modifyById(Integer.parseInt(idTeam), team);
                out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Equipo Modificado Correctamente</a>");;
            }
        } catch (TeamAlreadyExistException taee) {
            out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">El equipo ya existe en la Base de Datos</a>");
            taee.printStackTrace();
        } catch (SQLException sqle) {
            out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Se ha producido un error al conectar con la BBDD</a>");
            sqle.printStackTrace();
        }
    }
}
