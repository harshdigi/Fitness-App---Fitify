/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 9:42 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.adapter.HomeAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private Context context;
    private ArrayList<ExercisesModel> exercisesModels;
    private List<Integer> favourite_item_position;

    public FavouriteAdapter(Context context, ArrayList<ExercisesModel> exercisesModels, List<Integer> favourite_item_position) {
        this.context = context;
        this.exercisesModels = exercisesModels;
        this.favourite_item_position = favourite_item_position;
    }

    @NonNull
    @NotNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favourite_exercise_card,parent,false);
        FavouriteViewHolder favouriteViewHolder = new FavouriteViewHolder(view);
        return favouriteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavouriteViewHolder holder, final int position) {
        int index = favourite_item_position.get(position);
        holder.exerciseName.setText(exercisesModels.get(index).getName());
        holder.muscleWorked.setText(exercisesModels.get(index).getMuscleWorked());
        Glide.with(context)
                .load(exercisesModels.get(index).getImagePath())
                .into(holder.imageView);
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExerciseActivity.class);
                intent.putExtra("exerciseItem", (Parcelable) exercisesModels.get(position));
                intent.putExtra("exerciselist", (Serializable) exercisesModels);
                intent.putExtra("equipmentmodel", (Serializable) exercisesModels.get(position).getEquipment());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return favourite_item_position.size();
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        LinearLayout parentView;
        TextView exerciseName,muscleWorked;
        public FavouriteViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fav_exercise_img);
            exerciseName= itemView.findViewById(R.id.fav_exercise_name);
            muscleWorked= itemView.findViewById(R.id.fav_muscle_group);
            parentView = itemView.findViewById(R.id.card_favourite);
        }
    }
}
