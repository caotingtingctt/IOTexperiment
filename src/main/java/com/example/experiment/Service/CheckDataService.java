package com.example.experiment.Service;

import com.example.experiment.Dao.SaveData;
import com.example.experiment.Domain.DBData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class CheckDataService {
    protected static final Logger logger = LoggerFactory.getLogger(CheckDataService.class);
    @Autowired
    SaveData saveData;
    public String checkData(List list) {
        List tem=new ArrayList();
        List hum=new ArrayList();
        List co=new ArrayList();
        DBData dbData = new DBData();
        for (int i=0;i<3;i++) {
            Map map = (Map) list.get(i);
            if(isDouble(map.get("hum").toString())){
                hum.add(i,map.get("hum").toString());
            }else {
                hum.add(i,"fail");
            }

            if(isDouble(map.get("tem").toString())){
                tem.add(i,map.get("tem").toString());
            }else {
                tem.add(i,"fail");
            }

            if(isDouble(map.get("co").toString())){
                co.add(i,map.get("co").toString());
            }
            else {
                co.add(i,"fail");
            }

        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        dbData.setTime(df.format(new Date()));
        logger.info("检查数据完成，"+dbData.toString());
        return saveData.insert(dbData);
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    }
}
