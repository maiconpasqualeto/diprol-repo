/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.Serializable;
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
		mostraCampos = true;
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
		DiprolFacade.getInstance().calcularFechamentoCampanha(campanha);
	}
	
	/**
	 * 
	 */
	public void voltar() {
		init();
	}

}