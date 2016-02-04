package com.paito.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by patrick on 16/2/4.
 */
@Controller
@RequestMapping(value="/home")
public class HomeController {

    @RequestMapping(value = "/uploaddomain.json", method = RequestMethod.GET)
    public String redirectToDomainUpload(ModelMap modelMap){
        return "redirect:/pages/submitdomain.htm";
    }

    @RequestMapping(value = "/aboutpaito.shtml", method = RequestMethod.GET)
    public String redirectToAboutPaito(ModelMap modelMap){
        return "redirect:/pages/aboutpaito.htm";
    }


    @RequestMapping(value = "/paimairule.shtml", method = RequestMethod.GET)
    public String redirectToPaimaiRule(ModelMap modelMap){
        return "redirect:/pages/domainrule.htm";
    }


}
