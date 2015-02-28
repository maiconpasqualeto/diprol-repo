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
	public Object[] buscaSomatoriosParaFechamento(Campanha campanha) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Object[]> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select sum(re.entrada), "); //0
			hql.append("sum(re.saidaEstoque), ");	//1
			hql.append("sum(re.reforco), ");		//2
			hql.append("sum(re.devolucao), ");		//3
			hql.append("sum(re.saldoAtual), ");		//4
			hql.append("sum(re.fatura), ");			//5
			hql.append("sum(re.transferencia), ");	//6
			hql.append("sum(re.devolucaoSemTroca) ");//7
			
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
	 * @param uf
	 * @return
	 */
	public Long buscarSaldoAtual(String uf, Campanha campanha) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		Long saldo = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select re.saldoAtual ");
			hql.append("from ResumoEstoque re ");
			hql.append("where re.codCampanha = :codCampanha ");
			hql.append("and (re.codCef = '07.000000-0' or re.codCef = '10.000000-0') ");
			hql.append("and re.uf = :uf ");
			hql.append("order by re.codEstoqueResumo desc ");
			
			Query q = em.createQuery(hql.toString());
			q.setParameter("codCampanha", campanha.getCodCampanha());
			q.setParameter("uf", uf);
			q.setMaxResults(1);
			
			saldo = ((Integer) q.getSingleResult()).longValue();
			
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error("Erro ao buscar saldo atual", e);
		} finally {
            em.close();
        }
		return saldo;
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
