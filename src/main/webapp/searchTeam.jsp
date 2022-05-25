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
                    $.post("search-team", formValue, function(data) { <!-- servlet que recibe todos los datos del formulario -->
                        $("#result").html(data); <!-- Lo usamos para enviar la respuesta al div en la misma página -->
                    });
                });
            });
    </script>
    <!-- FIN Código para enviar el formulario de forma asíncrona -->

    <div class="container">
        <h2>Buscar Equipos</h2>
        <form>
            <div class="mb-2">
              <label for="searchtext" class="form-label">Cadena de texto a buscar</label>
              <input name="searchtext" type="text"  class="form-control w-25" id="searchtext"> <!-- input name: vital para poder acceder desde java como variables. w-25 anchura de la caja -->
            </div>
            <button type="submit" class="btn btn-dark">Buscar</button>
            <a href="index.jsp" class="btn btn-warning" type="submit">Menu Principal</a>
        </form>
        <div id="result"></div> <!-- Pinta el resultado del envio asincrono con AJAX -->
    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>