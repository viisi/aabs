package br.com.aabs.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.joda.time.DateTime;

import br.com.aabs.entidade.Cliente;
import br.com.aabs.entidade.Contrato;
import br.com.aabs.entidade.Parcela;
import br.com.aabs.servico.ClienteServico;
import br.com.aabs.servico.ContratoServico;

@Named
@ViewScoped
public class CadastroContratoView implements Serializable {
	
	@Inject
	private FacesContext facesContext;
	
	@EJB
	private ClienteServico clienteServico;
	
	@EJB
	private ContratoServico contratoServico;
	
	private Contrato contrato = new Contrato();
	
	private Cliente filtro = new Cliente();
	
	private Cliente cliente = new Cliente();
	
	private BigDecimal valorParcela;
	private Date dataPrimeiraParcela;
	
	private static final Logger log = Logger.getLogger(CadastroContratoView.class);
	
	public void init() throws Exception {
		recuperarDataPrimeiraParcela();
	}
	
	private void recuperarDataPrimeiraParcela() throws Exception {
		DateTime hoje = new DateTime();
		
		if(hoje.dayOfMonth().get() >= 10) {
			dataPrimeiraParcela = hoje.plusMonths(1).dayOfMonth().withMaximumValue().toDate();
		} else {
			dataPrimeiraParcela = hoje.dayOfMonth().withMaximumValue().toDate();
		}
	}
	
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
	
	public String cadastrar() throws Exception {
		try {
			
			if(validar()) {
				
				Parcela primeiraParcela = new Parcela();
				primeiraParcela.setData(dataPrimeiraParcela);
				primeiraParcela.setValor(valorParcela);
				
				contrato.setCliente(cliente);
				contratoServico.cadastrar(contrato, primeiraParcela);
				
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação realizada com sucesso!", null));
				
				this.contrato = new Contrato();
				this.valorParcela = null;
				
				recuperarDataPrimeiraParcela();
			}
			
		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
		
		return "";
	}
	
	private boolean validar() throws Exception {
		
		if(contrato.getValor() == null || contrato.getValor().equals(BigDecimal.ZERO)) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Valor do contrato inválido!", null));
			return false;
		}
		
		if(contrato.getQtdParcelas() == null || contrato.getQtdParcelas() < 1) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Quantidade de parcela inválido!", null));
			return false;
		}
		
		if(valorParcela == null || valorParcela.equals(BigDecimal.ZERO)) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Valor da parcela inválido!", null));
			return false;
		}
		
		if(dataPrimeiraParcela.before(new DateTime().dayOfMonth().withMaximumValue().toDate())) {
			//facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Data da primeira parcela inválida!", null));
			//return false;
		}
		
		if(cliente == null || cliente.getId() == null) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Selecione o cliente!", null));
			return false;
		}
		
		if(contrato.getValor().compareTo(valorParcela.multiply(new BigDecimal(contrato.getQtdParcelas()))) == 1) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Verifique o valor das parcelas!", null));
			return false;
		}
		
		return true;
	}
	
	public String alterar() throws Exception {
		try {
			
			contratoServico.alterar(contrato);
			
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação realizada com sucesso!", null));
			
		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
		
		return "";
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
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

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public Date getDataPrimeiraParcela() {
		return dataPrimeiraParcela;
	}

	public void setDataPrimeiraParcela(Date dataPrimeiraParcela) {
		this.dataPrimeiraParcela = dataPrimeiraParcela;
	}
}