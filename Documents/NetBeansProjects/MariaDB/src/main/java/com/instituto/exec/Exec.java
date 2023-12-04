/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.instituto.exec;

import com.instituto.inscripciones.MetodosInscripciones;
import com.instituto.inscripciones.ConsultasInscripciones;
import com.instituto.cursos.ConsultasCursos;
import com.instituto.cursos.Curso;
import com.instituto.cursos.MetodosCursos;
import com.instituto.estudiantes.Estudiante;
import com.instituto.estudiantes.MetodosEstudiantes;
import com.instituto.estudiantes.ConsultasEstudiantes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Cris
 */
public class Exec {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;

        try {
            //Establecemos conexion con la base de datos
            conn = establecerConexion();
            ConsultasEstudiantes conEstudiantes = new ConsultasEstudiantes(conn);
            ConsultasCursos conCursos = new ConsultasCursos(conn);
            ConsultasInscripciones conInscripciones = new ConsultasInscripciones(conn);

            //creamos las tablas si no estan creadas
            crearTablas(conEstudiantes, conCursos, conInscripciones);

            //Accedemos al menu de opciones para poder interactuar con la base de datos
            menuPrincipal(sc, conEstudiantes, conCursos, conInscripciones);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //cerramos la conexion con la base de datos
            cerrarConexion(conn);
        }
    }

//Establecer conexion con la base de datos
    private static Connection establecerConexion() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/Instituto";
        String user = "root";
        String pass = "root";
        Connection conn = DriverManager.getConnection(url, user, pass);
        System.out.println("Conexión exitosa a la base de datos.");
        return conn;
    }

//Cerrar conexion con la base de datos
    private static void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//Crear tablas
    private static void crearTablas(ConsultasEstudiantes conEstudiantes, ConsultasCursos conCursos, ConsultasInscripciones conInscripciones) throws SQLException {
        conEstudiantes.crearTablaEstudiantes();
        conCursos.crearTablaCursos();
        conInscripciones.crearTablaInscripciones();
    }

//Menú Principal
    private static void menuPrincipal(Scanner sc, ConsultasEstudiantes conEstudiantes, ConsultasCursos conCursos, ConsultasInscripciones conInscripciones) throws SQLException {
        Estudiante estudiante = new Estudiante();
        Curso curso = new Curso();
        int opcion = -1;

        do {
            System.out.println("-----------------------------------------------");
            System.out.println("Menú de opciones:");
            System.out.println("1. Insertar estudiante");
            System.out.println("2. Insertar curso");
            System.out.println("3. Registrar inscripción");
            System.out.println("4. Consultar estudiantes en un curso");
            System.out.println("5. Consultar cursos de un estudiante");
            System.out.println("6. Actualizar información de estudiante");
            System.out.println("7. Actualizar información de curso");
            System.out.println("8. Eliminar estudiante");
            System.out.println("9. Eliminar curso");
            System.out.println("10. Eliminar inscripciones");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");

            //Hacemos esto para comprobar que el número insertado se encuentra en unn formato equivocado
            if (sc.hasNextInt()) {
                opcion = Integer.parseInt(sc.nextLine());
            } else {
                sc.nextLine();  //Lo usamos para quitar la opcion invalida, y así que no nos de problemas
                System.out.println("Opción inválida. Debe ingresar un número.");
            }
            switch (opcion) {
                case 1:
                    //Llamamos al método para insertar estudiantes
                    MetodosEstudiantes.insertarEstudiante(estudiante, conEstudiantes);
                    break;
                case 2:
                    //Llamamos al método para insertar cursos
                    MetodosCursos.insertarCurso(curso, conCursos);
                    break;
                case 3:
                    //Llamamos al método para insertar inscripciones
                    MetodosInscripciones.insertarIncripcion(conInscripciones, conCursos, conEstudiantes);
                    break;
                case 4:
                    //Llamamos al método para visualizar los estudiantes que hay en un curso
                    MetodosEstudiantes.estudiantesEnCurso(conEstudiantes, conCursos);
                    break;
                case 5:
                    //Llamamos al método para visualizar los cursos que tiene un estudiantes
                    MetodosCursos.cursosDeEstudiantes(conCursos, conEstudiantes);
                    break;
                case 6:
                    //Llamamos al método para visualizar el menú de opciones para actualizar un estudiante
                    MetodosEstudiantes.menuActualizarEstudiantes(conEstudiantes);
                    break;
                case 7:
                    //Llamamos al método para visualizar el menú de opciones para actualizar un curso
                    MetodosCursos.menuActualizarCursos(conCursos);
                    break;
                case 8:
                    //Llamamos al método para eliminar estudiantes
                    MetodosEstudiantes.eliminarEstudiante(conEstudiantes);
                    break;
                case 9:
                    //Llamamos al método para eliminar cursos
                    MetodosCursos.eliminarCurso(conCursos);
                    break;
                case 10:
                    //Llamamos al metodo para visualizar el menú de opciones para eliminar una inscripción
                    MetodosInscripciones.menuEliminarInscripciones(conInscripciones, conCursos, conEstudiantes);
                    break;
                case 0:
                    //Salimos del programa
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    //Para cuando se ingrese un número entero que no se encuentre en el menú
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (opcion != 0);
    }

}
