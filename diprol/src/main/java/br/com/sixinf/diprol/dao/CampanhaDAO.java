/**
 * 
 */
package br.com.sixinf.diprol.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
			hql.append("order by c.campanha ");
												
			TypedQuery<Campanha> q = em.createQuery(hql.toString(), Campanha.class);
			
			list = q.getResultList();
			
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error("Erro ao buscar todos Campanha ativas", e);
		} finally {
            em.close();
        }
		return list;
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Object[] calcularFechamentoCampanha(Campanha campanha) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Object[]> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select sum(re.entrada), "); //0
			hql.append("sum(re.saidaEstoque), ");	//1
			hql.append("sum(re.reforco), ");		//2
			hql.append("sum(re.devolucao), ");		//3
			hql.append("sum(re.saldoAtual), ");		//4
			hql.append("sum(re.fatura) ");			//5
			
			hql.append("from ResumoEstoque re ");
			hql.append("where re.codCampanha = :codCampanha ");
			hql.append("and (re.codCef = '07.000000-0' or re.codCef = '10.000000-0') ");
			
			Query q = em.createQuery(hql.toString());
			q.setParameter("codCampanha", campanha.getCodCampanha());
			
			list = q.getResultList();
			
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error("Erro ao calcular fechamento campanha", e);
		} finally {
            em.close();
        }
		return list != null && !list.isEmpty() ? list.get(0) : null;
	}
	
	/**
	 * 
	 * @param campanha
	 */
	/*public void salvaFechamentoCampanha(Campanha campanha) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		EntityTransaction t = em.getTransaction();
		
		try {
			t.begin();
					
				StringBuilder hql = new StringBuilder();
				hql.append("update Campanha cm ");
				hql.append("set cm.previsto = :previsto ");
				hql.append("where cm.codContaMovimento = :codContaMovimento ");
				
		        Query q = em.createQuery(hql.toString());
		        
		        q.setParameter("previsto", c.getPrevisto());
		        q.setParameter("codContaMovimento", c.getCodContaMovimento());
		        
		        q.executeUpdate();
			
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw new LoggerException("Erro ao salvar fechamento Campanha", e, Logger.getLogger(getClass()));
		} finally {
            em.close();
        }
	}*/

}
