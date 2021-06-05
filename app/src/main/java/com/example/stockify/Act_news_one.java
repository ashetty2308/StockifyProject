package com.example.stockify;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.stream.Collectors;

public class Act_news_one extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_one);

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
        FloatingActionButton floatingActionButton;
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {

            imageView = findViewById(R.id.imageView);
            caption = findViewById(R.id.tvCaption);
            story = findViewById(R.id.tvStoryOrLinktoArticle);
            author = findViewById(R.id.author);
            title = findViewById(R.id.titleOfStory);
            continueReading = findViewById(R.id.urlToKeepReading);
            floatingActionButton = findViewById(R.id.floatingActionButton);

            story.setMovementMethod(new ScrollingMovementMethod());
            continueReading.setMovementMethod(new ScrollingMovementMethod());
            caption.setMovementMethod(new ScrollingMovementMethod());

            try {

                URL url = new URL("https://newsdata.io/api/1/news?apikey=pub_34062194a32fb87e2ed783d7187b10ed9bd&q=stocks");
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


            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        databaseReference1 = databaseReference1.child("Data").push();
                        databaseReference1.setValue(stringData.getJSONArray("results").getJSONObject(0).get("title").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            try {
                String imageURL = stringData.getJSONArray("results").getJSONObject(0).get("image_url").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String captionStr = stringData.getJSONArray("results").getJSONObject(0).get("description").toString();
                if(!captionStr.equalsIgnoreCase("null")){
                    caption.setText("Caption: "+stringData.getJSONArray("results").getJSONObject(0).get("description").toString());
                }
                else{
                    caption.setText("No caption provided!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                title.setText("Title: "+stringData.getJSONArray("results").getJSONObject(0).get("title").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String authorStr = stringData.getJSONArray("results").getJSONObject(0).get("creator").toString();
                if(!authorStr.contains("null")){
                    author.setText("By: "+stringData.getJSONArray("results").getJSONObject(0).getJSONArray("creator").get(0).toString());
                }
                else if(!authorStr.contains("null")){
                    author.setText("No author provided!");
                }
                else{
                    author.setText("No author provided!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if(!stringData.getJSONArray("results").getJSONObject(0).get("image_url").toString().equals("null")){
                    Picasso.get().load(stringData.getJSONArray("results").getJSONObject(0).get("image_url").toString()).into(imageView);
                }
                else{
                    imageView.setImageResource(R.drawable.wallstreet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String storyStr = stringData.getJSONArray("results").getJSONObject(0).get("content").toString();
                if(!storyStr.equalsIgnoreCase("null")){
                    story.setText("Article: "+stringData.getJSONArray("results").getJSONObject(0).get("content").toString());
                }
                else{
                    story.setText("No story provided!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } {

            }
            try {
                continueReading.setText("Link to Article: " +stringData.getJSONArray("results").getJSONObject(0).get("link").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }
    }
}