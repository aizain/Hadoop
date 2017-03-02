package com.gyzh.zain.zebra.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.gyzh.zain.zebra.domain.HTTPAPPHost;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 将数据持久化起来
 * @author zain
 * 17/02/19
 */
public class DBExec {
    
    /**
     * 存入数据库
     * @param list
     */
    public static void toDb(List<HTTPAPPHost> list) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            DataSource source = new ComboPooledDataSource();
            conn = source.getConnection();
            conn.setAutoCommit(false);
            String sql = "insert into F_HTTP_APP_HOST "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            for (HTTPAPPHost hah : list) {
                ps.setTimestamp(1, hah.getReportTime());
                ps.setInt(2, hah.getAppType());
                ps.setInt(3, hah.getAppSubtype());
                ps.setString(4, hah.getUserIP());
                ps.setInt(5, hah.getUserPort());
                ps.setString(6, hah.getAppServerIP());
                ps.setInt(7, hah.getAppServerPort());
                ps.setString(8, hah.getHost());
                ps.setString(9, hah.getCellid());
                ps.setLong(10, hah.getAttempts());
                ps.setLong(11, hah.getAccepts());
                ps.setLong(12, hah.getTrafficUL());
                ps.setLong(13, hah.getTrafficDL());
                ps.setLong(14, hah.getRetranUL());
                ps.setLong(15, hah.getRetranDL());
                ps.setLong(16, hah.getFailCount());
                ps.setLong(17, hah.getTransDelay());
                
                ps.addBatch();
            }
            
            ps.executeBatch();
            ps.clearBatch();
            conn.commit();
        } catch (Exception e) {
            if (null != conn)
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            e.printStackTrace();
        } finally {
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
