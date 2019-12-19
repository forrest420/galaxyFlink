package com.galaxy.flink.java.app1;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;

/**
 * @author wang.baozhi
 * @since 2019/12/16 下午7:24
 */
public class ApolloTest {
    public static void main(String[] args) throws Exception{
        /**
         * 在idea中的VM参数重指定如下配置
         * -Denv=PRO -Dapp.id=test1AppID -Dapollo.meta=http://bigdata-appsvr-130-5:8080 -Dapollo.cacheDir=/Users/baozhiwang/local_dir/tmp
         * */
        System.out.println("env: "+System.getProperty("env"));

        Config config = ConfigService.getAppConfig(); //config instance is singleton for each namespace and is never null
        String someKey = "broker";
        String someDefaultValue = "someDefaultValueForTheKey";
        String value = config.getProperty(someKey, someDefaultValue);
        System.out.println("value is "+value);
/*
        Thread.sleep(10000);
        value = config.getProperty(someKey, someDefaultValue);
        System.out.println("value is "+value);*/

        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                System.out.println("Changes for namespace " + changeEvent.getNamespace());
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
                }
            }
        });

        Thread.sleep(10000);

        value = config.getProperty(someKey, someDefaultValue);
        System.out.println("value is "+value);

    }
 }
