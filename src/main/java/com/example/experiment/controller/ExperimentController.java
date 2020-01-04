package com.example.experiment.controller;

import com.example.experiment.Service.CheckDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.*;

@RestController
public class ExperimentController {
    protected static final Logger logger = LoggerFactory.getLogger(ExperimentController.class);
    @Value("${Device1}")
    private String Device1;
    @Value("${Device2}")
    private String Device2;
    @Value("${Device3}")
    private String Device3;

    @Value("${DeviceCount}")
    private int DeviceCount;
    @Autowired
    CheckDataService checkDataService;

    private int index = 0;
    private Set ip = new HashSet();
    private List<Map> data = new ArrayList();

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        String ip = getIpAddr(request);

        this.ip.add(ip);
        logger.info("请求ip：" + this.ip.toString());

        if (this.ip.size() == DeviceCount) {
            index++;
            this.ip.clear();
            logger.info("检查数据批次,ip=" + ip + ",index=" + index);
            return "" + index;
        }

        return "" + index;

    }

    @PostMapping("/collect")
    public String getData(String tem, String hum, String co, String id, HttpServletRequest request) throws InterruptedException {
        logger.info("接收到数据，温度：" + tem + ",湿度：" + hum + ",二氧化碳：" + co + ",ip=" + getIpAddr(request));
        if (Integer.parseInt(id) == index && data.size() < 3) {
            Map a = new HashMap<String, String>();
            a.put("tem", tem);
            a.put("hum", hum);
            a.put("co", co);
            String ip = getIpAddr(request);
            if (ip.equals(Device1)) {
                data.add(0, a);
            } else if (ip.equals(Device2)) {
                data.add(1, a);
            } else if (ip.equals(Device3)) {
                data.add(2, a);
            }
        }
        if (data.size() == DeviceCount) {
            logger.info("第" + index + "次写入数据库");
            return checkDataService.checkData(data);
        }


        return "success";
    }

    /**
     * 获取请求IP
     *
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
