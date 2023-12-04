package com.instituto.inscripciones;

import java.sql.Date;

public class Inscripcion {

    private int id;
    private int idEstudiante;
    private int idCurso;
    private Date fechaInscripcion;

    public Inscripcion(int id, int idEstudiante, int idCurso, Date fechaInscripcion) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.idCurso = idCurso;
        this.fechaInscripcion = fechaInscripcion;
    }

    Inscripcion() {
        //Creamos el constructor vacio para que a la hora de crear la inscripción no de error si no ponemos los datos en este
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}