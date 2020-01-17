package jsf.clases;

import modelo.AsignarPersonaEquipo;
import jsf.clases.util.JsfUtil;
import jsf.clases.util.JsfUtil.PersistAction;
import session.bean.AsignarPersonaEquipoFacade;

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

@Named("asignarPersonaEquipoController")
@SessionScoped
public class AsignarPersonaEquipoController implements Serializable {

    @EJB
    private session.bean.AsignarPersonaEquipoFacade ejbFacade;
    private List<AsignarPersonaEquipo> items = null;
    private AsignarPersonaEquipo selected;

    public AsignarPersonaEquipoController() {
    }

    public AsignarPersonaEquipo getSelected() {
        return selected;
    }

    public void setSelected(AsignarPersonaEquipo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AsignarPersonaEquipoFacade getFacade() {
        return ejbFacade;
    }

    public AsignarPersonaEquipo prepareCreate() {
        selected = new AsignarPersonaEquipo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/idioma").getString("AsignarPersonaEquipoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/idioma").getString("AsignarPersonaEquipoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/idioma").getString("AsignarPersonaEquipoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AsignarPersonaEquipo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/idioma").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/idioma").getString("PersistenceErrorOccured"));
            }
        }
    }

    public AsignarPersonaEquipo getAsignarPersonaEquipo(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<AsignarPersonaEquipo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AsignarPersonaEquipo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AsignarPersonaEquipo.class)
    public static class AsignarPersonaEquipoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AsignarPersonaEquipoController controller = (AsignarPersonaEquipoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "asignarPersonaEquipoController");
            return controller.getAsignarPersonaEquipo(getKey(value));
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
            if (object instanceof AsignarPersonaEquipo) {
                AsignarPersonaEquipo o = (AsignarPersonaEquipo) object;
                return getStringKey(o.getIdAsignarPersonaEquipo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AsignarPersonaEquipo.class.getName()});
                return null;
            }
        }

    }

}
