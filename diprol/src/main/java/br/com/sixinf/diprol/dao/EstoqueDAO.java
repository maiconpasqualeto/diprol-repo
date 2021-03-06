/**
 * 
 */
package br.com.sixinf.diprol.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import br.com.sixinf.diprol.entidades.Campanha;
import br.com.sixinf.diprol.entidades.Estoque;
import br.com.sixinf.diprol.entidades.Movimento;
import br.com.sixinf.diprol.entidades.ResumoEstoque;
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
	 * @param dataAtual
	 * @return
	 */
	public List<Campanha> buscarCampanhasAtivasNoPeriodo(Date dataAtual) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Campanha> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Campanha c ");
			hql.append("where c.status = 'A' ");
			hql.append("and c.dataInicio <= :dataAtual ");
			hql.append("and c.dataTermino >= :dataAtual ");
												
			TypedQuery<Campanha> q = em.createQuery(hql.toString(), Campanha.class);
			q.setParameter("dataAtual", dataAtual);
			
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
	 * @param dataAtual
	 * @return
	 */
	public List<Campanha> buscarCampanhasAtivasMenosAtual(Campanha capanhaAtual) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Campanha> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Campanha c ");
			hql.append("where c.status = 'A' ");
			hql.append("and c.id != :codCampanhaAtual ");
			hql.append("order by c.codCampanha");
												
			TypedQuery<Campanha> q = em.createQuery(hql.toString(), Campanha.class);
			q.setParameter("codCampanhaAtual", capanhaAtual.getCodCampanha());
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro ao buscar Campanhas ativas menos atual", e);
		} finally {
            em.close();
        }
		return list;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Campanha> buscarCampanhasAtivasEncerradas() {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Campanha> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Campanha c ");
			hql.append("where c.status = 'A' ");
			hql.append("and c.dataTermino < :dataCampanha ");
			hql.append("order by c.codCampanha");
												
			TypedQuery<Campanha> q = em.createQuery(hql.toString(), Campanha.class);
			q.setParameter("dataCampanha", new Date());
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro ao buscar Campanhas encerradas ativas", e);
		} finally {
            em.close();
        }
		return list;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Campanha> buscarCampanhasAtivasPosteriores(Campanha campanhaAtual) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Campanha> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Campanha c ");
			hql.append("where c.status = 'A' ");
			hql.append("and c.dataInicio > :dataCampanha ");
			hql.append("order by c.codCampanha");
												
			TypedQuery<Campanha> q = em.createQuery(hql.toString(), Campanha.class);
			q.setParameter("dataCampanha", campanhaAtual.getDataInicio());
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro ao buscar Campanhas Posteriores", e);
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
			hql.append("order by m.codMovimento");
												
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
	public void salvarEstoque(Estoque estoque, Estoque estoqueContra, 
			Estoque estoqueDevolucao, Estoque estContraDevolucao) throws LoggerException {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		EntityTransaction t = em.getTransaction();
		
		try {
			t.begin();
			
			em.persist(estoque);
			
			if (estoqueContra != null)
				em.persist(estoqueContra);
			
			if (estoqueDevolucao != null)
				em.persist(estoqueDevolucao);
			
			if (estContraDevolucao != null)
				em.persist(estContraDevolucao);
			
			
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw new LoggerException("Erro ao salvar estoque devolucao", e, LOG);
		} finally {
            em.close();
        }
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Estoque> buscarEstoquesFechamentoResumo(Campanha c) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		List<Estoque> lista = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select e from Estoque e ");
			hql.append("inner join e.campanha c ");
			hql.append("join fetch e.movimento m ");
			hql.append("where c.codCampanha = :codCampanha ");
			hql.append("order by e.dataMovimento ");
												
			TypedQuery<Estoque> q = em.createQuery(hql.toString(), Estoque.class);
			q.setParameter("codCampanha", c.getCodCampanha());
			
			lista = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro ao buscar estoques para fechamento resumo", e);
		} finally {
            em.close();
        }
		return lista;
	}
	
	/**
	 * 
	 * @param resumos
	 * @throws LoggerException 
	 */
	public void atualizaResumoEstoque(
			Campanha campanha, Collection<ResumoEstoque> resumos) throws LoggerException {
		
		EntityManager em = AdministradorPersistencia.getEntityManager();
		EntityTransaction t = em.getTransaction();
		
		try {
			t.begin();
			
			StringBuilder hql = new StringBuilder();
			hql.append("delete ResumoEstoque re ");
			hql.append("where re.codCampanha = :codCampanha ");
			
	        Query q = em.createQuery(hql.toString());
	        
	        q.setParameter("codCampanha", campanha.getCodCampanha());
	        
	        q.executeUpdate();
			
			for (ResumoEstoque re : resumos)
				em.persist(re);
			
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw new LoggerException("Erro ao gravar resumos estoque", e, Logger.getLogger(getClass()));
		} finally {
            em.close();
        }
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Estoque> buscarEstoquesCancelamento(Date dataMovimento) {
		
		Date dataFinalMovimento = DateUtils.addSeconds(DateUtils.addMinutes(DateUtils.addHours(dataMovimento, 23), 59), 59);
		
		EntityManager em = AdministradorPersistencia.getEntityManager();
		List<Estoque> lista = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select e from Estoque e ");
			hql.append("join fetch e.campanha c ");
			hql.append("join fetch e.movimento m ");
			hql.append("join fetch e.cliente c ");
			hql.append("where e.dataMovimento between :dataMovimento and :dataFinalMovimento ");
			hql.append("and m.codMovimento = "
					+ "(select min(em.codMovimento) "
					+ "from Estoque es inner join es.movimento em "
					+ "where es.dataMovimento = e.dataMovimento) ");
			hql.append("order by e.dataMovimento ");
			
												
			TypedQuery<Estoque> q = em.createQuery(hql.toString(), Estoque.class);
			q.setParameter("dataMovimento", dataMovimento);
			q.setParameter("dataFinalMovimento", dataFinalMovimento);
			
			lista = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro ao buscar estoques para cancelamento", e);
		} finally {
            em.close();
        }
		return lista;
	}
	
	/**
	 * 
	 * @param resumos
	 * @throws LoggerException 
	 */
	public void excluiMovimentoEstoque(Estoque estoque) throws LoggerException {
		
		EntityManager em = AdministradorPersistencia.getEntityManager();
		EntityTransaction t = em.getTransaction();
		
		try {
			t.begin();
			
			StringBuilder hql = new StringBuilder();
			hql.append("delete Estoque e ");
			hql.append("where e.dataMovimento = :dataMovimento ");
			
	        Query q = em.createQuery(hql.toString());
	        
	        q.setParameter("dataMovimento", estoque.getDataMovimento());
	        
	        q.executeUpdate();
			
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw new LoggerException("Erro ao excluir movimento de estoque", e, Logger.getLogger(getClass()));
		} finally {
            em.close();
        }
	}
}
