package kiolk.github.com.timetrakerprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kiolk.github.com.timesheetprogressbar.TimeSheetBar;

public class MainActivity extends AppCompatActivity {

    private TimeSheetBar bar;
    private TimeSheetBar bar1;
    private TimeSheetBar bar2;
    private TimeSheetBar bar3;
    private TimeSheetBar bar4;
    private static final int THREE_HOUR = 10800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = findViewById(R.id.time_bar);
        bar1 = findViewById(R.id.time_bar_1);
        bar2 = findViewById(R.id.time_bar_2);
        bar3 = findViewById(R.id.time_bar_3);
        bar4 = findViewById(R.id.time_bar_4);

        setBar(bar, 576000, 144000, 172800);
        setBar(bar1, 576000, 133200, 172800);
        setBar(bar2, 576000, 144000 + THREE_HOUR, 172800);
        setBar(bar3, 576000, 144000 + THREE_HOUR *2, 172800);
        setBar(bar4, 576000, 144000 + THREE_HOUR * 3, 172800);
    }

    private void setBar(TimeSheetBar bar, int required, int tracked, int currentNeed) {
        bar.setmProgressHeight(120f);
        bar.setmRequiredSeconds(required);
        bar.setmTrackedSeconds(tracked);
        bar.setmRequiredSecondsRelativeToday(currentNeed);
    }
}
