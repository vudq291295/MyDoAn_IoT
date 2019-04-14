package com.dqv.mqtt.security.impl;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.wso2.carbon.identity.oauth2.stub.OAuth2TokenValidationService;
import org.wso2.carbon.identity.oauth2.stub.OAuth2TokenValidationServiceStub;
import org.wso2.carbon.identity.oauth2.stub.dto.*;

import com.dqv.jdbc.AuthJDBC;
import com.dqv.jdbc.ConnectDatabase;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Giovanni Baleani on 12/02/2015.
 */
public class Oauth2TokenValidator {
    private OAuth2TokenValidationServiceStub oAuth2TokenValidationService = null;
    private String identityURL;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public Oauth2TokenValidator(String identityURL, String userName, String password)
            throws MalformedURLException, AxisFault {

        this.identityURL = identityURL;
        String adminUserName = userName;
        String adminPassword = password;

        //create service client with given url
        String targetEndpointUrl = identityURL + "/services/OAuth2TokenValidationService.OAuth2TokenValidationServiceHttpsSoap12Endpoint/";
        logger.info("Truoc OAuth2TokenValidationServiceStub");
        oAuth2TokenValidationService = new OAuth2TokenValidationServiceStub(targetEndpointUrl);
        logger.info("sau OAuth2TokenValidationServiceStub");

        ServiceClient client = oAuth2TokenValidationService._getServiceClient();
        HttpTransportProperties.Authenticator authenticator = new HttpTransportProperties.Authenticator();
        authenticator.setUsername(adminUserName);
        authenticator.setPassword(adminPassword);
        authenticator.setPreemptiveAuthentication(true);
        Options options = client.getOptions();
        options.setProperty(HTTPConstants.AUTHENTICATE, authenticator);

        client.setOptions(options);
    }

    /**
     * Set trustStore system properties
     * @param trustStorePath javax.net.ssl.trustStore system property
     * @param trustStorePassword javax.net.ssl.trustStorePassword system property
     */
    public void initTrustStore(String trustStorePath, String trustStorePassword) {
        //set trust store properties required in SSL communication.
        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
    }

    public boolean tokenIsValid(String access_token,String passwordOrRefreshToken) throws Exception {
        OAuth2TokenValidationRequestDTO req = createRequestTokenDTO(access_token);
    	logger.info("VUDQ tokenIsValid:" + passwordOrRefreshToken);
//        OAuth2TokenValidationResponseDTO resp = oAuth2TokenValidationService.validate(req);
    	//logger.info("VUDQ tokenIsValid:" + resp.getAuthorizedUser());
    	Connection connnect = ConnectDatabase.getConnectDatabase();
    	AuthJDBC conn = new AuthJDBC(connnect);
    	boolean isValid = conn.checkAuth(access_token,passwordOrRefreshToken);
    	connnect.close();
//        boolean isValid = resp.getValid();
        return isValid;
    }

    public TokenInfo getTokenInfo(String access_token,String passwordOrRefreshToken) throws Exception {
    	logger.info("VUDQ getTokenInfo:" + access_token);
    	Connection connnect = ConnectDatabase.getConnectDatabase();
    	AuthJDBC conn = new AuthJDBC(connnect);
        OAuth2TokenValidationRequestDTO req = createRequestTokenDTO(access_token);

        OAuth2TokenValidationResponseDTO resp = conn.validation(access_token,passwordOrRefreshToken);
    	logger.info("VUDQ getTokenInfo resp:" + resp.getAuthorizedUser());
    	connnect.close();

        String authorizedUser = resp.getAuthorizedUser();
        List<String> scope =  new ArrayList<String>();
        Long expiryTime = resp.getExpiryTime();
        String errorMsg = resp.getErrorMsg();

//        String authorizedUser = "admin";
//        List<String> scope = new ArrayList<String>();
//        Long expiryTime = Long.parseLong("10000000");
//        String errorMsg = "";

        TokenInfo tinfo = new TokenInfo();
        tinfo.setAuthorizedUser(authorizedUser);
        tinfo.setScope(scope);
        tinfo.setErrorMsg(errorMsg);
        tinfo.setExpiryTime(expiryTime);
        return tinfo;
    }


    private OAuth2TokenValidationRequestDTO createRequestTokenDTO(String access_token) {
        OAuth2TokenValidationRequestDTO req = new OAuth2TokenValidationRequestDTO();
        OAuth2TokenValidationRequestDTO_OAuth2AccessToken token = new OAuth2TokenValidationRequestDTO_OAuth2AccessToken();
        token.setTokenType("bearer");
        token.setIdentifier(access_token);
        req.setAccessToken(token);
        return req;
    }


}
