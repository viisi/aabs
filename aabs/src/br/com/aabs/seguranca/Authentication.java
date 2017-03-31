package br.com.aabs.seguranca;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.User;

import br.com.aabs.entidade.Usuario;
import br.com.aabs.servico.UsuarioServico;

@PicketLink
public class Authentication extends BaseAuthenticator {

	@Inject 
    private DefaultLoginCredentials credentials;
	
	@Inject
	private PartitionManager partitionManager;
	
	@Inject 
    private UsuarioServico usuarioServico;
	
	@Inject
	private FacesContext facesContext;
	
	private static final Logger log = Logger.getLogger(Authentication.class);
	
	@Override
	public void authenticate() {
		
		try {
	        String hash = getSHA1Hash(credentials.getPassword());
			
			Usuario usu = usuarioServico.login(credentials.getUserId(), hash);
			if(usu != null) {
				
				User user = new User(usu.getLogin());
				
				setStatus(AuthenticationStatus.SUCCESS);
				setAccount(user);
				
				IdentityManager identityManager = this.partitionManager.createIdentityManager();
				identityManager.add(user);
				identityManager.updateCredential(user, new Password(hash));
				
			} else {
				setStatus(AuthenticationStatus.FAILURE);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário e/ou senha invalidos!", "");
				facesContext.addMessage(null, msg);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private static String getSHA1Hash(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            //md.update(getSalt());
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