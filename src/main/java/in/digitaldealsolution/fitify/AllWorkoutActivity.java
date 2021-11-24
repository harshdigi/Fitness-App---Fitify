/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 1:59 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import in.digitaldealsolution.fitify.utility.onWorkoutClickListner;
import android.util.Log;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.Serializable;
import java.util.ArrayList;

import in.digitaldealsolution.fitify.adapter.HomeAdapter.WorkoutAdapter;
import in.digitaldealsolution.fitify.model.WorkoutModel;

public class AllWorkoutActivity extends AppCompatActivity implements onWorkoutClickListner  {
    private ArrayList<WorkoutModel> workoutModels;
    private RecyclerView workoutRecycler;
    private RecyclerView.Adapter adapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_workout);
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.toolbar_allworkout);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        workoutModels = (ArrayList<WorkoutModel>)  getIntent().getSerializableExtra("workoutlist");
        shimmerFrameLayout = findViewById(R.id.shimmerLayout_workout);
        shimmerFrameLayout.startShimmer();
        workoutRecycler =findViewById(R.id.workout_recyclerview);
        putDataIntoRecycler(workoutModels);
    }

    public  void putDataIntoRecycler(ArrayList<WorkoutModel> workoutList){
        if(workoutList!=null){
            adapter = new WorkoutAdapter(this,workoutList, this,false);
            workoutRecycler.setHasFixedSize(true);
            LinearLayoutManager manager = new LinearLayoutManager(this) {
                @Override
                public void onLayoutCompleted(RecyclerView.State state) {
                    super.onLayoutCompleted(state);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            };
            workoutRecycler.setLayoutManager(manager);
            workoutRecycler.setAdapter(adapter);
        }

    }
    @Override
    public void onWorkoutClickListner(int position, String name) {
        Intent intent = new Intent(this, WorkoutListAcitivity.class);
        intent.putExtra("workoutName", name);
        startActivity(intent);
    }

}