package cenocloud.nero.com.cenocloud.model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cenocloud.nero.com.cenocloud.api.ApiClient;
import cenocloud.nero.com.cenocloud.entity.IotItem;
import cenocloud.nero.com.cenocloud.entity.IotList;
import cenocloud.nero.com.cenocloud.entity.ResponseResult;
import cz.msebera.android.httpclient.Header;

import static cenocloud.nero.com.cenocloud.utils.TGJson.fromJson;
import static cenocloud.nero.com.cenocloud.utils.TGJson.resultFromJson;

/**
 * Created by neroyang on 2018/3/18.
 */

public class IotModel {
    private ApiClient apiClient;
    private Handler handler;

    public IotModel() {
        apiClient = new ApiClient();
        apiClient.addHeader("FromAgent",apiClient.getFromAgent());
    }


    public void getApps(Integer uid,String token){
        System.out.println("URL "+apiClient.getApps(uid,token));
        apiClient.get(apiClient.getApps(uid,token), null,  new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println(responseString);
                IotList iotList = fromJson(responseString,IotList.class);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","getApps");
                bundle.putSerializable("getApps",iotList);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });

    }

    public void getAppInfo(Integer uid,Integer appId,String token){
        System.out.println("URL "+apiClient.getAppInfo(uid,token,appId));
        apiClient.get(apiClient.getAppInfo(uid,token,appId), null,  new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println(responseString);
                ResponseResult<IotItem> iotItemResponseResult = resultFromJson(responseString,IotItem.class);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","getAppInfo");
                bundle.putSerializable("getAppInfo",iotItemResponseResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });

    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
