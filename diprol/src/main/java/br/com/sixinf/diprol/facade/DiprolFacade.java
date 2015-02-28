/**
 * 
 */
package br.com.sixinf.diprol.facade;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;

import br.com.sixinf.diprol.DiprolHelper;
import br.com.sixinf.diprol.dao.CampanhaDAO;
import br.com.sixinf.diprol.dao.ClienteDAO;
import br.com.sixinf.diprol.dao.EstoqueDAO;
import br.com.sixinf.diprol.dao.FinanceiroDAO;
import br.com.sixinf.diprol.entidades.Campanha;
import br.com.sixinf.diprol.entidades.Cliente;
import br.com.sixinf.diprol.entidades.ContaMovimento;
import br.com.sixinf.diprol.entidades.Estoque;
import br.com.sixinf.diprol.entidades.Movimento;
import br.com.sixinf.diprol.entidades.ResumoEstoque;
import br.com.sixinf.ferramentas.dao.DAOException;
import br.com.sixinf.ferramentas.log.LoggerException;

/**
 * @author maicon
 *
 */
@SuppressWarnings("deprecation")
public class DiprolFacade {
	
	private static DiprolFacade facade;
	
	public static DiprolFacade getInstance() {
		if (facade == null)
			facade = new DiprolFacade();
		return facade;
	}
	
	private FinanceiroDAO financeiroDAO;
	private ClienteDAO clienteDAO;
	private EstoqueDAO estoqueDAO;
	
	
	
	public DiprolFacade() {
		financeiroDAO = FinanceiroDAO.getInstance();
		clienteDAO = ClienteDAO.getInstance();
		estoqueDAO = EstoqueDAO.getInstance();
	}
	
	/**
	 * 
	 * @param c
	 * @param novo
	 * @throws DAOException
	 */
	public void salvarCliente(Cliente c, boolean novo) throws DAOException{
		if (novo) {
			c.setStatus('A');
			c.setDataCadastro(new Date());
			clienteDAO.adicionar(c);
		} else {
			clienteDAO.alterar(c);
		}
	}

	/**
	 * 
	 * @param estoque
	 * @throws LoggerException 
	 */
	public void salvarEstoque(Estoque estoque, String ufDestino, 
			String codigoCEF, String codigoCEFContra, String usuarioCPF,
			Campanha campanhaPermuta) throws LoggerException {
		
		Estoque estContraDevolucao = null;
		Estoque estDevolucao = null;
		
		Estoque estContra = geraMovimentoEstoque(estoque, ufDestino, codigoCEF, codigoCEFContra, usuarioCPF);
		
		if ( estoque.getMovimento().getPermuta() != null && 
				estoque.getMovimento().getPermuta().equals('S') ) {
			
			Movimento mPermuta = 
					EstoqueDAO.getInstance().buscarMovimento(
							estoque.getMovimento().getCodMovimentoPermuta());
			
			estDevolucao = new Estoque();
			estDevolucao.setCampanha(campanhaPermuta);
			estDevolucao.setCodCEF(estoque.getCodCEF());
			estDevolucao.setCodCEFContrapartida(estoque.getCodCEFContrapartida());
			estDevolucao.setDataEnvio(estoque.getDataEnvio());
			estDevolucao.setDataMovimento(estoque.getDataMovimento());
			estDevolucao.setMovimento(mPermuta);
			estDevolucao.setObservacao(estoque.getObservacao());
			estDevolucao.setPac(estoque.getPac());
			estDevolucao.setQuantidade(estoque.getQuantidade());
			estDevolucao.setUf(estoque.getUf());
			estDevolucao.setUsuarioCPF(estoque.getUsuarioCPF());
			
			estContraDevolucao = geraMovimentoEstoque(estDevolucao, ufDestino, codigoCEF, codigoCEFContra, usuarioCPF);
		}
		
		estoqueDAO.salvarEstoque(estoque, estContra, estDevolucao, estContraDevolucao);
	}
	
	/**
	 * 
	 * @param estoque
	 * @param ufDestino
	 * @param codigoCEF
	 * @param codigoCEFContra
	 * @param usuarioCPF
	 * @param estContra
	 * 
	 * @return retorna o estoque de contra partida se houver
	 */
	private Estoque geraMovimentoEstoque(Estoque estoque, String ufDestino, 
			String codigoCEF, String codigoCEFContra, String usuarioCPF){
		
		Estoque estContra = null;
		
		Integer quantidadeInformada = estoque.getQuantidade();
		
		Estoque ultimoEstoque = 
				EstoqueDAO.getInstance().buscarUltimoEstoque(
						estoque.getCampanha(), estoque.getUf(), codigoCEF);
		
		Estoque ultimoEstoqueContra = 
				EstoqueDAO.getInstance().buscarUltimoEstoque(
						estoque.getCampanha(), ufDestino, codigoCEFContra);
		
		Campanha campanhaContra = estoque.getCampanha();
		Integer saldoAtualContra = 0;
		
		if (ultimoEstoqueContra != null) {
			campanhaContra = ultimoEstoqueContra.getCampanha();
			saldoAtualContra = ultimoEstoqueContra.getSaldoAtual();
		}
	
		
		Integer saldoAtualUltimoEst = 0;
		
		if (ultimoEstoque != null) 
			saldoAtualUltimoEst = ultimoEstoque.getSaldoAtual();
		
		if (estoque.getMovimento().getAcaoSaldo().equals("subtrae"))
			estoque.setQuantidade(estoque.getQuantidade() * -1);
		
		estoque.setDataMovimento(new Date());
		estoque.setSaldoAnterior(saldoAtualUltimoEst);
		estoque.setSaldoAtual(estoque.getSaldoAnterior() + estoque.getQuantidade());
		estoque.setUsuarioCPF(usuarioCPF);
		estoque.setCodCEF(codigoCEF);
		
		if ( estoque.getMovimento().getGeraContrapartida() != null && 
				estoque.getMovimento().getGeraContrapartida().equals('S') ) {
			
			estoque.setCodCEFContrapartida(codigoCEFContra);
			
			estContra = new Estoque();
			estContra.setCampanha(campanhaContra);			
			estContra.setUf(ufDestino);
			estContra.setCodCEF(codigoCEFContra);
			estContra.setUsuarioCPF(usuarioCPF);
			estContra.setObservacao(estoque.getObservacao());
			estContra.setDataMovimento(estoque.getDataMovimento());
			
			Movimento mContra = 
					EstoqueDAO.getInstance().buscarMovimento(
							estoque.getMovimento().getCodMovimentoContrapartida());
			
			estContra.setMovimento(mContra);
			estContra.setQuantidade(quantidadeInformada);
			
			if (estContra.getMovimento().getAcaoSaldo().equals("subtrae"))
				estContra.setQuantidade(estContra.getQuantidade() * -1);
			
			estContra.setSaldoAnterior(saldoAtualContra);
			estContra.setSaldoAtual(estContra.getSaldoAnterior() + estContra.getQuantidade());
		} 
		
		return estContra;
	}
	
	/**
	 * 
	 * @param contasMovimento
	 * @throws LoggerException 
	 */
	public void salvarMovimentosPrevisao(ContaMovimento[] contasMovimento) throws LoggerException{
		financeiroDAO.alterarPrevisaoMovimentos(contasMovimento);
	}
	
	/**
	 * 
	 * @param contasMovimento
	 * @throws LoggerException 
	 */
	public void salvarValoreRealizadosMovimentos(List<ContaMovimento> contasMovimento) throws LoggerException{
		financeiroDAO.alterarValoresRealizadosMovimentos(contasMovimento);
	}
	
	/**
	 * 
	 * @param nomeReport 
	 * @param atletasImpressao
	 * @return
	 * @throws LoggerException 
	 */
	public DefaultStreamedContent geraReport(String nomeReport, 
			Map<String, Object> parametros) throws LoggerException{
		
		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		ServletContext contextS = (ServletContext) externalContext.getContext();
		String arquivo = contextS
				.getRealPath("/resources/reports/" + nomeReport + ".jasper");
		
		Locale locale = new Locale("pt", "BR");
		parametros.put(JRParameter.REPORT_LOCALE, locale);
		
		try {
			
			JasperPrint print = JasperFillManager.fillReport(arquivo, parametros, 
					DiprolHelper.getConnection());
			
			JRPdfExporter exporter = new JRPdfExporter();
			
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			
			exporter.exportReport();
			is = new ByteArrayInputStream(baos.toByteArray());
			
		} catch (JRException e) {
			Logger.getLogger(getClass()).error("Erro ao gerar relatório jasper", e);
		} catch (SQLException e) {
			Logger.getLogger(getClass()).error("Erro ao gerar relatório jasper", e);
		} catch (NamingException e) {
			Logger.getLogger(getClass()).error("Erro ao gerar relatório jasper", e);
		}		
		
		return new DefaultStreamedContent(is, "application/pdf", nomeReport + ".pdf");
	}
	
	/**
	 * 
	 * @param campanha
	 */
	public void calcularFechamentoCampanha(Campanha campanha, BigDecimal valorUnitario) {
		Object[] somatorios = CampanhaDAO.getInstance().buscaSomatoriosParaFechamento(campanha);
		Long saldoMS = CampanhaDAO.getInstance().buscarSaldoAtual("MS", campanha);
		Long saldoMT = CampanhaDAO.getInstance().buscarSaldoAtual("MT", campanha);
						
		Long entrada = (Long) somatorios[0];
		Long saida = (Long) somatorios[1];
		Long transferencia = (Long) somatorios[6];
		
		BigDecimal qtdeRecebida = new BigDecimal(entrada - saida - Math.abs(transferencia));				
		BigDecimal reforco = new BigDecimal((Long) somatorios[2]);
		
		BigDecimal devolucao = new BigDecimal((Long) somatorios[3]);
		BigDecimal devolucaoSemTroca = new BigDecimal((Long) somatorios[7]);
		
		//BigDecimal qtdeDevolvida = devolucao.abs().add(devolucaoSemTroca.abs()).add(new BigDecimal(saldoMS).add(new BigDecimal(saldoMT)));
		BigDecimal qtdeDevolvida = devolucao.abs().add(new BigDecimal(saldoMS).add(new BigDecimal(saldoMT)));
		BigDecimal sicap = new BigDecimal(Math.abs((Long)somatorios[5]));
		
		BigDecimal vendaAlternativa = qtdeRecebida.add(reforco.abs()).subtract(qtdeDevolvida.abs()).subtract(sicap.abs());		
		BigDecimal comissaoFaturada = sicap.abs().multiply(campanha.getValorUnitario().divide(new BigDecimal(100))).multiply(campanha.getComissaoPercentual());
		BigDecimal aPagarEmReais = vendaAlternativa.multiply(valorUnitario);
		BigDecimal saldo = comissaoFaturada.subtract(aPagarEmReais);
		
		campanha.setQtdeRecebida(qtdeRecebida.abs());
		campanha.setQtdeReforco(reforco.abs());
		campanha.setQtdeDevolvida(qtdeDevolvida.abs());
		campanha.setSicap(sicap.abs());
		campanha.setVendaAlternativa(vendaAlternativa.abs());
		campanha.setComissaoCampanha(comissaoFaturada.abs());
		campanha.setValorCampanha(aPagarEmReais.abs());
		campanha.setSaldoCampanha(saldo.abs());
		
	}
	
	/**
	 * 
	 * @param campanha
	 * @throws LoggerException 
	 */
	public void geraCalculoResumoEstoque(Campanha campanha) throws LoggerException {
		List<Estoque> estoques = estoqueDAO.buscarEstoquesFechamentoResumo(campanha);
		Map<String, ResumoEstoque> resumos = new HashMap<String, ResumoEstoque>();
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		for (Estoque e : estoques) {
			String strDataMovimento = df.format(e.getDataMovimento());
			String chave = e.getUf() + strDataMovimento + e.getCodCEF();
			ResumoEstoque re = resumos.get(chave);
			if (re == null) {
				re = new ResumoEstoque();
				re.setCodCampanha(campanha.getCodCampanha());
				re.setUf(e.getUf());
				re.setCampanha(campanha.getCampanha());
				re.setCodCef(e.getCodCEF());
				re.setCodCefContrapartida(e.getCodCEFContrapartida());
				re.setDataMovimento(e.getDataMovimento());
				
				if ( "07.000000-0".equals(e.getCodCEF())) {
					re.setRazaoSocial("Diprol MS");
					re.setCanalAtendimento("Campo Grande");
					re.setCidade("Campo Grande");
					
				} else if ( "10.000000-0".equals(e.getCodCEF())) {
						re.setRazaoSocial("Diprol MT");
						re.setCanalAtendimento("Cuiabá");
						re.setCidade("Cuiabá");
				} else {
					Cliente c = ClienteDAO.getInstance().buscarClientePorCodigo(e.getCodCEF());
					if (c != null) {
						re.setRazaoSocial(c.getRazaoSocial());
						re.setCanalAtendimento(c.getCanalAtendimento());
						re.setCidade(c.getCidade());
					}
				}
				re.setSaldoAnterior(0);
				re.setEntrada(0);
				re.setReforco(0);
				re.setFatura(0);
				re.setAvista(0);
				re.setGratis(0);
				re.setNota(0);
				re.setBalcao(0);
				re.setDevolucao(0);
				re.setDevolucaoSemTroca(0);
				re.setTransferencia(0);
				re.setPreVenda(0);
				re.setSaidaEstoque(0);
				re.setSaldoAtual(0);
				re.setSaldoAnterior(e.getSaldoAnterior());
				re.setSaldoAtual(e.getSaldoAnterior());
				
				resumos.put(chave, re);
			}
			
			
			if (e.getMovimento().getCodMovimento().equals(1) ||
					e.getMovimento().getCodMovimento().equals(4)) {
				
				re.setEntrada(re.getEntrada() + e.getQuantidade());
				
			} else if (e.getMovimento().getCodMovimento().equals(23)) {
				
				re.setReforco(re.getReforco() + e.getQuantidade());
				
			} else if (e.getMovimento().getCodMovimento().equals(23)) {
				
				re.setReforco(re.getReforco() + e.getQuantidade());
				
			} else if (e.getMovimento().getCodMovimento().equals(5) ||
					e.getMovimento().getCodMovimento().equals(6)) {
				
				re.setFatura(re.getFatura() + e.getQuantidade());
				
			} else if (e.getMovimento().getCodMovimento().equals(9) ||
					e.getMovimento().getCodMovimento().equals(10)) {
				
				re.setAvista(re.getAvista() + e.getQuantidade());
				
			} else if (e.getMovimento().getCodMovimento().equals(7) ||
					e.getMovimento().getCodMovimento().equals(8)) {
				
				re.setGratis(re.getGratis() + e.getQuantidade());
				
			} else if (e.getMovimento().getCodMovimento().equals(11) ||
					e.getMovimento().getCodMovimento().equals(12)) {
				
				re.setNota(re.getNota() + e.getQuantidade());
				
			} else if (e.getMovimento().getCodMovimento().equals(21) ||
					e.getMovimento().getCodMovimento().equals(22)) {
				
				re.setBalcao(re.getBalcao() + e.getQuantidade());
				
			} else if (e.getMovimento().getCodMovimento().equals(13) ||
					e.getMovimento().getCodMovimento().equals(14) ||
					e.getMovimento().getCodMovimento().equals(15) ||
					e.getMovimento().getCodMovimento().equals(16)) {
				
				re.setDevolucao(re.getDevolucao() + e.getQuantidade());
				
			} else if (e.getMovimento().getCodMovimento().equals(17) ||
					e.getMovimento().getCodMovimento().equals(18) ||
					e.getMovimento().getCodMovimento().equals(19) ||
					e.getMovimento().getCodMovimento().equals(20)) {
				
				re.setDevolucaoSemTroca(re.getDevolucaoSemTroca() + e.getQuantidade());
				
			} else if (e.getMovimento().getCodMovimento().equals(3)) {
				
				re.setTransferencia(re.getTransferencia() + e.getQuantidade());
				
			} else if (e.getMovimento().getCodMovimento().equals(2)) {
				
				re.setSaidaEstoque(re.getSaidaEstoque() + e.getQuantidade());
				
			}
			
			re.setSaldoAtual(re.getSaldoAtual() + e.getQuantidade());
			
		}
		
		EstoqueDAO.getInstance().atualizaResumoEstoque(
				campanha, resumos.values());
	}

}
