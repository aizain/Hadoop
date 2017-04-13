package com.gyzh.zain.hbase.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseDao {
    private static HTable table = null;
    private HBaseDao() {
    }
    static{
        try {
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum","192.168.242.101:2181,192.168.242.102:2181,192.168.242.103:2181");
            table = new HTable(conf,"netflow");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static List<NetFlowInfo> find(String regex){
        try {
            Scan scan = new Scan();
            scan.setFilter(new RowFilter(CompareOp.EQUAL,new RegexStringComparator(regex)));
            ResultScanner rs = table.getScanner(scan);
            Result result = null;
            List<NetFlowInfo> list = new ArrayList<>();
            while((result = rs.next())!=null){
                NetFlowInfo nfi = new NetFlowInfo();
                nfi.setTime(new String(result.getValue("cf1".getBytes(), "time".getBytes())));
                nfi.setUrlname(new String(result.getValue("cf1".getBytes(), "urlname".getBytes())));
                nfi.setUvid(new String(result.getValue("cf1".getBytes(), "uvid".getBytes())));
                nfi.setSid(new String(result.getValue("cf1".getBytes(), "sid".getBytes())));
                nfi.setScount(new String(result.getValue("cf1".getBytes(), "scount".getBytes())));
                nfi.setStime(new String(result.getValue("cf1".getBytes(), "stime".getBytes())));
                nfi.setCip(new String(result.getValue("cf1".getBytes(), "cip".getBytes())));
                list.add(nfi);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static List<NetFlowInfo> find2(byte[] begin,byte[] end){
        try {
            Scan scan = new Scan();
            scan.setStartRow(begin);
            scan.setStopRow(end);
            ResultScanner rs = table.getScanner(scan);
            Result result = null;
            List<NetFlowInfo> list = new ArrayList<>();
            while((result = rs.next())!=null){
                NetFlowInfo nfi = new NetFlowInfo();
                nfi.setTime(new String(result.getValue("cf1".getBytes(), "time".getBytes())));
                nfi.setUrlname(new String(result.getValue("cf1".getBytes(), "urlname".getBytes())));
                nfi.setUvid(new String(result.getValue("cf1".getBytes(), "uvid".getBytes())));
                nfi.setSid(new String(result.getValue("cf1".getBytes(), "sid".getBytes())));
                nfi.setScount(new String(result.getValue("cf1".getBytes(), "scount".getBytes())));
                nfi.setStime(new String(result.getValue("cf1".getBytes(), "stime".getBytes())));
                nfi.setCip(new String(result.getValue("cf1".getBytes(), "cip".getBytes())));
                list.add(nfi);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static void add(NetFlowInfo nfi){
        try {
            Put put = new Put(Bytes.toBytes(nfi.getRowKey()));
            put.add(Bytes.toBytes("cf1"),Bytes.toBytes("time"),Bytes.toBytes(nfi.getTime()));
            put.add(Bytes.toBytes("cf1"),Bytes.toBytes("urlname"),Bytes.toBytes(nfi.getUrlname()));
            put.add(Bytes.toBytes("cf1"),Bytes.toBytes("uvid"),Bytes.toBytes(nfi.getUvid()));
            put.add(Bytes.toBytes("cf1"),Bytes.toBytes("sid"),Bytes.toBytes(nfi.getSid()));
            put.add(Bytes.toBytes("cf1"),Bytes.toBytes("scount"),Bytes.toBytes(nfi.getScount()));
            put.add(Bytes.toBytes("cf1"),Bytes.toBytes("stime"),Bytes.toBytes(nfi.getStime()));
            put.add(Bytes.toBytes("cf1"),Bytes.toBytes("cip"),Bytes.toBytes(nfi.getCip()));
            table.put(put);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
