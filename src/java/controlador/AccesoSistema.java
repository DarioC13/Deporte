/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import jsf.clases.util.SessionUtils;
import modelo.Usuario;
import session.bean.UsuarioFacade;

/**
 *
 * @author Francisco
 */
@Named(value = "accesoSistema")
@SessionScoped
public class AccesoSistema implements Serializable{
     public final static String USER_KEY = "auth_user";
    private static final long serialVersionUID = 1094801825228386363L;
    @EJB
    private UsuarioFacade ejbFacade;
    private Usuario selected= new Usuario();
    
    public AccesoSistema() {
        selected = new Usuario();
    }
    

    public void doLogin(ActionEvent e) throws IOException {
        Usuario us = getEjbFacade().validarUsuario(selected.getUsuario(), selected.getPassword());
        if (us != null) {
            gurdarSesion(us);
            switch (us.getIdRol().getIdRol()) {
                case 1:
                    asignarRecursoWeb("/administrador/admin.xhtml");
                    break;
                case 2:
                    asignarRecursoWeb("/administrador/IngresoAdmin.xhtml");
                    break;
                default:
                    break;
            }
        }else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso ", "Credenciales Incorrectas"));
        }
        
    }

    public void logout() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        extContext.getSessionMap().remove(USER_KEY);
        String url = extContext.encodeActionURL(
                context.getApplication().getViewHandler().getActionURL(context, "/login.xhtml"));
        extContext.redirect(url);
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
    }

    public void asignarRecursoWeb(String path) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        String url = extContext.encodeActionURL(
                context.getApplication().getViewHandler().getActionURL(context, path));
        extContext.getSessionMap().put(USER_KEY, new Usuario(selected.getUsuario(), selected.getPassword()));
        extContext.redirect(url);
    }


    public UsuarioFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(UsuarioFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Usuario getSelected() {
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }

    public void gurdarSesion(Usuario us) {
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("username", selected.getUsuario());
        session.setAttribute("usd", us);
    }
}
