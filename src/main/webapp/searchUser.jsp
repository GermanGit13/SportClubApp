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

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- Para usar la librería de Ajax en los formularios  -->
</head>

<body>
    <!-- Código para enviar el formulario de forma asíncrona -->
    <script type="text/javascript">
            $(document).ready(function() {
                $("form").on("submit", function(event) {
                    event.preventDefault();
                    var formValue = $(this).serialize();
                    $.post("search-user", formValue, function(data) { <!-- servlet que recibe todos los datos del formulario -->
                        $("#result").html(data); <!-- Lo usamos para enviar la respuesta al div en la misma página -->
                    });
                });
            });
    </script>
    <!-- FIN Código para enviar el formulario de forma asíncrona -->

    <div class="container">
        <h2>Buscar Usuarios</h2>
        <form>
            <div class="mb-2">
              <label for="searchtext" class="form-label">Cadena de texto a buscar</label>
              <input name="searchtext" type="text"  class="form-control w-25" id="searchtext"> <!-- input name: vital para poder acceder desde java como variables. w-25 anchura de la caja -->
            </div>
            <button type="submit" class="btn btn-primary">Buscar</button>
        </form>
        <div id="result"></div> <!-- Pinta el resultado del envio asincrono con AJAX -->
    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>