package it.sofk.slurp.ui.controls;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.annotation.NonNull;

import it.sofk.slurp.R;

public class ProgressRing extends View {

    private Paint foreground;
    private Paint outOfBounds;
    private Paint progress;

    private float currentAngle;
    private float maxProgress;
    private int strokeWidth;

    public ProgressRing(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isOutOfBounds()) {
            canvas.drawArc(strokeWidth, strokeWidth,
                    getWidth()-strokeWidth, getHeight()-strokeWidth,
                    0, 360, false, outOfBounds);
        }
        else {
            canvas.drawArc(strokeWidth, strokeWidth,
                    getWidth()-strokeWidth, getHeight()-strokeWidth,
                    0, 360, false, foreground);

            canvas.drawArc(strokeWidth, strokeWidth,
                    getWidth()-strokeWidth, getHeight()-strokeWidth,
                    0, currentAngle, false, progress);
        }
    }

    private void initialise() {

        this.foreground = new Paint();
        this.foreground.setColor(Color.BLACK);
        this.outOfBounds = new Paint();
        this.outOfBounds.setColor(Color.RED);
        this.progress = new Paint();
        this.progress.setColor(Color.BLUE);

        this.maxProgress = 100;
        this.strokeWidth = 12;
    }

    private boolean isOutOfBounds() {
        return currentAngle > 360;
    }

    public void initialise(Activity activity,
                           int maxProgress) {
        this.foreground = new Paint();
        this.foreground.setColor(getResources().getColor(R.color.purple_200, activity.getTheme()));
        this.outOfBounds = new Paint();
        this.outOfBounds.setColor(Color.RED);
        this.progress = new Paint();
        this.progress.setColor(getResources().getColor(R.color.purple_500, activity.getTheme()));

        setPaintStyle();

        this.maxProgress = maxProgress;
        this.strokeWidth = 12;
    }

    private void setPaintStyle() {
        progress.setAntiAlias(true);
        progress.setStyle(Paint.Style.STROKE);
        progress.setStrokeWidth(strokeWidth);

        foreground.setAntiAlias(true);
        foreground.setStyle(Paint.Style.STROKE);
        foreground.setStrokeWidth(strokeWidth);

        outOfBounds.setAntiAlias(true);
        outOfBounds.setStyle(Paint.Style.STROKE);
        outOfBounds.setStrokeWidth(strokeWidth);
        this.requestLayout();
    }

    public void setProgress(float progress, boolean animation) {
        float newAngle = (float)(360.0 / maxProgress) * progress;

        if (!animation) {
            this.currentAngle = newAngle;
            this.requestLayout();
            return;
        }

        ProgressAnimation progressAnimation =  new ProgressAnimation(this.currentAngle, newAngle, 0);
        this.startAnimation(progressAnimation);
    }

    protected void setAngle(float angle) {
        this.currentAngle = angle;
    }

    protected float getAngle() {
        return currentAngle;
    }

    protected class ProgressAnimation extends Animation {

        private final int duration;
        private final float oldAngle;
        private final float newAngle;

        public ProgressAnimation(float oldAngle, float newAngle, int duration) {
            this.oldAngle = oldAngle;
            this.newAngle = newAngle;
            this.duration = duration;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation transformation) {
            float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);
            this.setDuration(duration);
            ProgressRing.this.setAngle(newAngle);
            ProgressRing.this.requestLayout();
        }
    }
}