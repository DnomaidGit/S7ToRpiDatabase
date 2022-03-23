/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JLO
 */
@Entity
@Table(name = "produccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produccion.findAll", query = "SELECT p FROM Produccion p"),
    @NamedQuery(name = "Produccion.findById", query = "SELECT p FROM Produccion p WHERE p.id = :id"),
    @NamedQuery(name = "Produccion.findByFechaHora", query = "SELECT p FROM Produccion p WHERE p.fechaHora = :fechaHora"),
    @NamedQuery(name = "Produccion.findByPuestoTrabajo", query = "SELECT p FROM Produccion p WHERE p.puestoTrabajo = :puestoTrabajo"),
    @NamedQuery(name = "Produccion.findByChasis", query = "SELECT p FROM Produccion p WHERE p.chasis = :chasis"),
    @NamedQuery(name = "Produccion.findByModelo", query = "SELECT p FROM Produccion p WHERE p.modelo = :modelo"),
    @NamedQuery(name = "Produccion.findByEstado", query = "SELECT p FROM Produccion p WHERE p.estado = :estado")})
public class Produccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Column(name = "puesto_trabajo")
    private Integer puestoTrabajo;
    @Size(max = 20)
    @Column(name = "chasis")
    private String chasis;
    @Column(name = "modelo")
    private Integer modelo;
    @Column(name = "estado")
    private Integer estado;

    public Produccion() {
    }

    public Produccion(Integer id) {
        this.id = id;
    }

    public Produccion(Integer id, Date fechaHora) {
        this.id = id;
        this.fechaHora = fechaHora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public String getFechaHoraString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String strDate = df.format(fechaHora);
        return strDate;
    }


    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getPuestoTrabajo() {
        return puestoTrabajo;
    }

    public void setPuestoTrabajo(Integer puestoTrabajo) {
        this.puestoTrabajo = puestoTrabajo;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public Integer getModelo() {
        return modelo;
    }

    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produccion)) {
            return false;
        }
        Produccion other = (Produccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Produccion[ id=" + id + " ]";
    }
    
}
