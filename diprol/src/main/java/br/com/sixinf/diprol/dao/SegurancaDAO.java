/**
 * 
 */
package br.com.sixinf.diprol.dao;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.sixinf.diprol.entidades.Usuario;
import br.com.sixinf.ferramentas.dao.BridgeBaseDAO;
import br.com.sixinf.ferramentas.dao.HibernateBaseDAOImp;
import br.com.sixinf.ferramentas.persistencia.AdministradorPersistencia;

/**
 * @author maicon
 *
 */
public class SegurancaDAO extends BridgeBaseDAO {
	
	private static final Logger LOG = Logger.getLogger(SegurancaDAO.class);
	
	private static SegurancaDAO dao;
	
	public static SegurancaDAO getInstance(){
		if (dao == null)
			dao = new SegurancaDAO();
		return dao;
	}

	public SegurancaDAO() {
		super(new HibernateBaseDAOImp());
	}
	
	/**
	 * 
	 * @param cpf
	 * @return
	 */
	public Usuario buscarUsuarioPorCpf(String cpf) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		
		Usuario obj = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select u from Usuario u ");
			hql.append("where u.cpf = :cpf ");
			TypedQuery<Usuario> q = em.createQuery(hql.toString(), Usuario.class);
			q.setParameter("cpf", cpf);
			
			obj = q.getSingleResult();
			
		} catch (NoResultException e) { 
		
		} catch (Exception e) {
			LOG.error("Erro ao buscar usuario por cpf", e);
		} finally {
            em.close();
        }
		return obj;
	}
	
	/**
	 * 
	 * @param cpf
	 * @param senha
	 */
	public void alterarSenhaUsuario(String cpf, String senha) {
		EntityManager em = AdministradorPersistencia.getEntityManager();
		EntityTransaction t = em.getTransaction();
		
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("update Usuario u ");
			hql.append("set u.senha = :senha ");
			hql.append("where u.cpf = :cpf ");
			
	        Query q = em.createQuery(hql.toString());
	        
	        q.setParameter("senha", senha);
	        q.setParameter("cpf", cpf);
	        
	        t.begin();
	        	        
	        q.executeUpdate();
	        
	        t.commit();
	        
		} catch (Exception e) {
			t.rollback();
			LOG.error("Erro ao alterar senha usuario", e);
		} finally {
	        em.close();
	    }
	}
}
