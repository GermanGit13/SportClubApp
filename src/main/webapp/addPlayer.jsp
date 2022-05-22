<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%@ page import="com.svalero.sportsclubapp.domain.User" %>
<%@ page import="com.svalero.sportsclubapp.domain.Player" %>
<%@ page import="com.svalero.sportsclubapp.domain.Team" %>

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
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
                    $.post("addplayer", formValue, function(data) { <!-- servlet que recibe todos los datos del formulario -->
                        $("#result").html(data); <!-- Lo usamos para enviar la respuesta al div en la misma página -->
                    });
                });
            });
    </script>
    <!-- FIN Código para enviar el formulario de forma asíncrona -->

    <div class="container">
        <h2>Registra un nuevo Jugador</h2>
        <%-- action es la URL que va a procesar el formulario, post para dar de alta algo a través de un formulario --%>
        <%-- method http que voy a usar para comunicarme con el action   --%>
        <form action="addplayer" method="post">
            <div class="mb-2">
              <label for="firstname" class="form-label">Nombre del Jugador</label>
              <input name="firstname" type="text"  class="form-control w-25" id="firstname" placeholder="Ej: GERMAN"> <!-- input name: vital para poder acceder desde java como variables. w-25 anchura de la caja -->
            </div>
            <div class="mb-3">
              <label for="lastname" class="form-label">Apellidos</label>
              <input name="lastname" type="text" class="form-control w-25" id="lastname" placeholder="Ej: RODRIGUEZ SERRANO">
            </div>
            <div class="mb-3">
              <label for="numbers" class="form-label">Dorsal si tiene del año pasado</label>
              <input name="numbers" type="text" class="form-control w-25" id="numbers" placeholder="Ej: 13">
            </div>
            <div class="mb-3">
              <label for="yearOfBirth" class="form-label">Año de Nacimiento</label>
              <input name="yearOfBirth" type="text" class="form-control w-25" id="yearOfBirth" placeholder="Ej: 1981">
            </div>
            <div class="mb-3">
              <label for="dni" class="form-label">Apellidos</label>
              <input name="dni" type="text" class="form-control w-25" id="dni" placeholder="Ej: 50979686W">
            </div>

            <button type="submit" class="btn btn-primary">DAR DE ALTA</button>
        </form>
        <div id="result"></div> <!-- Pinta el resultado del envio asincrono con AJAX -->
    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>
