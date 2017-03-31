package br.com.aabs.servico;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import br.com.aabs.entidade.Cliente;

@Stateless
public class ClienteServicoImpl extends Servico implements ClienteServico {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1102620244224964833L;
	
	private static final Logger log = Logger.getLogger(ClienteServicoImpl.class);
	
	@Inject
	private EntityManager em;
	
	public Cliente carregar(Integer id) throws Exception {
		return em.find(Cliente.class, id);
	}
	
	public Cliente cadastrar(Cliente cliente) throws Exception {
		
		try {
			
			if(consultarPorCpf(cliente) != null) {
				throw new Exception("CPF já cadastrado");
			}
			
			em.persist(cliente);
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}
		
		return cliente;
	}
	
	public Cliente alterar(Cliente cliente) throws Exception {
		
		try {
			
			cliente = em.merge(cliente);
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}
		
		return cliente;
	}
	
	public void excluir(Cliente cliente) throws Exception {
		
		try {
			
			em.remove(em.getReference(Cliente.class, cliente.getId()));
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
	//TODO: Verificar filtro aqui no matchmode
	public List<Cliente> consultar(Cliente filtro) throws Exception {
		
		try {
			
			String sql = "from Cliente where 1=1";
			
			if(filtro.getNome() != null) {
				sql += " and nome like :nome";
			}
			
			if(filtro.getCpf() != null) {
				sql += " and cpf = :cpf";
			}
			
			Query q = em.createQuery(sql, Cliente.class); 
			
			if(filtro.getNome() != null) {
				q.setParameter("nome", "%" + filtro.getNome() + "%");
			}
			
			if(filtro.getCpf() != null) {
				q.setParameter("cpf", filtro.getCpf());
			}
			
			return q.getResultList();
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		
		return null;
	}

	@Override
	public Cliente consultarPorCpf(Cliente filtro) throws Exception {
		
		try {
			
			String sql = "from Cliente where cpf like :cpf";
			
			Query q = em.createQuery(sql, Cliente.class).setParameter("cpf", filtro.getCpf());
			
			return (Cliente) q.getSingleResult();
			
		} catch (NoResultException nre) {
			log.warn(nre.getMessage(), nre);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}
		
		return null;
	}
}