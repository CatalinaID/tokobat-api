package com.catalina.tokobat.controller;


import com.catalina.tokobat.common.Constants;
import com.catalina.tokobat.common.SHA1;
import com.catalina.tokobat.dao.UserDao;
import com.catalina.tokobat.dto.UserDto;
import com.catalina.tokobat.dto.UserLoginDto;
import com.catalina.tokobat.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Alifa on 3/12/2015.
 */
@Controller
@RequestMapping(value="/users")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserDao userDAO;
    

//    @RequestMapping(method = RequestMethod.POST, value = "/register")
//    public @ResponseBody
//    UserDto registerName(@RequestParam(value = "name") String name,
//                      @RequestParam(value = "msisdn") String msisdn,
//                      Model model) {
//
//        try {
//            log.info("register msisdn  " + msisdn + " name = " + name);
//            User user = userDAO.getUserByMsisdn(msisdn);
//            user.setName(name);
//            user = userDAO.updateUser(user);
//            log.info("success register msisdn  " + user.getMsisdn() + " name = " + user.getName());
//            UserDto userDto = new UserDto(Constants.DEFAULT_SUCCESS,user.getId(),user);
//            return userDto;
//        } catch (Exception e) {
//            UserDto userDto = new UserDto(e.getMessage(),Constants.ERROR_INDEX);
//        }
//        UserDto userDto = new UserDto(Constants.DEFAULT_FAIL,Constants.ERROR_INDEX);
//        return  userDto;
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<UserLoginDto> validate(
            @RequestParam String username,
            @RequestParam String password) {
        log.info("validate user with id = " + username );
        UserLoginDto res = new UserLoginDto();

        if (username.isEmpty() || password.isEmpty()) {
            res.setMessage("Username or password is empty");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        User user = userDAO.findByUsername(username);
        if (user == null) {
            res.setMessage("Username not registered");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        SHA1 sha1 = new SHA1(password + user.getSalt());
        if (!user.getHash().equals(sha1.hash())) {
            res.setMessage("Username and password don't match");
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }
        res.setId(user.getId());
        res.setName(user.getName());
        res.setMessage("Success");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
