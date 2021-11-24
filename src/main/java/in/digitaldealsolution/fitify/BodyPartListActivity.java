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

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import in.digitaldealsolution.fitify.adapter.CategoryAdapter;
import in.digitaldealsolution.fitify.model.EquipmentModel;
import in.digitaldealsolution.fitify.model.ExercisesModel;
import in.digitaldealsolution.fitify.model.WorkoutModel;
import in.digitaldealsolution.fitify.utility.EquipmentApi;
import in.digitaldealsolution.fitify.utility.ExerciseApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BodyPartListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private CategoryAdapter categoryAdapter;
    private String bodyPartName;
    private String uid;
    private Retrofit retrofit;
    private ExerciseApi exerciseApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_part_list);
        recyclerView = findViewById(R.id.bodypartlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        shimmerFrameLayout= findViewById(R.id.shimmerLayout_bodypartlist);
        shimmerFrameLayout.startShimmer();
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.toolbar_allbodypart);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        bodyPartName = getIntent().getStringExtra("bodyPartName");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.website_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        exerciseApi = retrofit.create(ExerciseApi.class);
        Call<ArrayList<ExercisesModel>> call = exerciseApi.getAllExercises("/exercise/?uid=" + uid);
        call.enqueue(new Callback<ArrayList<ExercisesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ExercisesModel>> call, Response<ArrayList<ExercisesModel>> response) {

                ArrayList<ExercisesModel> exercisesModels = response.body();
                PutDataIntoRecyclerView(exercisesModels);
            }

            @Override
            public void onFailure(Call<ArrayList<ExercisesModel>> call, Throwable t) {

            }
        });
    }

    private ArrayList<ExercisesModel> filter(String text, ArrayList<ExercisesModel> exerciseslist) {
        ArrayList<ExercisesModel> filterlist = new ArrayList<>();
        for(ExercisesModel exercisesModel : exerciseslist){

            if(exercisesModel.getMuscleGroup().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(exercisesModel);
            }

        }
        return filterlist;
    }
    private void PutDataIntoRecyclerView(ArrayList<ExercisesModel> exerciseslist) {
        ArrayList<ExercisesModel> filterList = new ArrayList<>();
        filterList = filter(bodyPartName,exerciseslist);
        categoryAdapter = new CategoryAdapter(this,filterList,exerciseslist);
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        };
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(categoryAdapter);

    }



}