/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.instituto.estudiantes;

import com.instituto.cursos.ConsultasCursos;
import com.instituto.cursos.MetodosCursos;
import static com.instituto.cursos.MetodosCursos.encontrarCurso;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Cris
 */
public class MetodosEstudiantes {

//Obtener el id de un estudiante
    public static int getIdEstudiante(ConsultasEstudiantes conEstudiantes) {
        Scanner sc = new Scanner(System.in);
        int idEstudiante = -1; // Valor predeterminado en caso de error

        //Hacemos un bucle para que cuando se ingrese un id invalido lo vuelva a preguntar
        do {
            System.out.print("ID del estudiante: ");
            if (sc.hasNextInt()) {
                //Si el usuario mete un int se realizara el siguiente código
                int inputId = sc.nextInt();

                if (conEstudiantes.existeEstudiante(inputId)) {
                    //Comprobamos si el id del estudiante ingresado existe o no en la base de datos
                    idEstudiante = inputId;
                } else {
                    //En el caso de que no exista
                    System.out.println("El ID del estudiante no existe en la base de datos.");
                }
            } else {
                //En caso que no sea un int se imprimirá esto
                System.out.println("ID inválido.");
                sc.nextLine();
            }
        } while (idEstudiante == -1);

        return idEstudiante;
    }

//Insertar estudiante
    public static void insertarEstudiante(Estudiante estudiante, ConsultasEstudiantes conEstudiantes) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int edadEstudiante = 0;
        //Insertamos los datos para ingresr el estudiante nuevo
        System.out.print("Nombre del estudiante: ");
        estudiante.setNombre(sc.nextLine());

        //Bucle para que en caso de que el usuario no ingrese correctamente los créditos que vuelva a pedirlos
        do {
            //Pedimos al usuario que ingrese la edad 
            System.out.print("Edad del estudiante: ");
            //Para evitar que nos puedan meter un String usaremos el siguiente "if"
            //para que en caso de ser un int se pueda ingresar la edad
            if (sc.hasNextInt()) {
                edadEstudiante = sc.nextInt();
                if (edadEstudiante <= 0) {
                    // Para cuando el número es negativo o cero
                    System.out.println("Edad inválida. La edad no puede ser cero o un número negativo");
                } else {
                    estudiante.setEdad(edadEstudiante);
                    sc.nextLine(); // Consume el salto de línea después del input del número entero
                }
            } else {
                System.out.println("Edad inválida. Debe ingresar un número válido.");
                sc.nextLine(); // Consumir input incorrecto
            }

        } while (edadEstudiante <= 0);

        System.out.print("Dirección del estudiante: ");
        estudiante.setDireccion(sc.nextLine());

        System.out.print("Correo del estudiante: ");
        estudiante.setCorreo(sc.nextLine());

        conEstudiantes.insertarEstudiante(estudiante);
        System.out.println("Estudiante Insertado");
    }

//Encontrar estudiante para devolver id de este
    public static void encontrarEstudiante(ConsultasEstudiantes conEstudiantes) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String nombre;
        List<Estudiante> estudiantesEncontrados;
        do {

            System.out.print("Nombre del Estudiante: ");
            nombre = sc.nextLine();
            //Llamamos al método para mostrar los estudiantes que hay con el nombre introducido
            estudiantesEncontrados = conEstudiantes.buscarEstudiantePorNombre(nombre);
            if (estudiantesEncontrados.isEmpty()) {
                //En el caso que el list este vacio enviara este mensaje
                System.out.println("No se ha encontrado ningún estudiante");
            } else {
                //En caso contrario enviara la lista de estudiantes
                System.out.println("--------------Estudiantes encontrados--------------");
                for (Estudiante estudiantes : estudiantesEncontrados) {
                    System.out.println("ID: " + estudiantes.getId() + " Nombre: " + estudiantes.getNombre());
                }
                System.out.println("---------------------------------------------------");
            }
        } while (estudiantesEncontrados.isEmpty());
    }

//Menu de opciones para actualizar la información de un estudiante
    public static void menuActualizarEstudiantes(ConsultasEstudiantes conEstudiantes) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int idEstudianteActualizar = 0;
        int opcionCampo = -1;
        int nuevaEdad = -1;
        do {
            //Hacemos un bucle que pida al usuario que parte de los datos del estudiante quiere actualizar,
            //en caso de querer salir del bucle habrá que salir con un 0

            System.out.println("--------------Actualizar Estudiante--------------");
            System.out.println("Menú de opciones para actualizar información del estudiante:");
            System.out.println("1. Actualizar nombre");
            System.out.println("2. Actualizar edad");
            System.out.println("3. Actualizar dirección");
            System.out.println("4. Actualizar correo");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elija una opción: ");
            if (sc.hasNextInt()) {
                //En caso de que el número sea un int se ejecutará esto
                opcionCampo = sc.nextInt();
                sc.nextLine();

            } else {
                //Si no es un int se ejecutará esto
                sc.nextLine();
                System.out.println("Opción inválida. Debe ingresar un número.");
                opcionCampo = 0;
            }

            if (opcionCampo != 0) {
                //Si la opcion es diferente de 0 se llama al método que te encuentra el id del estudiante y lo devuelve
                encontrarEstudiante(conEstudiantes);
                idEstudianteActualizar = getIdEstudiante(conEstudiantes);
            }

            switch (opcionCampo) {
                case 1:
                    // Actualizar nombre
                    System.out.print("Nuevo nombre del estudiante: ");
                    String nuevoNombre = sc.nextLine();
                    conEstudiantes.actualizarNombreEstudiante(idEstudianteActualizar, nuevoNombre);
                    System.out.println("Nombre Actualizado");
                    break;
                case 2:
                    // Actualizar edad
                    do {
                        nuevaEdad = 0;
                        System.out.print("Nueva edad del estudiante: ");
                        if (sc.hasNextInt()) {
                            //En el caso de que sea un int se hará lo siguiente
                            nuevaEdad = sc.nextInt();
                            sc.nextLine();
                            if (nuevaEdad <= 0) {
                                //En el caso que el numero sea igual o menor que 0 se imprimirá esto
                                System.out.println("Edad inválida. La edad no puede ser cero o un número negativo");
                            } else {
                                //En el caso de que el  número sea positivo se actualizarán los créditos
                                conEstudiantes.actualizarEdadEstudiante(idEstudianteActualizar, nuevaEdad);
                                System.out.println("Edad Actualizada");
                            }
                        } else {
                            System.out.println("Edad inválida. Debe ingresar un número válido.");
                            sc.nextLine();
                        }
                    } while (nuevaEdad <= 0);

                    break;
                case 3:
                    // Actualizar dirección
                    System.out.print("Nueva dirección del estudiante: ");
                    String nuevaDireccion = sc.nextLine();
                    conEstudiantes.actualizarDireccionEstudiante(idEstudianteActualizar, nuevaDireccion);
                    System.out.println("Dirección Actualizada");
                    break;
                case 4:
                    // Actualizar correo
                    System.out.print("Nuevo correo del estudiante: ");
                    String nuevoCorreo = sc.nextLine();
                    conEstudiantes.actualizarCorreoEstudiante(idEstudianteActualizar, nuevoCorreo);
                    System.out.println("Correo Actualizado");
                    break;
                case 0:
                    // Volver al menú principal
                    opcionCampo = 0;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }

        } while (opcionCampo
                != 0);
    }

//Eliminar Estudiantes
    public static void eliminarEstudiante(ConsultasEstudiantes conEstudiantes) throws SQLException {
        encontrarEstudiante(conEstudiantes);

        int idEstudianteEliminar = getIdEstudiante(conEstudiantes);
        conEstudiantes.eliminarEstudiante(idEstudianteEliminar);
        System.out.println("Estudiante Eliminado");
    }

//Consultar los estudiantes que tiene un curso
    public static void estudiantesEnCurso(ConsultasEstudiantes conEstudiantes, ConsultasCursos conCursos) throws SQLException {
        encontrarCurso(conCursos);
        List<Estudiante> estudiantesEnCurso = null;

        int idCursoConsulta = MetodosCursos.getIdCurso(conCursos);

        //Llamamos al metodo que nos devolverá una lista de los estudiantes que estan en ese curso
        estudiantesEnCurso = conEstudiantes.consultarEstudiantesEnCurso(idCursoConsulta);
        if (estudiantesEnCurso.isEmpty()) {
            //En el caso de que esta lista esté vacia se devolverá lo siguiente
            System.out.println("No se ha encontrado ningún estudiante");
        } else {
            //En el caso que el curso tenga estudiantes se le imprimirán por pantalla
            System.out.println("--------------Estudiantes--------------");

            for (Estudiante estudiantes : estudiantesEnCurso) {
                System.out.println("ID: " + estudiantes.getId() + ", Nombre: " + estudiantes.getNombre());
            }
        }
    }
}
