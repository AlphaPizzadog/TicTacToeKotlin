package com.wiley.fordummies.androidsdk.tictactoe.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import com.wiley.fordummies.androidsdk.tictactoe.R;

/**
 * Abstract class for Activity with only one Fragment.
 * <p>
 * TODO: Convert to Kotlin
 * <p>
 * Source: Phillips, Stewart, and Marsicano, Big Nerd Ranch Guide to Android Development, 3rd ed.
 */
@Keep
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container), new OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

                var sysBarsCutoutGestures = insets.getInsets(WindowInsetsCompat.Type.systemBars() |
                        WindowInsetsCompat.Type.displayCutout() | WindowInsetsCompat.Type.systemGestures());
                v.setPadding(sysBarsCutoutGestures.left, sysBarsCutoutGestures.top,
                        sysBarsCutoutGestures.right, sysBarsCutoutGestures.bottom);
                return WindowInsetsCompat.CONSUMED;
            }
        });
    }
}
