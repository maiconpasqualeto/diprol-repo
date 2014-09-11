/**
 * 
 */
package br.com.sixinf.diprol.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import br.com.sixinf.ferramentas.persistencia.AdministradorPersistencia;
import br.com.sixinf.ferramentas.persistencia.PersistenciaException;

/**
 * @author maicon
 *
 */
public class DiprolListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		AdministradorPersistencia.close();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			
			AdministradorPersistencia.createEntityManagerFactory("diprol");
			
		} catch (PersistenciaException e) {
			Logger.getLogger(DiprolListener.class).error(e);
		}
		
		
		
	}

}
