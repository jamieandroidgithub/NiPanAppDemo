package com.sf.np.adapter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sf.np.R;
import com.sf.np.activity.MainActivity;
import com.sf.np.activity.capture.CaptureActivity;
import com.sf.np.entity.ScanResultEntity;
import com.sf.np.utils.PreferencesWrapper;
import com.sf.np.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jamie on 2015/9/30.
 */
public class ScanResultAdapter extends RecyclerView.Adapter<ScanResultAdapter.ScanViewHolder> {

    private List<ScanResultEntity> mList;
    private CaptureActivity mContext;

    public ScanResultAdapter(CaptureActivity context) {
        this.mContext = context;

    }

    public void setData(List<ScanResultEntity> body) {
        if(mList == null){
            this.mList = body;
        }else {
            mList.clear();
            this.mList = body;
        }
    }
    public void addData(ScanResultEntity entity) {
        if (mList == null) {
            mList = new ArrayList<ScanResultEntity>();
        }
        if (!mList.contains(entity)) {
            mList.add(0,entity);
        }
    }

    @Override
    public ScanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_scan_result, parent, false);
        ScanViewHolder cardViewHolder = new ScanViewHolder(itemView);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(ScanViewHolder holder, int position) {
        ScanResultEntity body = mList.get(position);
        holder.expressId.setText("单号：" + body.expressId);
        holder.userName.setText("姓名：顺丰科技" );
        holder.address.setText("地址：深圳市南山区软件产业基地");
    }

    @Override
    public int getItemCount() {
        if(mList == null){
            return 0;
        }
        return mList.size();
    }

    public class ScanViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_express_id)
        TextView expressId;
        @Bind(R.id.item_name)
        TextView userName;
        @Bind(R.id.item_address)
        TextView address;
        @Bind(R.id.item_call_img)
        ImageView callImg;
        @Bind(R.id.item_send_sms_img)
        ImageView sendSmsImg;

        public ScanViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick(R.id.item_send_sms_img)
        void onClick(View v) {
            mContext.showPopupwindow();
        }

        @OnClick(R.id.item_call_img)
        void onClick1(View v) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + "95338"));
            mContext.startActivity(callIntent);
        }
    }
}
