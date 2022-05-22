<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"

    import="com.svalero.sportsclubapp.domain.User"
%>

<!-- Recuperamos la sesion y si es null lo redirect a login.jsp -->
<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
    }
%>
<!-- FIN Recuperamos la sesion y si es null lo redirect a login.jsp -->

<jsp:include page="header.jsp" />

<body>

    <div class="container"> <!-- Para que quede centrada la web gracias a la hoja de estilo de Bootstrap -->
        <div class="alert alert-warning" role="alert">
        <h2>WebApp Para clubs deportivos</h2>
          <h4 class="alert-heading">!!!!BIENVENIDO!!!! <% if (currentUser != null) out.print(currentUser.getFirstName()); %></h4>
          <p>GESTIONA TUS EQUIPOS - JUGADORES - ROPA DEPORTIVA</p>
          <hr>
          <a href="" class="btn btn-outline-dark">Modificar Perfil PENDIENTE</a>
          <a href="logout" class="btn btn-outline-danger">Cerrar Sesión</a>
        </div>

            <div class="container">
              <div class="row g-2">
        <%
            if ((currentUser !=null && (currentUser.getCoach().equals("TRUE")))) {
        %>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="addTeam.jsp">Añadir un Equipo</a></div> <!-- Referencia a JSP -->
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="showTeams.jsp">Listado de Equipos</a></p></div>
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="showUsers.jsp">Listado de Usuarios</a></p></div>
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="showPlayers.jsp">Listado de Jugadores</a></p></div>
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="addTeam.jsp">Listar Pedidos PENDIENTE</a></p></div>
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="searchPlayer.jsp">Buscador de Jugadores</a></p></div>
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="searchTeam.jsp">Buscador de Equipos</a></p></div>
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="searchTeam.jsp">Buscador de Equipos</a></p></div>
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="searchUser.jsp">Buscador de Usuarios</a></p></div>
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="addTeam.jsp">Buscar un pedido PENDIENTE</a></p></div>
                </div>
        <%
            }
        %>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="addPlayer.jsp">Alta jugador</a></p></div>
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="addTeam.jsp">Crear Pedido PENDIENTE</a></p></div>
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="addTeam.jsp">Mis Jugadores PENDIENTE</a></p></div>
                </div>
                <div class="col-6">
                  <div class="p-3 border bg-light"><p><a href="addTeam.jsp">Mis Pedidos PENDIENTE</a></p></div>
                </div>
              </div>
            </div>

    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>
