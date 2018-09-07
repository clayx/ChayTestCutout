package chay.org.chaytestcutout;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
        btn_change = findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);

        btn_change2 = findViewById(R.id.btn_change2);
        btn_change2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
//                NotchUtil.setNoneImmersiveWithNotch(this, true, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
                NotchUtil.setImmersiveWithNotch(this,true,WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
                setBtnAdapter(true);
                getWindowManager()
                        .updateViewLayout(getWindow().getDecorView(), getWindow().getDecorView().getLayoutParams());
                break;
            case R.id.btn_change2:
                NotchUtil.setNoneImmersiveNoneNotch(this, true, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER);
//                setBtnAdapter(false);
                getWindowManager()
                        .updateViewLayout(getWindow().getDecorView(), getWindow().getDecorView().getLayoutParams());
                break;
        }
    }

    public void setBtnAdapter(boolean isAdapter) {
        if (isAdapter) {
            int statusHeight = StatusBarUtil.getStatusBarHeight(this);
            ConstraintLayout.LayoutParams btn1Lp = (ConstraintLayout.LayoutParams) btn_change.getLayoutParams();
            btn1Lp.topMargin = statusHeight;
            btn_change.setLayoutParams(btn1Lp);

            ConstraintLayout.LayoutParams btn2Lp = (ConstraintLayout.LayoutParams) btn_change2.getLayoutParams();
            btn2Lp.topMargin = statusHeight;
            btn_change2.setLayoutParams(btn2Lp);
        } else {
            ConstraintLayout.LayoutParams btn1Lp = (ConstraintLayout.LayoutParams) btn_change.getLayoutParams();
            btn1Lp.topMargin = 0;
            btn_change.setLayoutParams(btn1Lp);

            ConstraintLayout.LayoutParams btn2Lp = (ConstraintLayout.LayoutParams) btn_change2.getLayoutParams();
            btn2Lp.topMargin = 0;
            btn_change2.setLayoutParams(btn2Lp);
        }
    }
}
