<!-- Para que decodifique bien los acentos, etc.. -->
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<!-- FIN Para que decodifique bien los acentos, etc..  -->

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.98.0">
    <title>Login WebApp Deportiva</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/sign-in/">

    <!-- Para usar la hoja de estilos de  Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!-- FIN Bootstrap -->
    <meta name="theme-color" content="#7952b3">

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

      .b-example-divider {
        height: 3rem;
        background-color: rgba(0, 0, 0, .1);
        border: solid rgba(0, 0, 0, .15);
        border-width: 1px 0;
        box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
      }
      .b-example-vr {
        flex-shrink: 0;
        width: 1.5rem;
        height: 100vh;
      }
      .bi {
        vertical-align: -.125em;
        fill: currentColor;
      }
      .nav-scroller {
        position: relative;
        z-index: 2;
        height: 2.75rem;
        overflow-y: hidden;
      }
      .nav-scroller .nav {
        display: flex;
        flex-wrap: nowrap;
        padding-bottom: 1rem;
        margin-top: -1px;
        overflow-x: auto;
        text-align: center;
        white-space: nowrap;
        -webkit-overflow-scrolling: touch;
      }
    </style>

    <link href="css/login.css" rel="stylesheet"> <!-- Ruta donde hemos creado el css en el proyecto -->
  </head>

  <body class="text-center">

  <main class="form-signin w-100 m-auto">

  <form action="login" method="post"> <!-- Para enviar el formulario -->
    <img class="mb-4" src="images/logo.png"> <!-- Podemos asignarle un tamaño alt="" width="72" height="57" si queremos -->
    <h1 class="h3 mb-3 fw-normal">INGRESA TUS DATOS PARA ENTRAR</h1>

    <div class="form-floating">
      <input type="text" name="username" class="form-control" id="floatingInput" placeholder="Usuario" value="JSR"> <!-- name: vital para poder acceder desde java como variables -->
      <label for="floatingInput">Usuario</label>
    </div>
    <div class="form-floating">
      <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Contraseña" value="1234"> <!-- name: vital para poder acceder desde java como variables -->
      <label for="floatingPassword">Contraseña</label>
    </div>


    <button class="btn btn-dark" type="submit">ENTRAR</button>
    <a href="newUser.jsp" class="btn btn-warning" type="submit">REGISTRARSE</a>
    <p class="mt-5 mb-3 text-muted">Cb Smv 2022</p>
  </form>

</main>

</body>
</html>
