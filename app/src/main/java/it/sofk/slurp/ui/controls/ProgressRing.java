package it.sofk.slurp.ui.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.annotation.NonNull;

public class ProgressRing extends View {

    private Paint foreground;
    private Paint outOfBounds;
    private Paint progress;

    private float currentAngle;
    private float maxProgress;
    private int strokeWidth = 25;

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
        this.strokeWidth = 25;
    }

    private boolean isOutOfBounds() {
        return currentAngle > 360;
    }

    public void initialise(int foregroundColor,
                           int progressColor,
                           int outOfBoundsColor,
                           int maxProgress,
                           int strokeWidth) {
        this.foreground = new Paint();
        this.foreground.setColor(foregroundColor);
        this.outOfBounds = new Paint();
        this.outOfBounds.setColor(outOfBoundsColor);
        this.progress = new Paint();
        this.progress.setColor(progressColor);

        setPaintStyle();

        this.maxProgress = maxProgress;
        this.strokeWidth = strokeWidth;
    }

    public void initialise(@NonNull int foregroundColor,
                           @NonNull int progressColor,
                           @NonNull int outOfBoundsColor,
                           float maxProgress) {
        this.foreground = new Paint();
        this.foreground.setColor(foregroundColor);
        this.outOfBounds = new Paint();
        this.outOfBounds.setColor(outOfBoundsColor);
        this.progress = new Paint();
        this.progress.setColor(progressColor);
        setPaintStyle();

        this.maxProgress = maxProgress;
        this.strokeWidth = 25;
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