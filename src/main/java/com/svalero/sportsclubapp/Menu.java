package com.svalero.sportsclubapp;


import com.svalero.sportsclubapp.dao.*;
import com.svalero.sportsclubapp.domain.*;
import com.svalero.sportsclubapp.exception.DniAlredyExistException;
import com.svalero.sportsclubapp.exception.TeamAlreadyExistException;
import com.svalero.sportsclubapp.exception.UserAlredyExistException;
import com.svalero.sportsclubapp.exception.UserNotFoundException;
import com.svalero.sportsclubapp.util.Constants;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private Scanner keyboard;
    private Database database;
    private Connection connection;
    private User currentUser;


    private List<Player> catalagoPlayer;
    private List<Team> catalogoTeam;
    private List<Clothing> catalogoClothing;

    public Menu() {
        keyboard = new Scanner(System.in);
        catalagoPlayer = new ArrayList<>();
        catalogoTeam = new ArrayList<>();
        catalogoClothing = new ArrayList<>();
    }

    //MÉTODO PARA CONECTAR CON LA BBDD
    public void connect() {
        database = new Database();
        connection = database.getConnection();
    }

    public void showMenu() {
        //NADA MÁS ARRANCAR MENU CONECTAMOS CON LA BBDD MEDIANTE MÉTODO ANTERIOR connect
        connect();
        login();

        String choice = null;

        do {
            System.out.println("Aplicación de Baloncesto: ");
            System.out.println("1. Registrar nuevo Usuario: ");
            System.out.println("2. Registrar Equipo: ");
            System.out.println("3. Registrar Jugador: ");
            System.out.println("4. Registrar Pedido Ropa: ");
            System.out.println("5. Asignar Jugador a Equipo: ");
            System.out.println("6. Asignar Entrenador: ");
            System.out.println("7. Modificar Equipo: ");
            System.out.println("8. Modificar Jugador: ");
            System.out.println("9. Modificar Pedido: ");
            System.out.println("10. Mostrar Usuario: ");
            System.out.println("11. Mostrar Equipos: ");
            System.out.println("12. Mostrar Equipos con jugadores: ");
            System.out.println("13. Mostrar Equipo en categoría: ");
            System.out.println("14. Mostrar Jugadores: ");
            System.out.println("15. Mostrar Pedidos: ");
            System.out.println("16. Mostrar Pedidos Usuario: ");
            System.out.println("17. Marcar Pagado Pedido: ");
            System.out.println("18. Borrar Jugador: ");
            System.out.println("19. Borrar Equipo: ");
            System.out.println("20. Borrar Pedido: ");
            System.out.println("21. Borrar Usuario: ");
            System.out.println("s. Salir");
            System.out.println("Opción: ");
            choice = keyboard.nextLine();

            switch (choice) {
                case "1":
                    addUser();
                    break;
                case "2":
                    addTeam();
                    break;
                case "3":
                    addPlayer();
                    break;
                case "4":
                    addClothing();
                    break;
                case "5":
                    assignTeam();
                    break;
                case "6":
                    //assignCoach);
                    break;
                case "7":
                    modifyTeam();
                    break;
                case "8":
                    modifyPlayer();
                    break;
                case "9":
                    //modifyClothing(); //MODIFICAR PEDIDO
                    break;
                case "10":
                    //showUser();
                    break;
                case "11":
                    showTeam();
                    break;
                case "12":
                    //showTeamPlayer();
                    break;
                case "13":
                    //showTeamCategory();
                    break;
                case "14":
                    showPlayer();
                    break;
                case "15":
                    addOrder();
                    break;
                case "16":
                    showOrderDetails();
                    break;
                case "17":
                    //payOrder();
                    break;
                case "18":
                    //deletePlayer();
                    break;
                case "19":
                    //deleteTeam();
                    break;
                case "20":
                    //deleteOrder();
                    break;
                case "21":
                    //deleteUser();
                    break;

            }
        } while (!choice.equals("s"));
    }

    private void login() {
        System.out.print("¿Cuál es tu usuario? ");
        String username = keyboard.nextLine();
        System.out.print("¿Cuál es tu contraseña? ");
        String password = keyboard.nextLine();

        UserDao userDao = new UserDao(connection);
        try {
            User user = userDao.getUser(username, password) //REVISAMOS SI EXISTE EL USUARIO CON LOS DATOS INTRODUCIDOS
                    .orElseThrow(UserNotFoundException::new); //SINO LANZAMOS LA EXCEPCIÓN QUE NO EXISTE
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
            System.exit(0); //SI NO SE PUEDE CONECTAR LE ECHAMOS DE LA APP
        } catch (UserNotFoundException unfe) {
            System.out.println(unfe.getMessage());
            System.exit(0); //SI EL USUARIO NO EXISTE LE ECHAMOS DE LA APP
        }
    }

    public void addUser() {
        UserDao userDao = new UserDao(connection);
        String username;

        System.out.println("Escribe tú nombre: ");
        String firstName = keyboard.nextLine();
        System.out.println("Escribe tu apellido: ");
        String lastName = keyboard.nextLine();
        System.out.println("Escribe tu email: ");
        String email = keyboard.nextLine();
        System.out.println("Escribe tu DNI: ");
        String dni = keyboard.nextLine();
        dni.toUpperCase();//NOS ASEGURAMOS QUE TODAS LAS LETRAS DEL DNI ESTARÁN EN MAYÚSCULAS
        do {
            System.out.println("Nombre de usuario: ");
            username = keyboard.nextLine();
            if (searchUsername(username)) {
                System.out.println("El usuario ya existe: ");
            } else {
                System.out.println("El usuario no existe en la BBDD: ");
            }
        } while (!searchUsername(username));
        System.out.println("Escribe tu contraseña: ");
        String password = keyboard.nextLine();
        User user = new User(firstName.trim(), lastName.trim(), email.trim(), dni.trim(), username.trim(), password.trim());

        try {
            userDao.add(user);
            System.out.println("Se ha registrado un nuevo equipo correctamente.");
        } catch (UserAlredyExistException uaee) {
            System.out.println(uaee.getMessage()); //RECOGE EL MENSAJE DE LA EXCEPCIÓN PERSONALIZADA
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCIÓN Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        }
    }

    private void addTeam() {
        //PARA DARLO DE ALTA EN LA BBDD CON EL DAO
        TeamDao teamDao = new TeamDao(connection);

        System.out.print("Introduzca nombre del equipo: ");
        String name = keyboard.nextLine();
        System.out.print("Introduzca la categoría equipo: ");
        String category = keyboard.nextLine();
        //TODO FALTA ENTRENADOR
        //System.out.print("Introduzca el entrenador del equipo: ");
        //String coach = keyboard.nextLine();
        //CREAMOS EL OBJETO TEAM CON LOS DATOS INTRODUCIDOS POR KEYBOARD
        Team team = new Team(name.trim(), category.trim(), Constants.QUOTA);

        //CONTROLAMOS LAS EXCEPCIONES QUE NO MANDAN DESDE EL TEAMDAO
        try {
            teamDao.add(team);
            System.out.println("Se ha registrado un nuevo equipo correctamente.");
        } catch (TeamAlreadyExistException taee) {
            System.out.println(taee.getMessage()); //RECOGE EL MENSAJE DE LA EXCEPCIÓN PERSONALIZADA
        } catch (SQLException sqle){
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCIÓN Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        }
    }

    private void addPlayer() {
        //PARA DARLO DE ALTA EN LA BBDD CON EL DAO
        PlayerDao playerDao = new PlayerDao(connection);

        System.out.print("Introduzca nombre del Jugador: ");
        String firstName = keyboard.nextLine();
        System.out.print("Introduzca apellidos del Jugador: ");
        String lastName = keyboard.nextLine();
        System.out.print("Introduzca el número de dorsal del Jugador (si disponía del año pasado): ");
        int number = keyboard.nextInt();
        System.out.print("Introduzca la fecha de nacimiento del Jugador: ");
        int yearOfBirth = keyboard.nextInt();
        System.out.print("Introduzca el DNI del Jugador: ");
        String dni = keyboard.nextLine();
        dni.toUpperCase();//NOS ASEGURAMOS QUE TODAS LAS LETRAS DEL DNI ESTARÁN EN MAYÚSCULAS
        //CREAMOS EL OBJETO PLAYER CON LOS DATOS INTRODUCIDOS POR KEYBOARD
        Player player = new Player(firstName.trim(), lastName.trim(), number, yearOfBirth, dni.trim());

        //CONTROLAMOS LAS EXCEPCIONES QUE NO MANDAN DESDE EL TEAMDAO
        try {
            playerDao.add(player);
            System.out.println("Se ha registrado un nuevo jugador correctamente.");

        } catch (DniAlredyExistException daee) {
            System.out.println(daee.getMessage()); //RECOGE EL MENSAJE DE LA EXCEPCIÓN PERSONALIZADA
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCIÓN Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        }
    }

    private void addClothing() {
        ClothingDao clothingDao = new ClothingDao(connection);

        System.out.print("Introduce tu DNI del jugador:");
        String dni = keyboard.nextLine();
        dni.toUpperCase();//NOS ASEGURAMOS QUE TODAS LAS LETRAS DEL DNI ESTARÁN EN MAYÚSCULAS
        System.out.print("Introduzca la serigrafía que llevará la equipación:");
        String serigraphy = keyboard.nextLine();
        System.out.print("Introduzca el número de dorsal del Jugador: ");
        int number = keyboard.nextInt();
        System.out.print("Introduzca la talla del Jugador: ");
        String size = keyboard.nextLine();
        //CREAMOS EL OBJETO PLAYER CON LOS DATOS INTRODUCIDOS POR KEYBOARD
        Clothing clothing = new Clothing(dni.trim(), serigraphy.trim(), number, size.trim(), Constants.PRICE);

        try {
            clothingDao.add(clothing);
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCIÓN Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        }
    }

    private void assignTeam() {
        PlayerDao playerDao = new PlayerDao(connection);
        TeamDao teamDao = new TeamDao(connection);
        //boolean assign = false;

        System.out.println("Introduzca el DNI del jugador para asignar el equipo");
        String dni = keyboard.nextLine();
        dni.toUpperCase();//NOS ASEGURAMOS QUE TODAS LAS LETRAS DEL DNI ESTARÁN EN MAYÚSCULAS
        System.out.println("Cual es su categoría: ");
        String searchCategory = keyboard.nextLine();
        System.out.println("Cual es el nombre del equipo: ");
        String searchName = keyboard.nextLine();

        try {
            Team team = teamDao.findByTeamAndCategory(searchName, searchCategory);
            Player player = playerDao.findByDni(dni);
            playerDao.addPlayerTeam(player, team);
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCIÓN Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        } catch (DniAlredyExistException daee) {
            System.out.println(daee.getMessage());
        }
    }

    private void modifyTeam() {
        //PARA DARLO DE ALTA EN LA BBDD CON EL DAO
        TeamDao teamDao = new TeamDao(connection);

        System.out.println("Introduzca el nombre de equipo a modificar: ");
        String nameTeam = keyboard.nextLine();
        System.out.println(("Introduzca la categoría del equipo"));
        String categoryTeam = keyboard.next();
        //TODO BUSCAR EL EQUIPO ANTES DE MODIFICARLO
        System.out.print("Introduzca nuevo nombre del equipo: ");
        String newName = keyboard.nextLine();
        System.out.print("Introduzca la categoría equipo: ");
        String newCategory = keyboard.nextLine();
        //TODO FALTA ENTRENADOR
        //System.out.print("Introduzca el entrenador del equipo: ");
        //String coach = keyboard.nextLine();
        //CREAMOS EL OBJETO TEAM CON LOS DATOS INTRODUCIDOS POR KEYBOARD
        Team newTeam = new Team(newName.trim(), newCategory.trim(), Constants.QUOTA);

        try {
            boolean modified = teamDao.modify(nameTeam, categoryTeam, newTeam);
            if (modified)
                System.out.println("Equipo modificado correctamente");
            else
                System.out.println("Equipo NO modificado o NO existe");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCIÓN Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        }
    }

    private void modifyPlayer() {
        PlayerDao playerDao = new PlayerDao(connection);
        Player player = new Player();

        System.out.println("Introduzca el dni del Jugador a modificar: ");
        String dniPlayer = keyboard.nextLine();
        dniPlayer.toUpperCase();//NOS ASEGURAMOS QUE TODAS LAS LETRAS DEL DNI ESTARÁN EN MAYÚSCULAS
        addPlayer();

        try {
            playerDao.findByDni(dniPlayer);

            boolean modified = playerDao.modify(dniPlayer, player); //TODO REVISAR SI CREA EL OBJETO PLAYER USANDO YA EL MÉTODO ADDPLAYER SINO HACER IGUAL QUE MODIFICAR TEAM
            if (modified)
                System.out.println("Jugador Modificado correctamente");
            else
                System.out.println("Jugador no modificado");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCIÓN Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        }

    }

    private void modifyClothing() {
        boolean modify = false;
        System.out.println("Introduzca el dni del jugador para modificar su pedido: ");
        String dni = keyboard.nextLine();
        //TODO realizar modificar Clothing
    }

    private void showTeam() {
        TeamDao teamDao = new TeamDao(connection);

        try {
            ArrayList<Team> teams = teamDao.findAll();
            for (Team team : teams) {
                System.out.println(team.getName() + team.getCategory());
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCIÓN Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        }
    }

    private void showPlayer() {
        PlayerDao playerDao = new PlayerDao(connection);

        try {
            ArrayList<Player> players = playerDao.findAll();
            for ( Player player : players) {
                System.out.println(player.getFirstName() + player.getLastName() + player.getDni() + player.getYearOfBirth());
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCIÓN Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        }
    }

    private void addOrder() {
        System.out.println("Dni del jugador o los jugadores que pides su equipación (separados por comas): ");
        String ordenClothings = keyboard.nextLine();
        ordenClothings.toUpperCase();//NOS ASEGURAMOS QUE TODAS LAS LETRAS DEL DNI ESTARÁN EN MAYÚSCULAS

        try {
            String[] clothingArray = ordenClothings.split(","); // EN CADA POSICIÓN DEL ARRAY UN DNI
            ClothingDao clothingDao = new ClothingDao(connection);// NOS CONECTADOS USANDO EL DAO CORRESPONDIENTE
            List<Clothing> clothings = new ArrayList<>(); //CREAMOS LA LISTA DE ROPA
            for (String clothingDni : clothingArray) { //RECORREMOS EL ARRAY
                Clothing clothing = clothingDao.findByDni(clothingDni.trim()).get();//AL SER UN OPCIONAL USAMOS .GET PARA QUE NOS LO DEVUELVA SIEMPRE
                clothings.add(clothing);
            }

            OrderDao orderDao = new OrderDao(connection);
            orderDao.add(currentUser, clothings); //CREAMOS LA ORDEN DE PEDIDO CON EL USUARIO QUE ESTA LOGEADO EN LA APP Y LA ROPA
            System.out.println("Pedido creado");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCIÓN Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        }
    }

    private void showOrderDetails() {
        OrderDao orderDao = new OrderDao(connection);

        //TODO INDICAR QUE PEDIDO QUIERE VER

        Order order = orderDao.getOrder(); //PARA TRAERNOS EL OBJETO Y PODER MOSTRARLO

        //TODO MOSTRAR DATOS DEL PEDIDO
    }

    private void payOrder() {
        OrderDao orderDao = new OrderDao(connection);

        //TODO INDICAR EL PEDIDO - OJO ME SIRVE PARA ASIGNAR EL ENTRENADOR

        orderDao.payOrder();
    }

    private void showClothing() {
        for (Clothing clothing : catalogoClothing) {
            System.out.println("Serigrafía: " +clothing.getSerigraphy());
            System.out.println("Dorsal: " +clothing.getNumber());
            System.out.println("Cuota: " + clothing.getSize());
        }
    }

    public void deleteTeam() {
        System.out.println("nombre del equipo a borrar");
        String nameTeam = keyboard.nextLine();
        System.out.println("categoria del equipo");
        String categoryTeam = keyboard.next();

        TeamDao teamDao = new TeamDao(connection);
        try {
            boolean deleted = teamDao.delete(nameTeam, categoryTeam);
            if (deleted)
                System.out.println("Equipo borrado correctamente");
            else
                System.out.println("Equipo NO borrado o NO existe");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCIÓN Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        }
    }


    public void searchTeam() {
        System.out.print("Búsqueda por name: ");
        String name = keyboard.nextLine();

        TeamDao teamDao = new TeamDao(connection);
        try {
            Optional<Team> optionalTeam = teamDao.findByName(name);
            Team team = optionalTeam.orElse(new Team("No existe el equipo", "")); //ME DAS EL EQUIPO O ESTE OTRO VACÍO QUE TE PINTO YO. ASÍ NUNCA DEVOLVERÁ NULO

            System.out.println(team.getName());
            System.out.println(team.getCategory());
            //TODO IMPRIMIR ENTRENADOR Y DEMÁS
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
    }

    public boolean searchUsername(String username) {
        UserDao userDao = new UserDao(connection);

        try {
            userDao.findByUsername(username);

        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
        return true;
    }
}
