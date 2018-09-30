package com.cuzz.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cuzz.ticket.service.TicketService;
import org.springframework.stereotype.Service;

/**
 * @Author: cuzz
 * @Date: 2018/9/30 12:32
 * @Description:
 */
@Service
public class UserService {

    @Reference
    TicketService ticketService;

    public void hello(){
        String ticket = ticketService.getTicket();
        System.out.println("您已经成功买票："+ticket);
    }
}
