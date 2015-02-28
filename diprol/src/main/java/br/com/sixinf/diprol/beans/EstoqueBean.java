/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import br.com.sixinf.diprol.dao.ClienteDAO;
import br.com.sixinf.diprol.dao.EstoqueDAO;
import br.com.sixinf.diprol.entidades.Campanha;
import br.com.sixinf.diprol.entidades.Cliente;
import br.com.sixinf.diprol.entidades.Estoque;
import br.com.sixinf.diprol.entidades.Movimento;
import br.com.sixinf.diprol.facade.DiprolFacade;
import br.com.sixinf.ferramentas.log.LoggerException;

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
	private String codCEF;
	private Campanha campanhaPermuta;
	private boolean mostraCampos = false;
	private boolean mostraCampoPermuta = false;
	private boolean botaoConfirmaAtivo = false;
	private List<Campanha> campanhasPermuta;
	private List<Cliente> clientesPesquisa;
	private String parPesquisaCliente;
	private Cliente clienteSelecionadoPesquisa;
	private Campanha campanhaDevolucao;
	private List<Campanha> campanhasDevolucao;
	private boolean mostraCampoDevolucao;
	private Campanha campanhaPrincipal;
	
	@ManagedProperty(value="#{segurancaBean}")
	private SegurancaBean segurancaBean;
	
	@PostConstruct
	public void init() {
		
		campanhas = EstoqueDAO.getInstance().buscarCampanhasAtivasNoPeriodo(new Date());
		if (campanhas == null ||
				campanhas.isEmpty()) {
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Não existe campanha para o período", 
						"Não existe campanha para o período");
			FacesContext.getCurrentInstance().addMessage(null, m);
			return;
		}
		
		String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
		
		if (uri.contains("form_estoque.xhtml"))
			movimentos = EstoqueDAO.getInstance().buscarMovimentosAtivosPorTipoCliente("diprol");
		else
			if (uri.contains("form_vendas.xhtml"))
			movimentos = EstoqueDAO.getInstance().buscarMovimentosAtivosPorTipoCliente("loterica");
		
		ufs = new ArrayList<String>();
		ufs.add("MS");
		ufs.add("MT");
		
		botaoConfirmaAtivo = true;
		
		estoque = new Estoque();
		estoque.setDataEnvio(new Date());
		
		clientesPesquisa = new ArrayList<Cliente>();
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

	public String getCodCEF() {
		return codCEF;
	}

	public void setCodCEF(String codCEF) {
		this.codCEF = codCEF;
	}

	public Campanha getCampanhaPermuta() {
		return campanhaPermuta;
	}

	public void setCampanhaPermuta(Campanha campanhaPermuta) {
		this.campanhaPermuta = campanhaPermuta;
	}

	public boolean isMostraCampos() {
		return mostraCampos;
	}

	public void setMostraCampos(boolean mostraCampos) {
		this.mostraCampos = mostraCampos;
	}

	public boolean isMostraCampoPermuta() {
		return mostraCampoPermuta;
	}

	public void setMostraCampoPermuta(boolean mostraCampoPermuta) {
		this.mostraCampoPermuta = mostraCampoPermuta;
	}

	public boolean isBotaoConfirmaAtivo() {
		return botaoConfirmaAtivo;
	}

	public void setBotaoConfirmaAtivo(boolean botaoConfirmaAtivo) {
		this.botaoConfirmaAtivo = botaoConfirmaAtivo;
	}

	public List<Campanha> getCampanhasPermuta() {
		return campanhasPermuta;
	}

	public void setCampanhasPermuta(List<Campanha> campanhasPermuta) {
		this.campanhasPermuta = campanhasPermuta;
	}

	public List<Cliente> getClientesPesquisa() {
		return clientesPesquisa;
	}

	public void setClientesPesquisa(List<Cliente> clientesPesquisa) {
		this.clientesPesquisa = clientesPesquisa;
	}

	public String getParPesquisaCliente() {
		return parPesquisaCliente;
	}

	public void setParPesquisaCliente(String parPesquisaCliente) {
		this.parPesquisaCliente = parPesquisaCliente;
	}

	public Cliente getClienteSelecionadoPesquisa() {
		return clienteSelecionadoPesquisa;
	}

	public void setClienteSelecionadoPesquisa(Cliente clienteSelecionadoPesquisa) {
		this.clienteSelecionadoPesquisa = clienteSelecionadoPesquisa;
	}

	public Campanha getCampanhaDevolucao() {
		return campanhaDevolucao;
	}

	public void setCampanhaDevolucao(Campanha campanhaDevolucao) {
		this.campanhaDevolucao = campanhaDevolucao;
	}

	public List<Campanha> getCampanhasDevolucao() {
		return campanhasDevolucao;
	}

	public void setCampanhasDevolucao(List<Campanha> campanhasDevolucao) {
		this.campanhasDevolucao = campanhasDevolucao;
	}

	public boolean isMostraCampoDevolucao() {
		return mostraCampoDevolucao;
	}

	public void setMostraCampoDevolucao(boolean mostraCampoDevolucao) {
		this.mostraCampoDevolucao = mostraCampoDevolucao;
	}

	public Campanha getCampanhaPrincipal() {
		return campanhaPrincipal;
	}

	public void setCampanhaPrincipal(Campanha campanhaPrincipal) {
		this.campanhaPrincipal = campanhaPrincipal;
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
	public void confirmaEstoque(){
		try {
			
			String codigoCEF = "";
			String codigoCEFContra = "";
			if (estoque.getUf().equals("MS")) {
				codigoCEF = "07.000000-0";
				codigoCEFContra = "10.000000-0";
			} else { 
				codigoCEF = "10.000000-0";
				codigoCEFContra = "07.000000-0";
			}
			
			estoque.setCampanha(campanhaPrincipal);
			
			DiprolFacade.getInstance().salvarEstoque(
					estoque, ufDestino, codigoCEF, codigoCEFContra,
						segurancaBean.getUsuario().getCpf(), null);
			
			FacesMessage m = new FacesMessage("Registro salvo com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			estoque = new Estoque();
			estoque.setDataEnvio(new Date());
			//campanhaPrincipal = null;
			
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Erro ao gravar venda", e);
			
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
	
	/**
	 * 
	 */
	public void confirmaInicioVendas(){
		mostraCampos = false;
		mostraCampoDevolucao = false;
		
		Cliente c = ClienteDAO.getInstance().buscarClientePorCodigo(codCEF);
		if (c == null) {
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Código CEF não encontrado", 
						"Código CEF não encontrado");
			FacesContext.getCurrentInstance().addMessage(null, m);
			return;
		}
		
		if (estoque.getMovimento().getCodMovimento().intValue() == 13) {
			
			campanhasPermuta = EstoqueDAO.getInstance().buscarCampanhasAtivasEncerradas();
			
			if (campanhasPermuta == null || 
					campanhasPermuta.isEmpty()) {
				FacesMessage m = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Não existem campanhas FECHADAS cadastradas", 
							"Não existem campanhas FECHADAS cadastradas");
				FacesContext.getCurrentInstance().addMessage(null, m);
				return;
			}
		} else {
			
			if (estoque.getMovimento().getCodMovimento().intValue() == 17) {
				
				campanhasDevolucao = EstoqueDAO.getInstance().buscarCampanhasAtivasEncerradas();
				
				if (campanhasDevolucao == null || 
						campanhasDevolucao.isEmpty()) {
					FacesMessage m = new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Não existem campanhas ANTERIORES cadastradas", 
								"Não existem campanhas ANTERIORES cadastradas");
					FacesContext.getCurrentInstance().addMessage(null, m);
					return;
				}
				
				mostraCampoDevolucao = true;
				
			} 
			
			campanhasPermuta = EstoqueDAO.getInstance().buscarCampanhasAtivasPosteriores(campanhaPrincipal);
			
			if (campanhasPermuta == null || 
					campanhasPermuta.isEmpty()) {
				FacesMessage m = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Não existem campanhas ABERTAS cadastradas", 
							"Não existem campanhas ABERTAS cadastradas");
				FacesContext.getCurrentInstance().addMessage(null, m);
				return;
			}
		} 
		
		
		
		if ( 'S' == estoque.getMovimento().getPermuta().charValue() ) 
			mostraCampoPermuta = true;
		else
			mostraCampoPermuta = false;
		
		estoque.setUf(c.getUf());
		
		if ( estoque.getMovimento().getGeraContrapartida() != null && 
				'S' == estoque.getMovimento().getGeraContrapartida().charValue() ) {
			
			ufDestino = c.getUf();
						
			mostraCamposContrap = true;
			
		} else
			mostraCamposContrap = false;
		
		mostraCampos = true;
	}
	
	/**
	 * @throws LoggerException 
	 * 
	 */
	public void confirmaVenda() {
		try {
			if (!mostraCampos) {
				FacesMessage m = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Nenhum registro lançado", 
							"Nenhum registro lançado");
				FacesContext.getCurrentInstance().addMessage(null, m);
				return;
			}
			String codigoCEFContra = "";
			if (ufDestino.equals("MS"))
				codigoCEFContra = "07.000000-0";
			else 
				codigoCEFContra = "10.000000-0";
			
			// e for devolução sem troca, a campanha atual do estoque tem que ser a campanha passada
			// cod movimento = 17
			if (mostraCampoDevolucao) {
				estoque.setCampanha(campanhaDevolucao);
			} else {
				estoque.setCampanha(campanhaPrincipal);
			}
									
			DiprolFacade.getInstance().salvarEstoque(
					estoque, ufDestino, codCEF, codigoCEFContra, 
					segurancaBean.getUsuario().getCpf(), campanhaPermuta);
			
			FacesMessage m = new FacesMessage("Registro salvo com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, m);
			
			Movimento mv = estoque.getMovimento();
			
			estoque = new Estoque();
			estoque.setDataEnvio(new Date());
			estoque.setMovimento(mv);
			
			//codCEF = null;
			campanhaPermuta = null;
			mostraCampos = false;
			mostraCampoPermuta = false;
			mostraCampoDevolucao = false;
			
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Erro ao gravar venda", e);
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Erro ao gravar venda", "Erro ao gravar venda");
			FacesContext.getCurrentInstance().addMessage(null, m);
		}
		
	}
	
	/**
	 * 
	 */
	public void pesquisarCliente(){
		clientesPesquisa = ClienteDAO.getInstance().buscarClientesPorFiltroRazaoSocial(parPesquisaCliente);
	}
	
	/**
	 * 
	 */
	public void pesquisaSelecionado(){
		if (clienteSelecionadoPesquisa != null) {
			this.codCEF = clienteSelecionadoPesquisa.getCodCEF();
			
			this.parPesquisaCliente = null;
			this.clienteSelecionadoPesquisa = null;
			this.clientesPesquisa = null;
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void onRowDblClckSelect(final SelectEvent event) {
	    clienteSelecionadoPesquisa = (Cliente) event.getObject();
		pesquisaSelecionado();
	}
	
}
