package com.instituto.inscripciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Cris
 */
public class ConsultasInscripciones {

    Connection conn;

    public ConsultasInscripciones(Connection conn) {
        this.conn = conn;
    }

//Creación de la tabla Inscripciones
    public void crearTablaInscripciones() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Inscripciones ("
                + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                + "IDEstudiante INT, "
                + "IDCurso INT, "
                + "FechaInscripcion DATE, "
                + "FOREIGN KEY (IDEstudiante) REFERENCES Estudiantes(ID) ON DELETE CASCADE, "
                + "FOREIGN KEY (IDCurso) REFERENCES Cursos(ID) ON DELETE CASCADE)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(createTableQuery)) {
            preparedStatement.execute();
        }
    }

//Insertar inscripcion
    public void registrarInscripcion(int idEstudiante, int idCurso) throws SQLException {

        String insertEnrollmentQuery = "INSERT INTO Inscripciones (IDEstudiante, IDCurso, FechaInscripcion) VALUES (?, ?, CURDATE())";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertEnrollmentQuery)) {
            preparedStatement.setInt(1, idEstudiante);
            preparedStatement.setInt(2, idCurso);
            preparedStatement.executeUpdate();
        }
    }

//Comprobación si exite la inscripción en la base de datos
    public boolean existeInscripcion(int idInscripcion) {
        String sql = "SELECT COUNT(*) FROM inscripciones WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idInscripcion);
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

//Eliminar Incscripciones
    public void eliminarInscripcion(int idInscripcion) throws SQLException {
        String query = "DELETE FROM Inscripciones WHERE ID = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idInscripcion);
            preparedStatement.executeUpdate();
        }
    }

//Optener las incripciones que tiene un estudiante especifico
    public List<Inscripcion> obtenerInscripcionesDeEstudiante(int idEstudiante) throws SQLException {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = "SELECT * FROM Inscripciones WHERE IDEstudiante = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idEstudiante);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Inscripcion inscripcion = new Inscripcion();
                    inscripcion.setId(resultSet.getInt("ID"));
                    inscripcion.setIdEstudiante(resultSet.getInt("IDEstudiante"));
                    inscripcion.setIdCurso(resultSet.getInt("IDCurso"));
                    inscripcion.setFechaInscripcion(resultSet.getDate("FechaInscripcion"));

                    inscripciones.add(inscripcion);
                }
            }
        }
        return inscripciones;
    }

//Optener las incripciones que tiene un curso especifico
    public List<Inscripcion> obtenerInscripcionesDeCurso(int idCurso) throws SQLException {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = "SELECT * FROM Inscripciones WHERE IDCurso = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idCurso);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Inscripcion inscripcion = new Inscripcion();
                    inscripcion.setId(resultSet.getInt("ID"));
                    inscripcion.setIdEstudiante(resultSet.getInt("IDEstudiante"));
                    inscripcion.setIdCurso(resultSet.getInt("IDCurso"));
                    inscripcion.setFechaInscripcion(resultSet.getDate("FechaInscripcion"));

                    inscripciones.add(inscripcion);
                }
            }
        }
        return inscripciones;
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
