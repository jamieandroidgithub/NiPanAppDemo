package com.sf.np.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sf.np.R;
import com.sf.np.ui.WheelView;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 887938 on 2015/10/8.
 */
public class WheelActivity extends AppCompatActivity{

    @Bind(R.id.wheel_view_wv)
    WheelView wva;

    private static final String[] PLANETS = new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Uranus", "Neptune", "Pluto"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheel);
        ButterKnife.bind(this);

        wva.setOffset(1);
        wva.setItems(Arrays.asList(PLANETS));
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("WheelActivity", "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });


    }
}
