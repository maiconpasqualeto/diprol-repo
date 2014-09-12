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

import br.com.sixinf.diprol.dao.SegurancaDAO;
import br.com.sixinf.diprol.entidades.Usuario;
import br.com.sixinf.ferramentas.Utilitarios;

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
		if (!Utilitarios.validaCpf(usuario.getCpf())) {
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "CPF inválido", "CPF inválido");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			return;
		}			
		
		Usuario u = SegurancaDAO.getInstance().buscarUsuarioPorCpf(usuario.getCpf());
		
		if (u == null) {
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Usuário não cadastrado", "Usuário ou senha inválidos");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			return;
		}
		
		if (!u.getStatus().equals('A')) {
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Usuário desativado, acesso negado", 
						"Usuário desativado, acesso negado");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			return;
		}
		
		if (!usuario.getSenha().equals(u.getSenha())) {
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Senha inválida", "Senha inválida");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			return;
		}
		FacesContext.getCurrentInstance().getExternalContext().redirect("principal.xhtml"); 
	}
		
}
