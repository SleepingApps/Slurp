package it.sofk.slurp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.sofk.slurp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.navViewMain.setOnNavigationItemSelectedListener(this);
        NavHostFragment navFragMain = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_frag_main);
        assert navFragMain != null;
        navController = navFragMain.getNavController();

        setContentView(binding.getRoot());
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