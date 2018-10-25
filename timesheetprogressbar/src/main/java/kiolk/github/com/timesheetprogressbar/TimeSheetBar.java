package kiolk.github.com.timesheetprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Printer;
import android.view.View;

public class TimeSheetBar extends View {

    public static final float RAOUND_X = 10f;
    public static final float ROUND_Y = 10f;
    public static final long EIGHT_HOURS_WORK_DAY = 28800;
    public static final float ONE_HOUR = 3600f;

    private RectF mRectF;
    private Paint mBackgroundPaint;
    private Paint mTrackedTimePaint;
    private Paint mCurrentUnTrackPaint;
    private Paint mMoreTrackedTimePaint;
    private Paint mNeedTrackTimePaint;
    private Paint mMoreCurrentDayTrackedTimePaint;
    private Paint mLinePaint;
    private Paint mLablePaint;
    private Paint mTextPaint;

    private float startPointX = 0;
    private float startPointY = 0;

    private int widthRec;
    private int heightRec;
    private int mBarType;
    private float mProgressHeight = 100;

    private int mTrackedTimeColor;
    private int mUntrackedTimeColor;
    private int mNeedTrackTimeColor;
    private int mDayNeedTrackTimeColor;
    private int mMoreTrackedTimeColor;
    private int mMoreCurrentDayTrackedTimeColor;

    private long mTrackedSeconds = 259200;
    private long mRequiredSeconds = 576000;
    private long mRequiredSecondsRelativeToday = EIGHT_HOURS_WORK_DAY;
    private long mStandardDayWorkDurationSeconds = EIGHT_HOURS_WORK_DAY;

    private long mTrackedBeforTodaySeconds = 0;
    private long mNeedTrackSeconds = 0;
    private long mCurrentTrakedSecconds = 0;
    private long mMoreTrackedSecconds = 0;
    private long mCurrentNeedTrakSeconds = 0;
    private long mUntrackedTime = 0;

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
        invalidate();
        return mTrackedSeconds;
    }

    public void setmTrackedSeconds(long mTrackedSeconds) {
        invalidate();
        this.mTrackedSeconds = mTrackedSeconds;
    }

    public long getmRequiredSeconds() {
        invalidate();
        return mRequiredSeconds;
    }

    public void setmRequiredSeconds(long mRequiredSeconds) {
        invalidate();
        this.mRequiredSeconds = mRequiredSeconds;
    }

    public long getmRequiredSecondsRelativeToday() {
        invalidate();
        return mRequiredSecondsRelativeToday;
    }

    public void setmRequiredSecondsRelativeToday(long mRequiredSecondsRelativeToday) {
        invalidate();
        this.mRequiredSecondsRelativeToday = mRequiredSecondsRelativeToday;
    }

    public long getmStandardDayWorkDurationSeconds() {
        invalidate();
        return mStandardDayWorkDurationSeconds;
    }

    public void setmStandardDayWorkDurationSeconds(long mStandardDayWorkDurationSeconds) {
        invalidate();
        this.mStandardDayWorkDurationSeconds = mStandardDayWorkDurationSeconds;
    }

    private void init(AttributeSet attrs) {

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TimeSheetBar, 0, 0);

        mTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_trackedTimeColor, Color.GREEN);
        mUntrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_unTrackedTimeColor, Color.GRAY);
        mDayNeedTrackTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_dayNeedTrackTimeColor, Color.RED);
        mNeedTrackTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_additionalNeedTrackTimeColor, Color.RED);
        mMoreTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_moreTrackedTimeColor, Color.GREEN);
        mMoreCurrentDayTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_moreCurrentDayTrakedTimeColor, Color.GREEN);
        mBarType = typedArray.getInt(R.styleable.TimeSheetBar_barType, BarType.DIVIDED.getType());

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
        mMoreCurrentDayTrackedTimePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMoreCurrentDayTrackedTimePaint.setColor(mMoreCurrentDayTrackedTimeColor);
        mNeedTrackTimePaint.setStyle(Paint.Style.FILL);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setStrokeWidth(3);
        mLinePaint.setStyle(Paint.Style.FILL);


        mLablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLablePaint.setStyle(Paint.Style.STROKE);
        mLablePaint.setColor(Color.GRAY);
        mLablePaint.setStrokeWidth(2);
        mLablePaint.setStyle(Paint.Style.FILL);
        mLablePaint.setColor(Color.YELLOW);
        mLablePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mProgressHeight / 2);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mRectF = new RectF(0, heightRec, widthRec, 0);
    }

    private void calculateTimersSeconds(){

        long trackedDiffTime = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;
        mTrackedBeforTodaySeconds = mTrackedSeconds;

        mUntrackedTime = mRequiredSeconds - mRequiredSecondsRelativeToday;
        if (trackedDiffTime > mTrackedSeconds) {
            mNeedTrackSeconds = trackedDiffTime;
            mTrackedBeforTodaySeconds = mTrackedSeconds;
        } else if (trackedDiffTime < mTrackedSeconds && mTrackedSeconds < mRequiredSecondsRelativeToday) {
            mTrackedBeforTodaySeconds = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;
            mCurrentTrakedSecconds = mTrackedSeconds - mTrackedBeforTodaySeconds;
            mCurrentNeedTrakSeconds = mStandardDayWorkDurationSeconds - mCurrentTrakedSecconds;
        } else if (mTrackedSeconds > mRequiredSecondsRelativeToday) {
            mTrackedBeforTodaySeconds = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;
            mCurrentTrakedSecconds = mStandardDayWorkDurationSeconds;
            mMoreTrackedSecconds = mTrackedSeconds - mRequiredSecondsRelativeToday;
            mCurrentNeedTrakSeconds = 0;
            mUntrackedTime = mUntrackedTime - mMoreTrackedSecconds;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        calculateTimersSeconds();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int selectedHeigt;

        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                selectedHeigt = height;
                break;
            case MeasureSpec.AT_MOST:
                selectedHeigt = (int) Math.min(height, getmProgressHeight() * 3);
                mProgressHeight = selectedHeigt / 3;
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
        float trackedBlockWidth;
        float needTrackBlockWidth = 0;
        float moreCurrentDayTrackedBlockWidth = 0;
        float moreTrakedTimeBlockWidth = 0;
        float untrakedTimeBlockwidth;
        float needCurrentTodayTrackBlockWidth;

        startPointY = getHeight() / 3;

        long trackedDiffTime = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;

        trackedBlockWidth = canculatPercantage(mTrackedSeconds);
        needCurrentTodayTrackBlockWidth = canculatPercantage(mStandardDayWorkDurationSeconds);
        untrakedTimeBlockwidth = canculatPercantage(mRequiredSeconds - mRequiredSecondsRelativeToday);


        if (trackedDiffTime > mTrackedSeconds) {
            needTrackBlockWidth = canculatPercantage(trackedDiffTime - mTrackedSeconds);
            trackedBlockWidth = canculatPercantage(mTrackedSeconds);
        } else if (trackedDiffTime < mTrackedSeconds && mTrackedSeconds < mRequiredSecondsRelativeToday) {
            trackedBlockWidth = canculatPercantage(mTrackedSeconds - (mTrackedSeconds - trackedDiffTime));
            moreCurrentDayTrackedBlockWidth = canculatPercantage(mTrackedSeconds - (mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds));
            needCurrentTodayTrackBlockWidth = canculatPercantage(mRequiredSecondsRelativeToday - mTrackedSeconds);
        } else if (mTrackedSeconds > mRequiredSecondsRelativeToday) {
            trackedBlockWidth = canculatPercantage(mTrackedSeconds - (mTrackedSeconds - trackedDiffTime));
            moreCurrentDayTrackedBlockWidth = canculatPercantage(mStandardDayWorkDurationSeconds);
            moreTrakedTimeBlockWidth = canculatPercantage(mTrackedSeconds - mRequiredSecondsRelativeToday);
            untrakedTimeBlockwidth = canculatPercantage(mRequiredSeconds - mTrackedSeconds);
            needCurrentTodayTrackBlockWidth = 0;
        }

        switch (BarType.getType(mBarType)) {
            case DIVIDED:
                float shiftPosition = 0;
                shiftPosition = drawSingleBlock(canvas, shiftPosition, trackedBlockWidth, mTrackedTimePaint);
                attactText(canvas, 0, trackedBlockWidth, mTrackedBeforTodaySeconds);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, needTrackBlockWidth, mNeedTrackTimePaint);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, moreCurrentDayTrackedBlockWidth, mMoreCurrentDayTrackedTimePaint);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, needCurrentTodayTrackBlockWidth, mCurrentUnTrackPaint);
//                attachLable(canvas, shiftPosition, needCurrentTodayTrackBlockWidth, mCurrentNeedTrakSeconds);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, moreTrakedTimeBlockWidth, mMoreTrackedTimePaint);
                drawSingleBlock(canvas, shiftPosition, untrakedTimeBlockwidth, mBackgroundPaint);
                attactText(canvas, shiftPosition, untrakedTimeBlockwidth, mUntrackedTime);
                break;
            case OVERLAID:
                attactText(canvas, 0, trackedBlockWidth, mTrackedBeforTodaySeconds);
                attactText(canvas, trackedBlockWidth + needTrackBlockWidth + moreCurrentDayTrackedBlockWidth + needTrackBlockWidth + moreTrakedTimeBlockWidth, untrakedTimeBlockwidth, mUntrackedTime);
                drawSingleBlock(canvas, 0, getWidth(), mBackgroundPaint);
                float trackedBlockWidthFromStart = trackedBlockWidth;
                float needTrackBlockWidthFromStart = trackedBlockWidthFromStart + needTrackBlockWidth;
                float moreCurrentDayTrackedBlockWidthFromStart = needTrackBlockWidthFromStart + moreCurrentDayTrackedBlockWidth;
                float needCurrentTodayTrackBlockWidthFromStart = moreCurrentDayTrackedBlockWidthFromStart + needCurrentTodayTrackBlockWidth;
                float moreTrakedTimeBlockWidthFromStart = needCurrentTodayTrackBlockWidthFromStart + moreTrakedTimeBlockWidth;

                drawSingleBlock(canvas, 0, moreTrakedTimeBlockWidthFromStart, mMoreTrackedTimePaint);
                drawSingleBlock(canvas, 0, needCurrentTodayTrackBlockWidthFromStart, mCurrentUnTrackPaint);
                drawSingleBlock(canvas, 0, moreCurrentDayTrackedBlockWidthFromStart, mMoreCurrentDayTrackedTimePaint);
                drawSingleBlock(canvas, 0, needTrackBlockWidthFromStart, mNeedTrackTimePaint);
                drawSingleBlock(canvas, 0, trackedBlockWidthFromStart, mTrackedTimePaint);
                break;
        }

        canvas.drawPath(new Path(), mBackgroundPaint);
    }

    private void attactText(Canvas canvas, float startBlockX, float endBlockX, long durationSeconds) {


        canvas.drawText(String.valueOf(durationSeconds / ONE_HOUR) + " h",
                startBlockX + (endBlockX  / 2),
                (float) (getHeight() * 0.9),
                mTextPaint);
    }

    private void attachLable(Canvas canvas, float startBlockX, float endBlockX, long durationSeconds) {
        float targetX = startBlockX + (endBlockX) / 2;
        float targetY = getHeight() / 4;
        float radius = (getHeight() / 3 - getHeight() / 9) / 2;
        canvas.drawLine(startBlockX + (endBlockX) / 2, getHeight() / 2, startBlockX + (endBlockX) / 2, targetY, mLinePaint);
        mLablePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(targetX, targetY, radius, mLablePaint);
        mLablePaint.setStyle(Paint.Style.STROKE);
        mLablePaint.setColor(Color.GRAY);
        canvas.drawCircle(targetX, targetY, radius, mLablePaint);
    }

    private float drawSingleBlock(Canvas canvas, float startPoint, float blockWidth, Paint mTrackedTimePaint) {
        mRectF.left = startPoint;
        mRectF.top = (getHeight() / 2) - (getmProgressHeight() / 2);
        mRectF.right = blockWidth + startPoint;
        mRectF.bottom = mRectF.top + getmProgressHeight();
        drawSingleBlock(canvas, mRectF, mTrackedTimePaint);
        return blockWidth + startPoint;
    }

    private float canculatPercantage(long pValue) {
        float percantage = pValue / (float) mRequiredSeconds;
        return getWidth() * percantage;
    }

    private void drawSingleBlock(Canvas canvas, RectF block, Paint paint) {
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

    public float getmProgressHeight() {
        return mProgressHeight;
    }

    public void setmProgressHeight(float mProgressHeight) {
        this.mProgressHeight = mProgressHeight;
    }
}
