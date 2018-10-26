package kiolk.github.com.timesheetprogressbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class TimeSheetBar extends View {

    @ColorInt
    public static final int TRACKED_TIME_DEFAULT_COLOR = 0xFF35d96f;
    public static final int CURRENT_TODAY_TRACKED_DEFAULT_COLOR = 0xFF35c26f;
    public static final int NEED_TRACK_DEFAULT_COLOR = 0xFFff2c26;
    public static final int CURRENT_TODAY_NEED_TRACK_DEFAULT_COLOR = 0xFFff5926;
    public static final int MORE_TRACKED_DEFAULT_COLOR = 0xFF35a96f;
    public static final int REQUEIRD_TRACK_DEFAULT_COLOR = 0xFFd0dbd0;
    public static final int TEXT_DEFAULT_COLOR = 0xFF434744;
    public static final int STROKE_DEFAULT_COLOR = 0xFFd6d834;

    public static final float ROUND_X = 10f;
    public static final float ROUND_Y = 10f;
    public static final long EIGHT_HOURS_WORK_DAY = 28800;
    public static final float ONE_HOUR = 3600f;
    public static final int DEFAULT_MAX_BAR_HEIGHT = 200;

    private RectF mRectF;
    private Rect mTmpRect;
    private Paint mBackgroundPaint;
    private Paint mTrackedTimePaint;
    private Paint mCurrentUnTrackPaint;
    private Paint mMoreTrackedTimePaint;
    private Paint mNeedTrackTimePaint;
    private Paint mMoreCurrentDayTrackedTimePaint;
    private Paint mLinePaint;
    private Paint mLabelPaint;
    private Paint mTextPaint;
    private Paint mStrokePaint;

    private float startPointX = 0;
    private float startPointY = 0;

    private int widthRec;
    private int heightRec;

    private int mBarType;
    private float mProgressHeight = DEFAULT_MAX_BAR_HEIGHT;
    private float mViewHeight;
    private float mViewWidth;

    private int mTrackedTimeColor;
    private int mUnTrackedTimeColor;
    private int mNeedTrackTimeColor;
    private int mDayNeedTrackTimeColor;
    private int mMoreTrackedTimeColor;
    private int mMoreCurrentDayTrackedTimeColor;
    private int mTextColor;
    private int mStrokeColor;

    private long mTrackedSeconds = 259200;
    private long mRequiredSeconds = 576000;
    private long mRequiredSecondsRelativeToday = EIGHT_HOURS_WORK_DAY;
    private long mStandardDayWorkDurationSeconds = EIGHT_HOURS_WORK_DAY;

    private long mTrackedBeforeTodaySeconds = 0;
    private long mNeedTrackSeconds = 0;
    private long mCurrentTrackedSecconds = 0;
    private long mMoreTrackedSeconds = 0;
    private long mCurrentNeedTrakSeconds = 0;
    private long mUnTrackedTime = 0;

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

    public long getTrackedSeconds() {
        invalidate();
        return mTrackedSeconds;
    }

    public void setTrackedSeconds(long mTrackedSeconds) {
        invalidate();
        this.mTrackedSeconds = mTrackedSeconds;
    }

    public long getRequiredSeconds() {
        return mRequiredSeconds;
    }

    public void setRequiredSeconds(long mRequiredSeconds) {
        invalidate();
        this.mRequiredSeconds = mRequiredSeconds;
    }

    public long getRequiredSecondsRelativeToday() {
        return mRequiredSecondsRelativeToday;
    }

    public void setRequiredSecondsRelativeToday(long mRequiredSecondsRelativeToday) {
        invalidate();
        this.mRequiredSecondsRelativeToday = mRequiredSecondsRelativeToday;
    }

    public long getStandardDayWorkDurationSeconds() {
        return mStandardDayWorkDurationSeconds;
    }

    public void setStandardDayWorkDurationSeconds(long mStandardDayWorkDurationSeconds) {
        invalidate();
        this.mStandardDayWorkDurationSeconds = mStandardDayWorkDurationSeconds;
    }

    public float getProgressHeight() {
        return mProgressHeight;
    }

    public void setProgressHeight(float mProgressHeight) {
        invalidate();
        this.mProgressHeight = mProgressHeight;
    }

    public int getTrackedTimeColor() {
        return mTrackedTimeColor;
    }

    public void setTrackedTimeColor(int mTrackedTimeColor) {
        invalidate();
        this.mTrackedTimeColor = mTrackedTimeColor;
    }

    public int getUnTrackedTimeColor() {
        return mUnTrackedTimeColor;
    }

    public void setUnTrackedTimeColor(int mUnTrackedTimeColor) {
        invalidate();
        this.mUnTrackedTimeColor = mUnTrackedTimeColor;
    }

    public int getNeedTrackTimeColor() {
        return mNeedTrackTimeColor;
    }

    public void setNeedTrackTimeColor(int mNeedTrackTimeColor) {
        invalidate();
        this.mNeedTrackTimeColor = mNeedTrackTimeColor;
    }

    public int getDayNeedTrackTimeColor() {
        return mDayNeedTrackTimeColor;
    }

    public void setDayNeedTrackTimeColor(int mDayNeedTrackTimeColor) {
        invalidate();
        this.mDayNeedTrackTimeColor = mDayNeedTrackTimeColor;
    }

    public int getMoreTrackedTimeColor() {
        return mMoreTrackedTimeColor;
    }

    public void setMoreTrackedTimeColor(int mMoreTrackedTimeColor) {
        invalidate();
        this.mMoreTrackedTimeColor = mMoreTrackedTimeColor;
    }

    public int getMoreCurrentDayTrackedTimeColor() {
        return mMoreCurrentDayTrackedTimeColor;
    }

    public void setMoreCurrentDayTrackedTimeColor(int mMoreCurrentDayTrackedTimeColor) {
        invalidate();
        this.mMoreCurrentDayTrackedTimeColor = mMoreCurrentDayTrackedTimeColor;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        invalidate();
        this.mTextColor = mTextColor;
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public void setStrokeColor(int mStrokeColor) {
        invalidate();
        this.mStrokeColor = mStrokeColor;
    }

    private void init(AttributeSet attrs) {

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TimeSheetBar, 0, 0);

        mTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_trackedTimeColor, TRACKED_TIME_DEFAULT_COLOR);
        mUnTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_unTrackedTimeColor, REQUEIRD_TRACK_DEFAULT_COLOR);
        mDayNeedTrackTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_dayNeedTrackTimeColor, CURRENT_TODAY_NEED_TRACK_DEFAULT_COLOR);
        mNeedTrackTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_additionalNeedTrackTimeColor, NEED_TRACK_DEFAULT_COLOR);
        mMoreTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_moreTrackedTimeColor, MORE_TRACKED_DEFAULT_COLOR);
        mMoreCurrentDayTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_moreCurrentDayTrakedTimeColor, CURRENT_TODAY_TRACKED_DEFAULT_COLOR);
        mTextColor = typedArray.getColor(R.styleable.TimeSheetBar_barTextColor, TEXT_DEFAULT_COLOR);
        mStrokeColor = typedArray.getColor(R.styleable.TimeSheetBar_barStrokeColor, STROKE_DEFAULT_COLOR);
        mProgressHeight = typedArray.getDimension(R.styleable.TimeSheetBar_maxBarHeight, DEFAULT_MAX_BAR_HEIGHT);
        mBarType = typedArray.getInt(R.styleable.TimeSheetBar_barType, BarType.DIVIDED.getType());

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(mUnTrackedTimeColor);
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

        mLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelPaint.setStyle(Paint.Style.STROKE);
        mLabelPaint.setColor(Color.GRAY);
        mLabelPaint.setStrokeWidth(2);
        mLabelPaint.setStyle(Paint.Style.FILL);
        mLabelPaint.setColor(Color.YELLOW);
        mLabelPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mProgressHeight / 2);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(1f);
        mStrokePaint.setColor(mStrokeColor);

        mRectF = new RectF(0, heightRec, widthRec, 0);
        mTmpRect = new Rect();
    }

    private void calculateTimersSeconds() {
        long trackedDiffTime = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;
        mTrackedBeforeTodaySeconds = mTrackedSeconds;
        mUnTrackedTime = mRequiredSeconds - mRequiredSecondsRelativeToday;

        if (trackedDiffTime > mTrackedSeconds) {
            mNeedTrackSeconds = trackedDiffTime;
        } else if (trackedDiffTime < mTrackedSeconds && mTrackedSeconds < mRequiredSecondsRelativeToday) {
            mTrackedBeforeTodaySeconds = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;
            mCurrentTrackedSecconds = mTrackedSeconds - mTrackedBeforeTodaySeconds;
            mCurrentNeedTrakSeconds = mStandardDayWorkDurationSeconds - mCurrentTrackedSecconds;
        } else if (mTrackedSeconds > mRequiredSecondsRelativeToday) {
            mTrackedBeforeTodaySeconds = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;
            mCurrentTrackedSecconds = mStandardDayWorkDurationSeconds;
            mMoreTrackedSeconds = mTrackedSeconds - mRequiredSecondsRelativeToday;
            mCurrentNeedTrakSeconds = 0;
            mUnTrackedTime = mUnTrackedTime - mMoreTrackedSeconds;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        calculateTimersSeconds();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int selectedHeight;

        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                selectedHeight = height;
                break;
            case MeasureSpec.AT_MOST:
                selectedHeight = (int) Math.min(height, getProgressHeight() * 3);
                break;
            case MeasureSpec.UNSPECIFIED:
                selectedHeight = height;
                break;
            default:
                selectedHeight = height;
                break;
        }

        int paddingY = getPaddingBottom() + getPaddingTop();
        int paddingX = getPaddingLeft() + getPaddingRight();
        mViewHeight = selectedHeight - paddingY;
        mViewWidth = width - paddingX;

        if (mViewHeight < getProgressHeight()) {
            setProgressHeight(mViewHeight);
        }

        height = height - paddingY;
        int size = (width > height) ? width : height;
        heightRec = size;
        widthRec = size;

        setMeasuredDimension(width, selectedHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float trackedBlockWidth;
        float needTrackBlockWidth = 0;
        float moreCurrentDayTrackedBlockWidth = 0;
        float moreTrackedTimeBlockWidth = 0;
        float unTrackedTimeBlockWidth;
        float needCurrentTodayTrackBlockWidth;


        if (mViewHeight / 3 < getProgressHeight()) {
            startPointY = 0;
        } else {
            startPointY = getHeight() / 3;
        }

        long trackedDiffTime = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;

        trackedBlockWidth = calculateBlockWidth(mTrackedSeconds);
        needCurrentTodayTrackBlockWidth = calculateBlockWidth(mStandardDayWorkDurationSeconds);
        unTrackedTimeBlockWidth = calculateBlockWidth(mRequiredSeconds - mRequiredSecondsRelativeToday);


        if (trackedDiffTime > mTrackedSeconds) {
            needTrackBlockWidth = calculateBlockWidth(trackedDiffTime - mTrackedSeconds);
            trackedBlockWidth = calculateBlockWidth(mTrackedSeconds);
        } else if (trackedDiffTime < mTrackedSeconds && mTrackedSeconds < mRequiredSecondsRelativeToday) {
            trackedBlockWidth = calculateBlockWidth(mTrackedSeconds - (mTrackedSeconds - trackedDiffTime));
            moreCurrentDayTrackedBlockWidth = calculateBlockWidth(mTrackedSeconds - (mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds));
            needCurrentTodayTrackBlockWidth = calculateBlockWidth(mRequiredSecondsRelativeToday - mTrackedSeconds);
        } else if (mTrackedSeconds > mRequiredSecondsRelativeToday) {
            trackedBlockWidth = calculateBlockWidth(mTrackedSeconds - (mTrackedSeconds - trackedDiffTime));
            moreCurrentDayTrackedBlockWidth = calculateBlockWidth(mStandardDayWorkDurationSeconds);
            moreTrackedTimeBlockWidth = calculateBlockWidth(mTrackedSeconds - mRequiredSecondsRelativeToday);
            unTrackedTimeBlockWidth = calculateBlockWidth(mRequiredSeconds - mTrackedSeconds);
            needCurrentTodayTrackBlockWidth = 0;
        }

        drawViewBorder(canvas);

        attachBarTitle(canvas, "Title");

        switch (BarType.getType(mBarType)) {
            case DIVIDED:
                float shiftPosition = getPaddingLeft();
                shiftPosition = drawSingleBlock(canvas, shiftPosition, trackedBlockWidth, mTrackedTimePaint);
                attachText(canvas, getPaddingLeft(), trackedBlockWidth, mTrackedBeforeTodaySeconds);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, needTrackBlockWidth, mNeedTrackTimePaint);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, moreCurrentDayTrackedBlockWidth, mMoreCurrentDayTrackedTimePaint);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, needCurrentTodayTrackBlockWidth, mCurrentUnTrackPaint);
//                attachLable(canvas, shiftPosition, needCurrentTodayTrackBlockWidth, mCurrentNeedTrakSeconds);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, moreTrackedTimeBlockWidth, mMoreTrackedTimePaint);
                drawSingleBlock(canvas, shiftPosition, unTrackedTimeBlockWidth, mBackgroundPaint);
                attachText(canvas, shiftPosition, unTrackedTimeBlockWidth, mUnTrackedTime);
                break;
            case OVERLAID:
                drawSingleBlock(canvas, getPaddingLeft(), mViewWidth, mBackgroundPaint);
                float trackedBlockWidthFromStart = trackedBlockWidth;
                float needTrackBlockWidthFromStart = trackedBlockWidthFromStart + needTrackBlockWidth;
                float moreCurrentDayTrackedBlockWidthFromStart = needTrackBlockWidthFromStart + moreCurrentDayTrackedBlockWidth;
                float needCurrentTodayTrackBlockWidthFromStart = moreCurrentDayTrackedBlockWidthFromStart + needCurrentTodayTrackBlockWidth;
                float moreTrakedTimeBlockWidthFromStart = needCurrentTodayTrackBlockWidthFromStart + moreTrackedTimeBlockWidth;

                drawSingleBlock(canvas, getPaddingLeft(), moreTrakedTimeBlockWidthFromStart, mMoreTrackedTimePaint);
                drawSingleBlock(canvas, getPaddingLeft(), needCurrentTodayTrackBlockWidthFromStart, mCurrentUnTrackPaint);
                drawSingleBlock(canvas, getPaddingLeft(), moreCurrentDayTrackedBlockWidthFromStart, mMoreCurrentDayTrackedTimePaint);
                drawSingleBlock(canvas, getPaddingLeft(), needTrackBlockWidthFromStart, mNeedTrackTimePaint);
                drawSingleBlock(canvas, getPaddingLeft(), trackedBlockWidthFromStart, mTrackedTimePaint);

                attachText(canvas, getPaddingLeft(), trackedBlockWidth, mTrackedBeforeTodaySeconds);
                attachText(canvas, trackedBlockWidth + needTrackBlockWidth + moreCurrentDayTrackedBlockWidth + needTrackBlockWidth + moreTrackedTimeBlockWidth, unTrackedTimeBlockWidth, mUnTrackedTime);
                break;
        }

        canvas.drawPath(new Path(), mBackgroundPaint);
    }

    private void attachBarTitle(Canvas canvas, String title) {
        if (mViewHeight / 3 >= mProgressHeight) {
            return;
        }

        mTextPaint.setTextSize(mProgressHeight / 2);
        mTextPaint.getTextBounds(title, 0, title.length(), mTmpRect);
        float textWidth = mTmpRect.right - mTmpRect.left;
        float textHeight = mTmpRect.bottom - mTmpRect.top;

        if (textWidth > mViewWidth) {
            return;
        }

        float startX = (mViewWidth - textWidth) / 2;
        float startY = ((mViewHeight / 3) - textHeight) / 2 + textHeight;

        canvas.drawText(title, startX + getPaddingLeft(), startY + getPaddingTop(), mTextPaint);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawViewBorder(Canvas canvas) {
        canvas.drawRoundRect((float) getPaddingLeft(), (float) getPaddingTop(), (float) getWidth() - getPaddingRight(), getHeight() - (float) getPaddingBottom(), 10, 10, mStrokePaint);
    }

    private void attachText(Canvas canvas, float startBlockX, float endBlockX, long durationSeconds) {
        if (mProgressHeight < 40) {
            return;
        }
        float textStartPoint;
        mTextPaint.setTextSize(mProgressHeight / 2);
        String text = String.valueOf(durationSeconds / ONE_HOUR) + " h";
        Rect r = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), r);
        float height = r.bottom - r.top;


        if (mViewHeight / 2 >= mProgressHeight) {
            textStartPoint = ((mViewHeight / 6) * 5) + height / 2 + getPaddingTop();
        } else {
            textStartPoint = mViewHeight / 2 + height / 2 + getPaddingTop();
        }

        canvas.drawText(String.valueOf(durationSeconds / ONE_HOUR) + " h",
                startBlockX + (endBlockX / 2),
                textStartPoint,
                mTextPaint);
    }

    private void attachLable(Canvas canvas, float startBlockX, float endBlockX, long durationSeconds) {
        float targetX = startBlockX + (endBlockX) / 2;
        float targetY = getHeight() / 4;
        float radius = (getHeight() / 3 - getHeight() / 9) / 2;
        canvas.drawLine(startBlockX + (endBlockX) / 2, getHeight() / 2, startBlockX + (endBlockX) / 2, targetY, mLinePaint);
        mLabelPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(targetX, targetY, radius, mLabelPaint);
        mLabelPaint.setStyle(Paint.Style.STROKE);
        mLabelPaint.setColor(Color.GRAY);
        canvas.drawCircle(targetX, targetY, radius, mLabelPaint);
    }

    private float drawSingleBlock(Canvas canvas, float startPoint, float blockWidth, Paint mTrackedTimePaint) {
        mRectF.left = startPoint;
        mRectF.top = (mViewHeight / 2) - (getProgressHeight() / 2) + getPaddingTop();
        mRectF.right = blockWidth + startPoint;
        mRectF.bottom = mRectF.top + getProgressHeight();
        drawSingleBlock(canvas, mRectF, mTrackedTimePaint);
        drawSingleBlock(canvas, mRectF, mStrokePaint);
        return blockWidth + startPoint;
    }

    private float calculateBlockWidth(long pValue) {
        return mViewWidth * (pValue / (float) mRequiredSeconds);
    }

    private void drawSingleBlock(Canvas canvas, RectF block, Paint paint) {
        canvas.drawRoundRect(block, ROUND_X, ROUND_Y, paint);
    }
}
