package com.gyzh.zain.hbase.phoenix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * phoenix基本api
 * @author zain
 * 17/04/10
 */
public class Demo1 {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:phoenix:hadoop01,hadoop02,hadoop03:2181");
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from TAB3");
        while(rs.next()){
               String v = rs.getString("c1");
               System.out.println(v);
        }
        stat.close();
        conn.close();
    }
}
