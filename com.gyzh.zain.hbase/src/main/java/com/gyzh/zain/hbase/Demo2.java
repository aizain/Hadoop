package com.gyzh.zain.hbase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.filter.InclusiveStopFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.RandomRowFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;

/**
 * HBASE API 增强-过滤器的使用 
 * 
 * @author zain
 * 17/04/09
 */
public class Demo2 {
    HTable table = null;
    
    public void before() throws Exception{
        //连接HBase表
        HBaseConfiguration conf = new HBaseConfiguration();
        conf.set("hbase.zookeeper.quorum","hadoop01:2181,hadoop02:2181,hadoop03:2181");
        table = new HTable(conf, "tab1".getBytes());
    }
    
    public void after() throws Exception{
        //关闭连接
        table.close();
    }
    
    
    public void demo6() throws Exception{
        //通过过滤器来查询数据
        Scan scan = new Scan();
        Filter filter1 = new RowFilter(CompareFilter.CompareOp.NOT_EQUAL, new RegexStringComparator("^.*3$"));
        Filter filter2 = new InclusiveStopFilter("rk4".getBytes());
        FilterList flist = new FilterList(Operator.MUST_PASS_ALL,filter1,filter2);
        scan.setFilter(flist);
        ResultScanner scanner = table.getScanner(scan);
        Result result = null;
        while((result = scanner.next())!=null){
            byte[] value = result.getValue("cf1".getBytes(), "c1".getBytes());
            String str = new String(value);
            System.out.println(str);
        }
    }
    
    public void demo5() throws Exception{
        //通过过滤器来查询数据
        Scan scan = new Scan();
        scan.setStartRow("rk2".getBytes());
        scan.setStopRow("rk4".getBytes());
        ResultScanner scanner = table.getScanner(scan);
        Result result = null;
        while((result = scanner.next())!=null){
            byte[] value = result.getValue("cf1".getBytes(), "c1".getBytes());
            String str = new String(value);
            System.out.println(str);
        }
    }
    
    public void demo4() throws Exception{
        //通过过滤器来查询数据
        Scan scan = new Scan();
        Filter filter = new RandomRowFilter((float) 0.5);
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        Result result = null;
        while((result = scanner.next())!=null){
            byte[] value = result.getValue("cf1".getBytes(), "c1".getBytes());
            String str = new String(value);
            System.out.println(str);
        }
    }
    
    public void demo3() throws Exception{
        //通过过滤器来查询数据
        Scan scan = new Scan();
        Filter filter = new PrefixFilter("rk".getBytes());
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        Result result = null;
        while((result = scanner.next())!=null){
            byte[] value = result.getValue("cf1".getBytes(), "c1".getBytes());
            String str = new String(value);
            System.out.println(str);
        }
    }
    
    public void demo2() throws Exception{
        //通过过滤器来查询数据
        Scan scan = new Scan();
        Filter filter = new RowFilter(CompareFilter.CompareOp.NOT_EQUAL, new RegexStringComparator("^.*3$"));
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        Result result = null;
        while((result = scanner.next())!=null){
            byte[] value = result.getValue("cf1".getBytes(), "c1".getBytes());
            String str = new String(value);
            System.out.println(str);
        }
    }
    
    public void demo1() throws Exception{
        //通过过滤器来查询数据
        Scan scan = new Scan();
        Filter filter = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator("rk2".getBytes()));
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        Result result = null;
        while((result = scanner.next())!=null){
            byte[] value = result.getValue("cf1".getBytes(), "c1".getBytes());
            String str = new String(value);
            System.out.println(str);
        }
    }
}
