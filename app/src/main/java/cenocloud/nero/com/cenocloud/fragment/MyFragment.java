package cenocloud.nero.com.cenocloud.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cenocloud.nero.com.cenocloud.R;
import cenocloud.nero.com.cenocloud.activity.PageActivity;
import cenocloud.nero.com.cenocloud.adapter.IotListAdapter;
import cenocloud.nero.com.cenocloud.entity.IotList;
import cenocloud.nero.com.cenocloud.entity.ResponseResult;
import cenocloud.nero.com.cenocloud.entity.UserAuth;
import cenocloud.nero.com.cenocloud.model.IotModel;
import cenocloud.nero.com.cenocloud.model.UserModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by neroyang on 2018/3/17.
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    private Handler activityHandler;
    private Handler modelHandler;
    private UserModel userModel;

    private ImageView avatarImageView;

    private TextView userName;
    private TextView real;
    private TextView email;

    private LinearLayout exitLinearLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment, container, false);

        initView(view);

        return view;
    }

    public void initView(View view) {

        avatarImageView = view.findViewById(R.id.avatar);

        userName = view.findViewById(R.id.username);
        real = view.findViewById(R.id.real);
        email = view.findViewById(R.id.email);

        SharedPreferences pref = getActivity().getSharedPreferences("userAuth", MODE_PRIVATE);
        userName.setText(pref.getString("name","登陆中..."));
        email.setText(pref.getString("email",""));

        exitLinearLayout = view.findViewById(R.id.exit);
        exitLinearLayout.setOnClickListener(this);

        userModel = new UserModel(getActivity());
        set_handler();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userAuth", Activity.MODE_PRIVATE);
        userModel.userAuth(sharedPreferences.getInt("id", 1), sharedPreferences.getString("token", "null"));
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
                    case "userAuth":
                        ResponseResult<UserAuth> userAuthResponseResult = (ResponseResult<UserAuth>) msg.getData().getSerializable("userAuth");
                        if (!userAuthResponseResult.getState()) {
                            userName.setText("Hi,您未登录");
                            email.setText("");
                            email.setHeight(0);
                            real.setText("登录/注册");
                            real.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.putExtra("page", "login");
                                    intent.setClass(getActivity(), PageActivity.class);
                                    startActivity(intent);
                                }
                            });
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
                            avatarImageView.setImageBitmap(bitmap);
                            Toast.makeText(getActivity(), userAuthResponseResult.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {

                            SharedPreferences pref = getContext().getSharedPreferences("userAuth", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("id", userAuthResponseResult.getData().getId());
                            editor.putString("company", userAuthResponseResult.getData().getCompany());
                            editor.putString("token", userAuthResponseResult.getData().getToken());
                            editor.putString("phone", userAuthResponseResult.getData().getPhone());
                            editor.putString("name", userAuthResponseResult.getData().getName());
                            editor.putString("email", userAuthResponseResult.getData().getEmail());
                            editor.commit();

                            userName.setText(userAuthResponseResult.getData().getName());
                            email.setText(userAuthResponseResult.getData().getEmail());
                        }
                        break;
                    default:
                        break;
                }

            }
        };
        userModel.setHandler(modelHandler);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit:
                SharedPreferences pref = getContext().getSharedPreferences("userAuth", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("id", 1);
                editor.putString("company", "null");
                editor.putString("token", "null");
                editor.putString("phone", "null");
                editor.putString("name", "null");
                editor.putString("email", "null");
                editor.commit();
                userModel.userAuth(1, "null");
                break;
            default:
                break;
        }
    }
}
