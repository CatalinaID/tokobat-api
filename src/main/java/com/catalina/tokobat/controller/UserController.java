package com.catalina.tokobat.controller;


import com.catalina.tokobat.common.Constants;
import com.catalina.tokobat.dao.UserDao;
import com.catalina.tokobat.dto.UserLoginResponse;
import com.catalina.tokobat.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Alifa on 3/12/2015.
 */
@Controller
@RequestMapping(value="/users")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserDao userDAO;
    
/*
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody
    User registerName(@RequestParam(value = "name") String name,
                      @RequestParam(value = "msisdn") String msisdn,
                      Model model) {
        log.info("register msisdn  " + msisdn + " name = " + name);

        try {
            User user = userDAO.getUserByMsisdn(msisdn);
            user.setName(name);
            user = userDAO.updateUser(user);
            return user;
        } catch (Exception e) {

        }
    } */

    @RequestMapping(method=RequestMethod.POST, value = "/login")
    public ResponseEntity<UserLoginResponse> login (
            @RequestParam(value = "uid") String uid,
            @RequestParam(value = "msisdn") String msisdn,
            @RequestParam(value = "credentials") String credentials) {
        
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("msisdn", msisdn);
        params.put("credentials", credentials);
        
        RestTemplate client = new RestTemplate();
        UserLoginResponse res;
        
        try {
            User user = userDAO.getUserByMsisdn(msisdn);
            String respon = client.getForObject(Constants.ECASH_URI
                    + "/loginMember?"
                    + "uid={uid}&msisdn={msisdn}&credentials={credentials}", 
                    String.class, params);
            ObjectMapper mapper = new ObjectMapper();
            res = mapper.readValue(respon, UserLoginResponse.class);
            res.setId(user.getId());
            switch (res.getStatus()) {
                case UserLoginResponse.LOGIN_VALID:
                    user.setSession(res.getToken());
                    user.setUid(uid);
                    userDAO.updateUser(user);
                    log.info("Login " + user.getId() + " valid");
                    break;
                case UserLoginResponse.LOGIN_INVALID:
                    log.info("Login " + user.getId() + " invalid");
                    break;
                case UserLoginResponse.LOGIN_BLOCKED:
                    log.info("Login " + user.getId() + " blocked");
                    break;
            }
        } catch (Exception ex) {
            res = new UserLoginResponse();
            res.setMessage(ex.getMessage());
            log.error(ex.getMessage());
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
