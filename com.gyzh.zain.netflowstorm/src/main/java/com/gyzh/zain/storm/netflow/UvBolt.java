package com.gyzh.zain.storm.netflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gyzh.zain.hbase.dao.HBaseDao;
import com.gyzh.zain.hbase.dao.NetFlowInfo;
import com.gyzh.zain.utils.DateUtils;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class UvBolt extends BaseRichBolt implements IRichBolt {
    private OutputCollector collector = null;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        //获取当前记录的uv
        long time = input.getLongByField("stime");
        String dateStr = DateUtils.format(time);
        String uvid = input.getStringByField("uvid");
        //拿着uvid到HBase检查 在今天的历史数据中是否出现过
        List<NetFlowInfo> list = HBaseDao.find("^"+dateStr+"_\\d*_"+uvid+"_.*$");
        int uv = list.size()<=0 ? 1 : 0;
        //输出数据
        List<Object> values = input.getValues();
        values.add(uv);
        collector.emit(input,values);
        //将这条数据存储到HBase中以供后续使用
        NetFlowInfo nfi = new NetFlowInfo();
        nfi.setTime(DateUtils.format(input.getLongByField("stime")));
        nfi.setUrlname(input.getStringByField("urlname"));
        nfi.setUvid(input.getStringByField("uvid"));
        nfi.setSid(input.getStringByField("sid"));
        nfi.setScount(input.getIntegerByField("scount")+"");
        nfi.setStime(input.getLongByField("stime")+"");
        nfi.setCip(input.getStringByField("cip"));
        HBaseDao.add(nfi);
        
        collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("urlname", "uvid","sid","scount","stime","cip","pv","uv"));
    }

}
