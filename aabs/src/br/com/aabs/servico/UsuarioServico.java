package br.com.aabs.servico;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;

import br.com.aabs.entidade.Usuario;
import br.com.aabs.seguranca.Authentication;

@Named
@RequestScoped
public class UsuarioServico extends Servico {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1102620244224964833L;
	
	private static final Logger log = Logger.getLogger(Authentication.class);
	
	@Inject
	private EntityManager em;
	
	public Usuario login(String login, String senha) throws Exception {
		Usuario usu = null;
		
		try {
			
			usu	 = (Usuario) em.createNativeQuery("select * from usuario where login = :login and senha = :senha", Usuario.class)
				.setParameter("login", login)
				.setParameter("senha", senha).getSingleResult();	
			
		} catch (NoResultException nre) {
			
		}
		
		return usu;
	}
	
	private static String getSHA1Hash(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
        	log.error(e.getMessage(), e);
        }
        return generatedPassword;
    }
}