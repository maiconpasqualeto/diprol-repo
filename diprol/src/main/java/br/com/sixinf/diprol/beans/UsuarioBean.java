/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sixinf.diprol.entidades.Usuario;

/**
 * @author maicon
 *
 */
@ManagedBean(name="usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private boolean mostraCampos;
	
	@PostConstruct
	public void init() {
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean isMostraCampos() {
		return mostraCampos;
	}

	public void setMostraCampos(boolean mostraCampos) {
		this.mostraCampos = mostraCampos;
	}

	/**
	 * 
	 */
	public void confirmaCpfUsuario(){
		mostraCampos = true;
	}
		
}
