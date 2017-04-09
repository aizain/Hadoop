package com.gyzh.zain.hbase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

/**
 * hbase试使用
 * @author zain
 * 17/04/09
 */
public class Demo1 {

    public void drop() throws Exception{
        //连接hbase
        HBaseConfiguration conf = new HBaseConfiguration();
        conf.set("hbase.zookeeper.quorum","hadoop01:2181,hadoop02:2181,hadoop03:2181");
        HBaseAdmin admin = new HBaseAdmin(conf);
        
        //删除表
        admin.disableTable("tab1".getBytes());
        admin.deleteTable("tab1".getBytes());
        
        //关闭资源
        admin.close();
    }

    public void delete() throws Exception{
        //连接HBase表
        HBaseConfiguration conf = new HBaseConfiguration();
        conf.set("hbase.zookeeper.quorum","hadoop01:2181,hadoop02:2181,hadoop03:2181");
        HTable table = new HTable(conf, "tab1".getBytes());
        
        //删除数据
        Delete delete = new Delete("rk1".getBytes());
        table.delete(delete);
        
        //关闭连接
        table.close();
    }

    public void get() throws Exception{
        //连接HBase表
        HBaseConfiguration conf = new HBaseConfiguration();
        conf.set("hbase.zookeeper.quorum","hadoop01:2181,hadoop02:2181,hadoop03:2181");
        HTable table = new HTable(conf, "tab1".getBytes());
        
        //获取数据
        Get get = new Get("rk1".getBytes());
        get.addColumn("cf1".getBytes(), "c1".getBytes());
        Result result = table.get(get);
        byte[] value = result.getValue("cf1".getBytes(), "c1".getBytes());
        String str = new String(value);
        System.out.println(str);
        
        //关闭连接
        table.close();
    }

    public void update() throws Exception{
        //连接HBase表
        HBaseConfiguration conf = new HBaseConfiguration();
        conf.set("hbase.zookeeper.quorum","hadoop01:2181,hadoop02:2181,hadoop03:2181");
        HTable table = new HTable(conf, "tab1".getBytes());
        
        //修改数据
        Put put = new Put("rk1".getBytes());
        put.add("cf1".getBytes(), "c1".getBytes(), "v11x".getBytes());
        table.put(put);
        
        //关闭连接
        table.close();
    }

    public void put() throws Exception{
        //连接HBase表
        HBaseConfiguration conf = new HBaseConfiguration();
        conf.set("hbase.zookeeper.quorum","hadoop01:2181,hadoop02:2181,hadoop03:2181");
        HTable table = new HTable(conf, "tab1".getBytes());
        
        //新增数据
        Put put = new Put("rk1".getBytes());
        put.add("cf1".getBytes(), "c1".getBytes(), "v11".getBytes());
        table.put(put);
        
        //关闭连接
        table.close();
    }

    public static void create() throws Exception {
        //连接hbase
        HBaseConfiguration conf = new HBaseConfiguration();
        conf.set("hbase.zookeeper.quorum","hadoop01:2181,hadoop02:2181,hadoop03:2181");
        HBaseAdmin admin = new HBaseAdmin(conf);
        
        //创建表
        //--创建表名描述器
        TableName tname = TableName.valueOf("tab1");
        //--创建表描述其
        HTableDescriptor desc = new HTableDescriptor(tname);
        //--创建列族描述器
        HColumnDescriptor cf1 = new HColumnDescriptor("cf1".getBytes());
        desc.addFamily(cf1);
        //--创建列族描述器
        HColumnDescriptor cf2 = new HColumnDescriptor("cf2".getBytes());
        desc.addFamily(cf2);
        //--调用方法创建表
        admin.createTable(desc);
    
        //关闭资源
        admin.close();
    }
    
    public static void main(String[] args) throws Exception {
        create();
    }
}
