package com.asiainfo.settle.controller;

import com.asiainfo.settle.service.SettService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author hansong
 * @ClassName SettleController
 * @create at  2019-07-16 16:27
 * @desc
 * @Version 1.0
 **/
@RestController
@RequestMapping("v1/settle")
public class SettleController {

    @Autowired
    private SettService settService;

    @GetMapping("/getData")
    public List<Map<String,Object>> getData(){
        return settService.getData();
    }
}
