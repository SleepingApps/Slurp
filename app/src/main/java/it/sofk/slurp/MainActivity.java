package it.sofk.slurp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;

import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.databinding.ActivityMainBinding;
import it.sofk.slurp.databinding.PopupChooseIntakeBinding;
import it.sofk.slurp.enumeration.Frequency;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    NavController navController;
    ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.navViewMain.setOnNavigationItemSelectedListener(this);
        NavHostFragment navFragMain = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_frag_main);
        assert navFragMain != null;
        navController = navFragMain.getNavController();

        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getFoodTypes().observe(this, foodTypes -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                for(FoodType foodType : foodTypes) viewModel.insert(new FoodInstance(foodType.getName(), LocalDate.now()));
        });
/*
        PopupWindow popupWindow = new PopupWindow();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        PopupChooseIntakeBinding binding = PopupChooseIntakeBinding.inflate(inflater);
        popupWindow.setContentView(binding.getRoot());
        popupWindow.showAtLocation(binding.getRoot(), Gravity.CENTER, 0, 0);
*/
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_food:
                navController.navigate(R.id.foodFragment);
                break;
            case R.id.menu_profile:
                navController.navigate(R.id.profileFragment);
                break;
        }
        return true;
    }
}