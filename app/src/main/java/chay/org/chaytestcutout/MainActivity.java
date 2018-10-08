package chay.org.chaytestcutout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import chay.org.chaytestcutout.i.OnCutoutListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_change, btn_change2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        NotchUtil.setNoneImmersiveNoneNotch(this, false, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
        setContentView(R.layout.activity_main);
        btn_change = findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);

        btn_change2 = findViewById(R.id.btn_change2);
        btn_change2.setOnClickListener(this);

        NotchUtil.isHasCutout(this, new OnCutoutListener() {
            @Override
            public void isHasCutout(boolean isHas) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                NotchUtil.setImmersiveWithNotch(this, false, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
                setBtnAdapter(true);
                //刷新页面会使Activity重走onCreate()方法
                getWindowManager()
                        .updateViewLayout(getWindow().getDecorView(), getWindow().getDecorView().getLayoutParams());
                break;
            case R.id.btn_change2:
                NotchUtil.setNoneImmersiveNoneNotch(this, true, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER);
                setBtnAdapter(false);
                getWindowManager()
                        .updateViewLayout(getWindow().getDecorView(), getWindow().getDecorView().getLayoutParams());
                break;
        }
    }

    public void setBtnAdapter(boolean isAdapter) {
        if (isAdapter) {
            NotchUtil.adaptStartusHeight(this, btn_change, 0, true);
            NotchUtil.adaptStartusHeight(this, btn_change2, 0, true);
        } else {
            NotchUtil.restoreMarginHeight(this, btn_change, 0, true);
            NotchUtil.restoreMarginHeight(this, btn_change2, 0, true);
        }
    }
}
