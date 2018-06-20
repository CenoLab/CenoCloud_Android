package cenocloud.nero.com.cenocloud.model;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.TextHttpResponseHandler;

import cenocloud.nero.com.cenocloud.api.ApiClient;
import cenocloud.nero.com.cenocloud.entity.IotItem;
import cenocloud.nero.com.cenocloud.entity.IotList;
import cenocloud.nero.com.cenocloud.entity.ResponseResult;
import cenocloud.nero.com.cenocloud.entity.UserAuth;
import cz.msebera.android.httpclient.Header;

import static cenocloud.nero.com.cenocloud.utils.TGJson.fromJson;
import static cenocloud.nero.com.cenocloud.utils.TGJson.resultFromJson;

/**
 * Created by neroyang on 2018/3/18.
 */

public class UserModel {
    private ApiClient apiClient;
    private Handler handler;

    private Context context;

    public UserModel(Context context) {

        apiClient = new ApiClient();
        apiClient.addHeader("FromAgent",apiClient.getFromAgent());
        context = context;
    }



    public void userAuth(Integer uid,String token){
        System.out.println("URL "+apiClient.getApps(uid,token));
        apiClient.get(apiClient.userAuth(uid,token), null,  new TextHttpResponseHandler() {
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
                ResponseResult<UserAuth> userAuthResponseResult = resultFromJson(responseString,UserAuth.class);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","userAuth");
                bundle.putSerializable("userAuth",userAuthResponseResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });

    }

    public void login(String email,String password,String verify,Header header){
        System.out.println("URL "+apiClient.login(email,password,verify));
        String[] js = header.getValue().split(";");
        String[] js_id = js[0].split("=");
        System.out.println(js_id[1]);
        apiClient.addHeader("Cookie","JSESSIONID="+js_id[1]);
        apiClient.get(apiClient.login(email,password,verify), null,  new TextHttpResponseHandler() {
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
                ResponseResult<UserAuth> userAuthResponseResult = resultFromJson(responseString,UserAuth.class);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","login");
                bundle.putSerializable("login",userAuthResponseResult);
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
