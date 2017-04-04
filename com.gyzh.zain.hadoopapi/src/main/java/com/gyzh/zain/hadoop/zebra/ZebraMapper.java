package com.gyzh.zain.hadoop.zebra;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class ZebraMapper extends Mapper<LongWritable, Text, Text, HTTPAPPHostBean> {
    
    private static Map<String, Integer> map = new HashMap<>();
    private static Map<String, Integer> map2 = new HashMap<>();
    static {
        // 基站中一行数据中各个指标的位置即为key
        map.put("cellid", 17);
        map.put("appType", 23);
        map.put("appSubtype", 24);
        map.put("userIP", 27);
        map.put("userPort", 29);
        map.put("appServerIP", 31);
        map.put("appServerPort", 33);
        map.put("host", 59);
        
        map2.put("reportTimeStr", 0);
        map2.put("appTypeCode", 19);
        map2.put("procdureStartTime", 20);
        map2.put("procdureEndTime", 21);
        map2.put("transStatus", 55);
        map2.put("interruptType", 68);
        map2.put("trafficUL", 34);
        map2.put("trafficDL", 35);
        map2.put("retranUL", 40);
        map2.put("retranDL", 41);
        
    }
    
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, HTTPAPPHostBean>.Context context)
            throws IOException, InterruptedException {
        
        String line = value.toString();
        String[] attrs = line.split("\\|");
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String fileName = fileSplit.getPath().getName();
        
        
        HTTPAPPHostBean hah = new HTTPAPPHostBean();
        setAttrs(hah, attrs, map);
        OtherInfo oi = new OtherInfo();
        setAttrs(oi, attrs, map2);
        
        // 2.业务逻辑处理，计算数据库中需要的字段
        // 设置时间片信息
        String reportTimeStr = attrs[0];
        hah.setReportTime(fileName.split("_")[1]);
        hah.setSlice(Long.parseLong(reportTimeStr));
        
        // 若ceilid为空，补全成000000000
        if (null == hah.getCellid() || "".equals(hah.getCellid())) {
            hah.setCellid("000000000");
        }
        
        // 业务逻辑：尝试次数 HTTP_ATTEMPT attepts if( App Type Code=103 ) then counter++
        if (103 == oi.getAppTypeCode()) {
            hah.setAttempts(1);
        }
        
        // 业务逻辑：接受次数    HTTP_Accept accepts if( App Type Code=103 & HTTP/WAP事物状态 in(10,11,12,13,14,15,32,33,34,35,36,37,38,48,49,50,51,52,53,54,55,199,200,201,202,203,204,205,206,302,304,306) && Wtp中断类型==NULL) then counter++
        String code = ",10,11,12,13,14,15,32,33,34,35,36,37,38,48,49,50,51,52,53,54,55,199,200,201,202,203,204,205,206,302,304,306,";
        if (103 == oi.getAppTypeCode() 
                && code.contains("," + oi.getTransStatus() + ",")
                && null == oi.getInterruptType()) {
            hah.setAccepts(1);
        }
        
        // 业务逻辑：上行流量 Traffic_UL_HTTP trafficUL   if( App Type Code=103  ) then counter = counter + UL Data
        // 业务逻辑：下行流量 traffic_DL_HTTP trafficDL   if( App Type Code=103  ) then counter = counter + DL Data
        // 业务逻辑：重传上行报文数 Retran_UL   retranUL    if( App Type Code=103  ) then counter = counter + 上行TCP重传报文数
        // 业务逻辑：重传下行报文数 Retran_DL   retranDL    if( App Type Code=103  ) then counter = counter + 下行TCP重传报文数
        if (103 == oi.getAppTypeCode()) {
            hah.setTrafficUL(oi.getTrafficUL());
            hah.setTrafficDL(oi.getTrafficDL());
            hah.setRetranUL(oi.getRetranUL());
            hah.setRetranDL(oi.getRetranDL());
        }
        
        // 业务逻辑：延时失败次数  HTTP_Fail_Count failCount   if( App Type Code=103 &&  HTTP/WAP事务状态==1  &&  Wtp中断类型==NULL ) then counter = counter + 1
        if (103 == oi.getAppTypeCode() 
                && 1 == oi.getTransStatus()
                && null == oi.getInterruptType()) {
            hah.setFailCount(1);
        }
        
        // 业务逻辑：传输时延    trans_delay1    transDelay  if( App Type Code=103  ) then counter = counter + (Procedure_End_time-Procedure_Start_time)
        if (103 == oi.getAppTypeCode()) {
            hah.setTransDelay(oi.getProcdureEndTime() - oi.getProcdureStartTime());
        }
        
        
        // 3.进行数据预处理
        // 进行数据的聚合
        String id = hah.getSlice() + "|" + hah.getAppType() + "|" + hah.getAppSubtype() + "|" 
            + hah.getUserIP() + "|" + hah.getUserPort() + "|" + hah.getAppServerIP() + "|"  
            + hah.getAppServerPort() + "|" + hah.getHost() + "|" + hah.getCellid();
        
        context.write(new Text(id), hah);
    }
    
    /**
     * 设置属性值
     * @param obj
     * @param attrs
     * @param map
     */
    private void setAttrs(Object obj, String[] attrs, Map<String, Integer> map) {
        try {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String attrName = entry.getKey();
                Class clz = obj.getClass();
                PropertyDescriptor pd = new PropertyDescriptor(attrName, clz);
                Method wm = pd.getWriteMethod();
                if (pd.getPropertyType() == String.class) {
                    wm.invoke(obj, attrs[entry.getValue()]);
                } else if (pd.getPropertyType() == Integer.class || pd.getPropertyType() == int.class) {
                    wm.invoke(obj, Integer.parseInt(attrs[entry.getValue()]));
                } else if (pd.getPropertyType() == Long.class || pd.getPropertyType() == long.class) {
                    wm.invoke(obj, Long.parseLong(attrs[entry.getValue()]));
                } else {
                    throw new RuntimeException("未知类型");
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
