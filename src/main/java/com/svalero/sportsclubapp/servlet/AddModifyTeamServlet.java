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

    //doPost PORQUE ESTOY DANDO DE ALTA DESDE UN FOMULARIO DESDE ADDTEAM.JSP

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html"); //PONERLO SIEMPRE PARA QUE NOS DEVUELVA COMO HTML Y NO TEXTO SIMPLE
        PrintWriter out = response.getWriter(); //DE ESTA FORMA ES MÁS CÓMODO PINTAR POR PANTALLA SOLO USANDO out.

        //SINO ESTA LOGEADO LO MANDO DE NUEVO AL INICIO DE SESIÓN
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
        }

        String name = request.getParameter("name"); //REQUEST PARA RECOGER LO QUE PROVIENE DEL USUARIO --> input name del formulario
        name = name.toUpperCase();
        String category = request.getParameter("category");
        category = category.toUpperCase();
        String action = request.getParameter("action"); //PARA INDICARLE SI QUEREMOS REGISTRAR O MODIFICAR
        String idUser = request.getParameter("idUser");
        String idTeam = request.getParameter("idTeam");
        Team team = new Team(name, category);

        Database database = new Database(); //CREAMOS UN OBJETO Database PARA CONECTARNOS A LA BBDD
        TeamDao teamDao = new TeamDao(database.getConnection()); //CREAMOS EL OBJETO DAO CORRESPONDIENTE Y LE PASAMOS LA CONEXIÓN A LA BBDD
        try {
            if (action.equals("register")) {
                teamDao.add(team);
                out.println("<div class='alert alert-success' role='alert'>Equipo Registrado en la BBDD correctamente</div>");
            } else {
                teamDao.modifyById(Integer.parseInt(idTeam), team);
                out.println("<div class='alert alert-success' role='alert'>Equipo Modificado en la BBDD correctamente</div>");
            }
        } catch (TeamAlreadyExistException taee) {
            out.println("<div class='alert alert-danger' role='alert'>El equipo ya existe en la BBDD</div>");
            taee.printStackTrace(); //PINTAMOS LAS TRAZAS DEL ERROR
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al conectar con la BBDD</div>");
            sqle.printStackTrace(); //PINTAMOS LAS TRAZAS DEL ERROR
        }
    }
}
