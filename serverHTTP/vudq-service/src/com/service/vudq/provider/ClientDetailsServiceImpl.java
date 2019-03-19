package com.service.vudq.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService{
	
    private static final String CLIENT_CREDENTIALS = "client_credentials";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String PASSWORD = "password";

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws OAuth2Exception {
	    List<String> authorizedGrantTypes = new ArrayList<>();
	    authorizedGrantTypes.add(PASSWORD);
	    authorizedGrantTypes.add(REFRESH_TOKEN);
	    authorizedGrantTypes.add(CLIENT_CREDENTIALS);
	    
	    if (clientId.equals("client1")) {
	        BaseClientDetails clientDetails = new BaseClientDetails();
	        clientDetails.setClientId("client1");
	        clientDetails.setClientSecret("client1");
	        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
	        return clientDetails;
	    } 
	    else if(clientId.equals("client2")){
	        BaseClientDetails clientDetails = new BaseClientDetails();
	        clientDetails.setClientId("client2");
	        clientDetails.setClientSecret("client2");
	        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
	        return clientDetails;
	    }
	    else{
	        throw new NoSuchClientException("No client with requested id: " + clientId);
	    }
	}

}
