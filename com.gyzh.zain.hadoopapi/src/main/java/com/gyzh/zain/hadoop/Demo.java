package com.gyzh.zain.hadoop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试hadoop api
 * @author zain
 * 17/04/02
 */
public class Demo {
    private static final String HADOOP_URI = "hdfs://106.75.49.22:9000";
    private static final Logger logger = LoggerFactory.getLogger(Demo.class);
    private static Configuration conf = new Configuration();
    
//    public static void main(String[] args) throws Exception {
//        FileSystem fs = TestConnection();
//        // TestMkdir(fs);
//        // TestCopyToLocal(fs);
//        // TestCopyFromLocal(fs);
//        // TestDel(fs);
//        // TestStatus(fs);
//        TestBlockLocation(fs);
//        fs.close();
//        
//    }
    
    
    /**
     * 连接api
     * @throws Exception
     */
    public static FileSystem TestConnection() throws Exception {
        System.out.println("TestConnection begin");
        // conf.set("dfs.replication", "1");
        
        // FileSystem是Hadoop的文件系统的抽象类，HDFS分布式文件系统只是Hadoop文件系统中的一种
        // 对应的实现类org.apache.hadoop.hdfs.DistributedFileSystem，HDFS是Hadoop开发者最常用的文件系统
        // 因为HDFS可以和MapReduce结合，从而到达处理海量数据的目的
        // Hftp：基于HTTP方式去访问HDFS文件系统（只读）
        // 还有本地文件系统、存档文件系统、基于安全模式的HTTP访问HDFS的文件系统
        FileSystem fs = FileSystem.get(new URI(HADOOP_URI), conf);
        System.out.println("TestConnection over --- " + fs);
        return fs;
    }
    
    /**
     * 创建目录
     * @throws Exception
     */
    public static void TestMkdir(FileSystem fs) throws Exception {
        System.out.println("TestMkdir begin");
        // 创建目录
        fs.mkdirs(new Path("/park07"));
        System.out.println("TestMkdir over");
    }
    
    /**
     * 下载到本地
     * @throws Exception
     */
    public static void TestCopyToLocal(FileSystem fs) throws Exception {
        System.out.println("TestCopyToLocal begin");
        // fs.copyToLocalFile(new Path("/park01/1.txt"), new Path("1.txt"));
        InputStream is = fs.open(new Path("/1.txt"));
        OutputStream os = new FileOutputStream(new File("1.txt"));
        IOUtils.copyBytes(is, os, conf);
        System.out.println("TestCopyToLocal over");
    }
    
    /**
     * 上传文件
     * @param fs
     * @throws Exception
     */
    public static void TestCopyFromLocal(FileSystem fs) throws Exception {
        System.out.println("TestCopyFromLocal begin");
        // fs.copyFromLocalFile(new Path("3.txt"), new Path("/park01"));
        OutputStream os = fs.create(new Path("/1.txt"));
        InputStream is = new FileInputStream(new File("1.txt"));
        // 流的对接，主要要写到文件名
        IOUtils.copyBytes(is, os, conf);
        System.out.println("TestCopyFromLocal over");
    }
    
    /**
     * 删除
     * @param fs
     * @throws Exception
     */
    public static void TestDel(FileSystem fs) throws Exception {
        System.out.println("TestDel begin");
        // fs.delete(new Path("/park01"));
        // true 递归删除， false 只能删除为空的目录
        fs.delete(new Path("/park01"), true);
        System.out.println("TestDel over");
    }
    
    /**
     * 查看文件信息
     * @param fs
     * @throws Exception
     */
    public static void TestStatus(FileSystem fs) throws Exception {
        System.out.println("TestStatus begin");
        FileStatus[] s = fs.listStatus(new Path("/"));
        System.out.println(Arrays.toString(s));
        System.out.println("TestStatus over");
    }
    
    /**
     * 查看分块信息
     * @param fs
     * @throws Exception
     */
    public static void TestBlockLocation(FileSystem fs) throws Exception {
        System.out.println("TestStatus begin");
        // BlockLocation[] bl = fs.getFileBlockLocations(new Path("/1.txt"), 0, 0);
        // BlockLocation[] bl = fs.getFileBlockLocations(new Path("/hadoop-2.7.3.tar.gz"), 0, 134217729);
        BlockLocation[] bl = fs.getFileBlockLocations(new Path("/hadoop-2.7.3.tar.gz"), 0, Integer.MAX_VALUE);
        System.out.println(Arrays.toString(bl));
        System.out.println("TestStatus over");
    }
}
