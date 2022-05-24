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
<%@ page import="com.svalero.sportsclubapp.dao.PlayerDao" %>
<%@ page import="com.svalero.sportsclubapp.domain.Player" %>
<%@ page import="com.svalero.sportsclubapp.domain.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="com.svalero.sportsclubapp.dao.TeamDao" %>
<%@ page import="com.svalero.sportsclubapp.domain.Team" %>
<!-- FIN importar las clases que nos van a  hacer falta -->

<jsp:include page="header.jsp" />

<body>
    <div class="container"> <!-- Para que quede centrada la web gracias a la hoja de estilo de Bootstrap -->
    <%
        String playerId = request.getParameter("id_player");
        Database database = new Database();
        PlayerDao playerDao = new PlayerDao(database.getConnection());
        Player player = null;
        try {
            Optional<Player> optionalPlayer = playerDao.findById(Integer.parseInt(playerId));
            player = optionalPlayer.get();
    %>
        <div class="container">
            <div class="card text-center">
              <div class="card-header">
                Detalles del Jugador
              </div>
              <div class="card-body">
                <h5 class="card-title"><%= player.getFirstName() %></h5>
                <p class="card-text">Apellidos: <strong><%= player.getLastName() %></strong></p>
                <p class="card-text">Año Nacimiento: <strong><%= player.getYearOfBirth() %></strong></p>
                <p class="card-text">Dni: <strong><%= player.getDni() %></strong></p>
                <p class="card-text">Número: <strong><%= player.getNumber() %></strong></p>
                <a href="addPlayer.jsp?id_player=<%= player.getIdPlayer() %>" class="btn btn-outline-warning">Modificar</a>
                <a href="coach.jsp?id_user=<%= player.getIdUser() %>" class="btn btn-outline-danger">Eliminar Pendiente</a>
                <a href="coach.jsp?id_user=<%= player.getIdUser() %>" class="btn btn-outline-success">Asignar Equipo Pendiente</a>
                <a href="user.jsp?id_user=<%= player.getIdUser() %>" class="btn btn-outline-info">Datos Padres</a>
              </div>
              <div class="card-footer text-muted">
        <%
            String idTeam = String.valueOf(player.getIdTeam());
            String name = null;
            String category = null;

            if (idTeam != null) {
                Database db = new Database();
                TeamDao teamDao = new TeamDao(db.getConnection());
                Team team = null;
                try {
                    Optional<Team> optionalTeam = teamDao.findById(Integer.parseInt(idTeam));
                    team = optionalTeam.get();
                    name = team.getName();
                    category = team.getCategory();
                } catch (SQLException sqle) {
        %>
            <div> class='alert alert-danger' role='alert'>Se ha producido al cargar el equipo del jugador</div>
        <%
                }
            } else {
                name = "Sin Asignar";
                category = "Sin Asignar";
            }
        %>
                Equipo:  <strong><%=  name + " Categoria: " + category %></strong>
                </div>
             </div>
        </div>
        <%
            } catch (SQLException sqle) {
        %>
            <div class='alert alert-danger' role='alert'>Se ha producido al cargar los datos del jugador</div>
        <%
            }
        %>

    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>