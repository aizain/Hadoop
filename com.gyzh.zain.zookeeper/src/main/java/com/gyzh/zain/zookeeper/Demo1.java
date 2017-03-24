package com.gyzh.zain.zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * zookeeper测试
 * @author zain
 * 17/03/23
 */
public class Demo1 {
    public static void main(String[] args) throws Exception {
        CountDownLatch cdl = new CountDownLatch(1);
        // 1.连接zookeeper服务器
        String connectString = "106.75.103.26:2181"
                + ",106.75.103.30:2181"
                + ",106.75.101.133:2181";
        ZooKeeper zk = new ZooKeeper(connectString, 5000
                , new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 校验是否完成连接
                if (event.getState() == KeeperState.SyncConnected) {
                    System.out.println("连接zookeeper服务器成功~");
                    cdl.countDown();
                }
                
            }
        }); // new Zookeeper end
        
        // 等待连接完毕
        cdl.await();
        
        // 创建节点 /park
//        zk.create("/park2", "abcdef".getBytes(), 
//                Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println("创建节点完毕~");
        
//        zk.delete("/park20000000002", -1);
//        System.out.println("删除节点完毕~");
        
        // 获取子节点
        List<String> list = zk.getChildren("/", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("触发子节点某操作");
            }
        });
        System.out.println("获取子节点：" + list);
        
        // 更新数据
        zk.setData("/park", "1234444".getBytes(), -1);
        
        // 获取数据
        byte[] data = zk.getData("/park", true, null);
        System.out.println("节点数据：" + new String(data));
        
        // 判断节点是否存在
        Stat stat = zk.exists("/park", true);
        System.out.println("节点是否存在：" + stat.toString());
    }
}
