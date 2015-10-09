package com.sf.np.activity.capture;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.np.R;
import com.sf.np.activity.MainActivity;
import com.sf.np.activity.base.BaseActivity;
import com.sf.np.adapter.ScanResultAdapter;
import com.sf.np.entity.ScanResultEntity;
import com.sf.np.utils.PreferencesWrapper;
import com.sf.np.utils.TLog;
import com.sf.np.utils.ToastUtils;

import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/*
 * Created by Jamie on 2015-09-30
 */

public class CaptureActivity extends BaseActivity {
    private static final String TAG = CaptureActivity.class.getSimpleName();
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

//    TextView scanText;
//    Button scanButton;

    ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private ScanResultAdapter mAdapter;

    private List<ScanResultEntity> resultList = new ArrayList<>();

    private PopupWindow mPopupWindow;

    String mPhoneNumber;

    private boolean playBeep;
    private MediaPlayer mediaPlayer;
    private static final float BEEP_VOLUME = 0.50f;
    private boolean vibrate;

    @Override
    public void setContentViewImp(Bundle savedInstanceState) {
        setContentView(R.layout.scan_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        //从Intent当中根据key取得value
        mPhoneNumber = intent.getStringExtra(MainActivity.PHONE_NUMBER);
    }

    @Override
    public void initView() {
        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
        preview.addView(mPreview);
    }

    @Override
    public void initData() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ScanResultAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {

    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    PreviewCallback previewCb = new PreviewCallback() {


        public void onPreviewFrame(byte[] data, Camera camera) {
            Parameters parameters = camera.getParameters();
            Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {
                    if (sym.getType() == Symbol.CODE128) {
                        playBeepSoundAndVibrate();
                        ToastUtils.makeShortText("扫描成功");
                        if (!sym.equals(syms)) {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = sym.getData();
                            uiHandler.sendMessage(msg);
                        }
                    }
                }
            }
        }
    };
    final Handler uiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ScanResultEntity entity = new ScanResultEntity();
                    entity.expressId = msg.obj.toString();
                    mAdapter.addData(entity);
                    mAdapter.notifyDataSetChanged();
                    continueScanning();
            }

        }
    };

    private void continueScanning() {
        barcodeScanned = false;
        mCamera.setPreviewCallback(previewCb);
        mCamera.startPreview();
        previewing = true;
        if(previewing){
            mCamera.autoFocus(autoFocusCB);
        }
    }

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };


    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        TLog.e(TAG, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        TLog.e(TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        TLog.e(TAG, "onStart");
    }

    public void showPopupwindow() {
        initPopuptWindow();
        ColorDrawable dw = new ColorDrawable(0x7F000000);
        mPopupWindow.setBackgroundDrawable(dw);
        mPopupWindow.showAtLocation(findViewById(R.id.recycler_view), Gravity.CENTER, 0, 0);
    }

    private void initPopuptWindow() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View popupWindow = layoutInflater.inflate(R.layout.integral_popupwindow, null);
        final TextView textViewType0 = (TextView) popupWindow.findViewById(R.id.text_view0);
        final TextView textViewType1 = (TextView) popupWindow.findViewById(R.id.text_view1);
        final TextView textViewType2 = (TextView) popupWindow.findViewById(R.id.text_view2);
        textViewType0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //检查输入内容是否为空,这里为了简单就没有判断是否是号码,短信内容长度的限制也没有做

                String str = textViewType0.getText().toString();
                sendSmsImp(str);
            }
        });
        textViewType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = textViewType1.getText().toString();
                sendSmsImp(str);
            }
        });

        textViewType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = textViewType2.getText().toString();
                sendSmsImp(str);
            }
        });

        mPopupWindow = new PopupWindow(popupWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
        mPopupWindow.setFocusable(true);

        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.update();
    }

    private void sendSmsImp(String str) {
        SmsManager smsManager = SmsManager.getDefault();
        if (str.trim().length() != 0) {
            try {
                PendingIntent pintent = PendingIntent.getBroadcast(CaptureActivity.this, 0, new Intent(), 0);
                smsManager.sendTextMessage(mPhoneNumber, null, str, pintent, null);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //提示发送成功
            ToastUtils.makeShortText("发送成功");
        } else {
            ToastUtils.makeShortText("发送地址或者内容不能为空");
        }
        mPopupWindow.dismiss();
    }

}
