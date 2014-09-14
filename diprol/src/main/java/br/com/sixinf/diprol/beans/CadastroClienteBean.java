/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import br.com.sixinf.diprol.dao.ClienteDAO;
import br.com.sixinf.diprol.entidades.Cliente;
import br.com.sixinf.diprol.facade.DiprolFacade;
import br.com.sixinf.ferramentas.Utilitarios;
import br.com.sixinf.ferramentas.dao.DAOException;

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
	private boolean novo = false;
	
	//private LazyDataModel<Cliente> dataModel;
	
	@PostConstruct
	public void init(){
		//dataModel = new ProdutoLazyList();
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String codCefCliente = facesContext.getExternalContext().getRequestParameterMap().get("codCEF");
		
		this.cliente = ClienteDAO.getInstance().buscarClientePorCodigo(codCefCliente);
		
		if (this.cliente == null) {
			this.cliente = new Cliente();
			this.novo = true;
		}
		
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
	
	/**
	 * @throws DAOException 
	 * 
	 */
	public void confirma() {
		if (cliente.getCnpj() != null && 
				!cliente.getCnpj().isEmpty() &&
				!Utilitarios.validaCnpj(cliente.getCnpj())) {
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "CNPJ inválido", "CNPJ inválido");
			FacesContext.getCurrentInstance().addMessage(null, m);
			return;
			
		} 
		try {
			DiprolFacade.getInstance().salvarCliente(cliente, novo);
			
			novo = false;
			
			FacesMessage m = new FacesMessage("Registro salvo com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
		} catch (Exception e) {
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Erro ao gravar registro", "Erro ao gravar registro");
			FacesContext.getCurrentInstance().addMessage(null, m);
			return;
		}
		
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws JSONException
	 */
	public void buscarCep(String cep) throws IOException, JSONException {		
		// Objeto URL
		//URL url = new URL("http://www.buscarcep.com.br?cep=" + cep + "&formato=xml&chave=1aLqB2mpqHoH/Xa/m8jkf0Wm/S99vK0");
		URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?formato=json&cep=" + cliente.getCep());
		
		// Objeto HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// M�todo
		connection.setRequestProperty("Request-Method", "GET");

		// V�ariavel do resultado
		connection.setDoInput(true);
		connection.setDoOutput(false);

		// Faz a conex�o
		connection.connect();
		
		InputStream is = connection.getInputStream();
		byte[] buf = new byte[is.available()];
		is.read(buf);
		is.close();
		String str = new String(buf);
		
		JSONObject j = new JSONObject(str);
		String resultado = j.getString("resultado_txt");
		if (resultado.contains("sucesso")) {
			this.cliente.setEndereco(j.getString("logradouro"));
			this.cliente.setBairro(j.getString("bairro"));
			this.cliente.setCidade(j.getString("cidade"));
			this.cliente.setUf(j.getString("uf"));
		}
	}
}
