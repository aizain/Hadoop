package com.gyzh.zain.hive.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Demo1 {
    public static void main(String[] args) throws Exception {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection conn = DriverManager.getConnection(
                "jdbc:hive2://106.75.37.189:10000/park2","","");
        PreparedStatement ps = conn.prepareStatement("select * from book");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            System.out.println(name);
        }
        rs.close();
        ps.close();
        conn.close();
    }
}
