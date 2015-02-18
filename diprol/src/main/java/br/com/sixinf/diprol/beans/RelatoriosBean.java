/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.StreamedContent;

import br.com.sixinf.diprol.dao.CampanhaDAO;
import br.com.sixinf.diprol.entidades.Campanha;
import br.com.sixinf.diprol.facade.DiprolFacade;
import br.com.sixinf.ferramentas.log.LoggerException;

/**
 * @author maicon
 *
 */
@ManagedBean(name="relatoriosBean")
@ViewScoped
public class RelatoriosBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String classificacao;
	private List<SelectItem> classificacoes = new ArrayList<SelectItem>();
	private List<Campanha> campanhas = new ArrayList<Campanha>();
	private Campanha campanha;
	private String tipoMovimento;
	
	@PostConstruct
	public void init() {
		String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
		
		if (uri.contains("rel_cliente.xhtml")) {
			classificacoes.add(new SelectItem("ALFABÉTICA"));
			classificacoes.add(new SelectItem("CÓDIGO CEF"));
		} else if (uri.contains("rel_estoque.xhtml")) {
			campanhas = CampanhaDAO.getInstance().buscarTodasCampanhas();
		}
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public List<SelectItem> getClassificacoes() {
		return classificacoes;
	}

	public void setClassificacoes(List<SelectItem> classificacoes) {
		this.classificacoes = classificacoes;
	}
	
	public List<Campanha> getCampanhas() {
		return campanhas;
	}

	public void setCampanhas(List<Campanha> campanhas) {
		this.campanhas = campanhas;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public String getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(String tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	/**
	 * 
	 * @return
	 * @throws JRException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws LoggerException
	 */
	public StreamedContent printRelEstoque() throws 
		JRException, IOException, ClassNotFoundException, SQLException, LoggerException {
		
		StreamedContent sc = null; 
				
		if (campanha.getSituacaoCampanha() == null ||
				!campanha.getSituacaoCampanha().equals("F")) 
			DiprolFacade.getInstance().geraCalculoResumoEstoque(campanha);
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("par_cod_campanha", campanha.getCodCampanha());
		parametros.put("par_campanha", campanha.getCampanha());
		
		sc = DiprolFacade.getInstance().geraReport("estoque", parametros);
		
		return sc;
	}
	
	/**
	 * 
	 * @return
	 * @throws JRException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws LoggerException
	 */
	public StreamedContent printClientes() throws 
		JRException, IOException, ClassNotFoundException, SQLException, LoggerException {  
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		if ( "ALFABÉTICA".equals(classificacao) )
			parametros.put("sort", "c.razao_social");
		else if ( "CÓDIGO CEF".equals(classificacao) )
			parametros.put("sort", "c.cod_cef");
		
		return DiprolFacade.getInstance().geraReport("clientes", parametros);
    }
}
