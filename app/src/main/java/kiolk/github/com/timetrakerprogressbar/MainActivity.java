package kiolk.github.com.timetrakerprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kiolk.github.com.timesheetprogressbar.TimeSheetBar;

public class MainActivity extends AppCompatActivity {

    private TimeSheetBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = findViewById(R.id.time_bar);
        bar.setmProgresHeight(60f);
        bar.setmRequiredSeconds(576000);
        bar.setmTrackedSeconds(144000);
        bar.setmRequiredSecondsRelativeToday(172800);
    }
}
