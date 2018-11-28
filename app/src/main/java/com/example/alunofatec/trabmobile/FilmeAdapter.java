package com.example.alunofatec.trabmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alunofatec.trabmobile.Filme;
import com.example.alunofatec.trabmobile.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FilmeAdapter extends ArrayAdapter<Filme> {


    public FilmeAdapter (Context context, List<Filme> filmes){
        super (context, -1, filmes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        Filme filmeDaVez = getItem(position);
        Context context = getContext();
        ViewHolder viewHolder = null;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.movie_item, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.imgFilme =
                  convertView.findViewById(R.id.imgFilme);
            viewHolder.titulo =
                    convertView.findViewById(R.id.txtTitulo);

        }
        viewHolder = (ViewHolder) convertView.getTag();
        new BaixaImagem(viewHolder.imgFilme).
                execute(context.getString(R.string.img_download_url,
                        filmeDaVez.getPosterpath()));

        viewHolder.titulo.setText(filmeDaVez.getTitle());
        return convertView;
    }
    private class ViewHolder{
        public ImageView imgFilme;
        public TextView titulo;
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
