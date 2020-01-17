package jsf.clases;

import Upload.Util.UtilPath;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import modelo.Equipo;
import jsf.clases.util.JsfUtil;
import jsf.clases.util.JsfUtil.PersistAction;
import session.bean.EquipoFacade;

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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("equipoController")
@SessionScoped
public class EquipoController implements Serializable {

    @EJB
    private session.bean.EquipoFacade ejbFacade;
    private List<Equipo> items = null;
    private Equipo selected;
    private UploadedFile fileClubDeportivo;
    
    private static UploadedFile uploadedFile;


    public UploadedFile getFileClubDeportivo() {
        return fileClubDeportivo;
    }

    public void setFileClubDeportivo(UploadedFile fileClubDeportivo) {
        this.fileClubDeportivo = fileClubDeportivo;
    }
    
    public EquipoController() {
    }

    public Equipo getSelected() {
        return selected;
    }

    public void setSelected(Equipo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EquipoFacade getFacade() {
        return ejbFacade;
    }

    public Equipo prepareCreate() {
        selected = new Equipo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        
//        subir();
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/idioma").getString("EquipoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/idioma").getString("EquipoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/idioma").getString("EquipoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Equipo> getItems() {
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

    public Equipo getEquipo(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Equipo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Equipo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Equipo.class)
    public static class EquipoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EquipoController controller = (EquipoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "equipoController");
            return controller.getEquipo(getKey(value));
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
            if (object instanceof Equipo) {
                Equipo o = (Equipo) object;
                return getStringKey(o.getIdEquipo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Equipo.class.getName()});
                return null;
            }
        }
    
    }
     public UploadedFile getUploadedFile() {
        return uploadedFile;
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        uploadedFile = event.getFile();
    }
    
    public void subir(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String realPath = UtilPath.getPathDefinida(ec.getRealPath("/"));
        String pathDefinition = realPath + File.separator + "web" +File.separator+ "resources"+ File.separator + "foto" + File.separator + uploadedFile.getFileName();
        try {
            FileInputStream in = (FileInputStream) uploadedFile.getInputstream();
            FileOutputStream out = new FileOutputStream(pathDefinition);

            byte[] buffer = new byte[(int) uploadedFile.getSize()];
            int contador = 0;

            while ((contador = in.read(buffer)) != -1) {
                out.write(buffer, 0, contador);
            }
            in.close();
            out.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();

        }
        selected.setLogo(uploadedFile.getFileName());
    }

}
