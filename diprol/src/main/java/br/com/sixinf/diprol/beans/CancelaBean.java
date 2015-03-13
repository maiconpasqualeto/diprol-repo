/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sixinf.diprol.dao.EstoqueDAO;
import br.com.sixinf.diprol.entidades.Estoque;
import br.com.sixinf.ferramentas.log.LoggerException;

/**
 * @author maicon
 *
 */
@ManagedBean(name="cancelaBean")
@ViewScoped
public class CancelaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date dataMovimento;
	private List<Estoque> estoques = new ArrayList<Estoque>();
		
	@PostConstruct
	public void init(){
		
	}
	
	public void confirma() {
		estoques = EstoqueDAO.getInstance().buscarEstoquesCancelamento(dataMovimento);
	}

	public Date getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}

	public List<Estoque> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<Estoque> estoques) {
		this.estoques = estoques;
	}

	/**
	 * 
	 * @param e
	 * @throws LoggerException 
	 */
	public void delete(Estoque e) throws LoggerException{
		EstoqueDAO.getInstance().excluiMovimentoEstoque(e);
		confirma();
	}
}
