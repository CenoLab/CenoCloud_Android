package cenocloud.nero.com.cenocloud.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cenocloud.nero.com.cenocloud.R;
import cenocloud.nero.com.cenocloud.activity.PageActivity;
import cenocloud.nero.com.cenocloud.entity.IotItem;
import cenocloud.nero.com.cenocloud.entity.IotList;
import cenocloud.nero.com.cenocloud.model.IotModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by neroyang on 2018/3/17.
 */

public class ConsoleFragment extends Fragment implements View.OnClickListener {

    private LinearLayout iotLayout;
    private LinearLayout streamLayout;
    private LinearLayout soundLayout;
    private LinearLayout aiLayout;
    private LinearLayout analysisLayout;
    private LinearLayout addLayout;

    private LinearLayout boxLayout;
    private LinearLayout addBoxLayout;

    private Handler activityHandler;
    private Handler modelHandler;
    private IotModel iotModel;

    private TextView alarm;
    private TextView need;
    private TextView wo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.console_fragment, container, false);

        initView(view);
        iotModel = new IotModel();

        set_handler();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userAuth", Activity.MODE_PRIVATE);
        iotModel.getApps(sharedPreferences.getInt("id",1),sharedPreferences.getString("token","null"));
        return view;
    }

    private void set_handler() {
        modelHandler = new Handler() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(getContext(), "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        break;
                    case "getApps":
                        IotList iotList = (IotList) msg.getData().getSerializable("getApps");
                        if (!iotList.getState()) {
                            Toast.makeText(getContext(), iotList.getMsg().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Integer alarm_num  = 0;
                            Integer need_num = 0;
                            Integer wo_num = 0;

                            for(IotItem iotItem : iotList.getData()){
                                if(iotItem.getMaxConnect()==iotItem.getCurrentConn()){
                                    alarm_num+=1;
                                }
                                if(iotItem.getMaxConnect()-iotItem.getCurrentConn()<100){
                                    need_num+=1;
                                }
                            }

                            alarm.setText(String.valueOf(alarm_num));
                            need.setText(String.valueOf(need_num));
                            wo.setText(String.valueOf(wo_num));
                        }
                        break;
                    default:
                        break;
                }

            }
        };
        iotModel.setHandler(modelHandler);
    }

    public void initView(View view) {
        iotLayout = view.findViewById(R.id.iot_layout);
        streamLayout = view.findViewById(R.id.stream_layout);
        soundLayout = view.findViewById(R.id.sound_layout);
        aiLayout = view.findViewById(R.id.ai_layout);
        analysisLayout = view.findViewById(R.id.analysis_layout);
        addLayout = view.findViewById(R.id.add_layout);

        boxLayout = view.findViewById(R.id.box_layout);
        addBoxLayout = view.findViewById(R.id.add_box_layout);

        alarm = view.findViewById(R.id.alarm);
        need = view.findViewById(R.id.need);
        wo = view.findViewById(R.id.wo);

        SharedPreferences pref = getActivity().getSharedPreferences("dashboard", MODE_PRIVATE);
        alarm.setText(String.valueOf(String.valueOf(pref.getInt("alarm",0))));
        need.setText(String.valueOf(String.valueOf(pref.getInt("need",0))));
        wo.setText(String.valueOf(String.valueOf(pref.getInt("wo",0))));

        iotLayout.setOnClickListener(this);
        streamLayout.setOnClickListener(this);
        soundLayout.setOnClickListener(this);
        aiLayout.setOnClickListener(this);
        analysisLayout.setOnClickListener(this);
        addLayout.setOnClickListener(this);

        boxLayout.setOnClickListener(this);
        addBoxLayout.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iot_layout:
                intent = new Intent();
                intent.putExtra("page", "iot");
                intent.setClass(getActivity(), PageActivity.class);
                startActivity(intent);
                break;
            case R.id.stream_layout:
                intent = new Intent();
                intent.putExtra("page", "stream");
                intent.setClass(getActivity(), PageActivity.class);
                startActivity(intent);
                break;
            case R.id.sound_layout:
                intent = new Intent();
                intent.putExtra("page", "sound");
                intent.setClass(getActivity(), PageActivity.class);
                startActivity(intent);
                break;
            case R.id.ai_layout:
                intent = new Intent();
                intent.putExtra("page", "ai");
                intent.setClass(getActivity(), PageActivity.class);
                startActivity(intent);
                break;
            case R.id.analysis_layout:
                intent = new Intent();
                intent.putExtra("page", "analysis");
                intent.setClass(getActivity(), PageActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public Handler getActivityHandler() {
        return activityHandler;
    }

    public void setActivityHandler(Handler activityHandler) {
        this.activityHandler = activityHandler;
    }
}
