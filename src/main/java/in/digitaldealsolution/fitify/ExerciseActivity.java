/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 1:59 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.digitaldealsolution.fitify.model.EquipmentModel;
import in.digitaldealsolution.fitify.model.ExercisesModel;

public class ExerciseActivity extends AppCompatActivity {
    private List<ExercisesModel> exercisesModelArrayList;
    private ExercisesModel exercisesModel;
    private List<EquipmentModel> equipmentModel;
    private TextView exercisename, musclegroup_name, muscleworked_name, exerciseDescription, equipment_required, moreExercise1_name, moreExercise2_name, moreExercise3_name;
    private ImageView moreExercise1_Image, moreExercise2_Image, moreExercise3_Image;
    private ConstraintLayout moreExercise1, moreExercise2, moreExercise3;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.toolbar_exercise);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        exercisesModel = (ExercisesModel) getIntent().getParcelableExtra("exerciseItem");
        equipmentModel = (List<EquipmentModel>) getIntent().getSerializableExtra("equipmentmodel");
        exercisesModelArrayList = new ArrayList<>();
        exercisesModelArrayList = (List<ExercisesModel>) getIntent().getSerializableExtra("exerciselist");
        youTubePlayerView = findViewById(R.id.exercise_video);
        getLifecycle().addObserver(youTubePlayerView);
        exercisename = (TextView) findViewById(R.id.exercise_name_value);
        musclegroup_name = (TextView) findViewById(R.id.muscle_group_name_value);
        muscleworked_name = (TextView) findViewById(R.id.muscle_worked_name_value);
        exerciseDescription = (TextView) findViewById(R.id.exercise_description_value);
        equipment_required = (TextView) findViewById(R.id.equipment_required_value);
        moreExercise1_name = (TextView) findViewById(R.id.more_exercise_text1);
        moreExercise2_name = (TextView) findViewById(R.id.more_exercise_text2);
        moreExercise3_name = (TextView) findViewById(R.id.more_exercise_text3);
        moreExercise1_Image = (ImageView) findViewById(R.id.more_exercise_image1);
        moreExercise2_Image = (ImageView) findViewById(R.id.more_exercise_image2);
        moreExercise3_Image = (ImageView) findViewById(R.id.more_exercise_image3);
        moreExercise1 = (ConstraintLayout) findViewById(R.id.more_exercise1);
        moreExercise2 = (ConstraintLayout) findViewById(R.id.more_exercise2);
        moreExercise3 = (ConstraintLayout) findViewById(R.id.more_exercise3);

        if (exercisesModel != null) {
            exercisename.setText(exercisesModel.getName());
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    Log.v("tAG",exercisesModel.getVideoPath());
                    if(exercisesModel.getVideoPath()!=null){
                        youTubePlayer.loadVideo(exercisesModel.getVideoPath(),0);
                    }


                }
            });
            musclegroup_name.setText(exercisesModel.getMuscleGroup());
            muscleworked_name.setText(exercisesModel.getMuscleWorked());
            exerciseDescription.setText(exercisesModel.getDescription());
            if (equipmentModel != null) {
                List<String> stringList = new ArrayList<>();
                String str = "";
                for (EquipmentModel equipmentModel : equipmentModel) {
                    stringList.add(equipmentModel.getName());
                }
                str = String.join(", ", stringList);
                equipment_required.setText(str);
            } else {
                Log.v("Tag", "No data");
            }

        }
        if (exercisesModelArrayList != null) {
            Collections.shuffle(exercisesModelArrayList);
            Glide.with(getApplicationContext())
                    .load(exercisesModelArrayList.get(0).getImagePath())
                    .into(moreExercise1_Image);
            moreExercise1_name.setText(exercisesModelArrayList.get(0).getName());
            Glide.with(getApplicationContext())
                    .load(exercisesModelArrayList.get(1).getImagePath())
                    .into(moreExercise2_Image);
            moreExercise2_name.setText(exercisesModelArrayList.get(1).getName());
            Glide.with(getApplicationContext())
                    .load(exercisesModelArrayList.get(2).getImagePath())
                    .into(moreExercise3_Image);
            moreExercise3_name.setText(exercisesModelArrayList.get(2).getName());

            moreExercise1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openExercise(0);
                }


            });
            moreExercise2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openExercise(1);
                }


            });
            moreExercise3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openExercise(2);
                }


            });
        }


    }

    private void openExercise(int position) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("exerciseItem", (Parcelable) exercisesModelArrayList.get(position));
        intent.putExtra("exerciselist", (Serializable) exercisesModelArrayList);
        intent.putExtra("equipmentmodel", (Serializable) equipmentModel);
        startActivity(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }
}