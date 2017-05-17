package com.unicauca.divsalud.managedbeans;

import com.unicauca.divsalud.entidades.Programas;
import com.unicauca.divsalud.managedbeans.util.JsfUtil;
import com.unicauca.divsalud.managedbeans.util.JsfUtil.PersistAction;
import com.unicauca.divsalud.sessionbeans.ProgramasFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;

@Named("programasController")
@SessionScoped
public class ProgramasController implements Serializable {

    @EJB
    private com.unicauca.divsalud.sessionbeans.ProgramasFacade ejbFacade;
    private List<Programas> items = null;
    private Programas selected;

    public ProgramasController() {
    }

    public Programas getSelected() {
        return selected;
    }

    public void setSelected(Programas selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProgramasFacade getFacade() {
        return ejbFacade;
    }

    public Programas prepareCreate() {
        selected = new Programas();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProgramasCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProgramasUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProgramasDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Programas> getItems() {
        //update();
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    public void seleccionPrograma(ValueChangeEvent e) {

        String departamentoSeleccionado = e.getNewValue().toString();

        switch (departamentoSeleccionado) {
            case "1":
                items = getFacade().buscarPorIdPrograma(1);
                break;
            case "2":
                items = getFacade().buscarPorIdPrograma(2);
                break;
            case "3":
                items = getFacade().buscarPorIdPrograma(3);
                break;
            case "4":
                items = getFacade().buscarPorIdPrograma(4);
                break;
            case "5":
                items = getFacade().buscarPorIdPrograma(5);
                break;
            case "6":
                items = getFacade().buscarPorIdPrograma(6);
                break;
            case "7":
                items = getFacade().buscarPorIdPrograma(7);
                break;
            case "8":
                items = getFacade().buscarPorIdPrograma(8);
                break;
            case "9":
                items = getFacade().buscarPorIdPrograma(9);
                break;
        }

    }
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Programas getProgramas(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Programas> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Programas> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Programas.class)
    public static class ProgramasControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProgramasController controller = (ProgramasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "programasController");
            return controller.getProgramas(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Programas) {
                Programas o = (Programas) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Programas.class.getName()});
                return null;
            }
        }

    }

}
