package com.gyzh.zain.storm.netflow.more;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import com.gyzh.zain.hbase.dao.HBaseDao;
import com.gyzh.zain.hbase.dao.NetFlowInfo;
import com.gyzh.zain.utils.DateUtils;

public class InsertHBaseBolt extends BaseRichBolt {
    private OutputCollector collector = null;
    
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
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

    }

}
