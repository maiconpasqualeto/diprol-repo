/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sixinf.diprol.dao.EstoqueDAO;
import br.com.sixinf.diprol.entidades.Movimento;

/**
 * @author maicon
 *
 */
@ManagedBean(name="estoqueBean")
@ViewScoped
public class EstoqueBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> campanhas;
	private List<String> ufs;
	private List<Movimento> movimentos;
	
	@PostConstruct
	public void init() {
		campanhas = new ArrayList<String>();
		campanhas.add("sele1");
		campanhas.add("sele2");
		campanhas.add("sele3");
		
		ufs = new ArrayList<String>();
		ufs.add("MS");
		ufs.add("MT");
		
		movimentos = EstoqueDAO.getInstance().buscarMovimentosAtivos();
	}
	
	public List<String> getCampanhas() {
		return campanhas;
	}

	public void setCampanhas(List<String> campanhas) {
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
	
	
}
