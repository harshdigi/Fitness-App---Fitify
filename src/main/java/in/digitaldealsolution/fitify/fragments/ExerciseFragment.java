/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 2:03 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import in.digitaldealsolution.fitify.adapter.ExerciseAdapter;
import in.digitaldealsolution.fitify.R;
import in.digitaldealsolution.fitify.model.EquipmentModel;
import in.digitaldealsolution.fitify.model.ExercisesModel;
import in.digitaldealsolution.fitify.model.WorkoutModel;
import in.digitaldealsolution.fitify.utility.ExerciseApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ExerciseFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ExercisesModel> exerciseslist;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ExerciseAdapter exerciseAdapter;
    private EditText exerciseSearch;
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_excercise, container, false);

        recyclerView = root.findViewById(R.id.exercise_recyclerView);
        exerciseslist = new ArrayList<>();
        exerciseSearch = root.findViewById(R.id.search_exercise);

        shimmerFrameLayout = root.findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.website_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ExerciseApi exerciseApi = retrofit.create(ExerciseApi.class);
        Call<ArrayList<ExercisesModel>> call = exerciseApi.getAllExercises("/exercise/?uid=" + uid);
        call.enqueue(new Callback<ArrayList<ExercisesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ExercisesModel>> call, Response<ArrayList<ExercisesModel>> response) {

                ArrayList<ExercisesModel> exercisesModels = response.body();
                exerciseslist.addAll(exercisesModels);
                PutDataIntoRecyclerView(exerciseslist);
            }

            @Override
            public void onFailure(Call<ArrayList<ExercisesModel>> call, Throwable t) {

            }
        });


        exerciseSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    filter(s.toString());
            }
        });


        return root;
    }

    private void filter(String text) {
        List<ExercisesModel> filterlist = new ArrayList<>();
        for(ExercisesModel exercisesModel : exerciseslist){
            List<EquipmentModel> equipmentModels = exercisesModel.getEquipment();
            List<WorkoutModel> workoutModels = exercisesModel.getWorkout();
            if(exercisesModel.getName().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(exercisesModel);
            }
            else if(exercisesModel.getMuscleGroup().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(exercisesModel);
            }
            else if(exercisesModel.getMuscleWorked().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(exercisesModel);
            }
            else {
                for (EquipmentModel equipmentModel : equipmentModels) {
                    if (equipmentModel.getName().toLowerCase().contains(text.toLowerCase())) {
                        filterlist.add(exercisesModel);
                    }
                }
                for (WorkoutModel workoutModel : workoutModels) {
                    if (workoutModel.getName().toLowerCase().contains(text.toLowerCase())) {
                        filterlist.add(exercisesModel);
                    }
                }
            }
            exerciseAdapter.filterlist(filterlist);
        }
    }

    private void PutDataIntoRecyclerView(ArrayList<ExercisesModel> exerciseslist) {
        exerciseAdapter = new ExerciseAdapter(getContext(),exerciseslist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        };
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(exerciseAdapter);

    }


}