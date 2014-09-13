/**
 * 
 */
package br.com.sixinf.diprol.facade;

import java.util.Date;

import br.com.sixinf.diprol.dao.ClienteDAO;
import br.com.sixinf.diprol.dao.SegurancaDAO;
import br.com.sixinf.diprol.entidades.Cliente;
import br.com.sixinf.ferramentas.dao.DAOException;

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
	
	public DiprolFacade() {
		segurancaDAO = SegurancaDAO.getInstance();
		clienteDAO = ClienteDAO.getInstance();
	}
	
	public void salvarCliente(Cliente c, boolean novo) throws DAOException{
		if (novo) {
			c.setStatus('A');
			c.setDataCadastro(new Date());
			clienteDAO.adicionar(c);
		} else {
			clienteDAO.alterar(c);
		}
	}

}
