package kiolk.github.com.timesheetprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class TimeSheetBar extends View {

    public static final float RAOUND_X = 10f;
    public static final float ROUND_Y = 10f;
    public static final long EIGHT_HOURS_WORK_DAY = 28800;

    private RectF mRectF;
    private Paint mBackgroundPaint;
    private Paint mTrackedTimePaint;
    private Paint mCurrentUnTrackPaint;
    private Paint mMoreTrackedTimePaint;
    private Paint mNeedTrackTimePaint;
    private int widthRec;
    private int heightRec;
    private float mProgresHeight = 20;

    private int mTrackedTimeColor;
    private int mUntrackedTimeColor;
    private int mNeedTrackTimeColor;
    private int mDayNeedTrackTimeColor;
    private int mMoreTrackedTimeColor;

    private long mTrackedSeconds = 259200;
    private long mRequiredSeconds = 576000;
    private long mRequiredSecondsRelativeToday = EIGHT_HOURS_WORK_DAY;
    private long mStandardDayWorkDurationSeconds = EIGHT_HOURS_WORK_DAY;

    public TimeSheetBar(Context context) {
        super(context);
        init(null);
    }

    public TimeSheetBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TimeSheetBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TimeSheetBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public long getmTrackedSeconds() {
        return mTrackedSeconds;
    }

    public void setmTrackedSeconds(long mTrackedSeconds) {
        this.mTrackedSeconds = mTrackedSeconds;
    }

    public long getmRequiredSeconds() {
        return mRequiredSeconds;
    }

    public void setmRequiredSeconds(long mRequiredSeconds) {
        this.mRequiredSeconds = mRequiredSeconds;
    }

    public long getmRequiredSecondsRelativeToday() {
        return mRequiredSecondsRelativeToday;
    }

    public void setmRequiredSecondsRelativeToday(long mRequiredSecondsRelativeToday) {
        this.mRequiredSecondsRelativeToday = mRequiredSecondsRelativeToday;
    }

    public long getmStandardDayWorkDurationSeconds() {
        return mStandardDayWorkDurationSeconds;
    }

    public void setmStandardDayWorkDurationSeconds(long mStandardDayWorkDurationSeconds) {
        this.mStandardDayWorkDurationSeconds = mStandardDayWorkDurationSeconds;
    }

    private void init(AttributeSet attrs) {

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TimeSheetBar, 0, 0);

        mTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_trackedTimeColor, Color.GREEN);
        mUntrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_unTrackedTimeColor, Color.GRAY);
        mDayNeedTrackTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_dayNeedTrackTimeColor, Color.RED);
        mNeedTrackTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_additionalNeedTrackTimeColor, Color.RED);
        mMoreTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_moreTrackedTimeColor, Color.GREEN);

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(mUntrackedTimeColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mTrackedTimePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTrackedTimePaint.setColor(mTrackedTimeColor);
        mTrackedTimePaint.setStyle(Paint.Style.FILL);
        mMoreTrackedTimePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMoreTrackedTimePaint.setColor(mMoreTrackedTimeColor);
        mMoreTrackedTimePaint.setStyle(Paint.Style.FILL);
        mCurrentUnTrackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCurrentUnTrackPaint.setColor(mDayNeedTrackTimeColor);
        mCurrentUnTrackPaint.setStyle(Paint.Style.FILL);
        mNeedTrackTimePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNeedTrackTimePaint.setColor(mNeedTrackTimeColor);
        mNeedTrackTimePaint.setStyle(Paint.Style.FILL);

        mRectF = new RectF(0, heightRec, widthRec, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int selectedHeigt;

        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                selectedHeigt = height;
                break;
            case MeasureSpec.AT_MOST:
                selectedHeigt = (int) Math.min(height, getmProgresHeight());
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                selectedHeigt = height;
                break;
        }

        int paddingY = getPaddingBottom() + getPaddingTop();
        int paddingx = getPaddingLeft() + getPaddingRight();
        width = width - paddingx;
        height = height - paddingY;
        int size = (width > height) ? width : height;
        heightRec = size;
        widthRec = size;
        setMeasuredDimension(width, selectedHeigt);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawTrackedBlock(canvas);

        canvas.drawRect(0, 0, getWidth(), getmProgresHeight(), mBackgroundPaint);
        canvas.drawRect(0, 0, 100, getmProgresHeight(), mTrackedTimePaint);
        canvas.drawRect(100, 0, 200, getmProgresHeight(), mMoreTrackedTimePaint);
        canvas.drawRect(200, 0, 300, getmProgresHeight(), mNeedTrackTimePaint);
        canvas.drawRect(300, 0, 400, getmProgresHeight(), mCurrentUnTrackPaint);
    }

    private void drawTrackedBlock(Canvas canvas) {
        if(mRequiredSecondsRelativeToday == mStandardDayWorkDurationSeconds){
//            mRectF()
        }
    }

    private void drawSingleBlock(Canvas canvas, RectF block, Paint paint){
        canvas.drawRoundRect(block, RAOUND_X, ROUND_Y, paint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public float getmProgresHeight() {
        return mProgresHeight;
    }

    public void setmProgresHeight(float mProgresHeight) {
        this.mProgresHeight = mProgresHeight;
    }
}
