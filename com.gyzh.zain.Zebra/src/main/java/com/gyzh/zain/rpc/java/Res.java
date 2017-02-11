package com.gyzh.zain.rpc.java;

import java.io.Serializable;

/**
 * 响应-对象传输
 * @author zain
 * 17/02/11
 */
public class Res implements Serializable {
    private int result;

    public int getResult() {
        return result;
    }
    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Res [result=" + result + "]";
    }
}
