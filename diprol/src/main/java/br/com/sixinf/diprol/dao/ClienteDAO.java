/**
 * 
 */
package br.com.sixinf.diprol.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;

import br.com.sixinf.diprol.entidades.Cliente;
import br.com.sixinf.ferramentas.dao.BridgeBaseDAO;
import br.com.sixinf.ferramentas.dao.HibernateBaseDAOImp;
import br.com.sixinf.ferramentas.persistencia.AdministradorPersistencia;

/**
 * @author maicon
 *
 */
public class ClienteDAO extends BridgeBaseDAO {
	
	private static final Logger LOG = Logger.getLogger(ClienteDAO.class);
	
	private static ClienteDAO dao;
	
	public static ClienteDAO getInstance(){
		if (dao == null)
			dao = new ClienteDAO();
		return dao;
	}

	public ClienteDAO() {
		super(new HibernateBaseDAOImp());
	}
	
	/**
	 * 
	 * @param cpf
	 * @return
	 */
	public Cliente buscarClientePorCodigo(String codCEF) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		Cliente obj = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Cliente c ");
			hql.append("where c.codCEF = :codCEF ");
			TypedQuery<Cliente> q = em.createQuery(hql.toString(), Cliente.class);
			q.setParameter("codCEF", codCEF);
			
			obj = q.getSingleResult();
			
		} catch (NoResultException e) { 
		
		} catch (Exception e) {
			LOG.error("Erro ao buscar cliente por codigo cef", e);
		} finally {
            em.close();
        }
		return obj;
	}
	
	
	/**
	 * 
	 * @param inicio
	 * @param fim
	 * @return
	 */
	public List<Cliente> buscarClientesPaginado(
			int inicio, int fim, String sortField, 
			SortOrder order, Map<String, String> filters) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		List<Cliente> list = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Cliente c ");
			hql.append("where c.status = 'A' ");
			
			montaHqlParameter(hql, filters, sortField, order);
									
			TypedQuery<Cliente> q = em.createQuery(hql.toString(), Cliente.class);
			q.setMaxResults(fim - inicio);
			q.setFirstResult(inicio);
			
			preencheQueryParameter(q, filters);
			
			list = q.getResultList();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro ao buscar clientes paginado", e);
		} finally {
            em.close();
        }
		return list;
	}
	
	/**
	 * 
	 * @return
	 */
	public Long buscarCountClientesPaginado(Map<String, String> filters) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		Long count = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select count(c) from Cliente c ");
			hql.append("where c.status = 'A' ");
			
			montaHqlParameter(hql, filters, "", SortOrder.UNSORTED);
			
			Query q = em.createQuery(hql.toString());
			
			preencheQueryParameter(q, filters);
			
			count = (Long) q.getSingleResult();
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			LOG.error("Erro no count lista clientes", e);
		} finally {
            em.close();
        }
		return count;
	}
	
	/**
	 * 
	 * @param hql
	 * @param filters
	 */
	private void montaHqlParameter(StringBuilder hql, Map<String, String> filters, String sortField, SortOrder order){
		String globalFilterParameter = filters.get("globalFilter");
		if (globalFilterParameter != null && 
				globalFilterParameter.isEmpty())
			filters.remove("globalFilter");
				
		if (!filters.isEmpty()) {			
			hql.append("and ");
						
			if (filters.containsKey("globalFilter")) {
				
				hql.append("c.codigoCEF = :codigoCEF or ");				
				hql.append("lower( c.razaoSocial ) like lower( :razaoSocial ) or ");
				hql.append("c.cnpj = :cnpj ");
			} else {		
				int i = 0;
				for (String str : filters.keySet()) {
					if (str.equals("codigoCEF"))
						hql.append("c." + str + "=:" + str + " ");
					else
						hql.append("lower( c." + str + ") like lower(:" + str + ") ");
					if (++i < filters.size())
						hql.append("and ");
				}
			}
		}
		
		if (!order.equals(SortOrder.UNSORTED))
			hql.append("order by p." + sortField + (order.equals(SortOrder.ASCENDING) ? " ASC " : " DESC "));
	}
	
	/**
	 * 
	 * @param q
	 * @param filters
	 */
	private void preencheQueryParameter(Query q, Map<String, String> filters) {
		if (filters.containsKey("globalFilter")) {
			q.setParameter("codigoCEF", "=" + filters.get("globalFilter"));
			q.setParameter("titulo", "%" + filters.get("globalFilter") + "%");
			q.setParameter("descricaoResumida", "%" + filters.get("globalFilter") + "%");
		} else {
			for (String str : filters.keySet()) {
				if (str.equals("codigoCEF"))
					q.setParameter(str, filters.get(str));
				else
					q.setParameter(str, "%" + filters.get(str) + "%");
			}
		}
	}

}
