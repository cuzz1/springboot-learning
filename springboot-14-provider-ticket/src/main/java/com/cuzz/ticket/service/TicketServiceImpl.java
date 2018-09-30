package com.cuzz.ticket.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @Author: cuzz
 * @Date: 2018/9/30 12:28
 * @Description:
 */
@Component
@Service // 这个是dubbo @Service
public class TicketServiceImpl implements TicketService{

    @Override
    public String getTicket() {
        return "《大话西游》";
    }
}
