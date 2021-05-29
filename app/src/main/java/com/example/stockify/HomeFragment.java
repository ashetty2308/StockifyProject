package com.example.stockify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class HomeFragment extends Fragment {


    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView headline = (TextView)getActivity().findViewById(R.id.textView);
        TextView description = (TextView)getActivity().findViewById(R.id.desc);

        headline.setText("Market News");
        description.setText("10 Headlines to get you up to speed");

        new GetStockNews().execute();


    }

    private class GetStockNews extends AsyncTask<Void, Void, Void> {

        ListView listView = getActivity().findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter;

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {

            try {

                JSONObject stringData = new JSONObject();
                ArrayList<String> titleList = new ArrayList<>();
                ArrayList<String> imageList = new ArrayList<>();

                URL url = new URL("https://newsdata.io/api/1/news?apikey=pub_3181677e360dff5788264fa1d678603f21a&q=stocks");
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String news = bufferedReader.lines().collect(Collectors.joining());
                stringData = new JSONObject(news);


                titleList.add(stringData.getJSONArray("results").getJSONObject(0).get("title").toString());
                titleList.add(stringData.getJSONArray("results").getJSONObject(1).get("title").toString());
                titleList.add(stringData.getJSONArray("results").getJSONObject(2).get("title").toString());
                titleList.add(stringData.getJSONArray("results").getJSONObject(3).get("title").toString());
                titleList.add(stringData.getJSONArray("results").getJSONObject(4).get("title").toString());
                titleList.add(stringData.getJSONArray("results").getJSONObject(5).get("title").toString());
                titleList.add(stringData.getJSONArray("results").getJSONObject(6).get("title").toString());
                titleList.add(stringData.getJSONArray("results").getJSONObject(7).get("title").toString());
                titleList.add(stringData.getJSONArray("results").getJSONObject(8).get("title").toString());
                titleList.add(stringData.getJSONArray("results").getJSONObject(9).get("title").toString());

                imageList.add(stringData.getJSONArray("results").getJSONObject(0).get("image_url").toString());
                imageList.add(stringData.getJSONArray("results").getJSONObject(1).get("image_url").toString());
                imageList.add(stringData.getJSONArray("results").getJSONObject(2).get("image_url").toString());
                imageList.add(stringData.getJSONArray("results").getJSONObject(3).get("image_url").toString());
                imageList.add(stringData.getJSONArray("results").getJSONObject(4).get("image_url").toString());
                imageList.add(stringData.getJSONArray("results").getJSONObject(5).get("image_url").toString());
                imageList.add(stringData.getJSONArray("results").getJSONObject(6).get("image_url").toString());
                imageList.add(stringData.getJSONArray("results").getJSONObject(7).get("image_url").toString());
                imageList.add(stringData.getJSONArray("results").getJSONObject(8).get("image_url").toString());
                imageList.add(stringData.getJSONArray("results").getJSONObject(9).get("image_url").toString());


                arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,titleList);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if(position==0){
                        Intent x = new Intent(getActivity(),Act_news_one.class);
                        startActivity(x);
                    }

                    if(position==1){
                        Intent x = new Intent(getActivity(),Act_news_two.class);
                        startActivity(x);
                    }

                    if(position==2){
                        Intent x = new Intent(getActivity(),Act_news_three.class);
                        startActivity(x);
                    }

                    if(position==3){
                        Intent x = new Intent(getActivity(),Act_news_four.class);
                        startActivity(x);
                    }

                    if(position==4){
                        Intent x = new Intent(getActivity(),Act_news_five.class);
                        startActivity(x);
                    }

                    if(position==5){
                        Intent x = new Intent(getActivity(),Act_news_six.class);
                        startActivity(x);
                    }

                    if(position==6){
                        Intent x = new Intent(getActivity(),Act_news_7.class);
                        startActivity(x);
                    }

                    if(position==7){
                        Intent x = new Intent(getActivity(),Act_news_eight.class);
                        startActivity(x);
                    }

                    if(position==8){
                        Intent x = new Intent(getActivity(),Act_news_nine.class);
                        startActivity(x);
                    }

                    if(position==9){
                        Intent x = new Intent(getActivity(),Act_news_ten.class);
                        startActivity(x);
                    }

                }
            });
        }
    }
}
