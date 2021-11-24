/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 9:42 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import in.digitaldealsolution.fitify.fragments.AccountFragment;
import in.digitaldealsolution.fitify.fragments.ExerciseFragment;
import in.digitaldealsolution.fitify.fragments.HomeFragment;
import in.digitaldealsolution.fitify.fragments.UtilityFragment;
import in.digitaldealsolution.fitify.model.ExercisesModel;

public class MainAdapter extends FragmentPagerAdapter {
    private Context context;
    private int totalTab;
    public MainAdapter(FragmentManager fm, Context context, int totalTab) {
        super(fm);
        this.context = context;
        this.totalTab = totalTab;
    }

    @Override
    public int getCount() {
        return totalTab;
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();

                return homeFragment;
            case 1:
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                return exerciseFragment;
            case 2:
                UtilityFragment utilityFragment = new UtilityFragment();
                return utilityFragment;
            case 3:
                AccountFragment accountFragment = new AccountFragment();
                return accountFragment;
            default:
                HomeFragment homeFragment1 = new HomeFragment();
                return homeFragment1;
        }
    }
}
