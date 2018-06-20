package cenocloud.nero.com.cenocloud.fragment.dev;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cenocloud.nero.com.cenocloud.R;

/**
 * Created by neroyang on 2018/3/17.
 */

public class DevFragment extends Fragment implements View.OnClickListener {

    private Handler activityHandler;
    private ImageView leftBt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dev_fragment, container, false);

        initView(view);
        return view;
    }
    public void initView(View view) {
        leftBt = view.findViewById(R.id.left_bt);
        leftBt.setOnClickListener(this);
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
