/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 13/06/21, 5:56 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.adapter.HomeAdapter;

import android.content.Context;
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
import in.digitaldealsolution.fitify.model.EquipmentModel;
import in.digitaldealsolution.fitify.model.ExercisesModel;
import in.digitaldealsolution.fitify.utility.onEquipmentClickListner;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder> {
    private Context context;
//    private ArrayList<ExercisesModel> exerciseList = new ArrayList<>();
    private ArrayList<EquipmentModel> equipmentList;
    private onEquipmentClickListner listener;

    public EquipmentAdapter(Context context, ArrayList<EquipmentModel> equipmentList, onEquipmentClickListner listener) {
        this.context = context;
//        this.exerciseList = exerciseList;
        this.equipmentList = equipmentList;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public EquipmentAdapter.EquipmentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.card_equipment_home, parent, false);
        return new EquipmentAdapter.EquipmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EquipmentViewHolder holder, final int position) {
        holder.equipmentName.setText(equipmentList.get(position).getName());
        Glide.with(context)
                .load(equipmentList.get(position).getImagePath())
                .into(holder.equipmentImage);

        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEquipmentClickListner(position, equipmentList.get(position).getName());
                /*Intent intent = new Intent(context, EquipmentListActivity.class);
                intent.putExtra("equipmentItem", (Parcelable) equipmentList.get(position));
                context.startActivity(intent);*/

            }
        });

    }


    @Override
    public int getItemCount() {
        return equipmentList.size();
    }

    public static class EquipmentViewHolder extends RecyclerView.ViewHolder {
        TextView equipmentName;
        ImageView equipmentImage;
        ConstraintLayout parentView;

        public EquipmentViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            equipmentName = itemView.findViewById(R.id.card_equipmemt_home_name);
            equipmentImage = itemView.findViewById(R.id.card_equipment_image);
            parentView = itemView.findViewById(R.id.card_main);
        }
    }
}
