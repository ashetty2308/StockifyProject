    package com.example.stockify;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class SavedFragment extends Fragment {


    public SavedFragment(){

    }


    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Data");

        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ListView savedLV = Objects.requireNonNull(getActivity()).findViewById(R.id.userSavedLV);
                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                ArrayAdapter<Collection<Object>> savedCustomAdapters;
                ArrayList<Collection<Object>> savedList = new ArrayList<>();

                for(int i = 0; i < map.size(); i++){
                    savedList.add(Collections.singleton(String.valueOf(map.get(map.keySet().toArray()[i]))));
                }

                savedCustomAdapters = new ArrayAdapter<Collection<Object>>(getActivity(), android.R.layout.simple_list_item_1,savedList);
                savedLV.setAdapter(savedCustomAdapters);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return inflater.inflate(R.layout.fragment_saved,container,false);
    }
}
