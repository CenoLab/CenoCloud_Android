package cenocloud.nero.com.cenocloud.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cenocloud.nero.com.cenocloud.R;
import cenocloud.nero.com.cenocloud.activity.MainActivity;
import cenocloud.nero.com.cenocloud.activity.PageActivity;

/**
 * Created by neroyang on 2018/3/17.
 */

public class CenoCloudFragment extends Fragment implements View.OnClickListener {

    private LinearLayout productIot;
    private LinearLayout productBox;
    private LinearLayout productJiankong;
    private LinearLayout productAll;

    private Handler activityHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ceno_fragment, container, false);

        initView(view);
        return view;
    }
    public void initView(View view){
        productIot = view.findViewById(R.id.product_iot);
        productBox = view.findViewById(R.id.product_box);
        productJiankong = view.findViewById(R.id.product_jiankong);
        productAll = view.findViewById(R.id.product_all);

        productIot.setOnClickListener(this);
        productBox.setOnClickListener(this);
        productJiankong.setOnClickListener(this);
        productAll.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.product_iot:
                intent = new Intent();
                intent.putExtra("page", "browser");
                intent.putExtra("url", "http://www.cenocloud.com/");
                intent.putExtra("product", "物联网");
                intent.setClass(getActivity(), PageActivity.class);
                startActivity(intent);
                break;
            case R.id.product_box:
                intent = new Intent();
                intent.putExtra("page", "browser");
                intent.putExtra("url", "http://www.cenocloud.com");
                intent.putExtra("product", "工控云盒");
                intent.setClass(getActivity(), PageActivity.class);
                startActivity(intent);
                break;
            case R.id.product_jiankong:
                intent = new Intent();
                intent.putExtra("page", "browser");
                intent.putExtra("url", "http://www.cenocloud.com");
                intent.putExtra("product", "设备监控");
                intent.setClass(getActivity(), PageActivity.class);
                startActivity(intent);
                break;
            case R.id.product_all:
                intent = new Intent();
                intent.putExtra("page", "console");
                intent.setClass(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
