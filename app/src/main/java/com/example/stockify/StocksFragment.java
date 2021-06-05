package com.example.stockify;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class StocksFragment extends Fragment {


    public StocksFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        new GetWeeklyData().execute();

        return inflater.inflate(R.layout.fragment_stocks,container,false);
    }
    @SuppressLint("StaticFieldLeak")
    public class GetWeeklyData extends AsyncTask<Void, Void, Void> {
        
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Button searchButtonSymbol = getActivity().findViewById(R.id.userSearchCompanyButton);
            EditText userInputSymbol = getActivity().findViewById(R.id.userSearchET);
            ListView userSearchListView = getActivity().findViewById(R.id.listViewUserSearch);
            TextView explanation = getActivity().findViewById(R.id.explanation);
            explanation.setMovementMethod(new ScrollingMovementMethod());
            explanation.setText("This part of the app allows you to find the ticker symbol for any company you are interesed in. The ticker symbol is how you search for stocks, so in order to make sure you find the correct stock for the company you want, this page allows you to find the ticker symbol. The left hand side has the ticker symbol, the middle has the company name, and the right has the company location. The ticker will be crucial for the next part of the app, which provides data about the stock you are interested in!");

            searchButtonSymbol.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {

                    ArrayAdapter<String> submitUserAdapter;

                    try {

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        JSONObject stringData = new JSONObject();

                        String userInputConfirmed = userInputSymbol.getText().toString();
                        userInputConfirmed.toUpperCase();
                        URL url = new URL("https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords="+userInputConfirmed+"&apikey=H9PSROZRHKTPCTOR");
                        URLConnection urlConnection = url.openConnection();
                        InputStream inputStream = urlConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                        String data = bufferedReader.lines().collect(Collectors.joining());
                        stringData = new JSONObject(data);

                        //getting the number of results returned by the API
                        //going to use this to create a perfect size ListView every time (since the API doesn't return the same amt of results for each search)

                        ArrayList<String> list = new ArrayList<>();

                        JSONArray numMatches = stringData.getJSONArray("bestMatches");

                        for(int i = 0; i < numMatches.length(); i++){

                            String companyName = numMatches.getJSONObject(i).get("2. name").toString();
                            String country = numMatches.getJSONObject(i).get("4. region").toString();
                            String tickerSymbol = numMatches.getJSONObject(i).get("1. symbol").toString();

                            list.add(tickerSymbol+" || "+companyName+" || "+country);

                            submitUserAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list);
                            userSearchListView.setAdapter(submitUserAdapter);

                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            super.onPostExecute(aVoid);
        }
    }
}




