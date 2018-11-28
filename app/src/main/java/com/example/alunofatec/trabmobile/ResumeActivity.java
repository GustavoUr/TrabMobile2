package com.example.alunofatec.trabmobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alunofatec.trabmobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ResumeActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume);

        Bundle extras = getIntent().getExtras();

        String idFilme = extras.getString("id");
        //get the value based on the key

        new ResumeActivity.DetalhesFilme().execute("https://api.themoviedb.org/3/movie/"+idFilme+"?api_key=fc9931e4fcce969bfc36f0081a26ffee&language=en-US" );


    }
    private class DetalhesFilme extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            URL url = null;
            try {
                url = new URL(urls[0]);

                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                InputStream inputStream =
                        connection.getInputStream();
                InputStreamReader inputStreamReader =
                        new InputStreamReader(inputStream);
                BufferedReader reader =
                        new BufferedReader(inputStreamReader);
                String linha = null;
                StringBuilder sb = new StringBuilder();
                while ((linha = reader.readLine()) != null){
                    sb.append(linha);
                }
                reader.close();
                return sb.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonS) {
            //Context context = getApplicationContext();
            TextView txtSinopse = (TextView) findViewById(R.id.txtSinopse);
            ImageView imgFilmeSelecionado = (ImageView) findViewById(R.id.imgFilmeSelecionado);
            try {
                JSONObject json = new JSONObject(jsonS);

                String posterPath = json.getString("poster_path");
                String overview = json.getString("overview");


                txtSinopse.setText(overview);
                new BaixaImagem(imgFilmeSelecionado).
                           execute("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + posterPath);


                //adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        private class BaixaImagem extends
                AsyncTask<String, Void, Bitmap> {

            private ImageView imgFilme;
            public BaixaImagem (ImageView imgFilme){
                this.imgFilme = imgFilme;
            }
            @Override
            protected void onPostExecute(Bitmap figura) {
                imgFilme.setImageBitmap(figura);
            }

            @Override
            protected Bitmap doInBackground(String... urls) {
                try {
                    URL url = new URL(urls[0]);
                    HttpURLConnection connection =
                            (HttpURLConnection) url.openConnection();
                    InputStream inputStream =
                            connection.getInputStream();
                    Bitmap figura =
                            BitmapFactory.decodeStream(inputStream);
                    return figura;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

            }
        }

    }
}
