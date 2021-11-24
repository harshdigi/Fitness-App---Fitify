/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 13/06/21, 10:30 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.digitaldealsolution.fitify.ExerciseActivity;
import in.digitaldealsolution.fitify.R;
import in.digitaldealsolution.fitify.model.BodyPartModels;
import in.digitaldealsolution.fitify.model.EquipmentModel;
import in.digitaldealsolution.fitify.model.ExercisesModel;
import in.digitaldealsolution.fitify.model.WorkoutModel;
import io.armcha.elasticview.ElasticView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<ExercisesModel> filterList,exerciseList;


    public CategoryAdapter(Context context, List<ExercisesModel> filterList, List<ExercisesModel> exerciseList) {
        this.context = context;
        this.filterList = filterList;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.custom_excercise_list, parent, false);
        return new CategoryAdapter.CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryViewHolder holder, final int position) {
        holder.exerciseName.setText(filterList.get(position).getName());
        holder.muscleWorked.setText(filterList.get(position).getMuscleWorked());
        holder.muscleGroup.setText(filterList.get(position).getMuscleGroup());
        List<String> stringList = new ArrayList<>();
        String str = "";
        for (EquipmentModel equipmentModel : filterList.get(position).getEquipment()) {
            stringList.add(equipmentModel.getName());
        }
        str = String.join(", ", stringList);
        holder.equipments.setText(str);
        Glide.with(context)
                .load(filterList.get(position).getImagePath())
                .into(holder.exerciseImage);
        holder.parentView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, ExerciseActivity.class);
                            intent.putExtra("exerciseItem", (Parcelable) filterList.get(position));
                            intent.putExtra("exerciselist", (Serializable) exerciseList);
                            intent.putExtra("equipmentmodel", (Serializable) filterList.get(position).getEquipment());
                            context.startActivity(intent);

                        }
                    });
    }




    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName, muscleWorked, muscleGroup, equipments;
        ImageView exerciseImage;
        ElasticView parentView;

        public CategoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.card_exercise_name);
            muscleGroup = itemView.findViewById(R.id.card_muscle_group_name);
            muscleWorked = itemView.findViewById(R.id.card_muscle_worked_name);
            exerciseImage = itemView.findViewById(R.id.exerciseImage);
            equipments = itemView.findViewById(R.id.card_equipment_name);
            parentView = itemView.findViewById(R.id.item_card_exercise);

        }
    }

}

