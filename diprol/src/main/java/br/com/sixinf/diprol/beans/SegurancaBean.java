/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.sixinf.diprol.entidades.Usuario;

/**
 * @author maicon
 *
 */
@ManagedBean(name="segurancaBean")
@ViewScoped
public class SegurancaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	@PostConstruct
	public void init() {
		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void logar() throws IOException {
		if (!usuario.getCpf().equals("admin") &&
				!usuario.getSenha().equals("admin")) {
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "CPF ou senha inválidos", "CPF ou senha inválidos");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			return;
		}
		FacesContext.getCurrentInstance().getExternalContext().redirect("principal.xhtml"); 
	}
		
}
