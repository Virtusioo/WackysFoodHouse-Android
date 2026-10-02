package com.example.wackysfoodhouse.features.navigation;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.wackysfoodhouse.R;
import com.example.wackysfoodhouse.databinding.ActivityNavigationBinding;
import com.example.wackysfoodhouse.features.deals.DealsFragment;
import com.example.wackysfoodhouse.features.food.FoodFragment;
import com.example.wackysfoodhouse.features.home.HomeFragment;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationActivity extends AppCompatActivity {

    public HomeFragment home = new HomeFragment();
    public DealsFragment deals = new DealsFragment();
    public FoodFragment food = new FoodFragment();

    public static void start(AppCompatActivity activity, ImageView logo) {
        Intent intent = new Intent(activity, NavigationActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                activity,
                logo,
                logo.getTransitionName()
        );

        activity.startActivity(intent, options.toBundle());
    }

    public void initFragments() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame, home)
                .hide(home)
                .add(R.id.frame, deals)
                .hide(deals)
                .add(R.id.frame, food)
                .hide(food)
                .commit();
    }

    public Fragment currentFragment;

    public void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right
                );

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        transaction.show(fragment)
                .commit();

        currentFragment = fragment;
    }

    public ActivityNavigationBinding ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ui = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        initFragments();
        switchFragment(home);

        ui.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.home) {
                    switchFragment(home);
                } else if (id == R.id.deals) {
                    switchFragment(deals);
                } else if (id == R.id.food) {
                    switchFragment(food);
                }

                return true;
            }
        });
    }
}