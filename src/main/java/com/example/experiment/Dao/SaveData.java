package com.example.experiment.Dao;

import com.example.experiment.Domain.DBData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SaveData {
    protected static final Logger logger = LoggerFactory.getLogger(SaveData.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public String insert(DBData dbData) {
        try {
            jdbcTemplate.update("insert into collection_data (time,device1_tem,device1_hum,device1_co,device2_tem,device2_hum,device2_co,device3_tem,device3_hum,device3_co) value(?,?,?,?,?,?,?,?,?,?)",
                    dbData.getTime(), dbData.getTem().get(0), dbData.getHum().get(0), dbData.getCo().get(0), dbData.getTem().get(1), dbData.getHum().get(1), dbData.getCo().get(1), dbData.getTem().get(2), dbData.getHum().get(2), dbData.getCo().get(2));
            logger.info("数据库插入成功！！");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
