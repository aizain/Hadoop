package com.gyzh.zain.zebra.exec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.gyzh.zain.zebra.Global;
import com.gyzh.zain.zebra.db.DBExec;
import com.gyzh.zain.zebra.domain.HTTPAPPHost;

/**
 * 处理从队列中取出的数据
 * @author zain
 * 17/02/19
 */
public class Exec implements Runnable {
    private static final Logger logger = Logger.getLogger(Exec.class);
    /**
     * 进行数据的聚合
     */
    @Override
    public void run() {
        try {
            Map<String, HTTPAPPHost> hahMap = new HashMap<>();
            long beginTime = System.currentTimeMillis();
            while (true) {
                HTTPAPPHost hah = Global.getDataqueue().poll(1, TimeUnit.SECONDS);
                if (null != hah) {
                    // 进行数据的聚合
                    String key = hah.getSlice() + "|" + hah.getAppType() + "|" + hah.getAppSubtype() + "|" 
                        + hah.getUserIP() + "|" + hah.getUserPort() + "|" + hah.getAppServerIP() + "|"  
                        + hah.getAppServerPort() + "|" + hah.getHost() + "|" + hah.getCellid();
                    
                    if (hahMap.containsKey(key)) {
                        HTTPAPPHost beforeHah = hahMap.get(key);
                        beforeHah.setAttempts(beforeHah.getAttempts() + hah.getAttempts());
                        beforeHah.setAccepts(beforeHah.getAccepts() + hah.getAccepts());
                        beforeHah.setTrafficUL(beforeHah.getTrafficUL() + hah.getTrafficUL());
                        beforeHah.setTrafficDL(beforeHah.getAttempts() + hah.getAttempts());
                        beforeHah.setRetranUL(beforeHah.getRetranUL() + hah.getRetranUL());
                        beforeHah.setRetranDL(beforeHah.getRetranDL() + hah.getRetranDL());
                        beforeHah.setFailCount(beforeHah.getFailCount() + hah.getFailCount());
                        beforeHah.setTransDelay(beforeHah.getTransDelay() + hah.getTransDelay());
                    } else {
                        hahMap.put(key, hah);
                    }
                }
                // 将处理好的数据，持久化到数据库中
                long nowTime = System.currentTimeMillis();
                if ((nowTime - beginTime) > 5000 && !hahMap.isEmpty()) {
                    // 将数据存储到数据库中
                    List<HTTPAPPHost> list = new ArrayList<>();
                    for (Map.Entry<String, HTTPAPPHost> entry : hahMap.entrySet()) {
                        list.add(entry.getValue());
                    }
                    logger.info("向数据库写入的数据量：" + list.size());
                    DBExec.toDb(list);
                    beginTime = System.currentTimeMillis();
                    hahMap.clear();
                }
            } // while    
        } catch (Exception e) {
            logger.error(e, e);
        }
    }
}
