package com.example.myapplication.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SanitAbastecimiento {

    @SerializedName("idAbastecimiento")
    @Expose
    private Integer idAbastecimiento;
    @SerializedName("tipoAbastecimiento")
    @Expose
    private String tipoAbastecimiento;
    @SerializedName("usuarioCreacion")
    @Expose
    private String usuarioCreacion;
    @SerializedName("usuarioModificacion")
    @Expose
    private String usuarioModificacion;
    @SerializedName("usuarioEliminacion")
    @Expose
    private String usuarioEliminacion;
    @SerializedName("fechaCreacion")
    @Expose
    private String fechaCreacion;
    @SerializedName("fechaModificacion")
    @Expose
    private String fechaModificacion;
    @SerializedName("fechaEliminacion")
    @Expose
    private String fechaEliminacion;

    @SerializedName("opcion")
    @Expose
    private String opcion = "";

    @SerializedName("foto")
    @Expose
    private byte[] foto;

    public Integer getIdAbastecimiento() {
        return idAbastecimiento;
    }

    public void setIdAbastecimiento(Integer idAbastecimiento) {
        this.idAbastecimiento = idAbastecimiento;
    }

    public String getTipoAbastecimiento() {
        return tipoAbastecimiento;
    }

    public void setTipoAbastecimiento(String tipoAbastecimiento) {
        this.tipoAbastecimiento = tipoAbastecimiento;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getUsuarioEliminacion() {
        return usuarioEliminacion;
    }

    public void setUsuarioEliminacion(String usuarioEliminacion) {
        this.usuarioEliminacion = usuarioEliminacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(String fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

}
