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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

import in.digitaldealsolution.fitify.BodyPartListActivity;
import in.digitaldealsolution.fitify.ExerciseActivity;
import in.digitaldealsolution.fitify.R;
import in.digitaldealsolution.fitify.model.BodyPartModels;
import in.digitaldealsolution.fitify.model.ExercisesModel;

public class BodyPartAdapter extends RecyclerView.Adapter<BodyPartAdapter.BodyPartViewHolder> {


    private Context context;
    private ArrayList<ExercisesModel> exercisesModels;
    private ArrayList<BodyPartModels> bodyPartModels;
    public BodyPartAdapter(Context context, ArrayList<ExercisesModel> exercisesModels, ArrayList<BodyPartModels> bodyPartModels) {
        this.context = context;
        this.exercisesModels = exercisesModels;
        this.bodyPartModels = bodyPartModels;
    }



    @NonNull
    @NotNull
    @Override
    public BodyPartViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.card_bodypart_home, parent, false);
        return new BodyPartAdapter.BodyPartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BodyPartViewHolder holder, final int position) {
        holder.bodyPartName.setText(bodyPartModels.get(position).getName());
        Glide.with(context)
                .load(bodyPartModels.get(position).getImage())
                .into(holder.bodyPartImage);

        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BodyPartListActivity.class);
                intent.putExtra("bodyPartName", bodyPartModels.get(position).getName());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return bodyPartModels.size();
    }

    public class BodyPartViewHolder extends RecyclerView.ViewHolder{
        TextView bodyPartName;
        ImageView bodyPartImage;
        ConstraintLayout parentView;
        public BodyPartViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            bodyPartName = itemView.findViewById(R.id.card_bodypart_home_name);
            bodyPartImage = itemView.findViewById(R.id.card_bodypart_image);
            parentView = itemView.findViewById(R.id.card_main_bodypart);
        }
    }
}
