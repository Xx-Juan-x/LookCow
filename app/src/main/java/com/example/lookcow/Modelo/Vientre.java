package com.example.lookcow.Modelo;

import java.sql.Timestamp;
import java.util.Date;

public class Vientre {
    private int id;
    private String fechaInicio;
    private String fechaParto;
    private String observaciones;
    private String estado;

    @Override
    public String toString() {

        return String.format("%-8s %-10s %-10s %-20s %-8s", id, fechaInicio, fechaParto, observaciones, estado);
    }

    public Vientre(int id, String fechaInicio, String fechaParto, String observaciones, String estado) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaParto = fechaParto;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public Vientre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaParto() {
        return fechaParto;
    }

    public void setFechaParto(String fechaParto) {
        this.fechaParto = fechaParto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
