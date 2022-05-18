package com.svalero.sportsclubapp.servlet;

import com.svalero.sportsclubapp.dao.Database;
import com.svalero.sportsclubapp.dao.TeamDao;
import com.svalero.sportsclubapp.domain.Team;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/showTeam") //ANOTACIÓN QUE ASOCIO LA RUTA PARA LLAMARLA DESDE LOS JSP
public class GetTeamsServlet extends HttpServlet {

    //doGet --> Petición información, doPost Recibir información
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html"); //PONERLO SIEMPRE PARA QUE NOS DEVUELVA COMO HTML Y NO TEXTO SIMPLE
        PrintWriter out = response.getWriter(); //DE ESTA FORMA ES MÁS CÓMODO PINTAR POR PANTALLA SOLO USANDO out.

        Database database = new Database(); //CREAMOS UN OBJETO Database PARA CONECTARNOS A LA BBDD
        TeamDao teamDao = new TeamDao(database.getConnection()); //CREAMOS EL OBJETO DAO CORRESPONDIENTE Y LE PASAMOS LA CONEXIÓN A LA BBDD
        try {
            out.println("<ul>"); //PARA CREAR LA LISTA ORDENADA
            List<Team> teams = teamDao.findAll();
            for (Team team : teams) {
                //PODRÍA HACER REFERENCIA A team tambien
                out.println("<li><p><a href='team.jsp?idTeam=" + team.getIdTeam() + "'>" + team.getName() + " / " + team.getCategory() + " / " + team.getIdUser() + "</a></p></li>");//AL PINCHAR SOBRE EL EQUIPO CON <a href='team.jsp?='" +team.jsp.getId() GENERO ENLACE ÚNICO A ESE EQUIPO
            }
            out.println("</ul>"); //FIN DE LA LISTA ORDENADA
            database.close(); //PARA CERRAR LA CONEXIÓN CON LA BBDD AL TERMINAR
        } catch (SQLException sqle) {
            out.println("<p style='color=red'>Se ha producido un error al conectar con la BBDD</p>");
            sqle.printStackTrace(); //PINTAMOS LAS TRAZAS DEL ERROR
        }
    }
}
