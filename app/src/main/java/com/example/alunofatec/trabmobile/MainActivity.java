package com.example.alunofatec.trabmobile;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> generos;
    ListView generosListView;
    private Context context;

    private ArrayAdapter<String> generosAdapter;
   // private FilmeAdapter  filmeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generos = new ArrayList<>();
      //  filmes = new ArrayList<>();

        generosListView = (ListView) findViewById(R.id.generosListView);
       // filmesListView  = (ListView) findViewById(R.id.filmeListView);

       // filmeAdapter = new FilmeAdapter(this, filmes);
        //filmesListView.setAdapter(filmeAdapter);

        generosAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, generos);
        generosListView.setAdapter(generosAdapter);
        new ListarGeneros().execute("https://api.themoviedb.org/3/genre/movie/list?api_key=fc9931e4fcce969bfc36f0081a26ffee&language=en-US");

        generosListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int posicao = arg2;
                String generoSelecionado = generos.get(posicao).toString();

                int fim = generoSelecionado.indexOf(" ");
                String id  = generoSelecionado.substring(0,fim);
                //context.getString(R.string.id_genres,id);
              // new ListarFilmes().execute("https://api.themoviedb.org/3/discover/movie?api_key=fc9931e4fcce969bfc36f0081a26ffee&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_genres=" + id);

                Intent activityMovies = new Intent(MainActivity.this, NovoFilmeActivity.class);
                activityMovies.putExtra("id",id);
                startActivity(activityMovies);

            }

        });

    }

   /* private class ListarFilmes extends AsyncTask<String, Void, String> {

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
            filmes.clear();
            try {
                JSONObject json = new JSONObject(jsonS);
                JSONArray movies = json.getJSONArray("results");
                for (int i = 0; i < movies.length(); i++){
                    JSONObject filmeDaVez = movies.getJSONObject(i);
                    int id = filmeDaVez.getInt("id");
                    String nomeFilme = filmeDaVez.getString("title");
                    String imgPath = filmeDaVez.getString("poster_path");
                    Filme f = new Filme();
                    f.setId(id);
                    f.setTitle(nomeFilme);
                    f.setPosterpath(imgPath);

                    //Weather w = new Weather(dt, temp_min, temp_max, humidity, description, icon);
                    filmes.add(f);
                }

                //adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }*/

    private class ListarGeneros extends AsyncTask<String, Void, String> {

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
            generos.clear();
            try {
                JSONObject json = new JSONObject(jsonS);
                JSONArray genres = json.getJSONArray("genres");
                for (int i = 0; i < genres.length(); i++){
                    JSONObject objGenre = genres.getJSONObject(i);
                    int id = objGenre.getInt("id");
                    String name = objGenre.getString("name");
                    Genero g = new Genero ();
                    g.setId(id);
                    g.setNome(name);
                    //Weather w = new Weather(dt, temp_min, temp_max, humidity, description, icon);
                    generos.add(g.toString());
                }
                generosAdapter.notifyDataSetChanged();
                //adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
