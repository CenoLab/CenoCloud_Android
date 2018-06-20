package cenocloud.nero.com.cenocloud.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cenocloud.nero.com.cenocloud.R;
import cenocloud.nero.com.cenocloud.entity.IotItem;
import cenocloud.nero.com.cenocloud.model.IotModel;

/**
 * Created by neroyang on 2018/3/18.
 */

public class IotListAdapter extends BaseAdapter {

    private List<IotItem> iotItemList;

    private Handler fragmentHandler;
    private Context mContext;


    public IotListAdapter(List<IotItem> iotItemList, Context mContext) {
        this.iotItemList = iotItemList;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return iotItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.iot_item,parent,false);
        }

        final TextView nameText = convertView.findViewById(R.id.name);
        nameText.setText(iotItemList.get(position).getName());

        String state = "运行中";
        int color = 0xff377d22;
        int red = 0xffeb3324;
        int orange = 0xfff3a83c;
        if (iotItemList.get(position).getCurrentConn() == iotItemList.get(position).getMaxConnect()) {
            state = "已满载";
            color = red;
        } else if (iotItemList.get(position).getMaxConnect() - iotItemList.get(position).getCurrentConn() < 500) {
            state = "即将满载";
            color = orange;
        }

        final TextView stateText = convertView.findViewById(R.id.state);
        stateText.setText(state);
        stateText.setTextColor(color);

        final TextView connectionText = convertView.findViewById(R.id.connection);
        connectionText.setText("连接数："+iotItemList.get(position).getCurrentConn());

        final TextView createTime = convertView.findViewById(R.id.time);
        createTime.setText(iotItemList.get(position).getCreateTime());

        final ImageView moreBt = convertView.findViewById(R.id.more_bt);

        moreBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","appInfo");
                bundle.putInt("appInfo",iotItemList.get(position).getId());
                message.setData(bundle);
                fragmentHandler.sendMessage(message);
            }
        });

        return convertView;
    }


    public Handler getFragmentHandler() {
        return fragmentHandler;
    }

    public void setFragmentHandler(Handler fragmentHandler) {
        this.fragmentHandler = fragmentHandler;
    }

    public List<IotItem> getIotItemList() {
        return iotItemList;
    }

    public void setIotItemList(List<IotItem> iotItemList) {
        this.iotItemList = iotItemList;
    }
}
