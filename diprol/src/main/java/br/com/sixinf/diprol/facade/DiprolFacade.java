/**
 * 
 */
package br.com.sixinf.diprol.facade;

import java.util.Date;

import br.com.sixinf.diprol.dao.ClienteDAO;
import br.com.sixinf.diprol.dao.EstoqueDAO;
import br.com.sixinf.diprol.dao.SegurancaDAO;
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
	public void salvarEstoque(Estoque estoque, String ufDestino, String usuarioCPF) throws LoggerException {
		
		Integer quantidadeInformada = estoque.getQuantidade();
		
		String codigoCEF = "";
		String codigoCEFContra = "";
		if (estoque.getUf().equals("MS")) {
			codigoCEF = "07.000000-0";
			codigoCEFContra = "10.000000-0";
		} else { 
			codigoCEF = "10.000000-0";
			codigoCEFContra = "07.000000-0";
		}
		
		Estoque ultimoEstoque = 
				EstoqueDAO.getInstance().buscarUltimoEstoque(
						estoque.getCampanha(), estoque.getUf(), codigoCEF);
		
		Estoque ultimoEstoqueContra = 
				EstoqueDAO.getInstance().buscarUltimoEstoque(
						estoque.getCampanha(), ufDestino, codigoCEFContra);
		
		Integer saldoAtualUltimoEst = 0;
		
		if (ultimoEstoque != null) 
			saldoAtualUltimoEst = ultimoEstoque.getSaldoAtual();
		
		if (estoque.getMovimento().getAcaoSaldo().equals("subtrai"))
			estoque.setQuantidade(estoque.getQuantidade() * -1);
		
		estoque.setDataMovimento(new Date());
		estoque.setSaldoAnterior(saldoAtualUltimoEst);
		estoque.setSaldoAtual(estoque.getSaldoAnterior() + estoque.getQuantidade());
		estoque.setUsuarioCPF(usuarioCPF);
		estoque.setCodCEF(codigoCEF);
		
		Estoque estContra = null;
		
		if ( estoque.getMovimento().getGeraContrapartida() != null && 
				estoque.getMovimento().getGeraContrapartida().equals('S') ) {
			
			estoque.setCodCEFContrapartida(codigoCEFContra);
			
			estContra = new Estoque();
			estContra.setCampanha(ultimoEstoqueContra.getCampanha());			
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
			
			if (estContra.getMovimento().getAcaoSaldo().equals("subtrai"))
				estContra.setQuantidade(estContra.getQuantidade() * -1);
			
			estContra.setSaldoAnterior(ultimoEstoqueContra.getSaldoAtual());
			estContra.setSaldoAtual(estContra.getSaldoAnterior() + estContra.getQuantidade());
		} 
		
		estoqueDAO.salvarEstoque(estoque, estContra);
	}

}
