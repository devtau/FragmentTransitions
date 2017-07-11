package com.devtau.fragmenttransitions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListFragment extends Fragment {

    public static String FRAGMENT_TAG = "com.example.fragmenttransitions.ListFragment";
    private ListAdapter.KittenClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListAdapter.KittenClickListener) {
            listener = (ListAdapter.KittenClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement KittenClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new ListAdapter(6, listener));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return view;
    }
}
