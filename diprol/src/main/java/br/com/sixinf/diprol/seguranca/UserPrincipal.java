/**
 * 
 */
package br.com.sixinf.diprol.seguranca;

import java.security.Principal;

import br.com.sixinf.diprol.entidades.Usuario;

/**
 * @author maicon
 *
 */
public class UserPrincipal implements Principal {

	private Usuario usuario;

	public UserPrincipal(Usuario usuario) {
		super();
		this.usuario = usuario;
	}
	
	public void setName(String cpf) {
		this.usuario.setCpf(cpf);
	}

	@Override
	public String getName() {
		return usuario.getCpf();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
