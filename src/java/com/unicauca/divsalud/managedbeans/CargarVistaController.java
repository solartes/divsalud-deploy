/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.divsalud.managedbeans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import org.primefaces.context.RequestContext;

@Named(value = "cargarVistaController")
@SessionScoped
public class CargarVistaController implements Serializable {

    private String ruta;

    public String getRuta() {
        return ruta;
    }

    public CargarVistaController() {

    }

    public void cargarRegistrarPaciente() {
        this.ruta = "/usuariodelsistema/profesionalsalud/enComun/paciente/Create.xhtml";
    }

    public void cargarEditarPaciente() {
        this.ruta = "/usuariodelsistema/profesionalsalud/enComun/paciente/Edit.xhtml";
    }

    public void cargarVerPaciente() {
        this.ruta = "/usuariodelsistema/profesionalsalud/enComun/paciente/View.xhtml";
    }

    public void cargarPerfilUsuario() {
        this.ruta = "/usuariodelsistema/perfilUsuario.xhtml";
    }

    //estadisticas
    public void cargarEstadisticaFacultadDiagnostico() {
        this.ruta = "/usuariodelsistema/profesionalsalud/odontologia/estadisticas/diagnosticosFacultades.xhtml";
    }

    public void cargarIndiceCOP() {
        this.ruta = "/usuariodelsistema/profesionalsalud/odontologia/estadisticas/indiceCOP.xhtml";
    }


}
