package it.sofk.slurp.food.weekly;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.sofk.slurp.R;

public class WeeklyFragment extends Fragment {

    Holder holder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);

        holder = new Holder(view);
        return view;
    }

    class Holder {



        Holder(View view){

        }
    }
}