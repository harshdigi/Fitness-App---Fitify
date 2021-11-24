/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 08/06/21, 4:51 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import in.digitaldealsolution.fitify.R;


public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}