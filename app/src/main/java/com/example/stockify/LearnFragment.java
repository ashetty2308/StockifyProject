package com.example.stockify;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class LearnFragment extends Fragment {


    public LearnFragment() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView defOpen = getActivity().findViewById(R.id.openDef);
        TextView defMarket = getActivity().findViewById(R.id.marketDef);
        TextView defHigh = getActivity().findViewById(R.id.highDef);
        TextView defLow = getActivity().findViewById(R.id.lowDef);
        TextView volume = getActivity().findViewById(R.id.volumeDef);
        TextView defClose = getActivity().findViewById(R.id.closeDef);



        defMarket.setText("Stock Market: a place where stocks (partial owenerships of a company) are bought and sold");
        defOpen.setText("Open Price: The open price is the price of the stock when the market opens.");
        defHigh.setText("High: The highest price at which a stock was at during a certain interval (in this case, the highst price during a 5 minute interval.");
        defLow.setText("Low: The lowest price at which a stock was at during a certain interval (in this case, the highst price during a 5 minute interval.");
        volume.setText("Volume: The amount of shares a company has present at a given time.");
        defClose.setText("Close Price: The price of a share when the market closes (In this case, the price of the share following the end of the 5 minute interval");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_learn, container, false);
    }

    public class WriteInNotes extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}