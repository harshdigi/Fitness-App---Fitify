/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 08/06/21, 5:15 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import in.digitaldealsolution.fitify.fragments.OnboardingFragment1;
import in.digitaldealsolution.fitify.fragments.OnboardingFragment2;
import in.digitaldealsolution.fitify.fragments.OnboardingFragment3;

public class OnboardingActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private ScreeSliderPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreeSliderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

    }


    private class ScreeSliderPagerAdapter extends FragmentStatePagerAdapter {


        public ScreeSliderPagerAdapter(@NonNull @NotNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    OnboardingFragment1 tab1 = new OnboardingFragment1();
                    return tab1;
                case 1:
                    OnboardingFragment2 tab2 = new OnboardingFragment2();
                    return tab2;
                case 2:
                    OnboardingFragment3 tab3 = new OnboardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}