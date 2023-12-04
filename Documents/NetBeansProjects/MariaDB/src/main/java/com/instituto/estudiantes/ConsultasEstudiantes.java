/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.instituto.estudiantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cris
 */
public class ConsultasEstudiantes {

    Connection conn;

//Constructor de las Consultas a los Estudiantes
    public ConsultasEstudiantes(Connection conn) {
        this.conn = conn;
    }

// Creación de la tabla Estudiantes
    public void crearTablaEstudiantes() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Estudiantes ("
                + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                + "Nombre VARCHAR(255), "
                + "Edad INT, "
                + "Direccion VARCHAR(255), "
                + "Correo VARCHAR(255))";
        try (PreparedStatement preparedStatement = conn.prepareStatement(createTableQuery)) {
            preparedStatement.execute();
        }
    }

//Insertar Estudiantes
    public void insertarEstudiante(Estudiante estudiante) throws SQLException {
        String insertStudentQuery = "INSERT INTO Estudiantes (Nombre, Edad, Direccion, Correo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertStudentQuery)) {
            preparedStatement.setString(1, estudiante.getNombre());
            preparedStatement.setInt(2, estudiante.getEdad());
            preparedStatement.setString(3, estudiante.getDireccion());
            preparedStatement.setString(4, estudiante.getCorreo());
            preparedStatement.executeUpdate();
        }
    }

//Buscar Id del Estudiante por su nombre
    public List<Estudiante> buscarEstudiantePorNombre(String nombre) throws SQLException {
        List<Estudiante> estudiantesEncontrados = new ArrayList<>();

        String buscarEstudianteQuery = "SELECT * FROM Estudiantes WHERE Nombre LIKE ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(buscarEstudianteQuery)) {
            preparedStatement.setString(1, "%" + nombre + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(resultSet.getInt("ID"));
                    estudiante.setNombre(resultSet.getString("Nombre"));
                    estudiante.setEdad(resultSet.getInt("Edad"));
                    estudiante.setDireccion(resultSet.getString("Direccion"));
                    estudiante.setCorreo(resultSet.getString("Correo"));
                    estudiantesEncontrados.add(estudiante);
                }
            }
        }
        return estudiantesEncontrados;
    }

//Comprobación si exite el usuario en la base de datos
    public boolean existeEstudiante(int idEstudiante) {
        String sql = "SELECT COUNT(*) FROM estudiantes WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEstudiante);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//Consultar los estudiantes que hay en un curso
    public List<Estudiante> consultarEstudiantesEnCurso(int idCurso) throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String listStudentsQuery = "SELECT * FROM Estudiantes "
                + "INNER JOIN Inscripciones ON Estudiantes.ID = Inscripciones.IDEstudiante "
                + "WHERE Inscripciones.IDCurso = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(listStudentsQuery)) {
            preparedStatement.setInt(1, idCurso);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(resultSet.getInt("ID"));
                    estudiante.setNombre(resultSet.getString("Nombre"));
                    estudiante.setEdad(resultSet.getInt("Edad"));
                    estudiante.setDireccion(resultSet.getString("Direccion"));
                    estudiante.setCorreo(resultSet.getString("Correo"));
                    estudiantes.add(estudiante);
                }
            }
        }
        return estudiantes;
    }

    //-------------------------Actualizar Estudiantes---------------------------------
//Actualizar nombre
    public void actualizarNombreEstudiante(int idEstudiante, String nuevoNombre) throws SQLException {
        String updateNombreQuery = "UPDATE Estudiantes SET Nombre = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateNombreQuery)) {
            preparedStatement.setString(1, nuevoNombre);
            preparedStatement.setInt(2, idEstudiante);
            preparedStatement.executeUpdate();
        }
    }

//Actualizar edad
    public void actualizarEdadEstudiante(int idEstudiante, int nuevaEdad) throws SQLException {
        String updateEdadQuery = "UPDATE Estudiantes SET Edad = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateEdadQuery)) {
            preparedStatement.setInt(1, nuevaEdad);
            preparedStatement.setInt(2, idEstudiante);
            preparedStatement.executeUpdate();
        }

    }

//Actualizar Direccion
    public void actualizarDireccionEstudiante(int idEstudiante, String nuevaDireccion) throws SQLException {
        String updateDireccionQuery = "UPDATE Estudiantes SET Direccion = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateDireccionQuery)) {
            preparedStatement.setString(1, nuevaDireccion);
            preparedStatement.setInt(2, idEstudiante);
            preparedStatement.executeUpdate();
        }
    }

//Actualizar Correo
    public void actualizarCorreoEstudiante(int idEstudiante, String nuevoCorreo) throws SQLException {
        String updateCorreoQuery = "UPDATE Estudiantes SET Correo = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateCorreoQuery)) {
            preparedStatement.setString(1, nuevoCorreo);
            preparedStatement.setInt(2, idEstudiante);
            preparedStatement.executeUpdate();
        }
    }
    
//-------------------------------------------------------------------------------------

//Eliminar Estudiantes
    public void eliminarEstudiante(int idEstudiante) throws SQLException {
        String deleteEstudianteQuery = "DELETE FROM Estudiantes WHERE ID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteEstudianteQuery)) {
            preparedStatement.setInt(1, idEstudiante);
            preparedStatement.executeUpdate();
        }
    }

//Optener el nombre del estudiante por su id, para saber a que hace referencia cuando recojemos el id de la inscripción
    public Estudiante obtenerEstudiantePorID(int idEstudiante) throws SQLException {
        Estudiante estudiante = null;
        String query = "SELECT * FROM Estudiantes WHERE ID = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idEstudiante);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    estudiante = new Estudiante();
                    estudiante.setId(resultSet.getInt("ID"));
                    estudiante.setNombre(resultSet.getString("Nombre"));
                    estudiante.setEdad(resultSet.getInt("Edad"));
                    estudiante.setDireccion(resultSet.getString("Direccion"));
                    estudiante.setCorreo(resultSet.getString("Correo"));
                }
            }
        }
        return estudiante;
    }

//Para cerrar la conexion a la base de datos
    public void cerrarConexion() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
