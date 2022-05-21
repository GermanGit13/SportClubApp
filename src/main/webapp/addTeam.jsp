<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%@ page import="com.svalero.sportsclubapp.domain.User" %>

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
                    $.post("addteam", formValue, function(data) { <!-- servlet que recibe todos los datos del formulario -->
                        $("#result").html(data); <!-- Lo usamos para enviar la respuesta al div en la misma página -->
                    });
                });
            });
    </script>
    <!-- FIN Código para enviar el formulario de forma asíncrona -->

    <div class="container">
        <h2>Registra un nuevo Equipo</h2>
        <%-- action es la URL que va a procesar el formulario, post para dar de alta algo a través de un formulario --%>
        <%-- method http que voy a usar para comunicarme con el action   --%>
        <form action="addteam" method="post">
            <div class="mb-2">
              <label for="name" class="form-label">Nombre del Equipo</label>
              <input name="name" type="text"  class="form-control w-25" id="name" placeholder="Ej: Cb Smv"> <!-- input name: vital para poder acceder desde java como variables. w-25 anchura de la caja -->
            </div>
            <div class="mb-3">
              <label for="category" class="form-label">Categoria del Equipo</label>
              <input name="category" type="text" class="form-control w-25" id="category" placeholder="Ej: INFANTIL">
            </div>
            <button type="submit" class="btn btn-primary">REGISTRAR</button>
        </form>
        <div id="result"></div> <!-- Pinta el resultado del envio asincrono con AJAX -->
    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>
