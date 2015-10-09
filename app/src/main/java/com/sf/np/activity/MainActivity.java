package com.sf.np.activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sf.np.R;
import com.sf.np.activity.base.BaseActivity;
import com.sf.np.activity.capture.CaptureActivity;
import com.sf.np.utils.PreferencesWrapper;
import com.sf.np.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final String PHONE_NUMBER = "phone_number";

    @Bind(R.id.scan_button)
    Button scanBtn;
    @Bind(R.id.input_phoneNum_et)
    EditText inputPhoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        setListener();
    }

    public void initData() {

    }


    public void setListener() {
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = inputPhoneNum.getText().toString().trim();
                if(!TextUtils.isEmpty(phoneNum)){
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    intent.putExtra(PHONE_NUMBER,phoneNum);
                    startActivity(intent);
                }else {
                    ToastUtils.makeShortText("请先输入手机号码");
                }


            }
        });


    }


}
