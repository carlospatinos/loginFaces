package com.gapsi.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

@ManagedBean
public class UserLoginView implements Serializable {
	private static final long serialVersionUID = 7480483082729505751L;
	
	private final Logger log =
	          Logger.getLogger(this.getClass().getName());

	private String username;

	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void login(ActionEvent event) {
		String urlToGo = "";
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		boolean loggedIn = false;

		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		ResourceBundle bundle = null;
		try {
		    bundle = ResourceBundle.getBundle("messages", fc.getViewRoot().getLocale());
		} catch (Exception ex) {
			log.log(Level.SEVERE, "Exception loading internationalization file", ex);
		}
		
		if (username != null && username.equals("admin") && password != null && password.equals("admin")) {
			log.log(Level.INFO, "Sucessful login attempt for user [{0}]", username);
			loggedIn = true;
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("login.welcome"), username);
			urlToGo = "main.xhtml?faces-redirect=true";			
		} else {
			log.log(Level.WARNING, "Failed login attempt for user [{0}]", username);
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("login.error"), bundle.getString("login.error.msg"));
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		context.addCallbackParam("loggedIn", loggedIn);
		
		if (loggedIn) {
			try {
				ec.redirect(urlToGo);
			} catch (IOException ex) {
				log.log(Level.SEVERE, "Exception happend when redirecting logged used", ex);
			}
		}
	}
}
