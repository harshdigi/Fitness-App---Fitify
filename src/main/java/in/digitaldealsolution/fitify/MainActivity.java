/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 2:07 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

import in.digitaldealsolution.fitify.adapter.MainAdapter;
import in.digitaldealsolution.fitify.model.ExercisesModel;


public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private ViewPager viewPager;
    private int prevmenu;

    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chipNavigationBar = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.main_container);
        final MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), this, 4);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_home, true);
        prevmenu = R.id.bottom_nav_home;
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        chipNavigationBar.setItemSelected(prevmenu, false);
                        chipNavigationBar.setItemSelected(R.id.bottom_nav_home, true);
                        prevmenu = R.id.bottom_nav_home;
                        break;
                    case 1:
                        chipNavigationBar.setItemSelected(prevmenu, false);
                        chipNavigationBar.setItemSelected(R.id.bottom_nav_exercise, true);
                        prevmenu = R.id.bottom_nav_exercise;
                        break;
                    case 2:
                        chipNavigationBar.setItemSelected(prevmenu, false);
                        chipNavigationBar.setItemSelected(R.id.bottom_nav_utility, true);
                        prevmenu = R.id.bottom_nav_utility;
                        break;
                    case 3:
                        chipNavigationBar.setItemSelected(prevmenu, false);
                        chipNavigationBar.setItemSelected(R.id.bottom_nav_account, true);
                        prevmenu = R.id.bottom_nav_account;
                        break;
                    default:
                        return;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(4);
        bottomMenu();
    }




    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment changefragment = null;
                switch (i) {
                    case R.id.bottom_nav_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.bottom_nav_exercise:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.bottom_nav_utility:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.bottom_nav_account:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}