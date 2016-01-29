package com.catalina.tokobat.controller;


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

    @RequestMapping(method=RequestMethod.GET, value="/list")
    public @ResponseBody String getAllUser (Model model) {

        log.info("Searching for all user");

        String test = "hello catalina!";

        return test;
    }

    @RequestMapping("/welcome")
    public ModelAndView helloWorld() {

        String message = "<br><div style='text-align:center;'>"
                + "<h3>********** Hello Calalina **********</div><br><br>";
        return new ModelAndView("welcome", "message", message);
    }



}
