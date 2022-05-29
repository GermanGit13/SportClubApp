<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%@ page import="com.svalero.sportsclubapp.domain.User" %>
<%@ page import="com.svalero.sportsclubapp.dao.Database" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.svalero.sportsclubapp.dao.UserDao" %>

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
    }

    String textButton = "";
    String idUser = request.getParameter("id_user");
    User user = null;
    if (idUser !=null) {
        textButton = "Modificar";
        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        try {
            Optional<User> optionalUser = userDao.findById(Integer.parseInt(idUser));
            user = optionalUser.get();
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
                    $.post("add-modify-user", formValue, function(data) { <!-- servlet que recibe todos los datos del formulario -->
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
                if (idUser !=null) {
                textHead = "Actualizar Datos";
            } else {
                textHead = "Registrar nuevo Usuario";
            }
            %>
        </h2>

        <%-- action es la URL que va a procesar el formulario, post para dar de alta algo a través de un formulario --%>
        <%-- method http que voy a usar para comunicarme con el action   --%>
        <form> <!-- action="addplayer" method="post" -->
            <div class="mb-2">
              <label for="firstname" class="form-label">Nombre: </label>
              <input name="firstname" type="text"  class="form-control w-25" id="firstname" value="<% if (user != null) out.print(user.getFirstName()); %>"> <!-- input name: vital para poder acceder desde java como variables. w-25 anchura de la caja -->
            </div>
            <div class="mb-3">
              <label for="lastname" class="form-label">Apellidos: </label>
              <input name="lastname" type="text" class="form-control w-25" id="lastname" value="<% if (user != null) out.print(user.getLastName()); %>"> <!-- placeholder="Ej: RODRIGUEZ SERRANO" -->
            </div>
            <div class="mb-3">
              <label for="email" class="form-label">Email:</label>
              <input name="email" type="text" class="form-control w-25" id="email" value="<% if (user != null) out.print(user.getEmail()); %>">
            </div>
            <div class="mb-3">
              <label for="dni" class="form-label">DNI:</label>
              <input name="dni" type="text" class="form-control w-25" id="dni" value="<% if (user != null) out.print(user.getDni()); %>">
            </div>
            <div class="mb-3">
                <label for="coach" class="form-label">Entrenador: TRUE si es quieres hacerle que lo sea</label>
                <input name="coach" type="text" class="form-control w-25" id="coach" value="<% if (user != null) out.print(user.getCoach()); %>">
            </div>

            <input type="hidden" name="action" value="<% if (user != null) out.print("Actualizar Perfil"); else out.print("register"); %>">
            <input type="hidden" name="idUser" value="<% if (user != null) out.print(user.getIdUser()); %>">
            <button type="submit" class="btn btn-dark"><%= textButton %></button> <!-- Variable para que en función del if declarado arriba aparezca registrar o modificar -->
            <a href="index.jsp" class="btn btn-warning" type="submit">Menu Principal</a>
        </form>
        <div id="result"></div> <!-- Pinta el resultado del envio asincrono con AJAX -->
    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>
