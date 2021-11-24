/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 13/06/21, 1:06 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import in.digitaldealsolution.fitify.model.EquipmentModel;
import in.digitaldealsolution.fitify.model.ExercisesModel;
import io.armcha.elasticview.ElasticView;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private Context context;
    private List<ExercisesModel> exerciseList,exerciseListCopy;


    public ExerciseAdapter(Context context, List<ExercisesModel> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.exerciseListCopy = exerciseList;
    }

    @NonNull
    @NotNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.custom_excercise_list, parent, false);
        return new ExerciseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ExerciseViewHolder holder, final int position) {
        holder.exerciseName.setText(exerciseList.get(position).getName());
        holder.muscleWorked.setText(exerciseList.get(position).getMuscleWorked());
        holder.muscleGroup.setText(exerciseList.get(position).getMuscleGroup());
        List<String> stringList = new ArrayList<>();
        String str = "";
        for (EquipmentModel equipmentModel : exerciseList.get(position).getEquipment()) {
            stringList.add(equipmentModel.getName());
        }
        str = String.join(", ", stringList);
        holder.equipments.setText(str);
        Glide.with(context)
                .load(exerciseList.get(position).getImagePath())
                .into(holder.exerciseImage);
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExerciseActivity.class);
                intent.putExtra("exerciseItem", (Parcelable) exerciseList.get(position));
                intent.putExtra("exerciselist", (Serializable) exerciseListCopy);
                intent.putExtra("equipmentmodel", (Serializable) exerciseList.get(position).getEquipment());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName, muscleWorked, muscleGroup, equipments;
        ImageView exerciseImage;
        ElasticView parentView;

        public ExerciseViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.card_exercise_name);
            muscleGroup = itemView.findViewById(R.id.card_muscle_group_name);
            muscleWorked = itemView.findViewById(R.id.card_muscle_worked_name);
            exerciseImage = itemView.findViewById(R.id.exerciseImage);
            equipments = itemView.findViewById(R.id.card_equipment_name);
            parentView = itemView.findViewById(R.id.item_card_exercise);

        }
    }
    public void filterlist(List<ExercisesModel> filterList){
        exerciseList= filterList;
        notifyDataSetChanged();
    }
}
