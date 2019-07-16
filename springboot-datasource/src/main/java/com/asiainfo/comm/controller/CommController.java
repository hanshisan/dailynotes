package com.asiainfo.comm.controller;

import com.asiainfo.comm.service.CommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author hansong
 * @ClassName CommController
 * @create at  2019-07-16 16:27
 * @desc
 * @Version 1.0
 **/
@RestController
@RequestMapping("v1/comm")
public class CommController {

    @Autowired
    private CommService commService;

    @GetMapping("/getData")
    public List<Map<String,Object>> getData(){
        return commService.getData();
    }
}
