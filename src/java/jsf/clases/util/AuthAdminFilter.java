/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.clases.util;

import controlador.AccesoSistema;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Diego Cale Pillco
 */
//@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthAdminFilter implements Filter {
    
    private FilterConfig configuration;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.configuration = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, NullPointerException {
        if (((HttpServletRequest) request).getSession().getAttribute(
                AccesoSistema.USER_KEY) == null) {
            System.out.println("Bloqueando Acceso..");
            ((HttpServletResponse) response).sendRedirect("../accessNegado.xhtml");
        } else {
            System.out.println("Continua Proceso..");
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        configuration = null;
    }
}
