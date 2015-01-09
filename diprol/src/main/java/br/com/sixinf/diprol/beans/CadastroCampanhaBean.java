/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import br.com.sixinf.diprol.dao.CampanhaDAO;
import br.com.sixinf.diprol.entidades.Campanha;

/**
 * @author maicon
 *
 */
@ManagedBean(name="cadastroCampanhaBean")
@ViewScoped
public class CadastroCampanhaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Campanha> campanhas;
	private Campanha campanha;
	private boolean mostraCampos;
	
	@PostConstruct
	public void init() {
		campanhas = CampanhaDAO.getInstance().buscarTodasCampanhas();
		campanha = new Campanha();
		mostraCampos = false;
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
	
	public boolean isMostraCampos() {
		return mostraCampos;
	}

	public void setMostraCampos(boolean mostraCampos) {
		this.mostraCampos = mostraCampos;
	}

	/**
	 * 
	 */
	public void confirmaCampanha(){
		if (campanha == null) {
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Selecione uma campanha", "Selecione uma campanha");
			FacesContext.getCurrentInstance().addMessage(null, m);
			return;
		}
		mostraCampos = true;
	}
	
	/**
	 * 
	 */
	public void confirmaCadastroCampanha(){
		try {
			
			if (campanha.getDataInicio().compareTo(new Date()) < 0) {
				FacesMessage m = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Data de início inválida", "Data de início inválida");
				FacesContext.getCurrentInstance().addMessage(null, m);
				return;
			}
			
			if (campanha.getDataTermino().compareTo(campanha.getDataInicio()) <= 0) {
				FacesMessage m = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Data de Término inválida", "Data de Término inválida");
				FacesContext.getCurrentInstance().addMessage(null, m);
				return;
			}
			
			if (campanha.getCodCampanha() == null) {
				campanha.setQtdeRecebida(BigDecimal.ZERO);
				campanha.setQtdeReforco(BigDecimal.ZERO);
				campanha.setQtdeDevolvida(BigDecimal.ZERO);
				campanha.setSicap(BigDecimal.ZERO);
				campanha.setVendaAlternativa(BigDecimal.ZERO);
				campanha.setDevolucaoAutorizada(BigDecimal.ZERO);
				campanha.setValorUnitario(BigDecimal.ZERO);
				campanha.setValorCampanha(BigDecimal.ZERO);
				campanha.setComissaoPercentual(BigDecimal.ZERO);
				campanha.setComissaoCampanha(BigDecimal.ZERO);
				campanha.setSaldoCampanha(BigDecimal.ZERO);
				CampanhaDAO.getInstance().adicionar(campanha);
			} else 
				CampanhaDAO.getInstance().alterar(campanha);
			
			FacesMessage m = new FacesMessage("Registro salvo com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			init();
			
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Erro ao gravar campanha", e);
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Erro ao gravar campanha", "Erro ao gravar campanha");
			FacesContext.getCurrentInstance().addMessage(null, m);
		}
	}
	
	/**
	 * 
	 */
	public void novaCampanha() {
		campanha = new Campanha();
		mostraCampos = true;
	}
}
