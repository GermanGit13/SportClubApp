package com.svalero.sportsclubapp.servlet;

import com.svalero.sportsclubapp.dao.Database;
import com.svalero.sportsclubapp.dao.PlayerDao;
import com.svalero.sportsclubapp.domain.Player;
import com.svalero.sportsclubapp.domain.User;
import com.svalero.sportsclubapp.exception.DniAlredyExistException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/add-modify-player")
public class AddModifyPlayerServlet extends HttpServlet {

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
         */
        String firstname = request.getParameter("firstname");
        firstname = firstname.toUpperCase();
        String lastname = request.getParameter("lastname");
        lastname = lastname.toUpperCase();
        String numbers = request.getParameter("numbers");
        String yearOfBirth = request.getParameter("yearOfBirth");
        String dni = request.getParameter("dni");
        dni = dni.toUpperCase();
        String idUser = request.getParameter("idUser");
        String idPlayer = request.getParameter("idPlayer");
        String action = request.getParameter("action");
        Player player = new Player(firstname, lastname, Integer.parseInt(numbers), Integer.parseInt(yearOfBirth), dni, currentUser.getIdUser());

        Database database = new Database(); //CREAMOS UN OBJETO Database PARA CONECTARNOS A LA BBDD
        PlayerDao playerDao = new PlayerDao(database.getConnection()); //CREAMOS EL OBJETO DAO CORRESPONDIENTE Y LE PASAMOS LA CONEXIÓN A LA BBDD
        try {
            if (action.equals("register")) {
                playerDao.add(player);
                out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Jugador Registrado Correctamente</a>");
            } else {
                player = new Player(firstname, lastname, Integer.parseInt(numbers), Integer.parseInt(yearOfBirth), dni);
                playerDao.modifyById(Integer.parseInt(idPlayer), player);
                out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Jugador Modificado Correctamente</a>");
            }
        } catch (DniAlredyExistException taee) {
            out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">DNI ya registrado en la Base de Datos</a>");
            taee.printStackTrace(); //PINTAMOS LAS TRAZAS DEL ERROR
        } catch (SQLException sqle) {
            out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Se ha producido un error al conectar con la BBDD</a>");
            sqle.printStackTrace(); //PINTAMOS LAS TRAZAS DEL ERROR
        }
    }
}
