package com.catalina.tokobat.controller;


import com.catalina.tokobat.common.Constants;
import com.catalina.tokobat.dao.UserDao;
import com.catalina.tokobat.dto.UserDto;
import com.catalina.tokobat.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by Alifa on 3/12/2015.
 */
@Controller
@RequestMapping(value="/user")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserDao userDAO;

    @RequestMapping(method=RequestMethod.GET, value="/{userId}")
    public @ResponseBody
    User getUser (@PathVariable(value="userId") long userId, Model model) {

        log.info("Searching for user with id = " + userId);

        User user = userDAO.getUserById(userId);
        return user;
    }

    @RequestMapping(method=RequestMethod.GET, value="/list")
    public @ResponseBody List<User> getAllUser (Model model) {

        log.info("Searching for all user");

        List<User> users = userDAO.getAllUser();

        return users;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public @ResponseBody
    User addUser(@RequestParam(value = "msisdn") String msisdn ,  @RequestParam(value = "uid") String uid,  @RequestParam(value = "name") String name, Model model) {
        log.info("new user  " + msisdn + " name = " + name);

        SecureRandom random = new SecureRandom();

        User user = new User();
        user.setName(name);
        user.setMsisdn(msisdn);
        user.setUid(uid);

        user = userDAO.addNewUser(user);
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody
    UserDto registerName(@RequestParam(value = "name") String name,
                      @RequestParam(value = "msisdn") String msisdn,
                      Model model) {
        log.info("register msisdn  " + msisdn + " name = " + name);

        try {
            User user = userDAO.getUserByMsisdn(msisdn);
            user.setName(name);
            user = userDAO.updateUser(user);
            UserDto userDto = new UserDto(Constants.DEFAULT_SUCCESS,user.getId(),user);
            return userDto;
        } catch (Exception e) {

        }
        UserDto userDto = new UserDto(Constants.DEFAULT_FAIL,Constants.ERROR_INDEX);
        return  userDto;
    }

}
