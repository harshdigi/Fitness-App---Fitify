/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 9:42 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.adapter.HomeAdapter;

import android.content.Context;
import android.util.TypedValue;
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

import java.util.ArrayList;

import in.digitaldealsolution.fitify.R;
import in.digitaldealsolution.fitify.model.WorkoutModel;
import in.digitaldealsolution.fitify.utility.onWorkoutClickListner;
import io.armcha.elasticview.ElasticView;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private Context context;
    private ArrayList<WorkoutModel> workoutModels;
    private Boolean horizontal;

    public WorkoutAdapter(Context context, ArrayList<WorkoutModel> workoutModels, onWorkoutClickListner listener, Boolean horizontal) {
        this.context = context;
        this.workoutModels = workoutModels;
        this.listener = listener;
        this.horizontal = horizontal;
    }

    private onWorkoutClickListner listener;

    @NonNull
    @NotNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.card_wrokout_home, parent, false);
        return new WorkoutAdapter.WorkoutViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WorkoutViewHolder holder, final int position) {

        Glide.with(context)
                .load(workoutModels.get(position).getImagePath())
                .into(holder.workoutImage);
        if(horizontal){
            float factor = holder.itemView.getContext().getResources().getDisplayMetrics().density;
            holder.elasticView.getLayoutParams().width = (int) (380 * factor);
        }
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWorkoutClickListner(position,workoutModels.get(position).getName());
                /*Intent intent = new Intent(context, EquipmentListActivity.class);
                intent.putExtra("equipmentItem", (Parcelable) equipmentList.get(position));
                context.startActivity(intent);*/

            }
        });
    }

    @Override
    public int getItemCount() {
        if(workoutModels.size()<=5 || !horizontal){
        return workoutModels.size();}
        else{
            return 6;
        }
    }


    public class WorkoutViewHolder extends RecyclerView.ViewHolder{
        ImageView workoutImage;
        ConstraintLayout parentView;
        ElasticView elasticView;
        public WorkoutViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            workoutImage = itemView.findViewById(R.id.card_workout_image);
            parentView = itemView.findViewById(R.id.card_workout_main);
            elasticView = itemView.findViewById(R.id.workout_card_parent);
        }
    }
}
