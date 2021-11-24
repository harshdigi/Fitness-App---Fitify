/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 2:03 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.digitaldealsolution.fitify.AllWorkoutActivity;
import in.digitaldealsolution.fitify.EquipmentListActivity;
import in.digitaldealsolution.fitify.R;
import in.digitaldealsolution.fitify.WorkoutListAcitivity;
import in.digitaldealsolution.fitify.adapter.HomeAdapter.BodyPartAdapter;
import in.digitaldealsolution.fitify.adapter.HomeAdapter.EquipmentAdapter;
import in.digitaldealsolution.fitify.adapter.HomeAdapter.FavouriteAdapter;
import in.digitaldealsolution.fitify.adapter.HomeAdapter.WorkoutAdapter;
import in.digitaldealsolution.fitify.adapter.SliderAdapterExample;
import in.digitaldealsolution.fitify.model.SliderItem;
import in.digitaldealsolution.fitify.model.BodyPartModels;
import in.digitaldealsolution.fitify.model.EquipmentModel;
import in.digitaldealsolution.fitify.model.ExercisesModel;
import in.digitaldealsolution.fitify.model.WorkoutModel;
import in.digitaldealsolution.fitify.utility.EquipmentApi;
import in.digitaldealsolution.fitify.utility.ExerciseApi;
import in.digitaldealsolution.fitify.utility.WorkoutApi;
import in.digitaldealsolution.fitify.utility.onEquipmentClickListner;
import in.digitaldealsolution.fitify.utility.onWorkoutClickListner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements onEquipmentClickListner,onWorkoutClickListner {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ImageSlider");
    private DatabaseReference databaseReferencefav = FirebaseDatabase.getInstance().getReference("FavExercises");
    private DatabaseReference databaseReferencebody = FirebaseDatabase.getInstance().getReference("BodyParts");
    private List<SliderItem> sliderItemList;
    private List<Integer> favItem;
    private ArrayList<BodyPartModels> bodyPartList;
    private LinearLayout linearLayout;
    private SliderView sliderView;
    private RecyclerView favouriteRecycler,equipmentRecycler,bodypartRecycler,workoutRecycler;
    private RecyclerView.Adapter adapterEquipment, adapterBodypart,adapterWorkout,adapterFavourite;
    private ExerciseApi exerciseApi;
    private EquipmentApi equipmentApi;
    private WorkoutApi workoutApi;
    private Retrofit retrofit;
    private TextView viewAllWorkout;
    private String uid;

    private ShimmerFrameLayout shimmerFrameLayout_ourfav,shimmerFrameLayout_bodypart,shimmerFrameLayout_equipment,shimmerFrameLayout_workout;
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        sliderView = root.findViewById(R.id.imageSlider);
        favouriteRecycler = root.findViewById(R.id.fav_exercises_recyclerview);
        equipmentRecycler = root.findViewById(R.id.equipment_recycler);
        bodypartRecycler= root.findViewById(R.id.bodypart_recycler);
        workoutRecycler = root.findViewById(R.id.workout_recycler);
        viewAllWorkout = root.findViewById(R.id.workout_viewAll);
        linearLayout = root.findViewById(R.id.workout_section);
        shimmerFrameLayout_ourfav = root.findViewById(R.id.shimmerLayout_ourfav);
        shimmerFrameLayout_bodypart = root.findViewById(R.id.shimmerLayout_bodypart);
        shimmerFrameLayout_equipment = root.findViewById(R.id.shimmerLayout_equipment);
        shimmerFrameLayout_workout = root.findViewById(R.id.shimmerLayout_workout);
        shimmerFrameLayout_workout.startShimmer();
        shimmerFrameLayout_ourfav.startShimmer();
        shimmerFrameLayout_equipment.startShimmer();
        shimmerFrameLayout_bodypart.startShimmer();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.website_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        exerciseApi = retrofit.create(ExerciseApi.class);
        equipmentApi = retrofit.create(EquipmentApi.class);
        workoutApi =retrofit.create(WorkoutApi.class);
        Call<ArrayList<ExercisesModel>> call = exerciseApi.getAllExercises("/exercise/?uid=" + uid);
        call.enqueue(new Callback<ArrayList<ExercisesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ExercisesModel>> call, Response<ArrayList<ExercisesModel>> response) {

                ArrayList<ExercisesModel> exercisesModels = response.body();
                getFavData(exercisesModels);
                getEquipmentData();
                getWorkoutData();
                getBodyPartData(exercisesModels);

            }

            @Override
            public void onFailure(Call<ArrayList<ExercisesModel>> call, Throwable t) {

            }
        });

        getSliderData();

        return root;
    }

    public void getWorkoutData(){
        Call<ArrayList<WorkoutModel>> call_workout = workoutApi.getAllWorkout("/workout/?uid=" + uid);
        call_workout.enqueue(new Callback<ArrayList<WorkoutModel>>() {
            @Override
            public void onResponse(Call<ArrayList<WorkoutModel>> call, Response<ArrayList<WorkoutModel>> response) {
                ArrayList<WorkoutModel> workoutModels = response.body();
                if(workoutModels!=null){
                    putDataIntoWorkoutRecycler(workoutModels);}

            }

            @Override
            public void onFailure(Call<ArrayList<WorkoutModel>> call, Throwable t) {

            }
        });
    }

    public  void putDataIntoWorkoutRecycler(final ArrayList<WorkoutModel> workoutList){
        if(workoutList!=null){
        workoutRecycler.setHasFixedSize(true);
        workoutRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false){
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                shimmerFrameLayout_workout.stopShimmer();
                shimmerFrameLayout_workout.setVisibility(View.GONE);

            }
        });
        adapterWorkout = new WorkoutAdapter(getContext(),workoutList, this,true);
        workoutRecycler.setAdapter(adapterWorkout);
        }
        viewAllWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllWorkoutActivity.class);
                intent .putExtra("workoutlist", (Serializable) workoutList);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onEquipmentClickListner(int position, String name) {
        Intent intent = new Intent(getActivity(), EquipmentListActivity.class);
        intent.putExtra("equipmentName", name);
        startActivity(intent);
    }

    @Override
    public void onWorkoutClickListner(int position, String name) {
        Intent intent = new Intent(getActivity(), WorkoutListAcitivity.class);
        intent.putExtra("workoutName", name);
        startActivity(intent);
    }
    //bodypart
    private void getBodyPartData(final ArrayList<ExercisesModel> exerciseslist) {
        databaseReferencebody.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                bodyPartList = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String name = dataSnapshot.getKey();
                    String imagePath= dataSnapshot.getValue(String.class);
                    BodyPartModels bodyPartModels = new BodyPartModels(name,imagePath);
                    bodyPartList.add(bodyPartModels);
                }
                putDataIntoBodyPartRecycler(bodyPartList,exerciseslist);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void putDataIntoBodyPartRecycler(ArrayList<BodyPartModels> bodyPartList, ArrayList<ExercisesModel> exerciseslist) {
        bodypartRecycler.setHasFixedSize(true);
        bodypartRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false){
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                shimmerFrameLayout_bodypart.stopShimmer();
                shimmerFrameLayout_bodypart.setVisibility(View.GONE);

            }
        });
        adapterBodypart = new BodyPartAdapter(getContext(),exerciseslist,bodyPartList);
        bodypartRecycler.setAdapter(adapterBodypart);
    }
    //Equiment
    private void getEquipmentData() {
        Call<ArrayList<EquipmentModel>> call = equipmentApi.getAllEquipment("/equipment/?uid=" + uid);
        call.enqueue(new Callback<ArrayList<EquipmentModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EquipmentModel>> call, Response<ArrayList<EquipmentModel>> response) {
                ArrayList<EquipmentModel> equipmentModels = response.body();
                putDataIntoEquipmentRecycler(equipmentModels);
            }

            @Override
            public void onFailure(Call<ArrayList<EquipmentModel>> call, Throwable t) {
            }
        });
    }
    public  void putDataIntoEquipmentRecycler(ArrayList<EquipmentModel> equipmentlist){
        equipmentRecycler.setHasFixedSize(true);
        equipmentRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false){
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                shimmerFrameLayout_equipment.stopShimmer();
                shimmerFrameLayout_equipment.setVisibility(View.GONE);

            }
        });
        adapterEquipment = new EquipmentAdapter(getContext(),equipmentlist, this);
        equipmentRecycler.setAdapter(adapterEquipment);
    }


    //Slider
    private void getSliderData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                sliderItemList = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    sliderItemList.add(dataSnapshot.getValue(SliderItem.class));
                }
                putDataIntoSLider(sliderItemList);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void putDataIntoSLider(List<SliderItem> sliderItemList) {
        SliderAdapterExample sliderAdapterExample = new SliderAdapterExample(getContext(),sliderItemList);
        sliderView.setSliderAdapter(sliderAdapterExample);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
    }


    //Favourite
    private void getFavData(final ArrayList<ExercisesModel> exerciseslist) {
        databaseReferencefav.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                favItem = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    favItem.add(Integer.valueOf(dataSnapshot.getKey()));
                }
                favouriteRecycler(favItem,exerciseslist);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void favouriteRecycler(List<Integer> favItem, ArrayList<ExercisesModel> exerciseslist) {
        favouriteRecycler.setHasFixedSize(true);
        favouriteRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false){
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                shimmerFrameLayout_ourfav.stopShimmer();
                shimmerFrameLayout_ourfav.setVisibility(View.GONE);
            }
        });
        adapterFavourite = new FavouriteAdapter(getContext(),exerciseslist,favItem);
        favouriteRecycler.setAdapter(adapterFavourite);
    }
}