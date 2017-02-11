package com.gyzh.zain.bio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 愉快的开头
 * @author zain
 * 17/01/26
 */
public class BIODemo {
    public static void main(String[] args) throws IOException {
        copy();
    }
    
    public static void copy() throws IOException {
        InputStream in = new FileInputStream("1.txt");
        OutputStream out = new FileOutputStream("2.txt");
        
        byte bs[] = new byte[1024];
        int i = -1;
        while((i = in.read(bs)) != -1) {
            out.write(bs, 0, i);
        }
        
        in.close();
        out.close();
    }
}
