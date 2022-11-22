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

    private final FoodItemBinding binding;
    public int compactSize = -1;
    public int expandedSize = -1;
    private boolean expanded;

    public FoodItemResizer(FoodItemBinding binding) {
        this.binding = binding;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void expand() {
        if (compactSize == -1)
            compactSize = getDP(148);

        if (expandedSize == -1)
            expandedSize = getItemCompleteSize();


        ValueAnimator animator = getSizeAnimator(binding.getRoot().getHeight(), expandedSize);
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

    public int getItemCompleteSize() {
        int fullSize = compactSize;
        {
            fullSize += binding.foodDesc.getLineCount() * binding.foodDesc.getLineHeight();
            ConstraintLayout.LayoutParams foodDescLayoutParams = (ConstraintLayout.LayoutParams) binding.foodDesc.getLayoutParams();
            fullSize += foodDescLayoutParams.topMargin + foodDescLayoutParams.bottomMargin;
        }

        return fullSize;
    }

    private ValueAnimator getSizeAnimator(int startSize, int finalSize) {
        ValueAnimator animator = ValueAnimator.ofInt(startSize, finalSize).setDuration(200);
        animator.addUpdateListener(animation -> {
            binding.getRoot().getLayoutParams().height = (Integer) animation.getAnimatedValue();
            binding.getRoot().requestLayout();
        });
        return animator;
    }

    private ValueAnimator getAlphaAnimator(float startAlpha, float finalAlpha) {
        ValueAnimator animator = ValueAnimator.ofFloat(startAlpha, finalAlpha).setDuration(80);
        animator.addUpdateListener(animation -> {
            Float value = (Float) animation.getAnimatedValue();
            binding.foodDesc.setAlpha(value);
            binding.foodDesc.requestLayout();
        });
        return animator;
    }

    private AnimatorSet getAnimatorSet(Animator animator) {
        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        set.setInterpolator(new LinearInterpolator());
        return set;
    }

    public int getDP(int pixels) {
        Resources resources = binding.getRoot().getContext().getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                pixels,
                resources.getDisplayMetrics());
    }
}