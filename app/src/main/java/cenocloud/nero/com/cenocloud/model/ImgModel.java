package cenocloud.nero.com.cenocloud.model;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import cenocloud.nero.com.cenocloud.api.ApiClient;
import cenocloud.nero.com.cenocloud.fragment.user.LoginFragment;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by neroyang on 2018/3/18.
 */

public class ImgModel {
    private ApiClient apiClient;
    private Handler handler;
    private Context context;

    public ImgModel(Context context) {

        apiClient = new ApiClient();
        apiClient.addHeader("FromAgent",apiClient.getFromAgent());
    }

    public void getVerifyCodeImg(String hash){
        System.out.println("URL "+apiClient.getVerifyCodeImg(hash));
        String[] allowedContentTypes = new String[] { "image/png", "image/jpeg","image" };
        apiClient.get(apiClient.getVerifyCodeImg(hash), null, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                for(Header header:headers){
                    if(header.toString().contains("Set-Cookie")){
                        LoginFragment.header = header;
                    }
                }

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","imgVerifyCode");
                bundle.putByteArray("imgVerifyCode",binaryData);
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                System.out.println(statusCode);
                System.out.println(error);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
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

