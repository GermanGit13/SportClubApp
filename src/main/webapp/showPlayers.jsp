<!-- Para importar las clases que nos van a  hacer falta -->
<%@ page import="com.svalero.sportsclubapp.dao.Database" %>
<%@ page import="com.svalero.sportsclubapp.dao.PlayerDao" %>
<%@ page import="com.svalero.sportsclubapp.domain.Player" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<!-- FIN importar las clases que nos van a  hacer falta -->

<html>
<head>
    <!-- Para usar la hoja de estilos de  Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!-- FIN Bootstrap -->
</head>

<body>
    <div class="container"> <!-- Para que quede centrada la web gracias a la hoja de estilo de Bootstrap -->
        <h2>Listado Completo de Jugadores</h2>
        <ul class="list-group">
          <%
          Database database = new Database(); //creamos la conexión con la BBDD
          PlayerDao playerDao = new PlayerDao(database.getConnection()); //Creamos un playerdao y le pasamos la conexion
          try {
              List<Player> players = playerDao.findAll();
              for (Player player: players) {
          %>
              <li class="list-group-item d-flex justify-content-between align-items-start w-50">
                  <div class="ms-2 me-auto">
                    <div class="fw-bold"><a href="player.jsp?id_player=<%= player.getIdPlayer() %>">Nombre: <%= player.getFirstName() %></a></div>
                    <div class="fw-bold">Apellidos:</div><%= player.getLastName() %>
                    <div class=>Año Nacimiento: <%= player.getYearOfBirth() %></div>
                    <div class=>Dni: <%= player.getDni() %></div>
                  </div>

              </li>
            <%
            }
          } catch (SQLException sqle) {
            %>
              <div class="alert alert-danger" role="alert">
                Error al conectar con la BBDD
              </div>
        <%
          }
        %>
          </ul>
    </div> <!-- Fin del container de Bootstrap -->
</body>
</html>