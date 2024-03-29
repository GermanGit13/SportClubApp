<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%@ page import="com.svalero.sportsclubapp.domain.User" %>
<%@ page import="com.svalero.sportsclubapp.domain.Team" %>
<%@ page import="com.svalero.sportsclubapp.dao.Database" %>
<%@ page import="com.svalero.sportsclubapp.dao.TeamDao" %>
<%@ page import="com.svalero.sportsclubapp.dao.UserDao" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
    }

    String textButton = "";
    String textHead = "";
    String idTeam = request.getParameter("id_team"); //RECOGEMOS EL IDTEAM DEL NAME
    Team team = null;
    if (idTeam != null) {
        textButton = "Modificar";
        textHead = "Modificar Equipo";
        Database database = new Database();
        TeamDao teamDao = new TeamDao(database.getConnection());
        try {
            Optional<Team> optionalTeam = teamDao.findById(Integer.parseInt(idTeam));
            team = optionalTeam.get();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    } else {
        textButton = "Registrar";
        textHead = "Registra Equipo";
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
                    $.post("add-modify-team", formValue, function(data) { <!-- servlet que recibe todos los datos del formulario -->
                        $("#result").html(data); <!-- Lo usamos para enviar la respuesta al div en la misma página -->
                    });
                });
            });
    </script>
    <!-- FIN Código para enviar el formulario de forma asíncrona -->

    <div class="container">
        <h2><%= textHead %></h2>

        <%-- action es la URL que va a procesar el formulario, post para dar de alta algo a través de un formulario --%>
        <%-- method http que voy a usar para comunicarme con el action   --%>
        <form>
            <div class="mb-2">
              <label for="name" class="form-label">Nombre del Equipo</label>
              <input name="name" type="text"  class="form-control w-25" id="name" value="<% if (team != null) out.print(team.getName()); %>"> <!-- input name: vital para poder acceder desde java como variables. w-25 anchura de la caja. value que asigne los datos se lleva el objeto Team -->
            </div>
            <div class="mb-3">
              <label for="category" class="form-label">Categoría del Equipo</label>
              <input name="category" type="text" class="form-control w-25" id="category" value="<%if (team != null) out.print(team.getCategory()); %>"> <!-- placeholder="Ej: INFANTIL" -->
            </div>

            <div class="form-label">
                <label for="idCoach">(Opcional): Entrenador </label>
                <select class="form-control w-25" id="idCoach" name="idCoach">
                    <option> Entrenadores </option>
                    <%
                        String searchCoach = "TRUE";
                        Database databaseUser = new Database();
                        UserDao userDao = new UserDao(databaseUser.getConnection());
                        ArrayList<User> users = userDao.findAllCoach(searchCoach);
                        for (User user : users) {
                            out.println("<option value=\"" + user.getIdUser() + "\">" + user.getFirstName() + "\">" + user.getLastName() + "</option>");
                        }
                    %>
                </select>
            </div>

            <input type="hidden" name="action" value="<% if (team != null) out.print("modify"); else out.print("register"); %>"> <!-- Para que vaya a modificar o crear nuevo -->
            <input type="hidden" name="idTeam" value="<% if (team != null) out.print(team.getIdTeam()); %>"> <!-- Para que vaya a modificar o crear nuevo -->
            <button type="submit" class="btn btn-dark"><%= textButton %></button> <!-- Variable para que en función del if declarado arriba aparezca registrar o modificar -->
        </form>
        <div id="result"></div> <!-- Pinta el resultado del envio asincrono con AJAX -->
        <a href="index.jsp" class="btn btn-warning" type="submit">Menu Principal</a>
    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>
