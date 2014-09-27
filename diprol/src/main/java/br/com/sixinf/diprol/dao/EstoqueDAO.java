/**
 * 
 */
package br.com.sixinf.diprol.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.sixinf.diprol.entidades.Campanha;
import br.com.sixinf.diprol.entidades.Estoque;
import br.com.sixinf.diprol.entidades.Movimento;
import br.com.sixinf.ferramentas.dao.BridgeBaseDAO;
import br.com.sixinf.ferramentas.dao.HibernateBaseDAOImp;
import br.com.sixinf.ferramentas.log.LoggerException;
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
	public List<Movimento> buscarMovimentosAtivosPorTipoCliente(String tipoCliente) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Movimento> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select m from Movimento m ");
			hql.append("where m.tipoCliente = :tipoCliente ");
			hql.append("and m.status = 'A' ");
												
			TypedQuery<Movimento> q = em.createQuery(hql.toString(), Movimento.class);
			q.setParameter("tipoCliente", tipoCliente);
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro ao buscar todos movimentos ativos", e);
		} finally {
            em.close();
        }
		return list;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Campanha> buscarCampanhasAtivas() {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Campanha> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Campanha c ");
			hql.append("where c.status = 'A' ");
												
			TypedQuery<Campanha> q = em.createQuery(hql.toString(), Campanha.class);
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro ao buscar todos Campanha ativas", e);
		} finally {
            em.close();
        }
		return list;
	}
	
	/**
	 * 
	 * @param c
	 * @param uf
	 * @param codigoCEF
	 * @return
	 */
	public Estoque buscarUltimoEstoque(Campanha c, String uf, String codigoCEF) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		Estoque o = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select e from Estoque e ");
			hql.append("inner join e.campanha c ");
			hql.append("where c.codCampanha = :codCampanha and ");
			hql.append("e.uf = :uf and ");
			hql.append("e.codCEF = :codCEF ");
			hql.append("order by e.id desc ");
												
			TypedQuery<Estoque> q = em.createQuery(hql.toString(), Estoque.class);
			q.setParameter("codCampanha", c.getCodCampanha());
			q.setParameter("uf", uf);
			q.setParameter("codCEF", codigoCEF);
			q.setMaxResults(1);
			
			o = q.getSingleResult();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro ao buscar ultimo estoque", e);
		} finally {
            em.close();
        }
		return o;
	}
	
	/**
	 * 
	 * @param codMovimento
	 * @return
	 */
	public Movimento buscarMovimento(Integer codMovimento) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		Movimento o = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select m from Movimento m ");
			hql.append("where m.codMovimento = :codMovimento ");
												
			TypedQuery<Movimento> q = em.createQuery(hql.toString(), Movimento.class);
			q.setParameter("codMovimento", codMovimento);
			
			o = q.getSingleResult();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro ao buscar movimento", e);
		} finally {
            em.close();
        }
		return o;
	}
	
	/**
	 * 
	 * @param estoque
	 * @param estoqueContra
	 * @throws LoggerException
	 */
	public void salvarEstoque(Estoque estoque, Estoque estoqueContra) throws LoggerException {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		EntityTransaction t = em.getTransaction();
		
		try {
			t.begin();
			
			em.persist(estoque);
			
			if (estoqueContra != null)
				em.persist(estoqueContra);
			
			
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw new LoggerException("Erro ao salvar estoque", e, LOG);
		} finally {
            em.close();
        }
	}
}
