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
