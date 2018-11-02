package kiolk.github.com.timetrakerprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import kiolk.github.com.timesheetprogressbar.TimeSheetBar;

public class MainActivity2 extends AppCompatActivity {

    private TimeSheetBar bar;
    private Button button;

    private TimeSheetBar bar2;
    private Button button2;
//    private TimeSheetBar bar1;
//    private TimeSheetBar bar2;
//    private TimeSheetBar bar3;
//    private TimeSheetBar bar4;
    private static final int THREE_HOUR = 10800;
    private static final int WORK_DAY = 28800;
    private static final int WORK_MONTH = 633600;
    private int trackedTime = WORK_DAY * 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        bar = findViewById(R.id.time_bar);
        button = findViewById(R.id.button);

        bar2 = findViewById(R.id.time_bar_2);
        button2 = findViewById(R.id.button_2);

        bar2.setTrackedSeconds(10);
        bar2.setStandardDayWorkDurationSeconds(55);
        bar2.setRequiredSeconds(1000);
        bar2.setRequiredSecondsRelativeToday(270);
        bar2.setSmallestHoleUnit(1);
        bar2.setValueUnit("pages");
        bar2.setBarAnimated(true);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long changeTracked = bar2.getTrackedSeconds() + new Random().nextInt(55);
                bar2.setTrackedSeconds(changeTracked);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackedTime += 7200 * 1.75;
                bar.setTrackedSeconds(trackedTime);
            }
        });
//        bar1 = findViewById(R.id.time_bar_1);
//        bar2 = findViewById(R.id.time_bar_2);
//        bar3 = findViewById(R.id.time_bar_3);
//        bar4 = findViewById(R.id.time_bar_4);
//        bar4.setProgressHeight(200);

        setBar(bar, 662400,  trackedTime,  WORK_DAY * 8);
//        setBar(bar1, WORK_MONTH, WORK_DAY * 8 + THREE_HOUR, WORK_DAY * 9);
//        setBar(bar2, WORK_MONTH, WORK_DAY * 15  - THREE_HOUR * 2, WORK_DAY * 16);
//        setBar(bar3, WORK_MONTH, WORK_DAY * 21 + THREE_HOUR * 4, WORK_DAY * 22);
//        setBar(bar4, WORK_MONTH, WORK_DAY * 5 + THREE_HOUR * 3, WORK_DAY * 10);
    }

    private void setBar(TimeSheetBar bar, int required, int tracked, int currentNeed) {
        bar.setRequiredSeconds(required);
        bar.setTrackedSeconds(tracked);
        bar.setRequiredSecondsRelativeToday(currentNeed);
        bar.setStandardDayWorkDurationSeconds(28800);
    }
}
