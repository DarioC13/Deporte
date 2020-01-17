package jsf.clases;

import modelo.EstadoEncuentro;
import jsf.clases.util.JsfUtil;
import jsf.clases.util.JsfUtil.PersistAction;
import session.bean.EstadoEncuentroFacade;

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

@Named("estadoEncuentroController")
@SessionScoped
public class EstadoEncuentroController implements Serializable {

    @EJB
    private session.bean.EstadoEncuentroFacade ejbFacade;
    private List<EstadoEncuentro> items = null;
    private EstadoEncuentro selected;

    public EstadoEncuentroController() {
    }

    public EstadoEncuentro getSelected() {
        return selected;
    }

    public void setSelected(EstadoEncuentro selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EstadoEncuentroFacade getFacade() {
        return ejbFacade;
    }

    public EstadoEncuentro prepareCreate() {
        selected = new EstadoEncuentro();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/idioma").getString("EstadoEncuentroCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/idioma").getString("EstadoEncuentroUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/idioma").getString("EstadoEncuentroDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EstadoEncuentro> getItems() {
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

    public EstadoEncuentro getEstadoEncuentro(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<EstadoEncuentro> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EstadoEncuentro> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EstadoEncuentro.class)
    public static class EstadoEncuentroControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstadoEncuentroController controller = (EstadoEncuentroController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estadoEncuentroController");
            return controller.getEstadoEncuentro(getKey(value));
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
            if (object instanceof EstadoEncuentro) {
                EstadoEncuentro o = (EstadoEncuentro) object;
                return getStringKey(o.getIdEstadoEncuentro());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EstadoEncuentro.class.getName()});
                return null;
            }
        }

    }

}
