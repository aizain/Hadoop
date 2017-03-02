package com.gyzh.zain.introspector;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class IntrospectorDemo {
    public static void main(String[] args) throws Exception {
        // 获取指定javabean类的bean信息
        BeanInfo bi = Introspector.getBeanInfo(Person.class);
        // 获取属性描述器
        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
        // 遍历属性描述
        for (PropertyDescriptor pd : pds) {
            System.out.println(pd);
        }
        PropertyDescriptor pd = new PropertyDescriptor("name", Person.class);
        System.out.println(pd);
        
        Person p = new Person();
        p.setName("zain");
        p.setAge(12);
        Method rm = pd.getReadMethod();
        String name = (String) rm.invoke(p, null);
        System.out.println(name);
        
        Method wm = pd.getWriteMethod();
        wm.invoke(p, "folikx");
        System.out.println(p.getName());
    }
}
