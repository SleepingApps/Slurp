package it.sofk.slurp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Holder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        holder = new Holder();
    }

    class Holder implements BottomNavigationView.OnNavigationItemSelectedListener {

        BottomNavigationView navViewMain;
        NavHostFragment navFragMain;
        NavController navController;

        Holder(){
            navViewMain = findViewById(R.id.nav_view_main);
            navViewMain.setOnNavigationItemSelectedListener(this);

            navFragMain = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_frag_main);
            assert navFragMain != null;
            navController = navFragMain.getNavController();
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
}