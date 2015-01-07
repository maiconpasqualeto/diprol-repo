/**
 * 
 */
package br.com.sixinf.diprol.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.sixinf.diprol.entidades.Conta;
import br.com.sixinf.diprol.entidades.ContaGrupo;
import br.com.sixinf.diprol.entidades.ContaMovimento;
import br.com.sixinf.diprol.entidades.TipoConta;
import br.com.sixinf.ferramentas.dao.BridgeBaseDAO;
import br.com.sixinf.ferramentas.dao.HibernateBaseDAOImp;
import br.com.sixinf.ferramentas.log.LoggerException;
import br.com.sixinf.ferramentas.persistencia.AdministradorPersistencia;

/**
 * @author maicon
 *
 */
public class FinanceiroDAO extends BridgeBaseDAO {
	
	private static FinanceiroDAO dao;
	
	public static FinanceiroDAO getInstance(){
		if (dao == null)
			dao = new FinanceiroDAO();
		return dao;
	}

	public FinanceiroDAO() {
		super(new HibernateBaseDAOImp());
	}
	
	
	/**
	 * 
	 * @return
	 */ 
	public List<Conta> buscarContasPorTipo(TipoConta tipoConta) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Conta> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Conta c ");
			hql.append("inner join fetch c.contaGrupo cg ");
			hql.append("where c.tipo = :tipoConta ");
			hql.append("order by cg.grupoConta, c.conta ");
															
			TypedQuery<Conta> q = em.createQuery(hql.toString(), Conta.class);
			q.setParameter("tipoConta", tipoConta.getTipo());
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Erro ao buscar as contas por tipo", e);
		} finally {
            em.close();
        }
		return list;
	}
	
	/**
	 * 
	 * @return
	 */ 
	public List<ContaMovimento> buscarValorDozeMeses(Conta conta, Integer ano) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<ContaMovimento> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select cm from ContaMovimento cm ");
			hql.append("where cm.ano = :ano ");
			hql.append("and cm.conta.codConta = :codConta ");
			hql.append("and cm.mes BETWEEN 1 AND 12 ");
			hql.append("order by cm.mes");
															
			TypedQuery<ContaMovimento> q = em.createQuery(hql.toString(), ContaMovimento.class);
			q.setParameter("codConta", conta.getCodConta());
			q.setParameter("ano", ano);
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Erro ao buscar as contas movimento dos 12 meses", e);
		} finally {
            em.close();
        }
		return list;
	}
	
	/**
	 * 
	 * @param produto
	 * @throws LoggerException
	 */
	public void alterarPrevisaoMovimentos(ContaMovimento[] contasMovimento) throws LoggerException {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		EntityTransaction t = em.getTransaction();
		
		try {
			t.begin();
			
			for (ContaMovimento c : contasMovimento) {
				if (c.getCodContaMovimento() != null) {
					// se já existir o registro altera
					
					StringBuilder hql = new StringBuilder();
					hql.append("update ContaMovimento cm ");
					hql.append("set cm.previsto = :previsto ");
					hql.append("where cm.codContaMovimento = :codContaMovimento ");
					
			        Query q = em.createQuery(hql.toString());
			        
			        q.setParameter("previsto", c.getPrevisto());
			        q.setParameter("codContaMovimento", c.getCodContaMovimento());
			        
			        q.executeUpdate();
			        
				} else {
					// se não existir inclui
					em.persist(c);
				}
			}
			
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw new LoggerException("Erro ao gravar previsões", e, Logger.getLogger(getClass()));
		} finally {
            em.close();
        }
	}
	
	/**
	 * 
	 * @return
	 */ 
	public List<ContaGrupo> buscarTodosGruposConta() {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<ContaGrupo> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select cg from ContaGrupo cg ");
															
			TypedQuery<ContaGrupo> q = em.createQuery(hql.toString(), ContaGrupo.class);
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Erro ao buscar tosod as Grupos de Conta", e);
		} finally {
            em.close();
        }
		return list;
	}
	
	/**
	 * 
	 * @return
	 */ 
	public List<ContaMovimento> buscarContasMovimento(Integer ano, Integer mes, ContaGrupo contaGrupo) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<ContaMovimento> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select cm from ContaMovimento cm ");
			hql.append("inner join fetch cm.conta c ");
			hql.append("inner join fetch c.contaGrupo cg ");
			hql.append("where cm.ano = :ano ");
			hql.append("and cm.mes = :mes ");
			hql.append("and cg.codGrupoConta = :codGrupoConta ");
															
			TypedQuery<ContaMovimento> q = em.createQuery(hql.toString(), ContaMovimento.class);
			
			q.setParameter("ano", ano);
			q.setParameter("mes", mes);
			q.setParameter("codGrupoConta", contaGrupo.getCodGrupoConta());
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Erro ao buscar contas movimento", e);
		} finally {
            em.close();
        }
		return list;
	}

	
	/**
	 * 
	 * @param produto
	 * @throws LoggerException
	 */
	public void alterarValoresRealizadosMovimentos(List<ContaMovimento> contasMovimento) throws LoggerException {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		EntityTransaction t = em.getTransaction();
		
		try {
			t.begin();
			
			for (ContaMovimento c : contasMovimento) {
				
				StringBuilder hql = new StringBuilder();
				hql.append("update ContaMovimento cm ");
				hql.append("set cm.realizado = :realizado ");
				hql.append("where cm.codContaMovimento = :codContaMovimento ");
				
		        Query q = em.createQuery(hql.toString());
		        
		        q.setParameter("realizado", c.getRealizado());
		        q.setParameter("codContaMovimento", c.getCodContaMovimento());
		        
		        q.executeUpdate();
		        
			}
			
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw new LoggerException("Erro ao gravar valores realizados", e, Logger.getLogger(getClass()));
		} finally {
            em.close();
        }
	}
	
	/**
	 * 
	 * @return
	 */ 
	public List<Conta> buscarContasPorGrupo(ContaGrupo contaGrupo) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Conta> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Conta c ");
			hql.append("inner join c.contaGrupo cg ");
			hql.append("where cg.codGrupoConta = :codGrupoConta ");
			hql.append("order by c.conta ");
															
			TypedQuery<Conta> q = em.createQuery(hql.toString(), Conta.class);
			q.setParameter("codGrupoConta", contaGrupo.getCodGrupoConta());
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Erro ao buscar as contas por grupo", e);
		} finally {
            em.close();
        }
		return list;
	}
}
