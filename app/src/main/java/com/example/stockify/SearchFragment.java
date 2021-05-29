package com.example.stockify;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
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
import java.text.DateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

public class SearchFragment extends Fragment {
    public SearchFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        new GetWeeklyData().execute();

        TextView banner = getActivity().findViewById(R.id.findStock);




        banner.setText("Find A Stock");
    }
    public class GetWeeklyData extends AsyncTask<Void, Void, Void> {

        JSONObject stringData = new JSONObject();
        String currentPrice = "";
        String yesterdayPrice = "";
        TextView yesterdayHighTV = getActivity().findViewById(R.id.highOfYesterday);
        TextView currentPriceTV = getActivity().findViewById(R.id.todayPrice);
        Button searchUserStock = getActivity().findViewById(R.id.searchStockButton);
        EditText userInput = getActivity().findViewById(R.id.editTextSearch);
        String userString = "";



        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {

            GraphView graph = getActivity().findViewById(R.id.graph);

            TextView companyDescription = getActivity().findViewById(R.id.companyDesc);

            companyDescription.setMovementMethod(new ScrollingMovementMethod());

            searchUserStock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    userString = String.valueOf(userInput.getText());
                    Log.d("Messi",userString);

                    graph.removeAllSeries();

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);


                    try {
                        JSONObject stringData = new JSONObject();


                        URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="+userString+"&interval=5min&apikey=ELX8PENIO5OJLHGG");
                        URLConnection urlConnection = url.openConnection();
                        InputStream inputStream = urlConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                        Log.d("URL",url.toString());

                        String news = bufferedReader.lines().collect(Collectors.joining());
                        stringData = new JSONObject(news);

                        String varX = stringData.getJSONObject("Meta Data").get("3. Last Refreshed").toString();

                        Log.d("check",varX);

                        currentPrice = String.valueOf(stringData.getJSONObject("Time Series (5min)").getJSONObject(varX).get("2. high"));

                        Log.d("last",currentPrice);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    try {
                        JSONObject stringDataTwo = new JSONObject();


                        URL urlTwo = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+userString+"&interval=5min&apikey=ELX8PENIO5OJLHGG");
                        URLConnection urlConnectionTwo = urlTwo.openConnection();
                        InputStream inputStreamTwo = urlConnectionTwo.getInputStream();
                        BufferedReader bufferedReaderTwo = new BufferedReader(new InputStreamReader(inputStreamTwo));

                        String newsTwo = bufferedReaderTwo.lines().collect(Collectors.joining());
                        stringDataTwo = new JSONObject(newsTwo);

                        Log.d("tsla",urlTwo.toString());
                        String varX2 = stringDataTwo.getJSONObject("Meta Data").get("3. Last Refreshed").toString();

                        Log.d("polo",varX2);


                        yesterdayPrice = stringDataTwo.getJSONObject("Time Series (Daily)").getJSONObject(varX2).get("2. high").toString();
                        Log.d("first",yesterdayPrice);


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray sd3 = new JSONArray();

                        URL u3 = new URL("https://financialmodelingprep.com/api/v3/profile/"+userString+"?apikey=3f822374d4ea6595c66a3fd55997932c");
                        URLConnection uc3 = u3.openConnection();
                        InputStream ip3 = uc3.getInputStream();
                        BufferedReader br3 = new BufferedReader(new InputStreamReader(ip3));

                        String st3 = br3.lines().collect(Collectors.joining());
                        sd3 = new JSONArray(st3);

                        Log.d("URL",u3.toString());
                        companyDescription.setText("Description: "+sd3.getJSONObject(0).getString("description"));


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }


                    graph.setTitle(userString+": Current Stock Price vs Yesterday High");



                    Double slope = Double.parseDouble(currentPrice)-Double.parseDouble(yesterdayPrice);
                    if(slope>0){
                        graph.setTitleColor(Color.GREEN);
                    }
                    if(slope<0){
                        graph.setTitleColor(Color.RED);
                    }
                    if(slope==0){
                        graph.setTitleColor(Color.DKGRAY);

                    }



                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(1, Double.parseDouble(yesterdayPrice)),
                            new DataPoint(2,Double.parseDouble(currentPrice))

                            //make sure to watch these and make sure its going in right order
                    });
                    graph.addSeries(series);

                    yesterdayHighTV.setText("Yesterday High: $"+yesterdayPrice);
                    currentPriceTV.setText("Current Price: $"+currentPrice);


                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            super.onPostExecute(aVoid);
        }
    }
}

