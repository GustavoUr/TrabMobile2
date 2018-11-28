package com.example.alunofatec.trabmobile;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class NovoFilmeActivity extends AppCompatActivity {

    ArrayList<Filme> filmes;
    ListView filmesListView;
    private FilmeAdapter  filmeAdapter;
    private Context context;
    String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies);
        filmes = new ArrayList<>();
        filmesListView  = (ListView) findViewById(R.id.filmeListView);
        filmeAdapter = new FilmeAdapter(this, filmes);
        filmesListView.setAdapter(filmeAdapter);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
//get the value based on the key
        }

        new ListarFilmes().execute("https://api.themoviedb.org/3/discover/movie?api_key=fc9931e4fcce969bfc36f0081a26ffee&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_genres=" + id );
        filmesListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int posicao = arg2;
                String filmeSelecionado = filmes.get(posicao).getId();

                //context.getString(R.string.id_genres,id);
                // new ListarFilmes().execute("https://api.themoviedb.org/3/discover/movie?api_key=fc9931e4fcce969bfc36f0081a26ffee&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_genres=" + id);

                Intent activityMovies = new Intent(NovoFilmeActivity.this, ResumeActivity.class);
                activityMovies.putExtra("id",filmeSelecionado);
                startActivity(activityMovies);

            }

        });
        /*nomeEditText = findViewById(R.id.nomeEditText);
        foneEditText = findViewById(R.id.foneEditText);
        emailEditText = findViewById(R.id.emailEditText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = nomeEditText.getEditableText().toString();
                String fone = foneEditText.getEditableText().toString();
                String email = emailEditText.getEditableText().toString();
                String chave = contatosReference.push().getKey();
                Contato contato = new Contato(chave, nome, fone, email);
                contatosReference.child(chave).setValue(contato);
                Toast.makeText(AdicionaContatoActivity.this,
                        getString(R.string.contato_adicionado),
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });*/
    }
    private class ListarFilmes extends AsyncTask<String, Void, String> {

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
                int tamanho = movies.length();
                for (int i = 0; i < tamanho; i++){
                    JSONObject filmeDaVez = movies.getJSONObject(i);
                    String id = filmeDaVez.getString("id");
                    String nomeFilme = filmeDaVez.getString("title");
                    String imgPath = filmeDaVez.getString("poster_path");
                    Filme f = new Filme();
                    f.setId(id);
                    f.setTitle(nomeFilme);
                    f.setPosterpath(imgPath);

                    //Weather w = new Weather(dt, temp_min, temp_max, humidity, description, icon);
                    filmes.add(f);
                }
                filmeAdapter.notifyDataSetChanged();

                //adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
