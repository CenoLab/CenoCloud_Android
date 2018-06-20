package cenocloud.nero.com.cenocloud.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

import cenocloud.nero.com.cenocloud.R;
import cenocloud.nero.com.cenocloud.entity.IotItem;
import cenocloud.nero.com.cenocloud.entity.ResponseResult;
import cenocloud.nero.com.cenocloud.entity.UserAuth;
import cenocloud.nero.com.cenocloud.fragment.browser.BoswerFragment;
import cenocloud.nero.com.cenocloud.fragment.dev.DevFragment;
import cenocloud.nero.com.cenocloud.fragment.iot.IotFragment;
import cenocloud.nero.com.cenocloud.fragment.iot.IotInfoFragment;
import cenocloud.nero.com.cenocloud.fragment.user.AuthLoginFragment;
import cenocloud.nero.com.cenocloud.fragment.user.LoginFragment;

/**
 * Created by neroyang on 2018/3/17.
 */

public class PageActivity extends AppCompatActivity {


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private DevFragment devFragment;
    private IotFragment iotFragment;
    private IotInfoFragment iotInfoFragment;
    private LoginFragment loginFragment;
    private BoswerFragment boswerFragment;
    private AuthLoginFragment authLoginFragment;

    private Handler activityHandler;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_page);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        initFragment();
        initView();

        intent = getIntent();
        String page = intent.getStringExtra("page");

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        jumpFragment(page);

    }

    public void jumpFragment(String page){
        switch (page){
            case "iot":
                fragmentTransaction.replace(R.id.fragment_container,iotFragment);
                fragmentTransaction.commit();
                break;
            case "stream":
                fragmentTransaction.replace(R.id.fragment_container,devFragment);
                fragmentTransaction.commit();
                break;

            case "sound":
                fragmentTransaction.replace(R.id.fragment_container,devFragment);
                fragmentTransaction.commit();
                break;

            case "ai":
                fragmentTransaction.replace(R.id.fragment_container,devFragment);
                fragmentTransaction.commit();
                break;

            case "analysis":
                fragmentTransaction.replace(R.id.fragment_container,devFragment);
                fragmentTransaction.commit();
                break;

            case "login":
                fragmentTransaction.replace(R.id.fragment_container,loginFragment);
                fragmentTransaction.commit();
                break;
            case "browser":
                Intent intent = getIntent();

                String product = intent.getStringExtra("product");
                String url = intent.getStringExtra("url");
                boswerFragment.setUrl(url);
                boswerFragment.setProductName(product);
                fragmentTransaction.replace(R.id.fragment_container,boswerFragment);
                fragmentTransaction.commit();
                break;
            case "authLogin":
                fragmentTransaction.replace(R.id.fragment_container,authLoginFragment);
                fragmentTransaction.commit();
                break;
            default:
                break;
        }
    }

    public void initFragment(){
        devFragment = new DevFragment();
        iotFragment = new IotFragment();
        iotInfoFragment = new IotInfoFragment();
        loginFragment = new LoginFragment();
        boswerFragment = new BoswerFragment();
        authLoginFragment = new AuthLoginFragment();
    }

    public void initView(){
        set_handler();
    }

    private void set_handler() {
        activityHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Intent intent;
                switch (msg.getData().getString("page")) {
                    case "main":
                        intent = new Intent();
                        intent.setClass(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        break;
                    case "appInfo":
                        fragmentTransaction = fragmentManager.beginTransaction();
                        iotInfoFragment.setAppId(msg.getData().getInt("appInfo"));
                        fragmentTransaction.replace(R.id.fragment_container,iotInfoFragment);
                        fragmentTransaction.commit();
                        break;
                    case "iot":
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container,iotFragment);
                        fragmentTransaction.commit();
                        break;
                    case "login":
                        intent = new Intent();
                        intent.setClass(getApplicationContext(),MainActivity.class);
                        ResponseResult<UserAuth> responseResult = (ResponseResult<UserAuth>) msg.getData().getSerializable("login");
                        if(!responseResult.getState()){
                            Toast.makeText(getApplicationContext(), responseResult.getMsg(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                            SharedPreferences pref = getSharedPreferences("userAuth",MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            System.out.println(responseResult);
                            editor.putInt("id",responseResult.getData().getId());
                            editor.putString("company",responseResult.getData().getCompany());
                            editor.putString("token",responseResult.getData().getToken());
                            editor.putString("phone",responseResult.getData().getPhone());
                            editor.putString("name",responseResult.getData().getName());
                            editor.putString("email",responseResult.getData().getEmail());
                            editor.commit();
                            intent.putExtra("page","me");
                            startActivity(intent);
                        }

                        break;
                    default:
                        break;
                }
            }
        };
        devFragment.setActivityHandler(activityHandler);
        iotFragment.setActivityHandler(activityHandler);
        iotInfoFragment.setActivityHandler(activityHandler);
        loginFragment.setActivityHandler(activityHandler);
        boswerFragment.setActivityHandler(activityHandler);
        authLoginFragment.setActivityHandler(activityHandler);
    }
}
