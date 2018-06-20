package cenocloud.nero.com.cenocloud.api;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by neroyang on 2018/3/17.
 */

public class ApiClient extends AsyncHttpClient {




    //首尔节点
    private String HOST = "http://119.28.155.88:8080/";
    private String FromAgent = "android";


    public String getApps(Integer uid,String token) {
        return HOST + "app/app/"+uid+"/"+token+"/auth/apps";
    }

    public String getAppInfo(Integer uid,String token,Integer appid){
        return HOST+"app/app/"+uid+"/"+token+"/auth/"+appid+"/appinfo";
    }

    public String userAuth(Integer uid,String token){
        return HOST+"sso/login/"+uid+"/"+token+"/auth";
    }

    public String login(String email,String password,String verify){
        return HOST+"sso/login/"+email+"/"+password+"/auth/"+verify+"/login";
    }

    public String getVerifyCodeImg(String hash){
        return HOST+"sso/login/"+hash+"/create";
    }


    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public String getFromAgent() {
        return FromAgent;
    }

    public void setFromAgent(String fromAgent) {
        FromAgent = fromAgent;
    }
}
