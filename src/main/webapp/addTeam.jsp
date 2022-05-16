<html>
<body>
<h2>Registra un nuevo Equipo</h2>
<%-- action es la URL que va a procesar el formulario, post para dar de alta algo a través de un formulario --%>
<%-- method http que voy a usar para comunicarme con el action   --%>
<form action="addteam" method="post">
    Nombre:
    <input type="text" name="name"/><br/>
    Categoria:
    <input type="text" name="category"/><br/>
    <input type="submit" value="Registrar"/><br/>
</form>
</body>
</html>
