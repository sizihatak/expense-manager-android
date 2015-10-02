package com.project.sizihatak.expense_manager_android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ListCostFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_cost, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().findViewById(R.id.fab).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AddCostFragment addCostFragment = new AddCostFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.main_activity_container, addCostFragment)
                .addToBackStack(null)
                .commit();
    }
}
