package com.svalero.sportsclubapp;


import com.svalero.sportsclubapp.dao.ClothingDao;
import com.svalero.sportsclubapp.dao.Database;
import com.svalero.sportsclubapp.dao.PlayerDao;
import com.svalero.sportsclubapp.dao.TeamDao;
import com.svalero.sportsclubapp.domain.Clothing;
import com.svalero.sportsclubapp.domain.Player;
import com.svalero.sportsclubapp.domain.Team;
import com.svalero.sportsclubapp.util.Constants;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private Scanner keyboard;
    private Database database;
    private Connection connection;

    private List<Player> catalagoPlayer;
    private List<Team> catalogoTeam;
    private List<Clothing> catalogoClothing;

    public Menu() {
        keyboard = new Scanner(System.in);
        catalagoPlayer = new ArrayList<>();
        catalogoTeam = new ArrayList<>();
        catalogoClothing = new ArrayList<>();
    }

    //metodo para conectar con la BBDD
    public void connect() {
        database = new Database();
        connection = database.getConnection();
    }

    public void showMenu() {
        //Nada más arrancar el menu conecto con la BBDD mediante metodo anterior public void connect;
        connect();

        String choice = null;

        do {
            System.out.println("Aplicación de Baloncesto: ");
            System.out.println("1. Añadir Equipo: ");
            System.out.println("2. Añadir Jugador: ");
            System.out.println("3. Crear Pedido Ropa: ");
            System.out.println("4. Asignar Jugador a Equipo: ");
            System.out.println("5. Modificar Equipo: ");
            System.out.println("6. Modificar Jugador: ");
            System.out.println("7. Modificar Pedido: ");
            System.out.println("8. Mostrar Equipos: ");
            System.out.println("9. Mostrar Jugadores: ");
            System.out.println("10. Mostrar Pedidos: ");
            System.out.println("11. Salir");
            System.out.println("Opción: ");
            choice = keyboard.nextLine();

            switch (choice) {
                case "1":
                    addTeam();
                    break;
                case "2":
                    addPlayer();
                    break;
                case "3":
                    addClothing();
                    break;
                case "4":
                    assignTeam();
                    break;
                case "5":
                    modifyTeam();
                    break;
                case "6":
                    modifyPlayer();
                    break;
                case "7":
                    modifyClothing();
                    break;
                case "8":
                    showTeam();
                    break;
                case "9":
                    showPlayer();
                    break;
                case "10":
                    showClothing();
                    break;
                case "11":
                    deleteTeam();
                    break;
                case "12":
                    //deletePlayer();
                    break;
                case "13":
                    //deleteClothing();
                    break;
                case "14":
                    //deleteClothing();
                    break;

            }
        } while (!choice.equals("15"));
    }

    private void addTeam() {
        //Pedimos los datos al usuario
        System.out.print("Introduzca nombre del equipo: ");
        String name = keyboard.nextLine();
        System.out.print("Introduzca la categoría equipo: ");
        String category = keyboard.nextLine();
        //TODO FALTA ENTRENADOR
        //System.out.print("Introduzca el entrenador del equipo: ");
        //String coach = keyboard.nextLine();
        //Creamos el objeto Team con los datos
        Team team = new Team(name.trim(), category.trim(), Constants.QUOTA);

        //Lo damos de alta en BBDD con el dao
        TeamDao teamDao = new TeamDao(connection);
        teamDao.add(team);
    }

    private void addPlayer() {
        //Pedimos los datos al usuario
        System.out.print("Introduzca nombre del Jugador: ");
        String firstName = keyboard.nextLine();
        System.out.print("Introduzca apellidos del Jugador: ");
        String lastName = keyboard.nextLine();
        System.out.print("Introduzca el número de dorsal del Jugador: ");
        int number = keyboard.nextInt();
        System.out.print("Introduzca la fecha de nacimiento del Jugador: ");
        int yearOfBirth = keyboard.nextInt();
        System.out.print("Introduzca el DNI del Jugador: ");
        String dni = keyboard.nextLine();
        //Creamos el objeto Player con los datos
        Player player = new Player(firstName.trim(), lastName.trim(), number, yearOfBirth, dni.trim());

        //Lo damos de alta en la base de datos con el dao
        PlayerDao playerDao = new PlayerDao(connection);
        playerDao.add(player);
    }

    private void addClothing() {
        //Pedimos los datos al usuario
        System.out.print("Introduzca el nombre y apellidos del jugador:");
        String name = keyboard.nextLine();
        System.out.print("Introduzca el DNI del jugador:");
        String dni = keyboard.nextLine();
        System.out.print("Introduzca la serigrafía que llevará la equipación:");
        String serigraphy = keyboard.nextLine();
        System.out.print("Introduzca el número de dorsal del Jugador: ");
        int number = keyboard.nextInt();
        System.out.print("Introduzca la talla del Jugador: ");
        String size = keyboard.nextLine();
        //Creamos el objeto Player con los datos
        Clothing clothing = new Clothing(name.trim(), dni.trim(), serigraphy.trim(), number, size.trim(), Constants.PRICE);

        //Lo damos de alta en la BBDD con el objeto dao
        ClothingDao clothingDao = new ClothingDao(connection);
        clothingDao.add(clothing);
    }

    //TODO Buscar Equipo, Jugador y Ropa Webinar5 min 37

    private void assignTeam() {
        boolean assign = false;
        System.out.println("Introduzca el DNI del jugador para asignar el equipo");
        String dni = keyboard.nextLine();
        for (Player player : catalagoPlayer) {
            if (player.getDni().equalsIgnoreCase(dni)) {
                System.out.println("Introduzca el equipo a asignar: ");
                String searchTeam = keyboard.nextLine();
                for (Team team : catalogoTeam) {
                    if (team.getName().equalsIgnoreCase(searchTeam)) {
                        player.getFirstName();
                        player.getLastName();
                        player.getNumber();
                        player.getYearOfBirth();
                        player.getDni();
                        player.setTeam(team);
                        System.out.println("Equipo asignado correctamente");
                        assign = true;
                    } else
                        System.out.println("Equipo no encontrado");
                }
            } if (!assign)
                System.out.println("Jugador no asignado a equipo");
        }
    }

    private void modifyTeam() {
        boolean modify = false;
        System.out.println("Introduzca el nombre de equipo a modificar: ");
        String nameTeam = keyboard.nextLine();
        for (Team team :catalogoTeam) {
            if (team.getName().equalsIgnoreCase(nameTeam)) {
                System.out.println("Nuevo nombre equipo");
                String newName = keyboard.nextLine();
                System.out.println("Nueva categoría del equipo");
                String newCategory = keyboard.nextLine();
                System.out.println("Nuevo entrenador del equipo");
                String newCoach = keyboard.nextLine();
                team.setName(newName);
                team.setCategory(newCategory);
                team.setCoach(newCoach);
                System.out.println("Equipo modificado correctamente");
                modify = true;
            }
        if (!modify)
            System.out.println("No se ha encontrado el equipo");
        }
    }
    //TODO realizar modificar Player
    private void modifyPlayer() {
        boolean modify = false;
        System.out.println("Introduzca el nombre de equipo a modificar: ");
        String nameTeam = keyboard.nextLine();
        for (Team team :catalogoTeam) {
            if (team.getName().equalsIgnoreCase(nameTeam)) {
                System.out.println("Nuevo nombre equipo");
                String newName = keyboard.nextLine();
                System.out.println("Nueva categoría del equipo");
                String newCategory = keyboard.nextLine();
                System.out.println("Nuevo entrenador del equipo");
                String newCoach = keyboard.nextLine();
                team.setName(newName);
                team.setCategory(newCategory);
                team.setCoach(newCoach);
                team.getQuota();
                System.out.println("Equipo modificado correctamente");
                modify = true;
            }
            if (!modify)
                System.out.println("No se ha encontrado el equipo");
        }
    }

    private void modifyClothing() {
        boolean modify = false;
        System.out.println("Introduzca el dni del jugador para modificar su pedido: ");
        String dni = keyboard.nextLine();
        for (Clothing clothing : catalogoClothing) {
            if (clothing.getDni().equalsIgnoreCase(dni)) {
                System.out.println("Nombre y Apellidos del jugador");
                String newName = keyboard.nextLine();
                System.out.println("Dni del Jugador");
                String newDni = keyboard.nextLine();
                System.out.println("Serigrafia");
                String newSerigraphy = keyboard.nextLine();
                System.out.println("Talla de la equipación");
                String newSize = keyboard.nextLine();
                clothing.setName(newName);
                clothing.setDni(newDni);
                clothing.setSerigraphy(newSerigraphy);
                clothing.setSize(newSize);
                clothing.getSize();
                System.out.println("Equipo modificado correctamente");
                modify = true;
            }
            if (!modify)
                System.out.println("No se ha encontrado el equipo");
        }
    }

    private void showTeam() {
        TeamDao teamDao = new TeamDao(connection);
        //TODO PROPAGAR LA EXCEPCION AL MENU DE USUARIO
        ArrayList<Team> teams = teamDao.findAll();
        for (Team team : teams) {
            System.out.println(team.getName());
        }
    }

    private void showPlayer() {
        for (Player player : catalagoPlayer) {
            System.out.println("Nombre: " + player.getFirstName());
            System.out.println("Apellidos: " + player.getLastName());
            System.out.println("Dorsal: " + player.getNumber());
            System.out.println("Fecha Nacimiento: " + player.getYearOfBirth());
            System.out.println("DNI: " + player.getDni());
            System.out.println("Equipo: " + player.getTeam());
        }
    }

    private void showClothing() {
        for (Clothing clothing : catalogoClothing) {
            System.out.println("Nombre y apellidos: " +clothing.getName());
            System.out.println("DNI: " +clothing.getDni());
            System.out.println("Serigrafía: " +clothing.getSerigraphy());
            System.out.println("Dorsal: " +clothing.getNumber());
            System.out.println("Cuota: " + clothing.getSize());
        }
    }

    public void deleteTeam() {
        System.out.println("nombre del equipo a borrar");
        String name = keyboard.nextLine();
        //TODO ELIMINAR DE LA BBDD
    }

    /*
    public void searchTeam() {
        System.out.print("Búsqueda por categoría: ");
        String category = keyboard.nextLine();

        TeamDao teamDao = new TeamDao(connection);
        try {
            Optional<Team> optionalTeam = teamDao.findByCategory(category);
            Team team = optionalTeam.orElse(new Team("No existe el título", ""));

            System.out.println(team.getName());
            System.out.println(team.getCategory());
            //TODO IMPRIMIR ENTRENADOR Y DEMAS
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
    } */
}
