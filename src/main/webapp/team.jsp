<!-- Para importar las clases que nos van a  hacer falta -->
<%@ page import="com.svalero.sportsclubapp.dao.Database" %>
<%@ page import="com.svalero.sportsclubapp.dao.TeamDao" %>
<%@ page import="com.svalero.sportsclubapp.domain.Team" %>
<%@ page import="com.svalero.sportsclubapp.domain.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<!-- FIN importar las clases que nos van a  hacer falta -->

<html>
<head>
    <!-- Para usar la hoja de estilos de  Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!-- FIN Bootstrap -->
</head>

<body>
    <div class="container"> <!-- Para que quede centrada la web gracias a la hoja de estilo de Bootstrap -->
    <%
        String teamId = request.getParameter("id_team");
        Database database = new Database();
        TeamDao teamDao = new TeamDao(database.getConnection());
        Team team = null;
        try {
            Optional<Team> optionalTeam = teamDao.findById(Integer.parseInt(teamId));
            team = optionalTeam.get();

    %>
        <div class="container">
            <div class="card text-center">
              <div class="card-header">
                Detalles del equipo
              </div>
              <div class="card-body">
                <h5 class="card-title"><%= team.getName() %></h5>
                <p class="card-text">Categoria: <strong><%= team.getCategory() %></strong></p>
                <p class="card-text">Cuota: <strong><%= team.getQuota() %></strong></p>
                <a href="buy?id=<%= team.getIdTeam() %>" class="btn btn-primary">Modificar</a>
              </div>
              <div class="card-footer text-muted">
                Entrenado por usuario:  <strong><%= team.getIdUser() %></strong>
              </div>
            </div>
        </div>
        <%
            } catch (SQLException sqle) {
        %>
            <div class='alert alert-danger' role='alert'>Se ha producido al cargar los datos del equipo</div>
        <%
            }
        %>

    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>