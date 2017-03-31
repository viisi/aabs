package br.com.aabs.servico;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class Servico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesContext facesContext;
	
	public String getLogin() {
		return facesContext.getExternalContext().getUserPrincipal().getName();
	}
	
	protected void getLogger() {
		
	}
	
	//org.hibernate.Session session = ((org.hibernate.ejb.EntityManagerImpl) em.getDelegate()).getSession();
	
	/*
	void getNamedObject() {
		BeanManager bm = CDI.current().getBeanManager();
		Bean<CrudService> bean = (Bean<CrudService>) bm.getBeans(CrudService.class).iterator().next();
		CreationalContext<CrudService> ctx = bm.createCreationalContext(bean);
		CrudService crudService = (CrudService) bm.getReference(bean, CrudService.class, ctx);
	}
	*/
}
