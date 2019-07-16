package com.asiainfo.comm.service;

import com.asiainfo.comm.dao.CommDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hansong
 * @ClassName CommService
 * @create at  2019-07-16 16:27
 * @desc
 * @Version 1.0
 **/
@Service
public class CommService {


    @Autowired
    private CommDao commDao;

    public List<Map<String, Object>> getData() {
        return commDao.getData();
    }
}
