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

<jsp:include page="header.jsp" />

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
                Detalles del Entrenador
              </div>
              <div class="card-body">
                <h5 class="card-title"><%= user.getFirstName() %></h5>
                <p class="card-text">Correo: <strong><%= user.getEmail() %></strong></p>
                <p class="card-text">Dni: <strong><%= user.getDni() %></strong></p>
                <a href="buy?id=<%= user.getIdUser() %>" class="btn btn-primary">Modificar</a>
              </div>
              <div class="card-footer text-muted">
                Código Coach:  <strong><%= user.getIdUser() %></strong>
              </div>
            </div>
        </div>

        <%
            } catch (SQLException sqle) {
        %>
            <div class='alert alert-danger' role='alert'>Se ha producido al cargar los datos del entrenador</div>
        <%
            }
        %>

    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>