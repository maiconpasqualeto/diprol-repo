/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.sixinf.diprol.dao.ClienteDAO;
import br.com.sixinf.diprol.entidades.Cliente;

/**
 * @author maicon
 *
 */
@ManagedBean(name="cadastroClienteBean")
@ViewScoped
public class CadastroClienteBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String codigoCEF;
	private Cliente cliente; 
	
	//private LazyDataModel<Cliente> dataModel;
	
	@PostConstruct
	public void init(){
		//dataModel = new ProdutoLazyList();
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String codCefCliente = facesContext.getExternalContext().getRequestParameterMap().get("codCEF");
		
		this.cliente = ClienteDAO.getInstance().buscarClientePorCodigo(codCefCliente);
		
	}

	public String getCodigoCEF() {
		return codigoCEF;
	}

	public void setCodigoCEF(String codigoCEF) {
		this.codigoCEF = codigoCEF;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void pesquisar() throws IOException {
		Cliente c = ClienteDAO.getInstance().buscarClientePorCodigo(codigoCEF);
		if (c == null) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("form_cliente.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("form_cliente.xhtml?codCEF=" + c.getCodCEF());
		}
	}
	
}
