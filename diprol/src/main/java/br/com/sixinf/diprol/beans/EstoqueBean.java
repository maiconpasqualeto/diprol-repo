/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.sixinf.diprol.dao.EstoqueDAO;
import br.com.sixinf.diprol.entidades.Campanha;
import br.com.sixinf.diprol.entidades.Estoque;
import br.com.sixinf.diprol.entidades.Movimento;
import br.com.sixinf.diprol.facade.DiprolFacade;

/**
 * @author maicon
 *
 */
@ManagedBean(name="estoqueBean")
@ViewScoped
public class EstoqueBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Campanha> campanhas;
	private List<String> ufs;
	private List<Movimento> movimentos;
	private Estoque estoque;
	private String ufSelecionado;
	private boolean mostraCamposContrap = false;
	private String ufDestino;
	
	@ManagedProperty(value="#{segurancaBean}")
	private SegurancaBean segurancaBean;
	
	@PostConstruct
	public void init() {
		campanhas = EstoqueDAO.getInstance().buscarCampanhasAtivas();
		
		movimentos = EstoqueDAO.getInstance().buscarMovimentosAtivosPorTipoCliente("diprol");
		
		ufs = new ArrayList<String>();
		ufs.add("MS");
		ufs.add("MT");
		
		estoque = new Estoque();
	}
	
	public List<Campanha> getCampanhas() {
		return campanhas;
	}

	public void setCampanhas(List<Campanha> campanhas) {
		this.campanhas = campanhas;
	}

	public List<String> getUfs() {
		return ufs;
	}

	public void setUfs(List<String> ufs) {
		this.ufs = ufs;
	}

	public List<Movimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}
	
	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public boolean isMostraCamposContrap() {
		return mostraCamposContrap;
	}

	public void setMostraCamposContrap(boolean mostraCamposContrap) {
		this.mostraCamposContrap = mostraCamposContrap;
	}

	public String getUfSelecionado() {
		return ufSelecionado;
	}

	public void setUfSelecionado(String ufSelecionado) {
		this.ufSelecionado = ufSelecionado;
	}

	public String getUfDestino() {
		return ufDestino;
	}

	public void setUfDestino(String ufDestino) {
		this.ufDestino = ufDestino;
	}

	public SegurancaBean getSegurancaBean() {
		return segurancaBean;
	}

	public void setSegurancaBean(SegurancaBean segurancaBean) {
		this.segurancaBean = segurancaBean;
	}

	/**
	 * 
	 * @param event
	 */
	public void movimentoChange(){
		
		if ( estoque.getMovimento().getGeraContrapartida() != null && 
				estoque.getMovimento().getGeraContrapartida().equals('S') ) {
			
			if (estoque.getUf().equals("MS"))
				ufDestino = "MT";
			else
				ufDestino = "MS";
			mostraCamposContrap = true;
		} else {
			ufDestino = "";
			mostraCamposContrap = false;
		}
	}

	/**
	 * 
	 */
	public void confirma(){
		try {
			
			DiprolFacade.getInstance().salvarEstoque(estoque, ufDestino, segurancaBean.getUsuario().getCpf());
			
			FacesMessage m = new FacesMessage("Registro salvo com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			estoque = new Estoque();
			
		} catch (Exception e) {
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Erro ao gravar estoque", "Erro ao gravar estoque");
			FacesContext.getCurrentInstance().addMessage(null, m);
		}
		
	}
	
	/**
	 * 
	 */
	public void ufChange(){
		if (mostraCamposContrap)
			if (estoque.getUf().equals("MS"))
				ufDestino = "MT";
			else
				ufDestino = "MS";
	}
	
}
