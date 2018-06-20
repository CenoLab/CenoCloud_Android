package cenocloud.nero.com.cenocloud.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import cenocloud.nero.com.cenocloud.R;
import cenocloud.nero.com.cenocloud.fragment.CenoCloudFragment;
import cenocloud.nero.com.cenocloud.fragment.ConsoleFragment;
import cenocloud.nero.com.cenocloud.fragment.MyFragment;
import cenocloud.nero.com.cenocloud.fragment.dev.DevFragment;
import cenocloud.nero.com.cenocloud.fragment.iot.IotFragment;
import cenocloud.nero.com.cenocloud.service.MqttService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int bottom_text_color = 0xffa0a0a0;
    int bottom_text_color_active = 0xff55c0d8;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    private Handler activityHandler;

    private CenoCloudFragment cenoCloudFragment;
    private ConsoleFragment consoleFragment;
    private MyFragment myFragment;


    LinearLayout bottomButtonCeno;
    LinearLayout bottomButtonConsole;
    LinearLayout bottomButtonSelf;

    ImageView bottomButtonCenoImg;
    ImageView bottomButtonConsoleImg;
    ImageView bottomButtonSelfImg;

    TextView bottomButtonCenoText;
    TextView bottomButtonConsoleText;
    TextView bottomButtonSelfText;

    MqttService mqttService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        Intent intent = getIntent();
        String page = intent.getStringExtra("page");
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        initFragment();
        initView();
//        try {
//            initService();
//        } catch (MqttException e) {
//            Toast.makeText(getApplicationContext(),"服务初始化失败",Toast.LENGTH_SHORT).show();
//        }

        if(page!=null){
            jumpFragment(page);
        }else{
            fragmentTransaction.replace(R.id.fragment_container,cenoCloudFragment);
            fragmentTransaction.commit();
        }


    }
    public void jumpFragment(String page){
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (page){
            case "me":
                fragmentTransaction.replace(R.id.fragment_container,myFragment);
                fragmentTransaction.commit();
                break;

            case "console":
                fragmentTransaction.replace(R.id.fragment_container,consoleFragment);
                fragmentTransaction.commit();
                break;

            default:
                break;
        }
    }

    private void initView() {
        bottomButtonCeno = (LinearLayout) this.findViewById(R.id.bottom_bt_ceno);
        bottomButtonSelf = (LinearLayout) this.findViewById(R.id.bottom_bt_self);
        bottomButtonConsole = (LinearLayout)this.findViewById(R.id.bottom_bt_console);


        bottomButtonCenoImg = (ImageView) this.findViewById(R.id.bottom_bt_ceno_img);
        bottomButtonConsoleImg = (ImageView) this.findViewById(R.id.bottom_bt_console_img);
        bottomButtonSelfImg = (ImageView) this.findViewById(R.id.bottom_bt_self_img);


        bottomButtonCenoText = (TextView) this.findViewById(R.id.bottom_bt_ceno_text);
        bottomButtonConsoleText = (TextView) this.findViewById(R.id.bottom_bt_console_text);
        bottomButtonSelfText = (TextView) this.findViewById(R.id.bottom_bt_self_text);


        bottomButtonCeno.setOnClickListener(this);
        bottomButtonSelf.setOnClickListener(this);
        bottomButtonConsole.setOnClickListener(this);


        bottomButtonClear();
        bottomButtonCenoImg.setImageDrawable(getResources().getDrawable(R.drawable.cloud));
        bottomButtonCenoText.setTextColor(bottom_text_color_active);

        set_handler();
    }

    public void initService() throws MqttException {
        mqttService = new MqttService(getApplicationContext(),
                "android_test",
                "",
                "tcp://www.cenocloud.com:1883",
                "QS_mRUniMohUJVAUqB1VUw==",
                "_Qq_AWhEGaTKu_8Bqi6hPQ==");

        mqttService.init(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println(topic+":"+message.toString());
                if(message.toString().equals("start")){
                    Intent intent = new Intent();
                    intent.putExtra("page", "authLogin");
                    intent.setClass(getApplicationContext(), PageActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        mqttService.connect(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                try {
                    mqttService.subscribeTopic("/1/tsu5AS_iXFLgs7o93jTMlQ==/android_test",1,new IMqttActionListener(){
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            Toast.makeText(getApplicationContext(),"登陆认证服务初始化成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            Toast.makeText(getApplicationContext(),"服务订阅失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Toast.makeText(getApplicationContext(),"服务连接失败",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initFragment() {
        cenoCloudFragment = new CenoCloudFragment();
        consoleFragment = new ConsoleFragment();
        myFragment = new MyFragment();

    }

    private void set_handler() {
        activityHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("page")) {
                    default:
                        break;
                }
            }
        };
        consoleFragment.setActivityHandler(activityHandler);
    }

    void bottomButtonClear(){
        bottomButtonCenoImg.setImageDrawable(getResources().getDrawable(R.drawable.cloud_gray));
        bottomButtonConsoleImg.setImageDrawable(getResources().getDrawable(R.drawable.console_gray));
        bottomButtonSelfImg.setImageDrawable(getResources().getDrawable(R.drawable.me_gray));

        bottomButtonCenoText.setTextColor(bottom_text_color);
        bottomButtonConsoleText.setTextColor(bottom_text_color);
        bottomButtonSelfText.setTextColor(bottom_text_color);

    }

    @Override
    public void onClick(View view) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.bottom_bt_ceno:
                fragmentTransaction.replace(R.id.fragment_container,cenoCloudFragment);
                bottomButtonClear();
                bottomButtonCenoImg.setImageDrawable(getResources().getDrawable(R.drawable.cloud));
                bottomButtonCenoText.setTextColor(bottom_text_color_active);
                break;
            case R.id.bottom_bt_console:
                fragmentTransaction.replace(R.id.fragment_container,consoleFragment);
                bottomButtonClear();
                bottomButtonConsoleImg.setImageDrawable(getResources().getDrawable(R.drawable.console));
                bottomButtonConsoleText.setTextColor(bottom_text_color_active);

                break;
            case R.id.bottom_bt_self:
                fragmentTransaction.replace(R.id.fragment_container,myFragment);
                bottomButtonClear();
                bottomButtonSelfImg.setImageDrawable(getResources().getDrawable(R.drawable.me));
                bottomButtonSelfText.setTextColor(bottom_text_color_active);

                break;
            default:break;
        }
        fragmentTransaction.commit();
    }
}
