package com.gyzh.zain.storm.netflow.more;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import com.gyzh.zain.hbase.dao.HBaseDao;
import com.gyzh.zain.hbase.dao.NetFlowInfo;
import com.gyzh.zain.utils.DateUtils;

public class AvgDeepBolt extends BaseRichBolt{
    private OutputCollector collector = null;
    
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        long beginTime = input.getLongByField("beginTime");
        long endTime = input.getLongByField("endTime");
    
        byte [] begin = (DateUtils.format(beginTime)+"_"+beginTime).getBytes();
        byte [] end = (DateUtils.format(endTime)+"_"+endTime).getBytes();
        //查询此范围内的数据
        List<NetFlowInfo> list = HBaseDao.find2(begin, end);
        
        //统计会话去重页面
        Map<String,Set<String>> map = new HashMap<>();
        for(NetFlowInfo nfi : list){
            if(map.containsKey(nfi.getSid())){
                map.get(nfi.getSid()).add(nfi.getUrlname());
            }else{
                Set<String> set = new HashSet<>();
                set.add(nfi.getUrlname());
                map.put(nfi.getSid(), set);
            }
        }
        
        //计算平均访问深度
        int allDeep = 0;
        int sCount = map.size();
        for(Map.Entry<String, Set<String>> entry : map.entrySet()){
            Set<String> set = entry.getValue();
            allDeep += set.size();
        }
        double avgDeep = Math.round(allDeep*1000.0/sCount)/1000.0;
        
        //输出数据
        List<Object> values = input.getValues();
        values.add(avgDeep);
        collector.emit(input,values);
        collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("beginTime","endTime","br","avgtime","avgdeep"));       
    }
    
}
