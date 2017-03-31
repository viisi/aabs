package br.com.aabs.view;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import br.com.aabs.entidade.Cliente;
import br.com.aabs.servico.ClienteServico;

@Named
@RequestScoped
public class ConsultaDadosView implements Serializable {
	
	@Inject
	private FacesContext facesContext;
	
	@EJB
	private ClienteServico clienteServico;
	
	private Cliente filtro = new Cliente();
	
	private Cliente cliente;
	
	private static final Logger log = Logger.getLogger(ConsultaDadosView.class);
	
	public String consultar() throws Exception {
		try {
			
			cliente = clienteServico.consultarPorCpf(filtro);
			
			if(cliente == null) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente não encontrado!", null));
			}
			
		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
		
		return "";
	}

	public Cliente getFiltro() {
		return filtro;
	}

	public void setFiltro(Cliente filtro) {
		this.filtro = filtro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}