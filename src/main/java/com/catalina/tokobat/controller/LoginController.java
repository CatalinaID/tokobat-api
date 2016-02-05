package com.catalina.tokobat.controller;

import com.catalina.tokobat.common.Constants;
import com.catalina.tokobat.dao.UserDao;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.inject.Inject;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author icha
 */
@Controller
@RequestMapping(value="/login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    
    @Inject
    private UserDao userDAO;

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody LoginResponse login (
            @RequestParam(value = "uid") String uid,
            @RequestParam(value = "msisdn") String msisdn,
            @RequestParam(value = "credentials") String credentials) {
        
        log.info("masuk");
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("msisdn", msisdn);
        params.put("credentials", credentials);
        
        RestTemplate client = new RestTemplate();
        
        String respon = client.getForObject(Constants.ECASH_URI
                + "/loginMember?"
                + "uid={uid}&msisdn={msisdn}&credentials={credentials}", 
                String.class, params);
        ObjectMapper mapper = new ObjectMapper();
        LoginResponse res;
        try {
            res = mapper.readValue(respon, LoginResponse.class);
            
            if (res.getStatus().equals(LoginResponse.LOGIN_VALID)) {
                //TODO: get user by msisdn, save token and uid
                log.info("Login valid");
            } else if (res.getStatus().equals(LoginResponse.LOGIN_INVALID)) {
                log.info("Login invalid");
            }
        } catch (IOException ex) {
            res = new LoginResponse();
            java.util.logging.Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    private class LoginResponse implements Serializable{
        private String msisdn;
        private String status;
        private String token;
        
        public static final String LOGIN_VALID = "VALID";
        public static final String LOGIN_INVALID = "INVALID";
        public static final String LOGIN_BLOCKED = "BLOCKED";

        public LoginResponse() {
        }

        public String getMsisdn() {
            return msisdn;
        }

        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
                
    }
}
