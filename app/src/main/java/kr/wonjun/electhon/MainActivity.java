package kr.wonjun.electhon;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v13.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Collections;

import kr.wonjun.electhon.databinding.ActivityMainBinding;
import kr.wonjun.electhon.fragment.CardFragment;
import kr.wonjun.electhon.fragment.FindFragment;
import kr.wonjun.electhon.fragment.HistoryFragment;
import kr.wonjun.electhon.fragment.SettingsFragment;
import kr.wonjun.electhon.views.ControllableViewPager;

public class MainActivity extends BaseActivity {
    static final int REQUEST_PERMISSIONS = 100;
    ActivityMainBinding binding;
    MainPagerAdapter pager;
    ArrayList<String> pageTitle = new ArrayList<>();
    ControllableViewPager mainPager;

    @Override
    protected void setDefault() {
        binding = (ActivityMainBinding) baseBinding;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("asdf", "request");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS);
        }
        disableToggle();
        Collections.addAll(pageTitle,
                "찾기", "내 카드", "사용 내역", "설정");
        pager = new MainPagerAdapter(getSupportFragmentManager());
        mainPager = binding.mainPager;
        mainPager.setAdapter(pager);
        mainPager.setOffscreenPageLimit(4);
        binding.mainBottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.main_find:
                        mainPager.setCurrentItem(0, true);
                        toolbar.setVisibility(View.GONE);
                        break;
                    case R.id.main_card:
                        mainPager.setCurrentItem(1, true);
                        toolbar.setVisibility(View.VISIBLE);
                        setToolbarTitle(pageTitle.get(1));
                        break;
                    case R.id.main_history:
                        mainPager.setCurrentItem(2, true);
                        toolbar.setVisibility(View.VISIBLE);
                        setToolbarTitle(pageTitle.get(2));
                        break;
                    case R.id.main_settings:
                        mainPager.setCurrentItem(3, true);
                        toolbar.setVisibility(View.VISIBLE);
                        setToolbarTitle(pageTitle.get(3));
                        break;

                }
            }
        });
    }

    @Override
    protected int onCreateViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected int onCreateViewToolbarId() {
        return R.id.toolbar;
    }

    class MainPagerAdapter extends FragmentStatePagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FindFragment();
                case 1:
                    return new CardFragment();
                case 2:
                    return new HistoryFragment();
                case 3:
                    return new SettingsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return pageTitle.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }
}
