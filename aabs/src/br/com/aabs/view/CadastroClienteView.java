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
public class CadastroClienteView implements Serializable {
	
	@Inject
	private FacesContext facesContext;
	
	@EJB
	private ClienteServico clienteServico;
	
	private Cliente cliente = new Cliente();
	
	private Integer idSelecionado;
	
	private static final Logger log = Logger.getLogger(CadastroClienteView.class);
	
	public String cadastrar() throws Exception {
		try {
			
			clienteServico.cadastrar(cliente);
			
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação realizada com sucesso!", null));
			
			this.cliente = new Cliente();
			
		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
		
		return "";
	}
	
	public String alterar() throws Exception {
		try {
			
			clienteServico.alterar(cliente);
			
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação realizada com sucesso!", null));
			
		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
		
		return "";
	}
	
	public void prepararEdicao() throws Exception {
		if(idSelecionado != null) {
			this.cliente = clienteServico.carregar(idSelecionado);
		}
	}
	
	public void limpar() throws Exception {
		this.cliente = new Cliente();
		this.idSelecionado = null;
	}
	
	public Integer getIdSelecionado() {
		return idSelecionado;
	}

	public void setIdSelecionado(Integer idSelecionado) {
		this.idSelecionado = idSelecionado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}