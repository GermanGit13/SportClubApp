package com.svalero.sportsclubapp.servlet;

import com.svalero.sportsclubapp.dao.Database;
import com.svalero.sportsclubapp.dao.PlayerDao;
import com.svalero.sportsclubapp.dao.UserDao;
import com.svalero.sportsclubapp.domain.Player;
import com.svalero.sportsclubapp.domain.User;
import com.svalero.sportsclubapp.exception.DniAlredyExistException;
import com.svalero.sportsclubapp.exception.UserAlredyExistException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/add-modify-user")
public class AddModifyUserServlet extends HttpServlet {

    //doPost PORQUE ESTOY DANDO DE ALTA DESDE UN FOMULARIO DESDE ADDPLAYER.JSP
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html"); //PONERLO SIEMPRE PARA QUE NOS DEVUELVA COMO HTML Y NO TEXTO SIMPLE
        PrintWriter out = response.getWriter(); //DE ESTA FORMA ES MÁS CÓMODO PINTAR POR PANTALLA SOLO USANDO out.

        //SINO ESTA LOGEADO LO MANDO DE NUEVO AL INICIO DE SESIÓN
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
        }

        String firstname = request.getParameter("firstname"); //REQUEST PARA RECOGER LO QUE PROVIENE DEL USUARIO --> input name del formulario
        firstname = firstname.toUpperCase();
        String lastname = request.getParameter("lastname");
        lastname = lastname.toUpperCase();
        String email = request.getParameter("email");
        email = email.toUpperCase();
        String dni = request.getParameter("dni");
        dni = dni.toUpperCase();
        String pass = request.getParameter("pass");
        String coach = request.getParameter("coach");
        coach = coach.toUpperCase();
        String idUser = request.getParameter("idUser");
        String action = request.getParameter("action");
        User user = new User(firstname, lastname, email, dni, pass);

        Database database = new Database(); //CREAMOS UN OBJETO Database PARA CONECTARNOS A LA BBDD
        UserDao userDao = new UserDao(database.getConnection()); //CREAMOS EL OBJETO DAO CORRESPONDIENTE Y LE PASAMOS LA CONEXIÓN A LA BBDD
        try {
            if (action.equals("register")) {
                userDao.add(user);
                out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Usuario registrado en la Base de Datos</a>");
            } else {
                user = new User(firstname, lastname, email, dni, coach);
                userDao.modifyById(Integer.parseInt(idUser), user);
                out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Usuario Modificado Correctamente</a>");
            }
        } catch (UserAlredyExistException uaee) {
            out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Usuario ya registrado en la Base de Datos</a>");;
            uaee.printStackTrace();
        } catch (SQLException sqle) {
            out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Se ha producido un error al conectar con la BBDD</a>");
            sqle.printStackTrace();
        }
    }
}
