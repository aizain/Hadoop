package com.gyzh.zain.storm.netflow.more;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;

import org.joda.time.DateTime;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

/**
	create table netFlowInfo2(
		startTime dateTime,
		endTime dateTime,
		br double,
		avgtime bigint,
		avgdeep double,
		primary key (startTime,endTime)
	);
 */
public class ToMySqlBolt2 extends BaseRichBolt {
	private OutputCollector collector = null;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}
	//"beginTime","endTime","br","avgtime","avgdeep"
	@Override
	public void execute(Tuple input) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			long beginTime = input.getLongByField("beginTime");
			long endTime = input.getLongByField("endTime");
			double br = input.getDoubleByField("br");
			long avgtime = input.getLongByField("avgtime");
			double avgdeep = input.getDoubleByField("avgdeep");
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.242.101:3306/netflowdb","root","root");
			conn.setAutoCommit(false);
			
			String sql = "insert into netFlowInfo2 values (?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1,new Timestamp(beginTime));
			ps.setTimestamp(2,new Timestamp(endTime));
			ps.setDouble(3, br);
			ps.setDouble(4, avgtime);
			ps.setDouble(5, avgdeep);
			ps.executeUpdate();
			
			collector.ack(input);
			conn.commit();
		} catch (Exception e) {
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new RuntimeException(e1);
				}
			}
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}finally {
					rs = null;
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}finally {
					ps = null;
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}finally {
					conn = null;
				}
			}
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
