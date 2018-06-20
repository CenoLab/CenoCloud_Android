package cenocloud.nero.com.cenocloud.fragment.iot;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cenocloud.nero.com.cenocloud.R;
import cenocloud.nero.com.cenocloud.adapter.IotListAdapter;
import cenocloud.nero.com.cenocloud.entity.IotItem;
import cenocloud.nero.com.cenocloud.entity.IotList;
import cenocloud.nero.com.cenocloud.entity.ResponseResult;
import cenocloud.nero.com.cenocloud.model.IotModel;

/**
 * Created by neroyang on 2018/3/18.
 */

public class IotInfoFragment extends Fragment implements View.OnClickListener {
    private ImageView leftBt;


    private Handler activityHandler;
    private Handler modelHandler;
    private IotModel iotModel;
    private Integer appId;

    private TextView name;
    private TextView company;
    private TextView state;
    private TextView type;
    private TextView desc;
    private TextView productKey;

    private TextView tech;
    private TextView trans;

    private TextView maxConnect;
    private TextView currentConn;
    private TextView currentOnline;
    private TextView messageCount;

    private TextView createTime;



    private Integer pageIndex = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iot_info_fragment, container, false);
        initView(view,inflater);
        return view;
    }
    public void initView(View view,LayoutInflater inflater) {

       name = view.findViewById(R.id.name);
       company= view.findViewById(R.id.company);
       state= view.findViewById(R.id.state);
       type= view.findViewById(R.id.type);
       desc = view.findViewById(R.id.description);
       productKey= view.findViewById(R.id.productKey);

       tech= view.findViewById(R.id.tech);
       trans= view.findViewById(R.id.trans);

       maxConnect= view.findViewById(R.id.maxConnect);
       currentConn= view.findViewById(R.id.currentConn);
       currentOnline= view.findViewById(R.id.currentOnline);
       messageCount= view.findViewById(R.id.messageCount);

       createTime= view.findViewById(R.id.createTime);

        iotModel = new IotModel();
        set_handler();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userAuth", Activity.MODE_PRIVATE);
        iotModel.getAppInfo(sharedPreferences.getInt("id",1),appId,sharedPreferences.getString("token","null"));

        leftBt = view.findViewById(R.id.left_bt);
        leftBt.setOnClickListener(this);


    }



    private void set_handler() {
        modelHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(getActivity(), "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        break;
                    case "getAppInfo":
                        ResponseResult<IotItem> itemResponseResult = (ResponseResult<IotItem>) msg.getData().getSerializable("getAppInfo");
                        if(!itemResponseResult.getState()){
                            Toast.makeText(getActivity(), itemResponseResult.getMsg(), Toast.LENGTH_SHORT).show();
                        }else {

                            String state_str = "运行中";
                            int color = 0xff377d22;
                            int red = 0xffeb3324;
                            int orange = 0xfff3a83c;
                            if (itemResponseResult.getData().getCurrentConn() == itemResponseResult.getData().getMaxConnect()) {
                                state_str = "已满载";
                                color = red;
                            } else if (itemResponseResult.getData().getMaxConnect() - itemResponseResult.getData().getCurrentConn() < 500) {
                                state_str = "即将满载";
                                color = orange;
                            }

                            name.setText(itemResponseResult.getData().getName());
                            company.setText(itemResponseResult.getData().getCompany());

                            state.setTextColor(color);
                            state.setText(state_str);

                            type.setText(itemResponseResult.getData().getType());
                            desc.setText(itemResponseResult.getData().getDescription());
                            productKey.setText(itemResponseResult.getData().getProductKey());

                            tech.setText(itemResponseResult.getData().getTech());
                            trans.setText(itemResponseResult.getData().getTrans()==1?"变长":"定长");

                            maxConnect.setText(itemResponseResult.getData().getMaxConnect().toString());
                            currentConn.setText(itemResponseResult.getData().getCurrentConn().toString());
                            currentOnline.setText(itemResponseResult.getData().getCurrentOnline().toString());
                            messageCount.setText(itemResponseResult.getData().getMessageCount().toString());

                            createTime.setText(itemResponseResult.getData().getCreateTime());
                        }
                        break;
                    default:
                        break;
                }

            }
        };
        iotModel.setHandler(modelHandler);

    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        Message message;
        Bundle bundle;

        switch (view.getId()) {
            case R.id.left_bt:
                message = new Message();
                bundle = new Bundle();
                bundle.putString("page", "iot");
                message.setData(bundle);
                activityHandler.sendMessage(message);
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

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

}
