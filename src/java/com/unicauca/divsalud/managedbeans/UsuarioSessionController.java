package com.unicauca.divsalud.managedbeans;

import com.unicauca.divsalud.sessionbeans.GrupoUsuarioTipoFacade;
import com.unicauca.divsalud.utilidades.Utilidades;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class UsuarioSessionController implements Serializable {

    @EJB
    private GrupoUsuarioTipoFacade ejbUsuarioTipo;

    private String nombreDeUsuario;
    private String contrasenia;
    private String nombreMostrar;
    private String identificacion;
    private boolean haySesion;
    private boolean errorSesion;

    public UsuarioSessionController() {

    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }

    public void setNombreMostrar(String nombreMostrar) {
        this.nombreMostrar = nombreMostrar;
    }

    public boolean isHaySesion() {
        return haySesion;
    }

    public void setHaySesion(boolean haySesion) {
        this.haySesion = haySesion;
    }

    public boolean isErrorSesion() {
        return errorSesion;
    }

    public void setErrorSesion(boolean errorSesion) {
        this.errorSesion = errorSesion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public void login() throws IOException, ServletException {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();

        if (req.getUserPrincipal() == null) {
            try {
                req.login(this.nombreDeUsuario, this.contrasenia);
                req.getServletContext().log("Autenticacion exitosa");
                this.haySesion = true; 
                this.errorSesion = false;

                if (this.ejbUsuarioTipo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getGrupoUsuarioTipoPK().getIdTipo() == 9 || this.ejbUsuarioTipo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getGrupoUsuarioTipoPK().getIdTipo() == 12) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/divisionsalud/faces/usuario/principal.xhtml");
                    identificacion = this.ejbUsuarioTipo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuariosSistema().getIdentificacion();
                    this.nombreMostrar = this.ejbUsuarioTipo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuariosSistema().getNombres();
                } else if (this.ejbUsuarioTipo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getGrupoUsuarioTipoPK().getIdTipo() == 8){
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/divisionsalud/faces/secretaria/principal.xhtml");
                    identificacion = this.ejbUsuarioTipo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuariosSistema().getIdentificacion();
                    this.nombreMostrar = this.ejbUsuarioTipo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuariosSistema().getNombres();
                } else if (this.ejbUsuarioTipo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getGrupoUsuarioTipoPK().getIdTipo() == 7){
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/divisionsalud/faces/usuariodelsistema/sexualidad/paciente/list.xhtml");
                    identificacion = this.ejbUsuarioTipo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuariosSistema().getIdentificacion();
                    this.nombreMostrar = this.ejbUsuarioTipo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuariosSistema().getNombres();
                }
            } catch (ServletException e) {

                this.errorSesion = true;

            }
        }

//        FacesContext.getCurrentInstance().getExternalContext().redirect("/divisionsalud/faces/usuario/principal.xhtml");
    }

    public StreamedContent getImagenFlujo() {
        return Utilidades.getImagenPorDefecto("foto");
    }
    

    public void logout() throws IOException, ServletException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        try {
            req.logout();
            req.getSession().invalidate();
            fc.getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/divisionsalud/");

        } catch (ServletException e) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILED", "Logout failed on backend"));
        }

    }

  
}
