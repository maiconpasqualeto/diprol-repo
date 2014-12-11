/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import br.com.sixinf.diprol.dao.SegurancaDAO;
import br.com.sixinf.diprol.entidades.Usuario;
import br.com.sixinf.ferramentas.dao.DAOException;

/**
 * @author maicon
 *
 */
@ManagedBean(name="usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{segurancaBean}")
	private SegurancaBean segurancaBean;
	
	private Usuario usuario;
	private String cpf;
	private boolean mostraCampos;
	private String alteraSenhaCpf;
	private String alteraSenhaPass;
	
	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest) 
				FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if( req.getRequestURI().contains("alterar_senha.xhtml") )
			alteraSenhaCpf = segurancaBean.getUsuario().getCpf();
		
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public SegurancaBean getSegurancaBean() {
		return segurancaBean;
	}

	public void setSegurancaBean(SegurancaBean segurancaBean) {
		this.segurancaBean = segurancaBean;
	}

	public String getAlteraSenhaCpf() {
		return alteraSenhaCpf;
	}

	public void setAlteraSenhaCpf(String alteraSenhaCpf) {
		this.alteraSenhaCpf = alteraSenhaCpf;
	}

	public String getAlteraSenhaPass() {
		return alteraSenhaPass;
	}

	public void setAlteraSenhaPass(String alteraSenhaPass) {
		this.alteraSenhaPass = alteraSenhaPass;
	}

	/**
	 * 
	 */
	public void confirmaCpfUsuario() {
		
		if ( !"ADMIN".equals(segurancaBean.getUsuario().getPerfil()) ) {
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "USUÁRIO NÃO TEM PERMISSÃO PARA ESSA TRANSAÇÃO", 
						"USUÁRIO NÃO TEM PERMISSÃO PARA ESSA TRANSAÇÃO");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			return;
		}
		
		
		usuario = SegurancaDAO.getInstance().buscarUsuarioPorCpf(cpf);
		if (usuario == null)
			usuario = new Usuario();
		
		mostraCampos = true;
	}
	
	/**
	 * @throws DAOException 
	 * 
	 */
	public void salvar() {
		try {
			
			if (usuario.getCpf() == null) {
				usuario.setCpf(cpf);
				SegurancaDAO.getInstance().adicionar(usuario);
			} else
				SegurancaDAO.getInstance().alterar(usuario);
			
			usuario = null;
			cpf = null;
			mostraCampos = false;
			
			FacesMessage m = new FacesMessage("Registro salvo com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
		} catch (DAOException e) {
			Logger.getLogger(getClass()).error("Erro ao gravar usuário", e);
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Erro ao gravar usuário", "Erro ao gravar usuário");
			FacesContext.getCurrentInstance().addMessage(null, m);
		}
	}
	
	/**
	 * 
	 */
	public String direcionaAlterarSenha() {
		alteraSenhaCpf = segurancaBean.getUsuario().getCpf();
		
		return "alterar_senha.xhtml?faces-redirect=true";
	}
	
	/**
	 * 
	 */
	public void confirmaAlterarSenha() {
		try {
			
			SegurancaDAO.getInstance().alterarSenhaUsuario(
					alteraSenhaCpf, alteraSenhaPass);
			
			FacesMessage m = new FacesMessage("Senha alterada com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			alteraSenhaPass = null;
			
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Erro ao alterar senha", e);
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Erro ao alterar senha", "Erro ao alterar senha");
			FacesContext.getCurrentInstance().addMessage(null, m);
		}
	}
		
}
