package com.catalina.tokobat.controller;

import com.catalina.tokobat.common.Constants;
import com.catalina.tokobat.dao.UserDao;
import com.catalina.tokobat.entity.User;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    
    @Inject
    private UserDao userDAO;

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody LoginResponse login (
            @RequestParam(value = "uid") String uid,
            @RequestParam(value = "msisdn") String msisdn,
            @RequestParam(value = "credentials") String credentials) {
        
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
            User user = userDAO.getUserByMsisdn(msisdn);
            switch (res.getStatus()) {
                case LoginResponse.LOGIN_VALID:
                    user.setSession(res.getToken());
                    user.setUid(uid);
                    userDAO.updateUser(user);
                    log.info("Login " + user.getId() + " valid");
                    break;
                case LoginResponse.LOGIN_INVALID:
                    log.info("Login " + user.getId() + " invalid");
                    break;
                case LoginResponse.LOGIN_BLOCKED:
                    log.info("Login " + user.getId() + " blocked");
                    break;
            }
        } catch (Exception ex) {
            res = new LoginResponse();
            log.error(null, ex);
        }
        return res;
    }
    
    public static class LoginResponse implements Serializable {
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
