package jsf.clases;

import java.io.File;
import java.io.IOException;
import modelo.Encuentros;
import jsf.clases.util.JsfUtil;
import jsf.clases.util.JsfUtil.PersistAction;
import session.bean.EncuentrosFacade;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Named("encuentrosController")
@SessionScoped
public class EncuentrosController implements Serializable {

    @EJB
    private session.bean.EncuentrosFacade ejbFacade;
    private List<Encuentros> items = null;
    private Encuentros selected;

    public EncuentrosController() {
    }

    public Encuentros getSelected() {
        return selected;
    }

    public void setSelected(Encuentros selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EncuentrosFacade getFacade() {
        return ejbFacade;
    }

    public Encuentros prepareCreate() {
        selected = new Encuentros();
        initializeEmbeddableKey();
        return selected;
    }
    
    private final String dirLogo = "/logo/logolimon.jpg";
    
    public void descargarPDF() throws URISyntaxException, IOException, JRException{
        File fichero = new File(getClass().getResource("/reportes/comision_de_disiplina.jasper").toURI());
        JasperReport jasperReport  = (JasperReport) JRLoader.loadObject(fichero);
        if (jasperReport != null) {
            Map parametros = new HashMap<>();
            parametros.put("sentencia", selected.getIdEncuentros());
            System.out.println(selected.getIdEncuentros());
//            parametros.put("logo", dirLogo);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conectar());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=Encuentro.pdf");
            System.out.println("Con");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, stream);            
                stream.flush();
                System.out.println("hola");
            }
            FacesContext.getCurrentInstance().responseComplete();
        }else{
            System.out.println("Archivo Nulo");
        }
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/idioma").getString("EncuentrosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/idioma").getString("EncuentrosUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/idioma").getString("EncuentrosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Encuentros> getItems() {
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

    public Encuentros getEncuentros(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Encuentros> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Encuentros> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Encuentros.class)
    public static class EncuentrosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EncuentrosController controller = (EncuentrosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "encuentrosController");
            return controller.getEncuentros(getKey(value));
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
            if (object instanceof Encuentros) {
                Encuentros o = (Encuentros) object;
                return getStringKey(o.getIdEncuentros());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Encuentros.class.getName()});
                return null;
            }
        }

    }
    
    public static Connection conectar() {
        Connection con = null;
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/db_deportes?user=root&password=";
            con = (Connection) DriverManager.getConnection(url);
            if (con != null) {
                System.out.println("Conexion Satisfactoria en BD");
            }
        } catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return con;
    }

}
