package com.dristy.WeeklyMealSpring.controller;

import com.dristy.WeeklyMealSpring.controller.data.LoginFormData;
import com.dristy.WeeklyMealSpring.filter.AuthenticationFilter;
import com.dristy.WeeklyMealSpring.service.UserService;
import com.dristy.WeeklyMealSpring.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class AuthenticationController {

    private final static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final static String INDEX_URL = "/";

    private final static String LOGIN_VIEW = "login";

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String login(HttpSession httpSession, ModelMap modelMap) {
        if (Util.isValidSession(httpSession)) {
            return Util.redirectTo("/weekly-meal/menu");
        }

        modelMap.put("loginFormData", new LoginFormData());
        return LOGIN_VIEW;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();

        return Util.redirectTo(INDEX_URL);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String processLogin(@ModelAttribute("loginFormData") @Valid LoginFormData loginFormData,
                               BindingResult bindingResult,
                               HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return LOGIN_VIEW;
        }

        if (userService.validateUserDate(loginFormData)) {
            log.info("User {} found!", loginFormData.getUsername().toLowerCase());
            httpSession.setAttribute("username", loginFormData.getUsername().toLowerCase());
            httpSession.setMaxInactiveInterval(10000000);
        } else {
            bindingResult.reject("login.message.invalid");
            return LOGIN_VIEW;
        }

        return Util.redirectTo(INDEX_URL);
    }
}
