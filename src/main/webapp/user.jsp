<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%
    String pagina = "";
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
    }
%>

<!-- Para importar las clases que nos van a  hacer falta -->
<%@ page import="com.svalero.sportsclubapp.dao.Database" %>
<%@ page import="com.svalero.sportsclubapp.dao.UserDao" %>
<%@ page import="com.svalero.sportsclubapp.domain.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<!-- FIN importar las clases que nos van a  hacer falta -->

<jsp:include page="headerAjax.jsp" />

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
                    <a href="addUser.jsp?id_user=<%= user.getIdUser() %>" class="btn btn-outline-warning">Modificar</a>
                    <a href="deleteUser?id_user=<%= user.getIdUser() %>" class="btn btn-outline-danger">Eliminar</a>
                    <%
                        if (user.getCoach().equals("TRUE")) {
                            pagina = "coach.jsp";
                        } else
                            pagina = "addUser.jsp";
                    %>
                    <a href="<%= pagina %>?id_user=<%= user.getIdUser() %>" class="btn btn-outline-info"><% if (user.getCoach().equals("TRUE")) out.print("Entrenador"); else out.print ("Hacer Entrenador"); %> </a>
                </div>
                    <div class="card-footer text-muted">
                    Entrenador:  <strong><%= user.getCoach() %></strong>
                    </div>
                    <div class="card-footer text-muted">
                    <a href="index.jsp" class="btn btn-warning" type="submit">Menu Principal</a>
                    </div>
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