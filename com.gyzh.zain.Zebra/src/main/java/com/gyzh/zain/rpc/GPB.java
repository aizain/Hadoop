package com.gyzh.zain.rpc;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.google.protobuf.InvalidProtocolBufferException;
import com.gyzh.zain.rpc.PersionX.Persion;

/**
 * 使用Google Protocol Buffer
 * @author zain
 * 17/02/11
 */
public class GPB {
    public static void main(String[] args) throws Exception {
        String path = "1.data";
        // 1.创建对象
        Persion p = Persion.newBuilder()
            .setId(1).setName("zain").setAdd("beijing")
            .build();
        
        System.out.println(p);
        
        // 2.序列化
        byte[] data = p.toByteArray();
        p.writeTo(new FileOutputStream(path));
        // 3.反序列化
        Persion px = Persion.parseFrom(data);
        px = Persion.parseFrom(new FileInputStream(path));
        System.out.println(px);
    }
}
