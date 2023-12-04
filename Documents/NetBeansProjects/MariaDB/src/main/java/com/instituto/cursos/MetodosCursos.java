/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.instituto.cursos;

import com.instituto.estudiantes.MetodosEstudiantes;
import com.instituto.estudiantes.ConsultasEstudiantes;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Cris
 */
public class MetodosCursos {

//Encontrar curso para devolver id de este
    public static void encontrarCurso(ConsultasCursos conCursos) throws SQLException {
        Scanner sc = new Scanner(System.in);
        List<Curso> cursosEncontrados = null;
        String nombreCurso;
        do {
            System.out.print("Nombre del curso: ");
            nombreCurso = sc.nextLine();
            //Llamamos al método para mostrar los cursos que hay con el nombre introducido
            cursosEncontrados = conCursos.buscarCursoPorNombre(nombreCurso);
            if (cursosEncontrados.isEmpty()) {
                //En el caso que el list este vacio enviara este mensaje
                System.out.println("No se ha encontrado ningún curso");
            } else {
                //En caso contrario enviara la lista de cursos
                System.out.println("--------------Cursos encontrados--------------");
                for (Curso cursos : cursosEncontrados) {
                    System.out.println("ID: " + cursos.getId() + " Nombre: " + cursos.getNombreCurso());

                }
                System.out.println("---------------------------------------------------");
            }
        } while (cursosEncontrados.isEmpty());

    }

//Insertar curso
    public static void insertarCurso(Curso curso, ConsultasCursos conCursos) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int creditos = 0;
        //Insertamos los datos para ingresr el curso nuevo
        System.out.print("Nombre del curso: ");
        curso.setNombreCurso(sc.nextLine());

        System.out.print("Descripción del curso: ");
        curso.setDescripcion(sc.nextLine());

        //Bucle para que en caso de que el usuario no ingrese correctamente los créditos que vuelva a pedirlos
        do {
            //Pedimos al usuario que ingrese los cretitos
            System.out.print("Créditos del curso: ");
            //Para evitar que nos puedan meter un String usaremos el siguiente "if"
            //para que en caso de ser un int se pueda ingresar el crédito
            if (sc.hasNextInt()) {
                if (creditos >= 0) {
                    //Para que cuando el número sea negativo o igual a cero de este error
                    System.out.println("Crédito inválido. El crédito no puede ser cero o un número negativo");
                }
                creditos = sc.nextInt();
                curso.setCreditos(creditos);
                sc.nextLine();
            } else {
                //En caso de no ser un int se imprimirá este mensaje
                System.out.println("Créditos inválidos. Debe ingresar un número válido.");
                sc.nextLine(); // Consumir la entrada incorrecta
            }

        } while (creditos <= 0);

        conCursos.insertarCurso(curso);
        System.out.println("Curso Insertado");
    }

//Obtener id curso
    public static int getIdCurso(ConsultasCursos conCursos) {
        Scanner sc = new Scanner(System.in);
        int idCurso = -1; // Valor predeterminado en caso de error

        //Hacemos un bucle para que cuando se ingrese un id invalido lo vuelva a preguntar
        do {
            System.out.print("ID del curso: ");
            if (sc.hasNextInt()) {
                //Si el usuario mete un int se realizara el siguiente código
                int inputId = sc.nextInt();
                sc.nextLine();
                if (conCursos.existeCurso(inputId)) {
                    //Comprobamos si el id del curso ingresado existe o no en la base de datos
                    idCurso = inputId;
                } else {
                    //En el caso de que no exista
                    System.out.println("El ID del curso no existe en la base de datos.");
                }
            } else {
                //En caso que no sea un int se imprimirá esto
                System.out.println("ID inválido");
                sc.nextLine(); // Consumir la entrada incorrecta
            }
        } while (idCurso == -1);

        return idCurso; // Devuelve el valor válido
    }

//Menu de opciones para actualizar la información de un curso
    public static void menuActualizarCursos(ConsultasCursos conCursos) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcionCampo = -1;
        int idCursoActualizar = 0;
        int nuevosCreditos = 0;

        do {
            //Hacemos un bucle que pida al usuario que  parte del curso quiere actualizar,
            //en caso de querer salir del bucle habrá que salir con un 0

            System.out.println("--------------Actualizar Curso--------------");
            System.out.println("Seleccione qué campo actualizar:");
            System.out.println("1. Nombre del Curso");
            System.out.println("2. Descripción del Curso");
            System.out.println("3. Créditos del Curso");
            System.out.println("0. Volver al menú inicial");
            System.out.print("Elija una opción: ");

            if (sc.hasNextInt()) {
                //En caso de que el número sea un int se ejecutará esto
                opcionCampo = sc.nextInt();
                sc.nextLine();

            } else {
                //Si no es un int se ejecutará esto
                sc.nextLine();
                opcionCampo = 0;
                System.out.println("Opción inválida. Debe ingresar un número.");
            }

            if (opcionCampo != 0) {
                //Si la opcion es diferente de 0 se llama al método que te encuentra el id del curso y lo devuelve
                encontrarCurso(conCursos);
                idCursoActualizar = getIdCurso(conCursos);
            }

            switch (opcionCampo) {
                case 1:
                    // Actualizar nombre del curso
                    System.out.print("Nuevo nombre del curso: ");
                    String nuevoNombre = sc.nextLine();
                    conCursos.actualizarNombreCurso(idCursoActualizar, nuevoNombre);
                    System.out.println("Nombre Actualizado");
                    break;
                case 2:
                    // Actualizar descripción del curso
                    System.out.print("Nueva descripción del curso: ");
                    String nuevaDescripcion = sc.nextLine();
                    conCursos.actualizarDescripcionCurso(idCursoActualizar, nuevaDescripcion);
                    System.out.println("Descripción Actualizada");
                    break;
                case 3:
                    // Actualizar créditos del curso
                    do {
                        nuevosCreditos = 0;
                        System.out.print("Nuevos créditos del curso: ");
                        if (sc.hasNextInt()) {
                            //En el caso de que sea un int se hará lo siguiente
                            nuevosCreditos = sc.nextInt();
                            sc.nextLine();
                            if (nuevosCreditos <= 0) {
                                //En el caso que el numero sea igual o menor que 0 se imprimirá esto
                                System.out.println("Crédito inválido. El crédito no puede ser cero o un número negativo");
                            } else {
                                //En el caso de que el  número sea positivo se actualizarán los créditos
                                conCursos.actualizarCreditosCurso(idCursoActualizar, nuevosCreditos);
                                System.out.println("Créditos Actualizados");
                            }
                        } else {
                            System.out.println("Créditos inválidos. Debe ingresar un número válido.");
                            sc.nextLine();
                        }
                    } while (nuevosCreditos <= 0);
                    break;
            }
        } while (opcionCampo != 0);
    }

//Eliminar Cursos
    public static void eliminarCurso(ConsultasCursos conCursos) throws SQLException {
        Scanner sc = new Scanner(System.in);
        encontrarCurso(conCursos);

        int idCursoEliminar = getIdCurso(conCursos);
        conCursos.eliminarCurso(idCursoEliminar);
        System.out.println("Curso Eliminado");
    }

//Consultar cursos de un estudiante
    public static void cursosDeEstudiantes(ConsultasCursos conCursos, ConsultasEstudiantes conEstudiantes) throws SQLException {
        Scanner sc = new Scanner(System.in);
        MetodosEstudiantes.encontrarEstudiante(conEstudiantes);
        List<Curso> cursosDelEstudiante = null;
        int idEstudianteConsulta = MetodosEstudiantes.getIdEstudiante(conEstudiantes);

        //Llamamos al metodo que nos devolverá una lista de los cursos que tiene un estudiante
        cursosDelEstudiante = conCursos.consultarCursosDeEstudiante(idEstudianteConsulta);
        if (cursosDelEstudiante.isEmpty()) {
            //En el caso de que esta lista esté vacia se devolverá lo siguiente
            System.out.println("No se ha encontrado ningún curso");
        } else {
            //En el caso que el estudiante tenga cursos se le imprimirán por pantalla
            System.out.println("--------------Cursos--------------");

            for (Curso cursos : cursosDelEstudiante) {
                System.out.println("ID: " + cursos.getId() + ", Nombre: " + cursos.getNombreCurso());
            }
        }
    }

}
