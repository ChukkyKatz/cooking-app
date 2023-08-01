package com.chukkykatz.cooking.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(final HttpServletRequest request, final Model model) {
        final Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        final String statusCode = status.toString();
        model.addAttribute("scCh1", statusCode.charAt(0));
        model.addAttribute("scCh2", statusCode.charAt(1));
        model.addAttribute("scCh3", statusCode.charAt(2));
        return "error";
    }
}
