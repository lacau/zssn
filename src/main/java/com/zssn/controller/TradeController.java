package com.zssn.controller;

import com.zssn.service.TradeService;
import com.zssn.vo.TradeVO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping
    public void create(@RequestBody @Valid TradeVO request) {
        tradeService.trade(request);
    }
}
