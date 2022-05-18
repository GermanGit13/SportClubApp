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
<html>
<head>
    <!-- Para usar la hoja de estilos de  Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!-- FIN Bootstrap -->
</head>

<body>
    <div class="container"> <!-- Para que quede centrada la web gracias a la hoja de estilo de Bootstrap -->
    <%
        String userId = request.getParameter("id_user");
        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        User user = null;
        try {
            Optional<User> optionalUser = userDao.findById(Integer.parseInt(userId));
            user = optionalUser.get();
    %>
        <div class="container">
            <div class="card text-center">
              <div class="card-header">
                Detalles del Usuario
              </div>
              <div class="card-body">
                <h5 class="card-title"><%= user.getFirstName() %></h5>
                <p class="card-text">Apellidos: <strong><%= user.getLastName() %></strong></p>
                <p class="card-text">Email: <strong><%= user.getEmail() %></strong></p>
                <p class="card-text">Dni: <strong><%= user.getDni() %></strong></p>
                <p class="card-text">Username: <strong><%= user.getUsername() %></strong></p>
                <a href="buy?id=<%= user.getIdUser() %>" class="btn btn-primary">Modificar</a>
              </div>
              <div class="card-footer text-muted">
                Entrenador:  <strong><%= user.getCoach() %></strong>
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