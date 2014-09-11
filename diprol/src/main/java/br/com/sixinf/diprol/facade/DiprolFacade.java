/**
 * 
 */
package br.com.sixinf.diprol.facade;

import br.com.sixinf.diprol.dao.SegurancaDAO;

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
	
	public DiprolFacade() {
		segurancaDAO = SegurancaDAO.getInstance();
	}

}
