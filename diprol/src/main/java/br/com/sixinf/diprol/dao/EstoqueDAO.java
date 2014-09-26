/**
 * 
 */
package br.com.sixinf.diprol.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.sixinf.diprol.entidades.Movimento;
import br.com.sixinf.ferramentas.dao.BridgeBaseDAO;
import br.com.sixinf.ferramentas.dao.HibernateBaseDAOImp;
import br.com.sixinf.ferramentas.persistencia.AdministradorPersistencia;

/**
 * @author maicon
 *
 */
public class EstoqueDAO  extends BridgeBaseDAO {
	
	private static final Logger LOG = Logger.getLogger(EstoqueDAO.class);
	
	private static EstoqueDAO dao;
	
	public static EstoqueDAO getInstance(){
		if (dao == null)
			dao = new EstoqueDAO();
		return dao;
	}

	public EstoqueDAO() {
		super(new HibernateBaseDAOImp());
	}
	
	
	/**
	 * 
	 * @return
	 */
	public List<Movimento> buscarMovimentosAtivos() {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Movimento> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select m from Movimento m ");
			hql.append("where m.status = 'A' ");
												
			TypedQuery<Movimento> q = em.createQuery(hql.toString(), Movimento.class);
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro ao buscar todos movimentos ativos", e);
		} finally {
            em.close();
        }
		return list;
	}
	

}
