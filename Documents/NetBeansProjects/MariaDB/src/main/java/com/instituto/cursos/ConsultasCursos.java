/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.instituto.cursos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cristina
 */
public class ConsultasCursos {

    Connection conn;
//Constructor de las Consultas a los Cursos
    public ConsultasCursos(Connection conn) {
        this.conn = conn;
    }

//Creación de la tabla Cursos
    public void crearTablaCursos() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Cursos ("
                + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                + "NombreCurso VARCHAR(255), "
                + "Descripcion VARCHAR(255), "
                + "Creditos INT)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(createTableQuery)) {
            preparedStatement.execute();
        }
    }

//Insertar Curso
    public void insertarCurso(Curso curso) throws SQLException {
        String insertCourseQuery = "INSERT INTO Cursos (NombreCurso, Descripcion, Creditos) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertCourseQuery)) {
            preparedStatement.setString(1, curso.getNombreCurso());
            preparedStatement.setString(2, curso.getDescripcion());
            preparedStatement.setInt(3, curso.getCreditos());
            preparedStatement.executeUpdate();
        }
    }

//Buscar Id del Curso por su nombre
    public List<Curso> buscarCursoPorNombre(String nombreCurso) throws SQLException {
        List<Curso> cursosEncontrados = new ArrayList<>();

        String buscarCursoQuery = "SELECT * FROM Cursos WHERE NombreCurso LIKE ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(buscarCursoQuery)) {
            preparedStatement.setString(1, "%" + nombreCurso + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Curso curso = new Curso();
                    curso.setId(resultSet.getInt("ID"));
                    curso.setNombreCurso(resultSet.getString("NombreCurso"));
                    curso.setDescripcion(resultSet.getString("Descripcion"));
                    curso.setCreditos(resultSet.getInt("Creditos"));
                    cursosEncontrados.add(curso);
                }
            }
        }
        return cursosEncontrados;
    }

    //-------------------------Actualizar Cursos---------------------------------
//Actualizar nombre
    public void actualizarNombreCurso(int idCurso, String nuevoNombre) throws SQLException {
        String updateNombreQuery = "UPDATE Cursos SET NombreCurso = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateNombreQuery)) {
            preparedStatement.setString(1, nuevoNombre);
            preparedStatement.setInt(2, idCurso);
            preparedStatement.executeUpdate();
        }
    }

//Actualizar descripción
    public void actualizarDescripcionCurso(int idCurso, String nuevaDescripcion) throws SQLException {
        String updateDescripcionQuery = "UPDATE Cursos SET Descripcion = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateDescripcionQuery)) {
            preparedStatement.setString(1, nuevaDescripcion);
            preparedStatement.setInt(2, idCurso);
            preparedStatement.executeUpdate();
        }
    }

//Actualizar creditos
    public void actualizarCreditosCurso(int idCurso, int nuevosCreditos) throws SQLException {
        String updateCreditosQuery = "UPDATE Cursos SET Creditos = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateCreditosQuery)) {
            preparedStatement.setInt(1, nuevosCreditos);
            preparedStatement.setInt(2, idCurso);
            preparedStatement.executeUpdate();
        }
    }

//------------------------------------------------------------------------------------------------

//Optener el nombre del curso por su id, para saber a que hace referencia cuando recojemos el id de la inscripción
    public Curso obtenerCursoPorID(int idCurso) throws SQLException {
        Curso curso = null;
        String query = "SELECT * FROM Cursos WHERE ID = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idCurso);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    curso = new Curso();
                    curso.setId(resultSet.getInt("ID"));
                    curso.setNombreCurso(resultSet.getString("NombreCurso"));
                    curso.setDescripcion(resultSet.getString("Descripcion"));
                    curso.setCreditos(resultSet.getInt("Creditos"));
                }
            }
        }
        return curso;
    }

//Comprobación si exite el curso en la base de datos
    public boolean existeCurso(int idCurso) {
        String sql = "SELECT COUNT(*) FROM cursos WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
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

//Consultar los cursos que tiene en un estudiante
    public List<Curso> consultarCursosDeEstudiante(int idEstudiante) throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        String listCoursesQuery = "SELECT Cursos.* FROM Cursos "
                + "INNER JOIN Inscripciones ON Cursos.ID = Inscripciones.IDCurso "
                + "WHERE Inscripciones.IDEstudiante = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(listCoursesQuery)) {
            preparedStatement.setInt(1, idEstudiante);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Curso curso = new Curso();
                    curso.setId(resultSet.getInt("ID"));
                    curso.setNombreCurso(resultSet.getString("NombreCurso"));
                    curso.setDescripcion(resultSet.getString("Descripcion"));
                    curso.setCreditos(resultSet.getInt("Creditos"));
                    cursos.add(curso);
                }
            }
        }
        return cursos;
    }

//Eliminar Cursos
    public void eliminarCurso(int idCurso) throws SQLException {
        String deleteCourseQuery = "DELETE FROM Cursos WHERE ID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteCourseQuery)) {
            preparedStatement.setInt(1, idCurso);
            preparedStatement.executeUpdate();
        }
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
