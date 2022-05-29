<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%@ page import="com.svalero.sportsclubapp.domain.User" %>
<%@ page import="com.svalero.sportsclubapp.dao.Database" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.svalero.sportsclubapp.domain.Player" %>
<%@ page import="com.svalero.sportsclubapp.dao.PlayerDao" %>

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
    }

    String textButton = "";
    String idPlayer = request.getParameter("id_player");
    Player player = null;
    if (idPlayer !=null) {
        textButton = "Modificar";
        Database database = new Database();
        PlayerDao playerDao = new PlayerDao(database.getConnection());
        try {
            Optional<Player> optionalPlayer = playerDao.findById(Integer.parseInt(idPlayer));
            player = optionalPlayer.get();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    } else {
        textButton = "Registrar";
    }
%>

<jsp:include page="headerAjax.jsp" />

<body>
    <!-- Código para enviar el formulario de forma asíncrona -->
    <script type="text/javascript">
            $(document).ready(function() {
                $("form").on("submit", function(event) {
                    event.preventDefault();
                    var formValue = $(this).serialize();
                    $.post("add-modify-player", formValue, function(data) { <!-- servlet que recibe todos los datos del formulario -->
                        $("#result").html(data); <!-- Lo usamos para enviar la respuesta al div en la misma página -->
                    });
                });
            });
    </script>
    <!-- FIN Código para enviar el formulario de forma asíncrona -->

    <div class="container">
        <h2>
            <%
                String textHead = "";
                if (idPlayer !=null) {
                textHead = "Modificar";
            } else {
                textHead = "Registrar nuevo Usuario";
            }
            %>
        </h2>

        <%-- action es la URL que va a procesar el formulario, post para dar de alta algo a través de un formulario --%>
        <%-- method http que voy a usar para comunicarme con el action   --%>
        <form> <!-- action="addplayer" method="post" -->
            <div class="mb-2">
              <label for="firstname" class="form-label">Nombre del Jugador</label>
              <input name="firstname" type="text"  class="form-control w-25" id="firstname" value="<% if (player != null) out.print(player.getFirstName()); %>"> <!-- input name: vital para poder acceder desde java como variables. w-25 anchura de la caja -->
            </div>
            <div class="mb-3">
              <label for="lastname" class="form-label">Apellidos</label>
              <input name="lastname" type="text" class="form-control w-25" id="lastname" value="<% if (player != null) out.print(player.getLastName()); %>"> <!-- placeholder="Ej: RODRIGUEZ SERRANO" -->
            </div>
            <div class="mb-3">
              <label for="numbers" class="form-label">Dorsal si tiene del año pasado</label>
              <input name="numbers" type="text" class="form-control w-25" id="numbers" value="<% if (player != null) out.print(player.getNumber()); %>">
            </div>
            <div class="mb-3">
              <label for="yearOfBirth" class="form-label">Año de Nacimiento</label>
              <input name="yearOfBirth" type="text" class="form-control w-25" id="yearOfBirth" value="<% if (player != null) out.print(player.getYearOfBirth()); %>">
            </div>
            <div class="mb-3">
              <label for="dni" class="form-label">DNI</label>
              <input name="dni" type="text" class="form-control w-25" id="dni" value="<% if (player != null) out.print(player.getDni()); %>">
            </div>

            <input type="hidden" name="action" value="<% if (player != null) out.print("modify"); else out.print("register"); %>">
            <input type="hidden" name="idPlayer" value="<% if (player != null) out.print(player.getIdPlayer()); %>">
            <input type="hidden" name="idUser" value="<% if (player != null) out.print(player.getIdUser()); %>">
            <button type="submit" class="btn btn-dark"><%= textButton %></button>
            <a href="index.jsp" class="btn btn-warning" type="submit">Menu Principal</a>
        </form>
        <div id="result"></div> <!-- Pinta el resultado del envio asincrono con AJAX -->
    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>
