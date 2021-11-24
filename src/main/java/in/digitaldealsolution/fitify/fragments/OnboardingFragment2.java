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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import in.digitaldealsolution.fitify.LoginActivity;
import in.digitaldealsolution.fitify.R;

public class OnboardingFragment2 extends Fragment {
    private TextView skip;
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_onboarding2, container, false);
        skip = root.findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
