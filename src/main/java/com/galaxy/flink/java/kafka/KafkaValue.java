package com.galaxy.flink.java.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.calcite.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.calcite.shaded.com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author wang.baozhi
 * @since 2019/8/22 下午7:55
 *
 *
 *
 */
public class KafkaValue implements MapFunction<String, String> {
    @Override
    public String map(String s) {
        String value="";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode actualObj = objectMapper.readTree(s);
          /*  String  content_type = actualObj.get("content_type").asText();
            String  source = actualObj.get("source").asText();
            String  app_ver = actualObj.get("app_ver").asText();
            String  error_code = actualObj.get("error_code").asText();
            String  result = actualObj.get("result").asText();
            String  video_sid = actualObj.get("video_sid").asText();
            String  account_id = actualObj.get("account_id").asText();
            String  user_id = actualObj.get("user_id").asText();*/

            String  content_type = handle(actualObj,"content_type");
            String  source = handle(actualObj,"source");
            String  app_ver = handle(actualObj,"app_ver");
            String  error_code = handle(actualObj,"error_code");
            String  result = handle(actualObj,"result");
            String  video_sid = handle(actualObj,"video_sid");
           /* String  account_id = handle(actualObj,"account_id");
            String  user_id = handle(actualObj,"user_id");
           */
            String  mac = handle(actualObj,"mac");

            //格式必须为纳秒,但是可以在telegraf中指定influxdb中使用ms得精度
           // String  svr_receive_time = actualObj.get("svr_receive_time").asText();
            String  happen_time = actualObj.get("happen_time").asText();

            // todo 对tag做字典排序,now do this manually
            // todo 000000,随机时间生成,防止数据覆盖
            //带下划线得字段
           // value= String.format("player_sdk_startplay,app_ver=%s,content_type=%s,error_code=%s,result=%s,source=%s video_sid=\"%s\",account_id=\"%s\",user_id=\"%s\" %s",app_ver,content_type,error_code,result,source,video_sid,account_id,user_id,svr_receive_time);

            //驼峰格式字段
            value= String.format("playerSdkStartplay,mac=%s,appVer=%s,contentType=%s,errorCode=%s,result=%s,source=%s,videoSid=%s stat=1 %s000000",mac,app_ver,content_type,error_code,result,source,video_sid,happen_time);
            //value= String.format("playerSdkStartplay,mac=%s,appVer=%s,contentType=%s,errorCode=%s,result=%s,source=%s videoSid=\"%s\" %s000000",mac,app_ver,content_type,error_code,result,source,user_id,happen_time);
            //value= String.format("playerSdkStartplay,mac=%s,appVer=%s,contentType=%s,errorCode=%s,result=%s,source=%s videoSid=\"%s\",accountId=\"%s\",userId=\"%s\" %s000000",mac,app_ver,content_type,error_code,result,source,video_sid,account_id,user_id,happen_time);
            //value= String.format("playerSdkStartplay,appVer=%s,contentType=%s,errorCode=%s,result=%s,source=%s videoSid=\"%s\",accountId=\"%s\",userId=\"%s\"",app_ver,content_type,error_code,result,source,video_sid,account_id,user_id);
          // System.out.println(value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public String handle(JsonNode jsonNode,String key){
        String value="";
        if(jsonNode.has(key) && StringUtils.isNoneEmpty(jsonNode.get(key).asText())){
            value=jsonNode.get(key).asText();
        }else{
            //todo 减少存储,可以将没有值得字段,不传送到kafka
            value="nil";
        }

        return value;


    }


}