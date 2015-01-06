/**
 * 
 */
package br.com.sixinf.diprol.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.sixinf.diprol.entidades.Campanha;
import br.com.sixinf.ferramentas.dao.BridgeBaseDAO;
import br.com.sixinf.ferramentas.dao.HibernateBaseDAOImp;
import br.com.sixinf.ferramentas.persistencia.AdministradorPersistencia;

/**
 * @author maicon
 *
 */
public class CampanhaDAO extends BridgeBaseDAO {
	
	private static CampanhaDAO dao;
	
	public static CampanhaDAO getInstance(){
		if (dao == null)
			dao = new CampanhaDAO();
		return dao;
	}

	public CampanhaDAO() {
		super(new HibernateBaseDAOImp());
	}
	
	public List<Campanha> buscarTodasCampanhas() {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Campanha> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Campanha c ");
			//hql.append("where c.status = 'A' ");
												
			TypedQuery<Campanha> q = em.createQuery(hql.toString(), Campanha.class);
			
			list = q.getResultList();
			
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error("Erro ao buscar todos Campanha ativas", e);
		} finally {
            em.close();
        }
		return list;
	}

}
