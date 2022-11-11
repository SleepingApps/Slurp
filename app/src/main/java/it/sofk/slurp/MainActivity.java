package it.sofk.slurp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.databinding.ActivityMainBinding;
import it.sofk.slurp.ui.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    NavController navController;
    MainActivityViewModel viewModel;
    ViewModel viewModelShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.navViewMain.setOnNavigationItemSelectedListener(this);
        NavHostFragment navFragMain = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_frag_main);
        assert navFragMain != null;
        navController = navFragMain.getNavController();

        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModelShared = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getFoodInstances().observe(this, foodInstances -> {
            if(foodInstances.size() == 0) {
                viewModelShared.setWeekStarted(false);
                if(binding.navViewMain.getMenu().getItem(0).isChecked())
                    navController.navigate(R.id.startWeekFragment);
            }
        });
        viewModelShared.getWeekStarted().observe(this, weekStarted -> {
            if (binding.navViewMain.getMenu().getItem(0).isChecked()){
                if (weekStarted) {
                    navController.navigate(R.id.foodFragment);
                } else {
                    navController.navigate(R.id.startWeekFragment);
                }
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_food:
                if(viewModelShared.isWeekStarted())
                    navController.navigate(R.id.foodFragment);
                else
                    navController.navigate(R.id.startWeekFragment);
                break;
            case R.id.menu_profile:
                navController.navigate(R.id.profileFragment);
                break;
        }
        return true;
    }

}