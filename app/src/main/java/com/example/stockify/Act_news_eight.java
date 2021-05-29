package com.example.stockify;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

public class Act_news_eight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_news_eight);


        new GetArticleDetails().execute();
    }
    private class GetArticleDetails extends AsyncTask<Void, Void, Void> {


        ImageView imageView;
        TextView caption;
        TextView story;
        TextView continueReading;
        TextView author;
        TextView title;
        JSONObject stringData = new JSONObject();

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {

            imageView = findViewById(R.id.imageView);
            caption = findViewById(R.id.tvCaption);
            story = findViewById(R.id.tvStoryOrLinktoArticle);
            author = findViewById(R.id.author);
            title = findViewById(R.id.titleOfStory);
            continueReading = findViewById(R.id.urlToKeepReading);


            story.setMovementMethod(new ScrollingMovementMethod());
            continueReading.setMovementMethod(new ScrollingMovementMethod());
            caption.setMovementMethod(new ScrollingMovementMethod());


            try {

                ArrayList<String> imageList = new ArrayList<>();

                author.setText("CHECKING");

                URL url = new URL("https://newsdata.io/api/1/news?apikey=pub_3181677e360dff5788264fa1d678603f21a&q=stocks");
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String news = bufferedReader.lines().collect(Collectors.joining());
                stringData = new JSONObject(news);


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
            try {
                String imageURL = stringData.getJSONArray("results").getJSONObject(7).get("image_url").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                caption.setText("Caption: "+stringData.getJSONArray("results").getJSONObject(7).get("description").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                title.setText("Title: "+stringData.getJSONArray("results").getJSONObject(7).get("title").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                author.setText("By: "+stringData.getJSONArray("results").getJSONObject(7).getJSONArray("creator").get(0).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if(!stringData.getJSONArray("results").getJSONObject(7).get("image_url").toString().equals("null")){
                    Picasso.get().load(stringData.getJSONArray("results").getJSONObject(7).get("image_url").toString()).into(imageView);
                }
                else{
                    imageView.setImageResource(R.drawable.wallstreet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {

                story.setText("Article: "+stringData.getJSONArray("results").getJSONObject(7).get("content").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } {

            }


            try {
                continueReading.setText("Link to Article: " +stringData.getJSONArray("results").getJSONObject(7).get("link").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(aVoid);
        }
    }
}