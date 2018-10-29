package kiolk.github.com.timetrakerprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kiolk.github.com.timesheetprogressbar.TimeSheetBar;

public class MainActivity2 extends AppCompatActivity {

    private TimeSheetBar bar;
//    private TimeSheetBar bar1;
//    private TimeSheetBar bar2;
//    private TimeSheetBar bar3;
//    private TimeSheetBar bar4;
    private static final int THREE_HOUR = 10800;
    private static final int WORK_DAY = 28800;
    private static final int WORK_MONTH = 633600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        bar = findViewById(R.id.time_bar);
//        bar1 = findViewById(R.id.time_bar_1);
//        bar2 = findViewById(R.id.time_bar_2);
//        bar3 = findViewById(R.id.time_bar_3);
//        bar4 = findViewById(R.id.time_bar_4);
//        bar4.setProgressHeight(200);

        setBar(bar, WORK_MONTH, WORK_DAY* 25- THREE_HOUR, WORK_DAY * 15);
//        setBar(bar1, WORK_MONTH, WORK_DAY * 8 + THREE_HOUR, WORK_DAY * 9);
//        setBar(bar2, WORK_MONTH, WORK_DAY * 15  - THREE_HOUR * 2, WORK_DAY * 16);
//        setBar(bar3, WORK_MONTH, WORK_DAY * 21 + THREE_HOUR * 4, WORK_DAY * 22);
//        setBar(bar4, WORK_MONTH, WORK_DAY * 5 + THREE_HOUR * 3, WORK_DAY * 10);
    }

    private void setBar(TimeSheetBar bar, int required, int tracked, int currentNeed) {
        bar.setRequiredSeconds(required);
        bar.setTrackedSeconds(tracked);
        bar.setRequiredSecondsRelativeToday(currentNeed);
        bar.setStandardDayWorkDurationSeconds(THREE_HOUR * 9);
    }
}
