/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.sixinf.diprol.dao.CampanhaDAO;
import br.com.sixinf.diprol.entidades.Campanha;
import br.com.sixinf.diprol.facade.DiprolFacade;
import br.com.sixinf.ferramentas.dao.DAOException;

/**
 * @author maicon
 *
 */
@ManagedBean(name="fechaCampanhaBean")
@ViewScoped
public class FechaCampanhaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Campanha> campanhas = new ArrayList<Campanha>();
	private Campanha campanha;
	private boolean mostraCampos;
	private BigDecimal custoRepasse;
	
	@PostConstruct
	public void init() {
		campanhas = CampanhaDAO.getInstance().buscarTodasCampanhas();
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

	public BigDecimal getCustoRepasse() {
		return custoRepasse;
	}

	public void setCustoRepasse(BigDecimal custoRepasse) {
		this.custoRepasse = custoRepasse;
	}

	public String getPeriodo() {
		if (campanha != null &&
				campanha.getDataInicio() != null &&
				campanha.getDataTermino() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(campanha.getDataInicio()) + " a " + sdf.format(campanha.getDataTermino());
		}
		return "";
	}
	
	/**
	 * 
	 */
	public void confirma() {
		custoRepasse = null;
		mostraCampos = true;
		if( !"F".equals(campanha.getSituacaoCampanha()) ) {			
			campanha.setSaldoCampanha(BigDecimal.ZERO);
			campanha.setComissaoCampanha(BigDecimal.ZERO);
			campanha.setValorCampanha(BigDecimal.ZERO);
		}
	}
	
	/**
	 * @throws DAOException 
	 * 
	 */
	public void confirmaFechar() throws DAOException {
		campanha.setSituacaoCampanha("F");
		
		CampanhaDAO.getInstance().alterar(campanha);
		
		FacesMessage m = new FacesMessage("Registro salvo com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, m);
		
		init();
	}
	
	/**
	 * 
	 */
	public void calcula() {
		DiprolFacade.getInstance().calcularFechamentoCampanha(
				campanha, custoRepasse);
	}
	
	/**
	 * 
	 */
	public void voltar() {
		init();
	}

}
