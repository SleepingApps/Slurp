package it.sofk.slurp.ui.extra;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import it.sofk.slurp.databinding.FoodItemBinding;

public class FoodItemResizer {

    private FoodItemBinding binding;
    private int originalHeight;
    private final static int MAX_SIZE = 500;
    private boolean expanded;

    public FoodItemResizer(FoodItemBinding binding) {
        this.binding = binding;
        originalHeight = binding.getRoot().getHeight();
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void expand() {
        originalHeight = binding.getRoot().getHeight();
        changeSize(binding.getRoot().getHeight(), MAX_SIZE, 6);
        expanded = true;
    }

    public void shrink() {
        changeSize(binding.getRoot().getHeight(), originalHeight, 1);
        expanded = false;
    }

    private void changeSize(int startSize, int finalSize, int elevation) {
        originalHeight = binding.getRoot().getHeight();
        ValueAnimator slideAnimator = ValueAnimator.ofInt(startSize, finalSize).setDuration(200);
        slideAnimator.addUpdateListener(animation -> {
            Integer value = (Integer) animation.getAnimatedValue();
            binding.getRoot().getLayoutParams().height = value;
            binding.getRoot().requestLayout();
            binding.getRoot().setElevation(elevation);
        });

        AnimatorSet set = new AnimatorSet();
        set.play(slideAnimator);
        set.setInterpolator(new LinearInterpolator());
        set.start();
        expanded = true;
    }
}