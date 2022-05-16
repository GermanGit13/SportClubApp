<%
    int idTeam = Integer.parseInt(request.getParameter("idTeam"));
    out.println("<p>Consultando el equipo con id " + idTeam);
    // Conecto con la base de datos
    // ....
%>
<p>Datos del Equipo <%= idTeam %></p>