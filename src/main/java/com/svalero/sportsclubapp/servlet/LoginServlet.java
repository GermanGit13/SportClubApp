package com.svalero.sportsclubapp.servlet;

import com.svalero.sportsclubapp.dao.Database;
import com.svalero.sportsclubapp.dao.UserDao;
import com.svalero.sportsclubapp.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /**
     * doPost PORQUE ESTOY DANDO DE ALTA DESDE UN FOMULARIO DESDE login.JSP
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username"); //RECOGEMOS LOS PARAMETROS QUE NOS ENVIA LOGIN.JSP MEDIANTE EL NAME
        String password = request.getParameter("password"); //RECOGEMOS LOS PARAMETROS QUE NOS ENVIA LOGIN.JSP MEDIANTE EL NAME

        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        try {
            Optional<User> user = userDao.login(username, password);//RECUPERAMOS EL USUARIO
            if (user.isPresent()) {
                HttpSession session = request.getSession(true); //INICIAMOS LA SESION
                session.setAttribute("currentUser", user.get()); //ALMACENAMOS COMO EL ACTUAL AL USER
                System.out.println("sesión iniciada");
                response.sendRedirect("index.jsp"); //REDIRIGIMOS
            } else {
                out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">No estás registrado o tus datos no son correctos</a>");
            }
        } catch (SQLException sqle) {
            out.println("<a href=\"index.jsp\" class=\"btn btn-warning\" type=\"submit\">Se ha producido un error al conectar con la BBDD</a>");
            sqle.printStackTrace();
        }
    }
}
