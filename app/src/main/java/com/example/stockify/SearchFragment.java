package com.example.stockify;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

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

        //directions for user
        TextView banner = getActivity().findViewById(R.id.findCompanySymbolBanner);
        banner.setText("Find A Stock");
    }
    public class GetWeeklyData extends AsyncTask<Void, Void, Void> {

        String currentPrice = "";
        String yesterdayPrice = "";
        TextView yesterdayHighTV = getActivity().findViewById(R.id.highOfYesterday);
        TextView currentPriceTV = getActivity().findViewById(R.id.todayPrice);
        Button searchUserStock = getActivity().findViewById(R.id.searchStockButton);
        EditText userInput = getActivity().findViewById(R.id.editTextSearch);
        String userString = "";
        ImageView companyLogo = getActivity().findViewById(R.id.companyLogo);



        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {

            GraphView graph = getActivity().findViewById(R.id.graph);

            graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
            graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
            TextView companyDescription = getActivity().findViewById(R.id.companyDesc);


            //allowing vertical scrolling for the company description TV
            companyDescription.setMovementMethod(new ScrollingMovementMethod());

            /*
                - all the API calls happen during the button click (after the user hits search)
                - Used a StrictMode ThreadPolicy to avoid leakages and making sure everything runs smoothly
             */

            searchUserStock.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {


                    userString = String.valueOf(userInput.getText());
                    userString.toUpperCase();

                    //clearing all the points on the graph I imported

                    graph.removeAllSeries();

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    /*
                        - First API call to Alpha Vantage
                        - This is the INTRADAY call to get the latest price. Using this as the first point of my graph.
                        - The intraday price will serve as the current price, and below, I'll explain what I compare it with.
                     */

                    try {
                        JSONObject stringData = new JSONObject();


                        URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="+userString+"&interval=5min&apikey=H9PSROZRHKTPCTOR");
                        URLConnection urlConnection = url.openConnection();
                        InputStream inputStream = urlConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                        String news = bufferedReader.lines().collect(Collectors.joining());
                        stringData = new JSONObject(news);

                        String varX = stringData.getJSONObject("Meta Data").get("3. Last Refreshed").toString();

                        currentPrice = String.valueOf(stringData.getJSONObject("Time Series (5min)").getJSONObject(varX).get("2. high"));

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    /*
                        - Second API call to Alpha Vantage
                        - However, this is the Time Series Daily API Call
                        - This is because I wanted to compare the current price to YESTERDAY'S HIGHEST
                        - This call will allow me to get yesterday's highest price, and then I can construct my graph
                     */
                    try {
                        JSONObject stringDataTwo = new JSONObject();


                        URL urlTwo = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+userString+"&interval=5min&apikey=H9PSROZRHKTPCTOR");
                        URLConnection urlConnectionTwo = urlTwo.openConnection();
                        InputStream inputStreamTwo = urlConnectionTwo.getInputStream();
                        BufferedReader bufferedReaderTwo = new BufferedReader(new InputStreamReader(inputStreamTwo));

                        String newsTwo = bufferedReaderTwo.lines().collect(Collectors.joining());
                        stringDataTwo = new JSONObject(newsTwo);

                        String varX2 = stringDataTwo.getJSONObject("Meta Data").get("3. Last Refreshed").toString();
                        Log.d("Var x2",varX2);
                        yesterdayPrice = stringDataTwo.getJSONObject("Time Series (Daily)").getJSONObject(varX2).get("2. high").toString();



                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    /*
                        - Third API call
                        - This is for the financial modeling prep API, and it gives me company descriptions, and image URLs, which I can load with Picasso
                        - Adds a great description if the user wants to find out more about the company
                        - The logo adds a good visualization feature for the user
                     */
                    try {
                        JSONArray sd3 = new JSONArray();

                        URL u3 = new URL("https://financialmodelingprep.com/api/v3/profile/"+userString+"?apikey=3f822374d4ea6595c66a3fd55997932c");
                        URLConnection uc3 = u3.openConnection();
                        InputStream ip3 = uc3.getInputStream();
                        BufferedReader br3 = new BufferedReader(new InputStreamReader(ip3));

                        String st3 = br3.lines().collect(Collectors.joining());
                        sd3 = new JSONArray(st3);

                        companyDescription.setText("Description: "+sd3.getJSONObject(0).getString("description"));
                        Picasso.get().load(sd3.getJSONObject(0).getString("image")).into(companyLogo);


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }


                    /*
                        - Graphs:
                            - Like I mentioned above, the graph's Y coordinates are based on yesterday's high, and the most recent price
                            - To get the trend of the graph, I decided to make it the slope of the graph
                            - The slope was calculated as so: current price - yesterday high
                            - Since it is over a 1 day time period, there is no need to divide this value by the change in X
                     */

                    graph.setTitle(userString.toUpperCase()+": Current Stock Price vs Yesterday High");

                    graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                    graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);


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


                    //Setting the linear line with the data points
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(1, Double.parseDouble(yesterdayPrice)),
                            new DataPoint(2,Double.parseDouble(currentPrice))

                    });

                    //adding the series (points) to the graph
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

