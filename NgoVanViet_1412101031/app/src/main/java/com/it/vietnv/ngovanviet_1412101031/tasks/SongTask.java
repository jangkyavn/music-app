package com.it.vietnv.ngovanviet_1412101031.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.it.vietnv.ngovanviet_1412101031.helpers.XMLDOMParser;
import com.it.vietnv.ngovanviet_1412101031.interfaces.IAsyncListener;
import com.it.vietnv.ngovanviet_1412101031.models.Song;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class SongTask extends AsyncTask<String, Void, String> {
    private IAsyncListener listener = null;
    private final String BASE_URL = "https://ct1801hpu.000webhostapp.com/music-app";

    public SongTask(IAsyncListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        XMLDOMParser parser = new XMLDOMParser();
        Document document = parser.getDocument(s);

        NodeList nodeList = document.getElementsByTagName("song");

        ArrayList<Song> arrNews = new ArrayList<>();
        Song song = null;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);

            song = new Song();
            song.setId(Integer.parseInt(parser.getValue(element, "id")));
            song.setSongName(parser.getValue(element, "songName"));
            song.setSingerName(parser.getValue(element, "singerName"));
            song.setAvatar(BASE_URL + parser.getValue(element, "singerAvatar"));
            song.setLink(BASE_URL + parser.getValue(element, "songLink"));

            arrNews.add(song);
        }

        listener.doStuff(arrNews);
    }

    @Override
    protected String doInBackground(String... strings) {
        return docNoiDung_Tu_URL(strings[0]);
    }

    private String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}
