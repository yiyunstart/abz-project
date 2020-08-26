package com.abz.controller;

import com.abz.TestDatas;
import com.abz.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.abz.service.BusinessService.SUCCESS;

@RestController
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/purchase", method = RequestMethod.GET)
    public String purchase(@RequestParam(required = false) Boolean rollback, @RequestParam(required = false) Integer count) {
        int orderCount = 30;
        if (count != null) {
            orderCount = count;
        }
        try {
            businessService.purchase(TestDatas.USER_ID, TestDatas.COMMODITY_CODE, orderCount,
                rollback == null ? false : rollback.booleanValue());
        } catch (Exception exx) {
            return "Purchase Failed:" + exx.getMessage();
        }
        return SUCCESS;
    }
}
