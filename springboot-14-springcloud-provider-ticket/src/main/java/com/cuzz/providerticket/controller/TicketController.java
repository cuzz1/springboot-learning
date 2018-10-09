package com.cuzz.providerticket.controller;

import com.cuzz.providerticket.service.TicketSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cuzz
 * @Date: 2018/10/9 11:04
 * @Description:
 */
@RestController
public class TicketController {

    @Autowired
    TicketSerivce ticketSerivce;

    @GetMapping("/ticket")
    public String getTicket() {
        return ticketSerivce.getTicket();
    }
}
