package cenocloud.nero.com.cenocloud.fragment.user;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.net.CookieStore;
import java.util.ArrayList;

import cenocloud.nero.com.cenocloud.R;
import cenocloud.nero.com.cenocloud.adapter.IotListAdapter;
import cenocloud.nero.com.cenocloud.entity.IotList;
import cenocloud.nero.com.cenocloud.entity.ResponseResult;
import cenocloud.nero.com.cenocloud.entity.UserAuth;
import cenocloud.nero.com.cenocloud.model.ImgModel;
import cenocloud.nero.com.cenocloud.model.IotModel;
import cenocloud.nero.com.cenocloud.model.UserModel;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.protocol.ClientContext;

/**
 * Created by neroyang on 2018/3/18.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    private ImageView leftBt;

    private Handler activityHandler;
    private Handler modelHandler;
    private UserModel userModel;
    private ImgModel imgModel;

    private LinearLayout login;
    private Button login_bt;
    private EditText email;
    private EditText password;
    private EditText verify;
    private ImageView verifyImage;

    public static Header header;

    private CookieStore cookies;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        initView(view);


        return view;
    }

    public void initView(View view) {
        leftBt = view.findViewById(R.id.left_bt);
        leftBt.setOnClickListener(this);

        login = view.findViewById(R.id.login);
        login_bt = view.findViewById(R.id.login_bt);
        email= view.findViewById(R.id.email);
        password= view.findViewById(R.id.password);
        verify = view.findViewById(R.id.verify);
        verifyImage = view.findViewById(R.id.verify_img);

        userModel = new UserModel(getActivity());
        imgModel = new ImgModel(getActivity());

        login.setOnClickListener(this);
        login_bt.setOnClickListener(this);

        set_handler();
        verifyImage.setOnClickListener(this);

        imgModel.getVerifyCodeImg(String.valueOf(System.currentTimeMillis()));

    }

    private void set_handler() {
        modelHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(getActivity(), "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        break;
                    case "login":
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("page", "login");
                        bundle.putSerializable("login",msg.getData().getSerializable("login"));
                        message.setData(bundle);
                        ResponseResult<UserAuth> responseResult = (ResponseResult<UserAuth>) msg.getData().getSerializable("login");
                        if(!responseResult.getState()){
                            imgModel.getVerifyCodeImg(String.valueOf(System.currentTimeMillis()));
                        }
                        activityHandler.sendMessage(message);
                        login.setEnabled(true);
                        login_bt.setEnabled(true);
                        break;
                    case "imgVerifyCode":
                        byte[] getimage = msg.getData().getByteArray("imgVerifyCode");
                        Bitmap bmp = BitmapFactory.decodeByteArray(getimage, 0, getimage.length);
                        verifyImage.setImageBitmap(bmp);
                        break;
                    default:
                        break;
                }

            }
        };
        userModel.setHandler(modelHandler);
        imgModel.setHandler(modelHandler);
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

            case R.id.login:
                System.out.println("login");
                userModel.login(email.getText().toString(),password.getText().toString(),verify.getText().toString(),header);
                login.setEnabled(false);
                login_bt.setEnabled(false);
                break;
            case R.id.login_bt:
                System.out.println("login_bt");
                userModel.login(email.getText().toString(),password.getText().toString(),verify.getText().toString(),header);
                login.setEnabled(false);
                login_bt.setEnabled(false);
                break;
            case R.id.verify_img:
                imgModel.getVerifyCodeImg(String.valueOf(System.currentTimeMillis()));
                break;
            default:
                break;
        }
    }
}
