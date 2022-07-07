package com.moringaschool.medicare.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moringaschool.medicare.R;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseDoctorFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_choose_doctor, container, false);
       return view;
    }



}