package br.com.aabs.servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.logging.Logger;
import org.joda.time.DateTime;

import br.com.aabs.entidade.Contrato;
import br.com.aabs.entidade.Parcela;

@Stateless
public class ContratoServicoImpl extends Servico implements ContratoServico {
	
	@Inject
	private EntityManager em;

	private static final Logger log = Logger.getLogger(ContratoServicoImpl.class);
		
	public Contrato carregar(Integer id) throws Exception {
		return em.find(Contrato.class, id);
	}
	
	public Contrato cadastrar(Contrato contrato, Parcela primeiraParcela) throws Exception {
		
		try {
			
			Date dataParcela = null;
			
			for(int i = 1; i <= contrato.getQtdParcelas(); i++) {
				
				if(dataParcela == null) {
					dataParcela = primeiraParcela.getData();
				}
				
				Parcela p = new Parcela();
				p.setCompensado("N");
				p.setContrato(contrato);
				p.setValor(primeiraParcela.getValor());
				p.setData(dataParcela);
				p.setNumParcela(i);
				
				if(contrato.getParcelas() == null) {
					contrato.setParcelas(new ArrayList<Parcela>(0));
				}
				contrato.getParcelas().add(p);
				
				dataParcela = new DateTime(dataParcela.getTime()).plusMonths(1).dayOfMonth().withMaximumValue().toDate();
			}
			
			contrato.setData(new Date());
			contrato.setAtivo("S");
			
			em.persist(contrato);
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}
		
		return contrato;
		
	}
	
	public Contrato alterar(Contrato contrato) throws Exception {
		
		try {
			
			contrato = em.merge(contrato);
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}
		
		return contrato;
	}
	
	public void excluir(Contrato contrato) throws Exception {
		
		try {
			
			em.remove(em.getReference(Contrato.class, contrato.getId()));
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public List<Contrato> ultimosContratos() throws Exception {
		return em.createQuery(
			    "SELECT c FROM Contrato c ORDER BY c.data DESC")
			    .setMaxResults(10)
			    .getResultList();
	}
}