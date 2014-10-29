/**
 * 
 */
package br.com.sixinf.diprol.facade;

import java.util.Date;

import br.com.sixinf.diprol.dao.ClienteDAO;
import br.com.sixinf.diprol.dao.EstoqueDAO;
import br.com.sixinf.diprol.dao.SegurancaDAO;
import br.com.sixinf.diprol.entidades.Campanha;
import br.com.sixinf.diprol.entidades.Cliente;
import br.com.sixinf.diprol.entidades.Estoque;
import br.com.sixinf.diprol.entidades.Movimento;
import br.com.sixinf.ferramentas.dao.DAOException;
import br.com.sixinf.ferramentas.log.LoggerException;

/**
 * @author maicon
 *
 */
public class DiprolFacade {
	
	private static DiprolFacade facade;
	
	public static DiprolFacade getInstance() {
		if (facade == null)
			facade = new DiprolFacade();
		return facade;
	}
	
	private SegurancaDAO segurancaDAO;
	private ClienteDAO clienteDAO;
	private EstoqueDAO estoqueDAO;
	
	
	
	public DiprolFacade() {
		segurancaDAO = SegurancaDAO.getInstance();
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

}