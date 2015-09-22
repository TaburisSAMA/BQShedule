package com.nama_gatsuo.dreamplan.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.nama_gatsuo.dreamplan.R;

import org.joda.time.DateTime;

import java.util.Calendar;

/**
 * Created by nagamatsuayumu on 15/03/29.
 */
public class ScaleView extends View {
    private DateTime minDate;
    private DateTime maxDate;

    private Paint mFillPaint;
    private Paint mLinePaint;
    private Paint mSpecPaint;

    private int nx;
    private int dx;

    public ScaleView(Context context) {
        this(context, null);
    }

    public ScaleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.GRAY);

        mFillPaint = new Paint();
        mFillPaint.setColor(getResources().getColor(R.color.scale_dayoff));

        mSpecPaint = new Paint();
        mSpecPaint.setColor(getResources().getColor(R.color.green));
    }

    public void setRange(DateTime minDate, DateTime maxDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    public void setXAxis(int nx, int dx) {
        this.nx = nx;
        this.dx = dx;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        canvas.drawColor(Color.rgb(255, 255, 255));
        int height = canvas.getHeight();

        for (int i = 0; i < nx; i++) {
            int dayOfWeek = minDate.getDayOfWeek();
            int dayOfToday = minDate.getDayOfYear();

            // 周末的颜色区别出来
            if (dayOfWeek == 6) {
                canvas.drawRect(dx*i, 0, dx*(i+2), height, mFillPaint);
            }

            if (dayOfToday == calendar.get(Calendar.DAY_OF_YEAR)) {
                canvas.drawRect(dx*i, 0, dx*(i+1), height, mSpecPaint);
            }

            // 竖线分割
            canvas.drawLine(dx*i, 0, dx*i, height, mLinePaint);

            minDate = minDate.plusDays(1);
        }
    }
}
