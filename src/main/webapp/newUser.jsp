<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%@ page import="com.svalero.sportsclubapp.domain.User" %>

<jsp:include page="headerAjax.jsp" />

<body>
    <!-- Código para enviar el formulario de forma asíncrona -->
    <script type="text/javascript">
            $(document).ready(function() {
                $("form").on("submit", function(event) {
                    event.preventDefault();
                    var formValue = $(this).serialize();
                    $.post("new-user", formValue, function(data) { <!-- servlet que recibe todos los datos del formulario -->
                        $("#result").html(data); <!-- Lo usamos para enviar la respuesta al div en la misma página -->
                    });
                });
            });
    </script>
    <!-- FIN Código para enviar el formulario de forma asíncrona -->

    <div class="container">
        <h2>Date de alta en la Web</h2>
        <%-- action es la URL que va a procesar el formulario, post para dar de alta algo a través de un formulario --%>
        <%-- method http que voy a usar para comunicarme con el action   --%>
        <form> <!-- action="addUser" method="post" -->
            <div class="mb-2">
              <label for="firstname" class="form-label">Nombre</label>
              <input name="firstname" type="text"  class="form-control w-25" id="firstname"> <!-- input name: vital para poder acceder desde java como variables. w-25 anchura de la caja -->
            </div>
            <div class="mb-3">
              <label for="lastname" class="form-label">Apellidos</label>
              <input name="lastname" type="text" class="form-control w-25" id="lastname"> <!-- placeholder="Ej: RODRIGUEZ SERRANO" -->
            </div>
            <div class="mb-3">
              <label for="email" class="form-label">Email</label>
              <input name="email" type="text" class="form-control w-25" id="email">
            </div>
            <div class="mb-3">
              <label for="dni" class="form-label">DNI</label>
              <input name="dni" type="text" class="form-control w-25" id="dni">
            </div>
            <div class="mb-3">
              <label for="username" class="form-label">Usuario</label>
              <input name="username" type="text" class="form-control w-25" id="username">
            </div>
            <div class="mb-3">
                <label for="pass" class="form-label">Contraseña</label>
                <input name="pass" type="text" class="form-control w-25" id=pass>
            </div>

            <button type="submit" class="btn btn-primary">REGISTRAR</button>
            <a href="login.jsp" class="btn btn-primary">INICIAR SESION</a>
        </form>
        <div id="result"></div> <!-- Pinta el resultado del envio asincrono con AJAX -->
    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>
