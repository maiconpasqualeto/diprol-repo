/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import br.com.sixinf.diprol.dao.FinanceiroDAO;
import br.com.sixinf.diprol.entidades.Conta;
import br.com.sixinf.diprol.entidades.ContaGrupo;
import br.com.sixinf.diprol.entidades.ContaMovimento;
import br.com.sixinf.diprol.entidades.TipoConta;
import br.com.sixinf.diprol.facade.DiprolFacade;

/**
 * @author maicon
 *
 */
@ManagedBean(name="financeiroBean")
@ViewScoped
public class FinanceiroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ano;
	private String[] listaAnos = 
			new String[]{"2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024"};
	
	private List<Conta> contas = new ArrayList<Conta>();
	private ContaMovimento[] contasMovimento = new ContaMovimento[12];
	private boolean renderPrevisao;
	
	private Conta conta;
	
	// Realizado
	private List<SelectItem> meses = new ArrayList<SelectItem>();
	private Integer mes;
	private List<ContaGrupo> contasGrupo = new ArrayList<ContaGrupo>();
	private ContaGrupo contaGrupo;
	private boolean renderRealizado;
	
	private List<ContaMovimento> contasMovimentoRealizado = new ArrayList<ContaMovimento>();
	
	
	@ManagedProperty(value="#{segurancaBean}")
	private SegurancaBean segurancaBean;
		
	@PostConstruct
	private void init() {
		contas = FinanceiroDAO.getInstance().buscarContasPorTipo(TipoConta.DESPESAS);
		
		for (int i=0; i<contasMovimento.length; i++) 
			contasMovimento[i] = new ContaMovimento();
		
		renderPrevisao = false;
		
		meses.add(new SelectItem(1, "Janeiro"));
		meses.add(new SelectItem(2, "Fevereiro"));
		meses.add(new SelectItem(3, "Março"));
		meses.add(new SelectItem(4, "Abril"));
		meses.add(new SelectItem(5, "Maio"));
		meses.add(new SelectItem(6, "Junho"));
		meses.add(new SelectItem(7, "Julho"));
		meses.add(new SelectItem(8, "Agosto"));
		meses.add(new SelectItem(9, "Setembro"));
		meses.add(new SelectItem(10, "Outubro"));
		meses.add(new SelectItem(11, "Novembro"));
		meses.add(new SelectItem(12, "Dezembro"));
		
		contasGrupo = FinanceiroDAO.getInstance().buscarTodosGruposConta();
		
		renderRealizado = false;
	}
	
	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String[] getListaAnos() {
		return listaAnos;
	}

	public void setListaAnos(String[] listaAnos) {
		this.listaAnos = listaAnos;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public boolean isRenderPrevisao() {
		return renderPrevisao;
	}

	public void setRenderPrevisao(boolean renderPrevisao) {
		this.renderPrevisao = renderPrevisao;
	}
	
	public ContaMovimento[] getContasMovimento() {
		return contasMovimento;
	}

	public void setContasMovimento(ContaMovimento[] contasMovimento) {
		this.contasMovimento = contasMovimento;
	}

	public SegurancaBean getSegurancaBean() {
		return segurancaBean;
	}

	public void setSegurancaBean(SegurancaBean segurancaBean) {
		this.segurancaBean = segurancaBean;
	}

	public List<SelectItem> getMeses() {
		return meses;
	}

	public void setMeses(List<SelectItem> meses) {
		this.meses = meses;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public List<ContaGrupo> getContasGrupo() {
		return contasGrupo;
	}

	public void setContasGrupo(List<ContaGrupo> contasGrupo) {
		this.contasGrupo = contasGrupo;
	}

	public ContaGrupo getContaGrupo() {
		return contaGrupo;
	}

	public void setContaGrupo(ContaGrupo contaGrupo) {
		this.contaGrupo = contaGrupo;
	}

	public boolean isRenderRealizado() {
		return renderRealizado;
	}

	public void setRenderRealizado(boolean renderRealizado) {
		this.renderRealizado = renderRealizado;
	}

	public List<ContaMovimento> getContasMovimentoRealizado() {
		return contasMovimentoRealizado;
	}

	public void setContasMovimentoRealizado(
			List<ContaMovimento> contasMovimentoRealizado) {
		this.contasMovimentoRealizado = contasMovimentoRealizado;
	}

	/**
	 * 
	 */
	public void confirmaPrevisao(){
		List<ContaMovimento> movs = FinanceiroDAO.getInstance().buscarValorDozeMeses(conta, Integer.valueOf(ano));
		for (ContaMovimento c : movs) {
			switch (c.getMes()) {
			case 1: 
				contasMovimento[0] = c;
				break;
			case 2:
				contasMovimento[1] = c;
				break;
			case 3:
				contasMovimento[2] = c;
				break;
			case 4:
				contasMovimento[3] = c;
				break;
			case 5:
				contasMovimento[4] = c;
				break;
			case 6:
				contasMovimento[5] = c;
				break;
			case 7:
				contasMovimento[6] = c;
				break;
			case 8:
				contasMovimento[7] = c;
				break;
			case 9:
				contasMovimento[8] = c;
				break;
			case 10:
				contasMovimento[9] = c;
				break;
			case 11:
				contasMovimento[10] = c;
				break;
			case 12:
				contasMovimento[11] = c;
				break;
			}
		}
		
		renderPrevisao = true;
	}
	
	/**
	 * 
	 */
	public void salvarPrevisao(){
		try {
			Date dataLancamento = GregorianCalendar.getInstance().getTime();
			
			for (int i=0; i<contasMovimento.length; i++) {
				ContaMovimento c = contasMovimento[i];
				if (c.getCodContaMovimento() == null) {				
					c.setAno(Integer.valueOf(ano));
					c.setConta(conta);
					c.setDataLancamento(dataLancamento);
					c.setUsuarioCpf(segurancaBean.getUsuario().getCpf());
					c.setMes(i+1); 
				}
			}
			
			DiprolFacade.getInstance().salvarMovimentosPrevisao(contasMovimento);
			
			FacesMessage m = new FacesMessage("Registro salvo com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			init();
			
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Erro ao gravar previsao", e);
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Erro ao gravar previsao", "Erro ao gravar previsao");
			FacesContext.getCurrentInstance().addMessage(null, m);
		}
		
	}
	
	/**
	 * 
	 */
	public void confirmaRealizado(){
		contasMovimentoRealizado = FinanceiroDAO.getInstance().buscarContasMovimento(Integer.valueOf(ano), mes, contaGrupo);
		
		if (contasMovimentoRealizado.isEmpty()) {
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Não existe previsão para esta conta", 
						"Não existe previsão para esta conta");
			FacesContext.getCurrentInstance().addMessage(null, m);
			renderRealizado = false;
		} else {
			renderRealizado = true;
		}
	}
	
	
	/**
	 * 
	 */
	public void salvarRealizado(){
		try {
			
			DiprolFacade.getInstance().salvarValoreRealizadosMovimentos(contasMovimentoRealizado);
			
			FacesMessage m = new FacesMessage("Registro salvo com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			renderRealizado = false;
			
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Erro ao gravar valores realizados", e);
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Erro ao gravar valores realizados", 
						"Erro ao gravar valores realizados");
			FacesContext.getCurrentInstance().addMessage(null, m);
		}
		
	}
	
}
