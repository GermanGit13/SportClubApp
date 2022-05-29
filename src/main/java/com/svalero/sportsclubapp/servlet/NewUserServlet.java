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

@WebServlet("/new-user")
public class NewUserServlet extends HttpServlet {

    /**
     * doPost PORQUE ESTOY DANDO DE ALTA DESDE UN FOMULARIO DESDE newUser.JSP
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String firstname = request.getParameter("firstname"); //REQUEST PARA RECOGER LO QUE PROVIENE DEL USUARIO --> input name del formulario
        firstname = firstname.toUpperCase();
        String lastname = request.getParameter("lastname");
        lastname = lastname.toUpperCase();
        String email = request.getParameter("email");
        String dni = request.getParameter("dni");
        dni = dni.toUpperCase();
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        User user = new User(firstname, lastname, email, dni, username, pass);

        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        try {
            userDao.add(user);
            out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Usuario registrado correctamente</a>");
        } catch (UserAlredyExistException uaee) {
            out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Usuario ya existe en la Base de Datos</a>");
            uaee.printStackTrace();
        } catch (SQLException sqle) {
            out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Se ha producido un error al conectar con la BBDD</a>");
            sqle.printStackTrace();
        }
    }
}
