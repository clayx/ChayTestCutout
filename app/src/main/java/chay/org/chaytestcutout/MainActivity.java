package chay.org.chaytestcutout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_change, btn_change2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotchUtil.setImmersiveBarsMode(this,false,true,true, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
//        NotchThirdUtil.setNormalMode(this);
        btn_change = findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);

        btn_change2 = findViewById(R.id.btn_change2);
        btn_change2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_change:
//                NotchUtil.setNotchMode(this, false);
//                break;
//            case R.id.btn_change2:
//                NotchUtil.setNotchMode(this, true);
//                break;
        }
    }
}