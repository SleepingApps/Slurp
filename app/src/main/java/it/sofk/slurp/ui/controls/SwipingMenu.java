package it.sofk.slurp.ui.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SwipingMenu extends View implements View.OnTouchListener {

    private String[] menus;
    private int[] textSizes, textBoundaries;
    private int width, columnWidth;
    private int margin = 6;
    private volatile int index = 0;
    private Paint paint;

    public SwipingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(24);
        paint.setFakeBoldText(true);

        this.setOnTouchListener(this);
    }

    public void setMenu(String[] menus) {
        this.menus = menus;
        requestLayout();
    }

    public void selectMenu(int index) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (menus == null || getWidth() == 0) return;
        updateBoundaries();
        int x1 = columnWidth * index;
        int x2 = x1 + columnWidth;

        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(0, 0, width, getHeight(), 90, 90, paint);
        paint.setColor(Color.DKGRAY);
        selectMenu(0);
        canvas.drawRoundRect(x1, margin, x2, getHeight()-margin, 90, 90, paint);
        paint.setColor(Color.WHITE);
        for (int i = 0; i < menus.length; i++) {
            canvas.drawText(menus[i], textBoundaries[i], (int)(getHeight()/1.6), paint);
        }

    }

    private void updateBoundaries() {
        width = getWidth();

        textSizes = new int[menus.length];
        for (int i = 0; i < menus.length; i++) {
            textSizes[i] = (int)paint.measureText(menus[i]);
        }

        columnWidth = width / menus.length;
        textBoundaries = new int[menus.length];
        for (int i = 0, position = 0; i < textBoundaries.length; i++) {
            position += (columnWidth - textSizes[i]) / 2;
            textBoundaries[i] = position;
            position += columnWidth/2 + textSizes[i]/2;
        }
    }

    public void animate1() {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

        for (int i = 0; i < menus.length ; i--) {
            if (x > columnWidth * i
                    && x < columnWidth * (i+1)) {
                index = i;
            }
        }
        return true;
    }
}
