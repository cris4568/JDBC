/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.instituto.inscripciones;

import com.instituto.cursos.ConsultasCursos;
import com.instituto.cursos.Curso;
import com.instituto.estudiantes.Estudiante;
import com.instituto.estudiantes.MetodosEstudiantes;
import com.instituto.estudiantes.ConsultasEstudiantes;
import static com.instituto.cursos.MetodosCursos.encontrarCurso;
import static com.instituto.cursos.MetodosCursos.getIdCurso;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Cris
 */
public class MetodosInscripciones {

//insertar incripcion
    public static void insertarIncripcion(ConsultasInscripciones conInscripciones, ConsultasCursos conCursos, ConsultasEstudiantes conEstudiantes) throws SQLException {
        MetodosEstudiantes.encontrarEstudiante(conEstudiantes);
        encontrarCurso(conCursos);

        int idEstudiante = 0;
        int idCurso = 0;
        //Obtenemos el id del estudiante y del curso
        idEstudiante = MetodosEstudiantes.getIdEstudiante(conEstudiantes);
        idCurso = getIdCurso(conCursos);
        //Llamamos al método para crear una nueva inscripción
        conInscripciones.registrarInscripcion(idEstudiante, idCurso);
        System.out.println("Inscripción Insertada");
    }

//Eliminar Inscripciónes
    public static void eliminarInscripciones(ConsultasInscripciones conInscripciones) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int idInscripcion = -1;
        do {
            System.out.print("ID de la inscripcion a Eliminar: ");
            if (sc.hasNextInt()) {
                //comprobamos si el dato pasado es un número int
                int inputId = sc.nextInt();
                if (conInscripciones.existeInscripcion(inputId)) {
                    //Comprobamos si la inscripción ingresada existe, en caso afirmativo se procederá a eliminarla
                    idInscripcion = inputId;
                    conInscripciones.eliminarInscripcion(idInscripcion);
                    System.out.println("Inscripción Eliminada");
                } else {
                    //En caso contrario se imprimirá lo siguiente
                    System.out.println("El ID de la inscripción no existe en la base de datos.");
                }

            } else {
                //En caso de ingresa un id invalido se imprimirá lo siguiente
                System.out.println("ID inválido.");
                sc.nextLine(); // Consumir la entrada incorrecta
            }
        } while (idInscripcion == -1);
    }

//  Menu de opciones para eliminar inscripciones
    public static void menuEliminarInscripciones(ConsultasInscripciones conInscripciones, ConsultasCursos conCursos, ConsultasEstudiantes conEstudiantes) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcionEliminar = -1; // Inicializamos con un valor inválido para entrar en el bucle.

        do {
            //Hacemos un bucle que pida al usuario si quiere ver las inscripciones de un usuario o las inscripciones de un curso,
            //esto lo pedirá hasta que el usuario ponga un 0
            System.out.println("Seleccione lo que desea mostrar:");
            System.out.println("1. Mostrar inscripciones por estudiante");
            System.out.println("2. Mostrar inscripciones por curso");
            System.out.println("0. Volver al menú inicial");
            System.out.print("Elija una opción: ");

            if (sc.hasNextInt()) {
                //En caso de que el número sea un int se ejecutará esto
                opcionEliminar = sc.nextInt();
                sc.nextLine();
                switch (opcionEliminar) {
                    case 1:
                        //Muestra las inscripciones que tiene un estudiante especifico y elimina
                        mostrarInscripcionesDeEstudiante(conInscripciones, conCursos, conEstudiantes);
                        eliminarInscripciones(conInscripciones);

                        break;
                    case 2:
                        // Muestra las incripciones que tiene un curso especifico y elimina
                        mostrarInscripcionesDeCurso(conInscripciones, conCursos, conEstudiantes);
                        eliminarInscripciones(conInscripciones);

                        break;
                    case 0:
                        // Volver al menú principal
                        break;
                    default:
                        System.out.println("Opción inválida. Debe seleccionar una opción válida.");
                        break;
                }
            } else {
                //Si no es un int se ejecutará esto
                System.out.println("Opción inválida. Debe ingresar un número.");
                sc.nextLine();
            }
        } while (opcionEliminar != 0);
    }

//Encontrar el id de las incripciones que tiene un estudiante
    public static void mostrarInscripcionesDeEstudiante(ConsultasInscripciones conInscripciones, ConsultasCursos conCursos, ConsultasEstudiantes conEstudiantes) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String nombreEstudiante;
        List<Estudiante> estudiantesEncontrados = null;
        do {
            System.out.print("Nombre del estudiante: ");
            nombreEstudiante = sc.nextLine();
            //Llamamos al método para que nos devuelva los estudiantes que tiene un curso
            estudiantesEncontrados = conEstudiantes.buscarEstudiantePorNombre(nombreEstudiante);

            if (!estudiantesEncontrados.isEmpty()) {
                //Si la lista no esta vacia imprime los estudiantes que hay en dicho curso y muestra la id de esta inscripción
                System.out.println("--------------Estudiantes--------------");
                for (Estudiante estudiante : estudiantesEncontrados) {
                    //Nos recorremos la lista
                    //Llamamos a la consulta que nos devuelve los usuarios en dicho curso y su id de inscripción
                    List<Inscripcion> inscripciones = conInscripciones.obtenerInscripcionesDeEstudiante(estudiante.getId());
                    for (Inscripcion inscripcion : inscripciones) {
                        //Nos recorremos la nueva lista 
                        Curso curso = conCursos.obtenerCursoPorID(inscripcion.getIdCurso());
                        System.out.println("Estudiante: " + estudiante.getNombre() + ", Curso: " + curso.getNombreCurso() + ", ID Incripcion: " + inscripcion.getId());
                    }

                }
                System.out.println("-----------------------------------");
            } else {
                //En caso de estar el list vacio se imprimirá por pantalla lo siguiente
                System.out.println("No se encontró ningún estudiante con ese nombre.");
            }
        } while (estudiantesEncontrados.isEmpty());
    }

//Encontrar el id de las incripciones que tiene un curso
    public static void mostrarInscripcionesDeCurso(ConsultasInscripciones conInscripciones, ConsultasCursos conCursos, ConsultasEstudiantes conEstudiantes) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String nombreCurso;
        List<Curso> cursosEncontrados = null;
        do {
            System.out.print("Nombre del curso: ");
            nombreCurso = sc.nextLine();
            //Llamamos al método para que nos devuelva los cursos a los que esta inscrito un estudiante
            cursosEncontrados = conCursos.buscarCursoPorNombre(nombreCurso);

            if (!cursosEncontrados.isEmpty()) {
                //Si la lista no esta vacia imprime los cirsos que tiene dicho estudiante y muestra la id de esta inscripción
                System.out.println("Inscripciones de estudiantes en cursos con nombre " + nombreCurso + ":");
                for (Curso curso : cursosEncontrados) {
                    //Nos recorremos la lista
                    //Llamamos a la consulta que nos devuelve los usuarios en dicho curso y su id de inscripción
                    List<Inscripcion> inscripciones = conInscripciones.obtenerInscripcionesDeCurso(curso.getId());

                    System.out.println("--------------Curso " + curso.getNombreCurso() + "--------------");
                    System.out.println("Curso: " + curso.getNombreCurso());
                    if (inscripciones.isEmpty()) {
                        //En caso de no tener inscripciones en ese curso imprimirá esto
                        System.out.println("No hay inscripciones en este curso.");
                    } else {
                        //En caso que haya contenido en la lista mostrará las inscripciónes de dicho curso
                        System.out.println("Inscripciones:");
                        for (Inscripcion inscripcion : inscripciones) {
                            //Nos recorremos la nueva lista 
                            Estudiante estudiante = conEstudiantes.obtenerEstudiantePorID(inscripcion.getIdEstudiante());
                            System.out.println("Estudiante: " + estudiante.getNombre() + ", ID Incripcion: " + inscripcion.getId());
                        }

                    }
                }
                System.out.println("-------------------------------------------");
            } else {
                //En caso de estar el list vacio se imprimirá por pantalla lo siguiente
                System.out.println("No se encontró ningún curso con ese nombre.");
            }
        } while (cursosEncontrados.isEmpty());
    }

}
