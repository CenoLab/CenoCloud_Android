package cenocloud.nero.com.cenocloud.fragment.iot;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cenocloud.nero.com.cenocloud.R;
import cenocloud.nero.com.cenocloud.adapter.IotListAdapter;
import cenocloud.nero.com.cenocloud.entity.IotItem;
import cenocloud.nero.com.cenocloud.entity.IotList;
import cenocloud.nero.com.cenocloud.entity.ResponseResult;
import cenocloud.nero.com.cenocloud.model.IotModel;
import cenocloud.nero.com.cenocloud.view.ScroolListView;

/**
 * Created by neroyang on 2018/3/17.
 */

public class IotFragment extends Fragment implements View.OnClickListener {

    private ImageView leftBt;

    private Handler activityHandler;
    private Handler modelHandler;
    private IotModel iotModel;

    private ScroolListView scroolListView;
    private IotListAdapter iotListAdapter;

    private LinearLayout aminLinearLoyout;

    private List<IotItem> iotItemList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iot_fragment, container, false);

        initView(view);
        initGear(view);



        return view;
    }
    private void initGear(View view) {
        RotateAnimation gearAnim = (RotateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_gear);
        RotateAnimation gearAnimSmall = (RotateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_gear_small);


        ImageView gearLeft = view.findViewById(R.id.gear_left);
        ImageView gearRight = view.findViewById(R.id.gear_right);

        gearLeft.startAnimation(gearAnim);
        gearRight.startAnimation(gearAnimSmall);
    }

    public void initView(View view) {
        leftBt = view.findViewById(R.id.left_bt);
        leftBt.setOnClickListener(this);

        aminLinearLoyout = view.findViewById(R.id.anim_container);
        aminLinearLoyout.setVisibility(View.VISIBLE);

        scroolListView = view.findViewById(R.id.iot_list);
        iotItemList  = new ArrayList<>();
        iotListAdapter = new IotListAdapter(iotItemList,getActivity());
        scroolListView.setAdapter(iotListAdapter);


        iotModel = new IotModel();
        set_handler();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userAuth", Activity.MODE_PRIVATE);
        iotModel.getApps(sharedPreferences.getInt("id",1),sharedPreferences.getString("token","null"));
    }

    private void set_handler() {
        modelHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(getActivity(), "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        break;
                    case "getApps":
                        IotList iotList = (IotList) msg.getData().getSerializable("getApps");
                        if (!iotList.getState()) {
                            Toast.makeText(getActivity(), iotList.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            aminLinearLoyout.setVisibility(View.GONE);
                            iotItemList = iotList.getData();
                            iotListAdapter.setIotItemList(iotItemList);
                            iotListAdapter.notifyDataSetChanged();
                        }
                        break;
                    case "appInfo":
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("page", "appInfo");
                        bundle.putInt("appInfo", msg.getData().getInt("appInfo"));
                        message.setData(bundle);
                        activityHandler.sendMessage(message);
                        break;
                    default:
                        break;
                }

            }
        };
        iotModel.setHandler(modelHandler);
        iotListAdapter.setFragmentHandler(modelHandler);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public Handler getActivityHandler() {
        return activityHandler;
    }

    public void setActivityHandler(Handler activityHandler) {
        this.activityHandler = activityHandler;
    }

    @Override
    public void onClick(View view) {
        Message message;
        Bundle bundle;

        switch (view.getId()) {
            case R.id.left_bt:
                message = new Message();
                bundle = new Bundle();
                bundle.putString("page", "main");
                message.setData(bundle);
                activityHandler.sendMessage(message);
                break;
            default:
                break;
        }
    }
}
