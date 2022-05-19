package com.svalero.sportsclubapp.servlet;

import com.svalero.sportsclubapp.dao.Database;
import com.svalero.sportsclubapp.dao.UserDao;
import com.svalero.sportsclubapp.domain.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/search-user")
public class SearchUserServlet extends HttpServlet {

    //doPost PORQUE ESTOY DANDO DE ALTA DESDE UN FOMULARIO DESDE searchPlayer.JSP
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
        response.setContentType(("text/html")); //PONERLO SIEMPRE PARA QUE NOS DEVUELVA COMO HTML Y NO TEXTO SIMPLE
        PrintWriter out = response.getWriter(); //DE ESTA FORMA ES MÁS CÓMODO PINTAR POR PANTALLA SOLO USANDO out.

        String searchText = request.getParameter("searchtext"); //REQUEST PARA RECOGER LO QUE PROVIENE DEL USUARIO --> input searchtext del formulario
        searchText = searchText.toUpperCase();

        Database database = new Database(); //CREAMOS UN OBJETO Database PARA CONECTARNOS A LA BBDD
        UserDao userDao = new UserDao(database.getConnection()); //CREAMOS EL OBJETO DAO CORRESPONDIENTE Y LE PASAMOS LA CONEXIÓN A LA BBDD
        try {
            ArrayList<User> users = userDao.findAll(searchText); //USO EL METODO FINDALL CON PARAMETRO SEARCHTEXT
            StringBuilder result = new StringBuilder("<ul class='list-group'>"); //CONSTRUYO UN STRING CON LOS li
            for (User user : users) {
                result.append("<li class='list-group-item'>").append("Nombre: " + user.getFirstName() + " Apellidos: " + user.getLastName() + " Dni: " + user.getDni() + " Correo: " + user.getEmail()).append("</li>");
            }
            result.append("</ul>");
            out.println(result); //DEVOLVEMOS EL STRING CON TODOS LOS li
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al conectar con la BBDD</div>");
            sqle.printStackTrace(); //PINTAMOS LAS TRAZAS DEL ERROR
        }
    }
}