package com.nama_gatsuo.dreamplan.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.nama_gatsuo.dreamplan.R;
import com.nama_gatsuo.dreamplan.model.Project;

import org.joda.time.DateTime;

import java.util.Calendar;


/**
 * Created by nagamatsuayumu on 15/03/28.
 */
public class CalendarView extends View {
    private DateTime minDate;
    private DateTime maxDate;

    private Paint mCharPaint;
    private Paint mFillPaint;
    private Paint mLinePaint;
    private Paint mSpecPaint;

    private float scale;

    private int nx;
    private int dx;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scale = getContext().getResources().getDisplayMetrics().density;

        mCharPaint = new Paint();
        mCharPaint.setAntiAlias(true);
        mCharPaint.setColor(Color.BLACK);
        mCharPaint.setTextSize(8.f * scale);

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
        canvas.drawColor(Color.rgb(255, 255, 255));
        int height = canvas.getHeight();
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < nx; i++) {
            int dayOfMonth = minDate.getDayOfMonth();
            int dayOfWeek = minDate.getDayOfWeek();
            int dayOfToday = minDate.getDayOfYear();


            // 显示月份
            if (dayOfMonth == 1) {
                //月份数字
                canvas.drawText(String.valueOf(minDate.getMonthOfYear()), dx*i+3*scale, height/2-3*scale, mCharPaint);
                //月份分割线
                canvas.drawLine(dx * i, 0, dx * i, height / 2, mLinePaint);
            }
            if (dayOfToday == calendar.get(Calendar.DAY_OF_YEAR)) {
                canvas.drawRect(dx * i, height / 2, dx * (i + 1), height, mSpecPaint);
            }

            // 日期里周末的颜色特殊
            if (dayOfWeek == 6 || dayOfWeek == 7) {
                canvas.drawRect(dx*i, height/2, dx*(i+1), height, mFillPaint);
            }

            // 显示日期（哪一天）
            canvas.drawText(String.valueOf(dayOfMonth), dx*i+10, height-10, mCharPaint);

            // 日期分割线
            canvas.drawLine(dx*i, height/2, dx*i, height, mLinePaint);
            canvas.drawLine(dx*i, height/2, dx*(i+1)-1, height/2, mLinePaint);


            minDate = minDate.plusDays(1);
        }
    }
}
