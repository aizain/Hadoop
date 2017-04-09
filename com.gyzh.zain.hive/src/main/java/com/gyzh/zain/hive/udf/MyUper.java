package com.gyzh.zain.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * hive自定义函数
 * 
 * @author zain
 * 17/04/09
 */
public class MyUper extends UDF {
    
    /**
     * 使用该命名函数，随意入参和返回值
     */
    public String evaluate(String str) {
        return str.toUpperCase();
    }
}
