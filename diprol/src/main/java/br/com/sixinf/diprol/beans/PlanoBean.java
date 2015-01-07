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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

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
		contas = new ArrayList<Conta>();
		while (contas.size() < 6)
			contas.add(new Conta());
		mostraCampos = true;
	}
	
	/**
	 * 
	 */
	public void salvarPlano() {
		try {
			int i = 1;
			for (Conta c : contas) {				
				if (c.getConta().isEmpty() ^
						c.getTipo().charValue() == '\0') {
					
					FacesMessage m = new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Os campos Conta e Tipo são obrigatórios na linha " + i, 
								"Os campos Conta e Tipo são obrigatórios" + i);
					FacesContext.getCurrentInstance().addMessage(null, m);
					
					return;
				}
				i++;
			}
			
			FinanceiroDAO.getInstance().salvarPlanoDeContas(contaGrupo, contas);
			
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
