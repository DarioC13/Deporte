package jsf.clases;

import modelo.TipoAlbitro;
import jsf.clases.util.JsfUtil;
import jsf.clases.util.JsfUtil.PersistAction;
import session.bean.TipoAlbitroFacade;

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

@Named("tipoAlbitroController")
@SessionScoped
public class TipoAlbitroController implements Serializable {

    @EJB
    private session.bean.TipoAlbitroFacade ejbFacade;
    private List<TipoAlbitro> items = null;
    private TipoAlbitro selected;

    public TipoAlbitroController() {
    }

    public TipoAlbitro getSelected() {
        return selected;
    }

    public void setSelected(TipoAlbitro selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoAlbitroFacade getFacade() {
        return ejbFacade;
    }

    public TipoAlbitro prepareCreate() {
        selected = new TipoAlbitro();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/idioma").getString("TipoAlbitroCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/idioma").getString("TipoAlbitroUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/idioma").getString("TipoAlbitroDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoAlbitro> getItems() {
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

    public TipoAlbitro getTipoAlbitro(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<TipoAlbitro> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TipoAlbitro> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TipoAlbitro.class)
    public static class TipoAlbitroControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoAlbitroController controller = (TipoAlbitroController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoAlbitroController");
            return controller.getTipoAlbitro(getKey(value));
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
            if (object instanceof TipoAlbitro) {
                TipoAlbitro o = (TipoAlbitro) object;
                return getStringKey(o.getIdTipoAlbitro());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoAlbitro.class.getName()});
                return null;
            }
        }

    }

}
