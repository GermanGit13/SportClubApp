<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
    }
%>

<!-- Para importar las clases que nos van a  hacer falta -->
<%@ page import="com.svalero.sportsclubapp.dao.Database" %>
<%@ page import="com.svalero.sportsclubapp.dao.TeamDao" %>
<%@ page import="com.svalero.sportsclubapp.domain.Team" %>
<%@ page import="com.svalero.sportsclubapp.domain.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<!-- FIN importar las clases que nos van a  hacer falta -->

<jsp:include page="header.jsp" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- Para usar la librería de Ajax en los formularios  -->

<body>
        <script type="text/javascript">
            $(document).ready(function () {
                $("form").on("submit", function (event) {
                    event.preventDefault();
                    var formValue = $(this).serialize();
                    $.post("delete-team", formValue, function (data) {
                        $("#result").html(data);
                    });
                });
            });
        </script>
        <%
            String idTeam = request.getParameter("id_team");
            Database database = new Database();
            TeamDao teamDao = new TeamDao((database.getConnection()));
            Team team = null;
            try {
                Optional<Team> optionalTeam = teamDao.findById(Integer.parseInt(idTeam));
                team = optionalTeam.get();
        %>
        <div class="container">
            <h2>Eliminar Equipo</h2>
            <div class="card text-center">
                <div class="card-header">¿Estás seguro que quieres eliminar el equipo?</div>
                <div class="card-body">
                    <a href="deleteTeam?id_team=<%= team.getIdTeam() %>" class="btn btn-danger">Si</a>
                    <a href="showTeams.jsp" class="btn btn-outline-danger">No</a>
                </div>
            </div>
        </div>
        <div id="result"></div>
        </div>
        <%
        } catch (SQLException sqle) {
        %>
        <div class='alert alert-danger' role='alert'>Se ha producido un error. intentalo más tarde</div>
        <%
            }
        %>
</body>
</html>