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
<%@ page import="com.svalero.sportsclubapp.dao.UserDao" %>
<%@ page import="com.svalero.sportsclubapp.domain.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<!-- FIN importar las clases que nos van a  hacer falta -->

<jsp:include page="headerAjax.jsp" />

<body>
    <div class="container"> <!-- Para que quede centrada la web gracias a la hoja de estilo de Bootstrap -->
        <h2>Listado Completo de Usuarios</h2>
        <ul class="list-group">
          <%
            Database database = new Database(); //creamos la conexión con la BBDD
            UserDao userDao = new UserDao(database.getConnection()); //Creamos un userDao y le pasamos la conexion
            try {
                List<User> users = userDao.findAll();
                for (User user: users) {
          %>
                    <li class="list-group-item d-flex justify-content-between align-items-start w-50">
                        <div class="ms-2 me-auto">
                          <div class="fw-bold"><a target="_blank" href="user.jsp?id_user=<%= user.getIdUser() %>">Nombre: <%= user.getFirstName() %></a></div> <!-- target="_blank" para abrir una pestaña nueva en el enlace -->
                          <div class="fw-bold">Apellidos:</div><%= user.getLastName() %>
                          <div class=>Correo: <%= user.getEmail() %></div>
                          <div class=>Dni: <%= user.getDni() %></div>
                          <div class=>Username: <%= user.getUsername() %></div>
                        </div>

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