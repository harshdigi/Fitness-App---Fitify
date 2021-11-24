/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 17/06/21, 11:05 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import in.digitaldealsolution.fitify.BMICalculator;
import in.digitaldealsolution.fitify.BMRActivity;
import in.digitaldealsolution.fitify.CaloriesActivity;
import in.digitaldealsolution.fitify.ProteinActivity;
import in.digitaldealsolution.fitify.R;

public class UtilityFragment extends Fragment {
    ConstraintLayout bmi, protein, calories,bmr;
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_utility, container, false);

        bmi = root.findViewById(R.id.bmi_calculator);
        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BMICalculator.class);
                startActivity(intent);
            }
        });

        protein = root.findViewById(R.id.protein_calculator);
        protein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProteinActivity.class);
                startActivity(intent);
            }
        });

        calories = root.findViewById(R.id.calories_calculator);
        calories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaloriesActivity.class);
                startActivity(intent);
            }
        });
        bmr = root.findViewById(R.id.bmr_calculator);
        bmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BMRActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}