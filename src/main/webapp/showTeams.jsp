<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
    }
%>

<!-- Para importar las clases que nos van a  hacer falta -->
<%@ page import="com.svalero.sportsclubapp.dao.Database" %>
<%@ page import="com.svalero.sportsclubapp.dao.TeamDao" %>
<%@ page import="com.svalero.sportsclubapp.dao.UserDao" %>
<%@ page import="com.svalero.sportsclubapp.domain.Team" %>
<%@ page import="com.svalero.sportsclubapp.domain.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<!-- FIN importar las clases que nos van a  hacer falta -->

<jsp:include page="headerAjax.jsp" />

<body>
    <div class="container"> <!-- Para que quede centrada la web gracias a la hoja de estilo de Bootstrap -->
        <h2>Listado Completo de Equipos</h2>
        <ul class="list-group">
          <%
            String pagina = "";
            String variable = "";
            Database database = new Database(); //creamos la conexión con la BBDD
            TeamDao teamDao = new TeamDao(database.getConnection()); //Creamos un teamdao y le pasamos la conexion
            try {
                List<Team> teams = teamDao.findAll();
                for (Team team: teams) {
          %>

            <%
                if (team.getIdUser() != null) {
                    pagina = "coach.jsp";
                } else
                    pagina = " ";
            %>
                    <li class="list-group-item d-flex justify-content-between align-items-start w-50">
                        <div class="ms-2 me-auto">
                          <div class="fw-bold"><a target="_blank" href="team.jsp?id_team=<%= team.getIdTeam() %>"><%= team.getName() %></a></div> <!-- target="_blank" para abrir una pestaña nueva en el enlace -->
                          <%= team.getCategory() %>
                        </div>
                        <span class="list-group-item d-flex justify-content-between align-items-start w-35"><a href="<%= pagina %>?id_user=<%= team.getIdUser() %>"> Coach </a></span>
                    </li>
          <%
                }
            } catch (SQLException sqle) {
          %>
                    <div class="alert alert-danger" role="alert">
                      Error al conectar con la BBDD
                    </div>
          <%
            }
          %>
        </ul>
    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>
