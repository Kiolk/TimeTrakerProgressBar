package kiolk.github.com.timesheetprogressbar;

import android.animation.ObjectAnimator;
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

/**
 */
public class TimeSheetBar extends View {

    @ColorInt
    public static final int TRACKED_TIME_DEFAULT_COLOR = 0xFF35d96f;
    public static final int CURRENT_TODAY_TRACKED_DEFAULT_COLOR = 0xFF35c26f;
    public static final int NEED_TRACK_DEFAULT_COLOR = 0xFFff2c26;
    public static final int CURRENT_TODAY_NEED_TRACK_DEFAULT_COLOR = 0xFFff5926;
    public static final int MORE_TRACKED_DEFAULT_COLOR = 0xFF35a96f;
    public static final int REQUIRED_TRACK_DEFAULT_COLOR = 0xFFd0dbd0;
    public static final int TEXT_DEFAULT_COLOR = 0xFF434744;
    public static final int STROKE_DEFAULT_COLOR = 0xFFd6d834;
    public static final int MORE_THAN_MONTH_TRACKED_DEFAULT_COLOR = 0xFF092603;

    public static final float ROUND_X = 10f;
    public static final float ROUND_Y = 10f;
    public static final long EIGHT_HOURS_WORK_DAY = 28800;
    public static final float ONE_HOUR = 3600f;
    public static final int DEFAULT_MAX_BAR_HEIGHT = 200;
    private static final String EMPTY_STRING = "";
    public static final float MIN_PROGRESS_WIDTH = 200f;
    public static final float MIN_PROGRESS_HEIGHT = 40f;
    public static final int FINAL_ANIMATION_FACTOR_VALUE = 100;
    public static final int DEFAULT_ANIMATION_DURATION_MILLISECONDS = 1000;

    private RectF mRectF;
    private Path mShapePath;
    private Rect mTmpRect;
    private Paint mBackgroundPaint;
    private Paint mTrackedTimePaint;
    private Paint mCurrentUnTrackPaint;
    private Paint mMoreTrackedTimePaint;
    private Paint mNeedTrackTimePaint;
    private Paint mMoreCurrentDayTrackedTimePaint;
    private Paint mMoreThanMonthTrackedPaint;
    private Paint mLinePaint;
    private Paint mLabelPaint;
    private Paint mTextPaint;
    private Paint mStrokePaint;
    private Paint mCloudBlockPaint;

    private float startPointX = 0;
    private float startPointY = 0;

    private int mBarType;
    private int mAnimationDuration;
    private float mProgressHeight = MIN_PROGRESS_HEIGHT;
    private float mProgressWidth = MIN_PROGRESS_WIDTH;
    private float mViewHeight;
    private float mViewWidth;
    private boolean isLabelUnder;
    private boolean isAnimated;

    private int mTrackedTimeColor;
    private int mUnTrackedTimeColor;
    private int mNeedTrackTimeColor;
    private int mDayNeedTrackTimeColor;
    private int mMoreTrackedTimeColor;
    private int mMoreCurrentDayTrackedTimeColor;
    private int mMoreThanMonthTrackedTimeColor;
    private int mTextColor;
    private int mStrokeColor;

    private long mTrackedSeconds = 259200;
    private long mRequiredSeconds = 576000;
    private long mRequiredSecondsRelativeToday = EIGHT_HOURS_WORK_DAY;
    private long mStandardDayWorkDurationSeconds = EIGHT_HOURS_WORK_DAY;
    private String mBarTitle = "";

    private long mTrackedBeforeTodaySeconds = 0;
    private long mNeedTrackSeconds = 0;
    private long mCurrentTrackedSeconds = 0;
    private long mMoreTrackedSeconds = 0;
    private long mCurrentNeedTrackSeconds = 0;
    private long mUnTrackedTime = 0;
    private long mMoreThanMontTrackedSeconds = 0;

    private ObjectAnimator mAnimator;
    private float animationFactor = FINAL_ANIMATION_FACTOR_VALUE;
    private boolean isCloudUp = false;

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

    public void setTrackedSeconds(int mTrackedSeconds) {
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

    public int getMoreThanMonthTrackedTimeColor() {
        return mMoreThanMonthTrackedTimeColor;
    }

    public void setMoreThanMonthTrackedTimeColor(int color) {
        this.mMoreThanMonthTrackedTimeColor = color;
        invalidate();
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

    public String getBarTitle() {
        return mBarTitle;
    }

    public void setBarTitle(String mBarTitle) {
        invalidate();
        this.mBarTitle = mBarTitle;
    }

    public boolean isLabelUnder() {
        return isLabelUnder;
    }

    /**
     * Set possibility display labels for indicator under bar.
     *
     * @param labelUnder - if true try show under bar, else show information inside bar.
     */
    public void setLabelUnder(boolean labelUnder) {
        isLabelUnder = labelUnder;
        invalidate();
    }

    private float getAnimationFactor() {
        return animationFactor;
    }

    private void setAnimationFactor(float mAnimationFactor) {
        invalidate();
        this.animationFactor = mAnimationFactor;
    }

    private void init(AttributeSet attrs) {

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TimeSheetBar, 0, 0);

        mTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_trackedTimeColor, TRACKED_TIME_DEFAULT_COLOR);
        mUnTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_unTrackedTimeColor, REQUIRED_TRACK_DEFAULT_COLOR);
        mDayNeedTrackTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_dayNeedTrackTimeColor, CURRENT_TODAY_NEED_TRACK_DEFAULT_COLOR);
        mNeedTrackTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_additionalNeedTrackTimeColor, NEED_TRACK_DEFAULT_COLOR);
        mMoreTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_moreTrackedTimeColor, MORE_TRACKED_DEFAULT_COLOR);
        mMoreCurrentDayTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_moreCurrentDayTrakedTimeColor, CURRENT_TODAY_TRACKED_DEFAULT_COLOR);
        mMoreThanMonthTrackedTimeColor = typedArray.getColor(R.styleable.TimeSheetBar_moreThanMontTrackedTimeColor, MORE_THAN_MONTH_TRACKED_DEFAULT_COLOR);
        mTextColor = typedArray.getColor(R.styleable.TimeSheetBar_barTextColor, TEXT_DEFAULT_COLOR);
        mStrokeColor = typedArray.getColor(R.styleable.TimeSheetBar_barStrokeColor, STROKE_DEFAULT_COLOR);
        mProgressHeight = typedArray.getDimension(R.styleable.TimeSheetBar_maxBarHeight, MIN_PROGRESS_HEIGHT);
        isLabelUnder = typedArray.getBoolean(R.styleable.TimeSheetBar_isLabelUnder, false);
        mBarTitle = typedArray.getString(R.styleable.TimeSheetBar_barTitle);
        isAnimated = typedArray.getBoolean(R.styleable.TimeSheetBar_isAnimated, false);
        mAnimationDuration = typedArray.getInt(R.styleable.TimeSheetBar_animationDuration, DEFAULT_ANIMATION_DURATION_MILLISECONDS);

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
        mMoreThanMonthTrackedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMoreThanMonthTrackedPaint.setStyle(Paint.Style.FILL);
        mMoreThanMonthTrackedPaint.setColor(mMoreThanMonthTrackedTimeColor);

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

        mCloudBlockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCloudBlockPaint.setStyle(Paint.Style.FILL);
        mCloudBlockPaint.setColor(Color.YELLOW);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mProgressHeight / 2);

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(1f);
        mStrokePaint.setColor(mStrokeColor);

//        mRectF = new RectF(0, heightRec, widthRec, 0);
        mRectF = new RectF(0, 0, 0, 0);
        mTmpRect = new Rect();
        mShapePath = new Path();

        animated();
    }

    private void animated() {
        if (!isAnimated) {
            return;
        }

        mAnimator = ObjectAnimator.ofFloat(this, "animationFactor", FINAL_ANIMATION_FACTOR_VALUE);
        mAnimator.setDuration(mAnimationDuration);
        mAnimator.start();
    }

    private void calculateTimersSeconds() {
        long trackedDiffTime = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;
        mTrackedBeforeTodaySeconds = mTrackedSeconds;
        mUnTrackedTime = mRequiredSeconds - mRequiredSecondsRelativeToday;

        if (trackedDiffTime > mTrackedSeconds) {
            mCurrentNeedTrackSeconds = mStandardDayWorkDurationSeconds;
            mNeedTrackSeconds = trackedDiffTime - mTrackedSeconds;
        } else if (trackedDiffTime < mTrackedSeconds && mTrackedSeconds < mRequiredSecondsRelativeToday) {
            mTrackedBeforeTodaySeconds = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;
            mCurrentTrackedSeconds = mTrackedSeconds - mTrackedBeforeTodaySeconds;
            mCurrentNeedTrackSeconds = mStandardDayWorkDurationSeconds - mCurrentTrackedSeconds;
        } else if (mTrackedSeconds > mRequiredSecondsRelativeToday && mTrackedSeconds < mRequiredSeconds) {
            mTrackedBeforeTodaySeconds = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;
            mCurrentTrackedSeconds = mStandardDayWorkDurationSeconds;
            mMoreTrackedSeconds = mTrackedSeconds - mRequiredSecondsRelativeToday;
            mCurrentNeedTrackSeconds = 0;
            mUnTrackedTime = mUnTrackedTime - mMoreTrackedSeconds;
        } else if (mTrackedSeconds > mRequiredSeconds) {
            mTrackedBeforeTodaySeconds = mRequiredSecondsRelativeToday - mStandardDayWorkDurationSeconds;
            mCurrentTrackedSeconds = mStandardDayWorkDurationSeconds;
            mMoreTrackedSeconds = mRequiredSeconds - mRequiredSecondsRelativeToday;
            mCurrentNeedTrackSeconds = 0;
            mUnTrackedTime = 0;
            mMoreThanMontTrackedSeconds = mTrackedSeconds - mRequiredSeconds;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        calculateTimersSeconds();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int selectedHeight;
        int selectedWidth;

        int paddingY = getPaddingBottom() + getPaddingTop();
        int paddingX = getPaddingLeft() + getPaddingRight();

        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                selectedWidth = width;
                break;
            case MeasureSpec.AT_MOST:
                selectedWidth = (int) Math.min(width, MIN_PROGRESS_WIDTH);
                break;
            case MeasureSpec.UNSPECIFIED:
                selectedWidth = width;
                break;
            default:
                selectedWidth = width;
        }

        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
//                selectedHeight = height;
                selectedHeight = (int) Math.min(height, getProgressHeight() * 3 + paddingY);
                break;
            case MeasureSpec.AT_MOST:
                selectedHeight = (int) Math.min(height, getProgressHeight() * 3);
                break;
            case MeasureSpec.UNSPECIFIED:
                selectedHeight = (int) Math.min(height, getProgressHeight() * 3 + paddingY);
//                if (selectedHeight < getProgressHeight() * 3 + paddingY) {
//                    selectedHeight = (int) (getProgressHeight() * 3 + paddingY);
//                }
                break;
            default:
                selectedHeight = height;
                break;
        }

        mViewHeight = selectedHeight - paddingY;
        mViewWidth = selectedWidth - paddingX;

        if (mViewHeight < getProgressHeight()) {
            setProgressHeight(mViewHeight);
        }

        setMeasuredDimension(selectedWidth, selectedHeight);
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
        float moreThanMonthTrackedBlockWidth = 0;


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
        } else if (mTrackedSeconds > mRequiredSecondsRelativeToday && mTrackedSeconds < mRequiredSeconds) {
            trackedBlockWidth = calculateBlockWidth(mTrackedSeconds - (mTrackedSeconds - trackedDiffTime));
            moreCurrentDayTrackedBlockWidth = calculateBlockWidth(mStandardDayWorkDurationSeconds);
            moreTrackedTimeBlockWidth = calculateBlockWidth(mTrackedSeconds - mRequiredSecondsRelativeToday);
            unTrackedTimeBlockWidth = calculateBlockWidth(mRequiredSeconds - mTrackedSeconds);
            needCurrentTodayTrackBlockWidth = 0;
        } else if (mTrackedSeconds > mRequiredSeconds) {
            trackedBlockWidth = calculateBlockWidth(mTrackedSeconds - (mTrackedSeconds - trackedDiffTime));
            moreCurrentDayTrackedBlockWidth = calculateBlockWidth(mStandardDayWorkDurationSeconds);
            moreTrackedTimeBlockWidth = calculateBlockWidth(mRequiredSeconds - mRequiredSecondsRelativeToday);
            unTrackedTimeBlockWidth = 0;
            needCurrentTodayTrackBlockWidth = 0;
            moreThanMonthTrackedBlockWidth = calculateBlockWidth(mTrackedSeconds - mRequiredSeconds);
        }

        drawViewBorder(canvas);

        attachBarTitle(canvas);

        switch (BarType.getType(mBarType)) {
            case DIVIDED:
                float shiftPosition = getPaddingLeft();
                shiftPosition = drawSingleBlock(canvas, shiftPosition, trackedBlockWidth, mTrackedTimePaint);
                attachText(canvas, getPaddingLeft(), trackedBlockWidth, mTrackedBeforeTodaySeconds, trackedBlockWidth);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, needTrackBlockWidth, mNeedTrackTimePaint);
                attachText(canvas, shiftPosition - needTrackBlockWidth, needTrackBlockWidth, mNeedTrackSeconds, needTrackBlockWidth);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, moreCurrentDayTrackedBlockWidth, mMoreCurrentDayTrackedTimePaint);
                attachText(canvas, shiftPosition - moreCurrentDayTrackedBlockWidth, moreCurrentDayTrackedBlockWidth, mCurrentTrackedSeconds, moreCurrentDayTrackedBlockWidth);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, needCurrentTodayTrackBlockWidth, mCurrentUnTrackPaint);
                attachText(canvas, shiftPosition - needCurrentTodayTrackBlockWidth, needCurrentTodayTrackBlockWidth, mCurrentNeedTrackSeconds, needCurrentTodayTrackBlockWidth);
//                attachLable(canvas, shiftPosition, needCurrentTodayTrackBlockWidth, mCurrentNeedTrackSeconds);
//                attachText(canvas, shiftPosition, moreTrackedTimeBlockWidth, mMoreTrackedSeconds, moreTrackedTimeBlockWidth );
                shiftPosition = drawSingleBlock(canvas, shiftPosition, moreTrackedTimeBlockWidth, mMoreTrackedTimePaint);
                attachText(canvas, shiftPosition - moreTrackedTimeBlockWidth, moreTrackedTimeBlockWidth, mMoreTrackedSeconds, moreTrackedTimeBlockWidth);
                shiftPosition = drawSingleBlock(canvas, shiftPosition, unTrackedTimeBlockWidth, mBackgroundPaint);
                attachText(canvas, shiftPosition - unTrackedTimeBlockWidth, unTrackedTimeBlockWidth, mUnTrackedTime, unTrackedTimeBlockWidth);
                drawSingleBlock(canvas, shiftPosition, moreThanMonthTrackedBlockWidth, mMoreThanMonthTrackedPaint);
                attachText(canvas, shiftPosition, moreThanMonthTrackedBlockWidth, mMoreThanMontTrackedSeconds, moreThanMonthTrackedBlockWidth);
                break;
            case OVERLAID:
                float trackedBlockWidthFromStart = trackedBlockWidth;
                float needTrackBlockWidthFromStart = trackedBlockWidthFromStart + needTrackBlockWidth;
                float moreCurrentDayTrackedBlockWidthFromStart = needTrackBlockWidthFromStart + moreCurrentDayTrackedBlockWidth;
                float needCurrentTodayTrackBlockWidthFromStart = moreCurrentDayTrackedBlockWidthFromStart + needCurrentTodayTrackBlockWidth;
                float moreTrackedTimeBlockWidthFromStart = needCurrentTodayTrackBlockWidthFromStart + moreTrackedTimeBlockWidth;

                drawSingleBlock(canvas, getPaddingLeft(), mViewWidth, mMoreThanMonthTrackedPaint);
                drawSingleBlock(canvas, getPaddingLeft(), mViewWidth - moreThanMonthTrackedBlockWidth, mBackgroundPaint);
                drawSingleBlock(canvas, getPaddingLeft(), moreTrackedTimeBlockWidthFromStart, mMoreTrackedTimePaint);
                drawSingleBlock(canvas, getPaddingLeft(), needCurrentTodayTrackBlockWidthFromStart, mCurrentUnTrackPaint);
                drawSingleBlock(canvas, getPaddingLeft(), moreCurrentDayTrackedBlockWidthFromStart, mMoreCurrentDayTrackedTimePaint);
                drawSingleBlock(canvas, getPaddingLeft(), needTrackBlockWidthFromStart, mNeedTrackTimePaint);
                drawSingleBlock(canvas, getPaddingLeft(), trackedBlockWidthFromStart, mTrackedTimePaint);

                attachText(canvas, getPaddingLeft(), trackedBlockWidth, mTrackedBeforeTodaySeconds, trackedBlockWidth);
                attachText(canvas, getPaddingLeft() + trackedBlockWidth, needTrackBlockWidth, mNeedTrackSeconds, needTrackBlockWidth);
                attachText(canvas, trackedBlockWidth + getPaddingLeft(), moreCurrentDayTrackedBlockWidth, mCurrentTrackedSeconds, moreCurrentDayTrackedBlockWidth);
                attachText(canvas, getPaddingLeft() + trackedBlockWidth + needTrackBlockWidth + moreCurrentDayTrackedBlockWidth, needCurrentTodayTrackBlockWidth, mCurrentNeedTrackSeconds, needCurrentTodayTrackBlockWidth);
                attachText(canvas, trackedBlockWidth + moreCurrentDayTrackedBlockWidth + getPaddingLeft(), moreTrackedTimeBlockWidth, mMoreTrackedSeconds, moreTrackedTimeBlockWidth);
                attachText(canvas, trackedBlockWidth + needTrackBlockWidth + moreCurrentDayTrackedBlockWidth + needCurrentTodayTrackBlockWidth + moreTrackedTimeBlockWidth + getPaddingLeft(), unTrackedTimeBlockWidth, mUnTrackedTime, unTrackedTimeBlockWidth);
                attachText(canvas, trackedBlockWidth + needTrackBlockWidth + moreCurrentDayTrackedBlockWidth + needCurrentTodayTrackBlockWidth + moreTrackedTimeBlockWidth + unTrackedTimeBlockWidth + getPaddingLeft(), moreThanMonthTrackedBlockWidth, mMoreThanMontTrackedSeconds, moreThanMonthTrackedBlockWidth);
                break;
        }

        canvas.drawPath(new Path(), mBackgroundPaint);
    }

    private void attachBarTitle(Canvas canvas) {
        if (mViewHeight / 2.5 < mProgressHeight) {
            return;
        }

        if (mBarTitle == null) {
            return;
        }

        mTextPaint.setTextSize(mProgressHeight / 2);
        mTextPaint.getTextBounds(mBarTitle.toUpperCase(), 0, mBarTitle.length(), mTmpRect);
        float textWidth = mTmpRect.right - mTmpRect.left;
        float textHeight = mTmpRect.bottom - mTmpRect.top;

        if (textWidth > mViewWidth) {
            return;
        }

        float startX = (mViewWidth - textWidth) / 2;
        float startY = ((mViewHeight / 3) - textHeight) / 2 + textHeight;

        mTextPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(mBarTitle.toUpperCase(), startX + getPaddingLeft(), startY + getPaddingTop(), mTextPaint);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawViewBorder(Canvas canvas) {
        canvas.drawRoundRect((float) getPaddingLeft(), (float) getPaddingTop(), (float) getWidth() - getPaddingRight(), getHeight() - (float) getPaddingBottom(), 10, 10, mStrokePaint);
    }

    private void attachText(Canvas canvas, float startBlockX, float endBlockX, long durationSeconds, float blockWidth) {
        if (mProgressHeight < 40) {
            return;
        }
        float textStartPoint;
        mTextPaint.setTextSize(mProgressHeight / 2);
        String text = String.valueOf(durationSeconds / ONE_HOUR) + " h";
        Rect r = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), r);
        float textHeight = r.bottom - r.top;
        float textWidth = r.right - r.left;

        if (textWidth > blockWidth) {

            drawAdditionalCloud(canvas, startBlockX + (endBlockX / 2), mViewHeight / 2 + getPaddingTop(), durationSeconds);
//            drawAdditionalCloud(canvas, 70, 70, durationSeconds);
            return;
        }

        if (mViewHeight / 2 >= mProgressHeight && isLabelUnder) {
            textStartPoint = ((mViewHeight / 6) * 5) + textHeight / 2 + getPaddingTop();
        } else {
            textStartPoint = mViewHeight / 2 + textHeight / 2 + getPaddingTop();
        }

        mTextPaint.setTextAlign(Paint.Align.CENTER);

        if (isAnimated && animationFactor < FINAL_ANIMATION_FACTOR_VALUE) {
            return;
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

    private float drawSingleBlock(final Canvas canvas, float startPoint, float blockWidth, final Paint mTrackedTimePaint) {
        mRectF.left = startPoint;
        mRectF.top = (mViewHeight / 2) - (getProgressHeight() / 2) + getPaddingTop();

        float rightBorder = startPoint;
        rightBorder = startPoint + blockWidth * (animationFactor / 100);

        mRectF.right = rightBorder;


        mRectF.bottom = mRectF.top + getProgressHeight();

        drawSingleBlock(canvas, mRectF, mTrackedTimePaint);
        drawSingleBlock(canvas, mRectF, mStrokePaint);

        return blockWidth + startPoint;
    }

    private float calculateBlockWidth(long pValue) {
        return mViewWidth * (pValue / (float) Math.max(mRequiredSeconds, mTrackedSeconds));
    }

    private void drawSingleBlock(Canvas canvas, RectF block, Paint paint) {
        canvas.drawRoundRect(block, ROUND_X, ROUND_Y, paint);
    }

    private void drawAdditionalCloud(Canvas canvas, float blockCenterX, float centerY, long durationSeconds) {
        if (mViewHeight / 3 < mProgressHeight || durationSeconds == 0 || animationFactor < FINAL_ANIMATION_FACTOR_VALUE) {
            return;
        }

        float attachPointY = centerY;

        float centerX = blockCenterX;
        float cloudPadding = (mProgressHeight) / 10;
        float cloudHeight = cloudPadding * 8;

        if (isCloudUp) {
            attachPointY += mProgressHeight / 2 + cloudPadding * 2;
        } else {
            attachPointY -= mProgressHeight / 2;
        }

        mShapePath.moveTo(blockCenterX, attachPointY);
        mShapePath.lineTo(blockCenterX - cloudPadding, attachPointY - cloudPadding);
        mShapePath.lineTo(blockCenterX, attachPointY - cloudPadding * 2);
        mShapePath.lineTo(blockCenterX + cloudPadding, attachPointY - cloudPadding);
        mShapePath.lineTo(blockCenterX, attachPointY);
        mShapePath.close();

        String text = String.valueOf(durationSeconds / ONE_HOUR) + " h";
        Rect r = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), r);
        float textHeight = r.bottom - r.top;
        float textWidth = r.right - r.left;

        mTextPaint.setTextAlign(Paint.Align.LEFT);

        float startCloudX = centerX - textWidth * 0.75f;
        float endCloudX = centerX + textWidth * 0.75f;

        if(startCloudX < 0 ){
            endCloudX = endCloudX - startCloudX + getPaddingLeft();
            startCloudX = getPaddingLeft();
        }

        if(endCloudX > mViewWidth + getPaddingLeft()){
            startCloudX = mViewWidth + getPaddingLeft() - (endCloudX - startCloudX) - cloudPadding;
            endCloudX = mViewWidth + getPaddingLeft();
        }

        mRectF.left = startCloudX;
        mRectF.right = endCloudX;

        if (isCloudUp) {
            mRectF.top = centerY + mProgressHeight / 2 + cloudPadding;
            mRectF.bottom = centerY + mProgressHeight / 2 + cloudPadding + cloudHeight;
        } else {
            mRectF.top = centerY - mProgressHeight / 2 - cloudPadding - cloudHeight;
            mRectF.bottom = centerY - mProgressHeight / 2 - cloudPadding;
        }

        canvas.drawPath(mShapePath, mCloudBlockPaint);
        canvas.drawRoundRect(mRectF, ROUND_X, ROUND_Y, mCloudBlockPaint);

        float textStartPoint;

        if (isCloudUp) {
            textStartPoint = centerY + mProgressHeight / 2 + cloudPadding + (cloudHeight - textHeight) / 2 + textHeight;
        } else {
            textStartPoint = centerY - mProgressHeight / 2 - cloudPadding - (cloudHeight - textHeight) / 2;
        }
        canvas.drawText(String.valueOf(durationSeconds / ONE_HOUR) + " h",
                startCloudX + (endCloudX - startCloudX) / 2 - textWidth / 2,
                textStartPoint,
                mTextPaint);

        isCloudUp = !isCloudUp;
    }

    @Override
    public int getPaddingTop() {
        int paddingY = super.getPaddingTop() + super.getPaddingBottom();
        if(paddingY > getHeight() - mProgressHeight){
            return  0;
        }
        return super.getPaddingTop();
    }

    @Override
    public int getPaddingBottom() {
        int paddingY = super.getPaddingTop() + super.getPaddingBottom();
        if(paddingY > getHeight() - mProgressHeight){
            return  0;
        }
        return super.getPaddingBottom();
    }
}
