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

import br.com.sixinf.diprol.dao.FinanceiroDAO;
import br.com.sixinf.diprol.entidades.Conta;
import br.com.sixinf.diprol.entidades.ContaGrupo;

/**
 * @author maicon
 *
 */
@ManagedBean(name="planoBean")
@ViewScoped
public class PlanoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ContaGrupo> grupos;
	private ContaGrupo contaGrupo;
	private boolean mostraCampos;
	private List<Conta> contas;
	
	@PostConstruct
	public void init() {
		grupos = FinanceiroDAO.getInstance().buscarTodosGruposConta();
		mostraCampos = false;
		contas = new ArrayList<Conta>();
	}
	
	/**
	 * 
	 */
	public void confirmaGrupoSelecionado() {
		contas = FinanceiroDAO.getInstance().buscarContasPorGrupo(contaGrupo);
		while (contas.size() < 6)
			contas.add(new Conta());
		
		mostraCampos = true;
	}
	
	/**
	 * 
	 */
	public void novoGrupoConta() {
		contaGrupo = new ContaGrupo();
		mostraCampos = true;
	}
	
	/**
	 * 
	 */
	public void salvarPlano() {
		if (contaGrupo.getCodGrupoConta() == null) {
			// TODO [maicon] fazer o salvar
		} else {
			
		}
		mostraCampos = false;
	}

	public List<ContaGrupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<ContaGrupo> grupos) {
		this.grupos = grupos;
	}

	public ContaGrupo getContaGrupo() {
		return contaGrupo;
	}

	public void setContaGrupo(ContaGrupo contaGrupo) {
		this.contaGrupo = contaGrupo;
	}

	public boolean isMostraCampos() {
		return mostraCampos;
	}

	public void setMostraCampos(boolean mostraCampos) {
		this.mostraCampos = mostraCampos;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}
	
}
