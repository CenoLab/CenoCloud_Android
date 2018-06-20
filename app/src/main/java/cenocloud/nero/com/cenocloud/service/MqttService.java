package cenocloud.nero.com.cenocloud.service;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by neroyang on 2018/3/19.
 */

public class MqttService {
    Context ctx;
    String clientId="client";//连接标示，同一个标示，用于2个连接，会踢掉对方。
    String userContext;//任意字符串，自定义。
    String addr = "tcp://www.cenocloud.com:1883";

    String appKey ="";
    String appSecret="";

    MqttAndroidClient cli_;
    MqttConnectOptions opt_;

    public MqttService(Context ctx, String clientId, String userContext, String addr, String appKey, String appSecret) {
        this.ctx = ctx;
        this.clientId = clientId;
        this.userContext = userContext;
        this.addr = addr;
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    public void init(MqttCallback mqttCallback){
        cli_ = new MqttAndroidClient(ctx,addr,clientId);
        opt_ = new MqttConnectOptions();
        opt_.setCleanSession(true);//是否保持该clientid的session状态，以便断开后保存离线消息
        opt_.setConnectionTimeout(5000);//连接超时
        opt_.setKeepAliveInterval(60*1000);//心跳包检测间隔
        opt_.setPassword(appSecret.toCharArray());
        opt_.setUserName(appKey);
        cli_.setCallback(mqttCallback);//消息的回调
    }

    public void connect(IMqttActionListener actionListener) throws MqttException {
        cli_.connect(opt_, userContext, actionListener);
    }

    public void subscribeTopic(String topic,int QOS,IMqttActionListener actionListener) throws MqttException {
        cli_.subscribe(topic,QOS,userContext,actionListener);
    }

    public void pushMsg(String topic,int QOS,byte[] payload,IMqttActionListener actionListener) throws MqttException {
        cli_.publish(topic, payload,QOS,false,userContext,actionListener);
    }

    public void disconnect(IMqttActionListener actionListener) throws MqttException {
        cli_.disconnect(userContext,actionListener);
    }

}
