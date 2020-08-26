package com.abz.controller;

import com.abz.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.abz.service.AccountService.FAIL;
import static com.abz.service.AccountService.SUCCESS;


@RestController
@RefreshScope
public class ConfigController {

    @Autowired
    private AccountService accountService;

    @Value("${abz.name}")
    private String url ;

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String reduce() {

        return url;
    }
}
