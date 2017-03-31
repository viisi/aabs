package br.com.aabs.view;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import br.com.aabs.entidade.Cliente;
import br.com.aabs.servico.ClienteServico;

@Named
@ViewScoped
public class ConsultaClienteView implements Serializable {
	
	@Inject
	private FacesContext facesContext;
	
	@EJB
	private ClienteServico clienteServico;
	
	private Cliente filtro = new Cliente();
	
	private List<Cliente> clientes;
	
	private static final Logger log = Logger.getLogger(ConsultaClienteView.class);
	
	public String consultar() throws Exception {
		try {
			
			clientes = clienteServico.consultar(filtro);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
		
		return "";
	}
	
	public String excluir(Cliente cliente) throws Exception {
		
		try {
			
			if(cliente != null && cliente.getId() != null) {
				clienteServico.excluir(cliente);
				clientes.remove(cliente);
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação realizada com sucesso!", null));
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
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

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
}