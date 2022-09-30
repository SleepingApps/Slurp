package it.sofk.slurp.ui.extra;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.animation.LinearInterpolator;

import androidx.constraintlayout.widget.ConstraintLayout;

import it.sofk.slurp.databinding.FoodItemBinding;

public class FoodItemResizer {

    private FoodItemBinding binding;
    private static int COMPACT_SIZE = 0;
    private int expandedSize = -1, compactSize = -1;
    private boolean expanded;

    public FoodItemResizer(FoodItemBinding binding) {
        this.binding = binding;
        FoodItemResizer.COMPACT_SIZE = getDP(148);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void expand() {
        if (compactSize == -1)
            compactSize = binding.getRoot().getHeight();

        int fullSize = getItemCompleteSize();

        ValueAnimator animator = getSizeAnimator(binding.getRoot().getHeight(), fullSize);
        AnimatorSet set = getAnimatorSet(animator);
        set.start();
        binding.getRoot().setElevation(6);
        expanded = true;

        ValueAnimator alphaAnimator = getAlphaAnimator(0.0f, 1.0f);
        AnimatorSet alphaSet = getAnimatorSet(alphaAnimator);
        alphaSet.start();
    }

    public void shrink() {
        ValueAnimator sizeAnimator = getSizeAnimator(binding.getRoot().getHeight(), compactSize);
        AnimatorSet sizeSet = getAnimatorSet(sizeAnimator);
        sizeSet.start();
        binding.getRoot().setElevation(1);
        expanded = false;

        ValueAnimator alphaAnimator = getAlphaAnimator(1.0f, 0.0f);
        AnimatorSet alphaSet = getAnimatorSet(alphaAnimator);
        alphaSet.start();
    }

    private int getItemCompleteSize() {
        int fullSize = binding.ellipse.getMeasuredHeight()
                + binding.textView8.getMeasuredHeight() + 45;

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) binding.textView8.getLayoutParams();
        fullSize += params.topMargin + params.bottomMargin;

        return fullSize;
    }

    public int getDP(int pixels) {
        Resources resources = binding.getRoot().getContext().getResources();
        int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                pixels,
                resources.getDisplayMetrics());
        return dp;
    }

    private ValueAnimator getSizeAnimator(int startSize, int finalSize) {
        ValueAnimator animator = ValueAnimator.ofInt(startSize, finalSize).setDuration(200);
        animator.addUpdateListener(animation -> {
            Integer value = (Integer) animation.getAnimatedValue();
            binding.getRoot().getLayoutParams().height = value;
            binding.getRoot().requestLayout();
        });
        return animator;
    }

    private ValueAnimator getAlphaAnimator(float startAlpha, float finalAlpha) {
        ValueAnimator animator = ValueAnimator.ofFloat(startAlpha, finalAlpha).setDuration(80);
        animator.addUpdateListener(animation -> {
            Float value = (Float) animation.getAnimatedValue();
            binding.textView8.setAlpha(value);
            binding.textView8.requestLayout();
        });
        return animator;
    }

    private AnimatorSet getAnimatorSet(Animator animator) {
        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        set.setInterpolator(new LinearInterpolator());
        return set;
    }
}