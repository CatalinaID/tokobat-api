package com.catalina.tokobat.controller;

import com.catalina.tokobat.common.Constants;
import com.catalina.tokobat.common.SHA1;
import com.catalina.tokobat.dao.ApotekDao;
import com.catalina.tokobat.dto.ApotekDto;
import com.catalina.tokobat.dto.ApotekLoginDto;
import com.catalina.tokobat.entity.Apotek;
import com.catalina.tokobat.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alifa on 2/7/2016.
 */
@Controller
@RequestMapping(value="/apotek")
public class ApotekController {

    private static Logger log = LoggerFactory.getLogger(ApotekController.class);

    @Inject
    private ApotekDao apotekDAO;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public @ResponseBody
    ApotekLoginDto validate(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String psswd, Model model) {
        log.info("validate user with id = " + username );

        if (username!=null) {
            Apotek apotek = apotekDAO.findByUsername(username);
            if (apotek != null) {
                SHA1 sha1 = new SHA1(psswd+apotek.getSalt());
                if (apotek.getHash().equals(sha1.hash())) {
                    return new ApotekLoginDto(Constants.SUCCESS_INDEX, "success", apotek.getId(), apotek.getName());
                } else {
                    return new ApotekLoginDto(Constants.ERROR_INDEX, "invalid", apotek.getId(), apotek.getName());
                }
            }
        }
        return new ApotekLoginDto(Constants.ERROR_INDEX, "error");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public @ResponseBody
    List<ApotekDto> listApotek(
          Model model) {

        List<Apotek> list = new ArrayList<>();
        List<ApotekDto> listDto = new ArrayList<>();

        try {
            list = apotekDAO.listAll();
            for (int i=0; i<list.size(); i++) {
                listDto.add(new ApotekDto(list.get(i).getId(), list.get(i).getName(),
                        list.get(i).getAddress(), list.get(i).getLat(), list.get(i).getLng()));
            }
        } catch (Exception e) {

        }
        return listDto;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public @ResponseBody
    ApotekDto addApotek(@RequestParam(value = "username") String username , @RequestParam(value = "name") String name,
                    @RequestParam(value = "password") String psswd, Model model) {
        log.info("new apotek = " + username + " name = " + name);

        SecureRandom random = new SecureRandom();

        Apotek apotek = new Apotek();
        apotek.setUsername(username);
        apotek.setName(name);
        String userSalt = new BigInteger(130, random).toString(32);
        apotek.setSalt(userSalt);
        apotek.setBalance(new BigDecimal("0"));
        apotek.setLat("-6.89148");
        apotek.setLng("107.6106591");
        SHA1 sha1 = new SHA1(psswd+apotek.getSalt());
        apotek.setHash(sha1.hash());

        apotek = apotekDAO.add(apotek);
        if (apotek!=null) {
            ApotekDto apotekDto = new ApotekDto(apotek.getId(),apotek.getName(),apotek.getUsername(),apotek.getAddress()
                    ,apotek.getLat(),apotek.getLng(),apotek.getBalance(), Constants.DEFAULT_SUCCESS);
            return apotekDto;
        }

        ApotekDto fail = new ApotekDto(Constants.ERROR_INDEX,Constants.DEFAULT_FAIL);
        return fail;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateLokasi")
    public @ResponseBody
    ApotekDto addApotek(@RequestParam(value = "id") long id , @RequestParam(value = "lat") String lat , @RequestParam(value = "lng") String lng, Model model) {
        log.info("update apotek = " + lat + " - " + lng);

        SecureRandom random = new SecureRandom();

        Apotek apotek = apotekDAO.getApotekById(id);
        apotek.setLat(lat);
        apotek.setLng(lng);
        try {
            apotek = apotekDAO.update(apotek);
            return new ApotekDto(apotek.getId(),apotek.getName(),apotek.getUsername(),apotek.getAddress()
                    ,apotek.getLat(),apotek.getLng(),apotek.getBalance(), Constants.DEFAULT_SUCCESS);
        } catch (Exception e) {

        }

        ApotekDto fail = new ApotekDto(Constants.ERROR_INDEX,Constants.DEFAULT_FAIL);
        return fail;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updatePwd")
    public @ResponseBody
    ApotekDto updatePwd(@RequestParam(value = "id") long id , @RequestParam(value = "hash") String hash , @RequestParam(value = "salt") String salt, Model model) {

        SecureRandom random = new SecureRandom();

        Apotek apotek = apotekDAO.getApotekById(id);
        apotek.setHash(hash);
        apotek.setSalt(salt);
        try {
            apotek = apotekDAO.update(apotek);
            return new ApotekDto(apotek.getId(),apotek.getName(),apotek.getUsername(),apotek.getAddress()
                    ,apotek.getLat(),apotek.getLng(),apotek.getBalance(), Constants.DEFAULT_SUCCESS);
        } catch (Exception e) {

        }

        ApotekDto fail = new ApotekDto(Constants.ERROR_INDEX,Constants.DEFAULT_FAIL);
        return fail;
    }
}
