package it.saimao.bamardictionary.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;

import it.saimao.bamardictionary.R;
import it.saimao.bamardictionary.databinding.ActivityPrimaryBinding;
import it.saimao.bamardictionary.fragments.FavouriteFragment;
import it.saimao.bamardictionary.fragments.HistoryFragment;
import it.saimao.bamardictionary.fragments.HomeFragment;

public class PrimaryActivity extends AppCompatActivity {
    private ActivityPrimaryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPrimaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initActions();
    }

    private void initActions() {
        binding.navigation.setOnItemSelectedListener(menuItem -> {

            Fragment fragment = null;
            if (menuItem.getItemId() == R.id.mHome) {
                fragment = new HomeFragment();
            } else if (menuItem.getItemId() == R.id.mFav) {
                fragment = new FavouriteFragment();
            } else if (menuItem.getItemId() == R.id.mHistory) {
                fragment = new HistoryFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }
            return false;

        });
    }
}