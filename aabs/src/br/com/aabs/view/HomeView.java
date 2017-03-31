package br.com.aabs.view;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.aabs.entidade.Contrato;
import br.com.aabs.servico.ContratoServico;

@Named
@RequestScoped
public class HomeView implements Serializable {
	
	@Inject
	private FacesContext facesContext;
	
	@EJB
	private ContratoServico contratoServico;
	
	private List<Contrato> ultimosContratos;
	
	public void init() throws Exception {
		ultimosContratos = contratoServico.ultimosContratos();
	}

	public List<Contrato> getUltimosContratos() {
		return ultimosContratos;
	}

	public void setUltimosContratos(List<Contrato> ultimosContratos) {
		this.ultimosContratos = ultimosContratos;
	}
}