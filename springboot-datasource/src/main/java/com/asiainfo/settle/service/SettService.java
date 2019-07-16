package com.asiainfo.settle.service;

import com.asiainfo.settle.dao.SettleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hansong
 * @ClassName CommController
 * @create at  2019-07-16 16:27
 * @desc
 * @Version 1.0
 **/
@Service
public class SettService {

    @Autowired
    private SettleDao settleDao;


    public List<Map<String, Object>> getData() {

        return settleDao.getData();
    }
}
