package com.example.hedgehog.kursach;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hedgehog on 16.04.17.
 */

public class ConnectToDB extends AsyncTask<URL, Void, String> {

    TextView textView;

    public ConnectToDB(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(URL... params) {
        String imageUrl;
        String s = "";
        String name;
        String description;
        String year;
        ArrayList<String> genres = new ArrayList<>();
        int ageLimit;

        try {
            Document doc = Jsoup.connect("http://10.0.2.2:8888/source_http.htm").get();
            name = doc.select("h1").text();
            Element element = doc.select("table[class=info]").first();
            year = element.select("tr").first().select("a").text();
            description = doc.select("div[itemprop=description").text();
            String ageLimitStr = doc.select("[class^=ageLimit]").attr("class");
            String[] asd = ageLimitStr.split("age");
            ageLimit = Integer.parseInt(asd[2]);
            imageUrl = doc.select("div[id=photoBlock]").select("img").first().attr("src");
            s += "Image URL: " + imageUrl + "\n";
            s += "Name: " + name + "\n";
            s += "Year: " + year + "\n";
            Elements elements = doc.select("span[itemprop=genre]").select("a");
            for (Element e : elements
                    ) {
                genres.add(e.text());
            }
//            s += genres.get(1) + "\n";
            s += "Genres: " + genres + "\n";
            s += "Age limit: " + ageLimit + "\n";
            s += "Description: " + description + "\n\n";


        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            Document document = Jsoup.connect("https://www.kinopoisk.ru/top/").userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                    .referrer("http://www.google.com")
//                    .ignoreHttpErrors(true)
//                    .get();
//            Elements elements = document.select("tr[id^=top250]");
//            for (Element e : elements
//                 ) {
////                s += "\n" + "https://kinopoisk.ru" + e.select("[href^=/film]").attr("href");
////                String as = "https://www.kinopoisk.ru" + e.select("[href^=/film]").attr("href");
////                Document doc1 = Jsoup.connect(as).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
////                        .referrer("http://www.google.com")
////                        .ignoreHttpErrors(true)
////                        .get();
////                name = doc1.toString();
////                s += "\n" + as;
//            }
////            Element element = document.select("tr[id^=top250]").first();
////            s += "\n" + element.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return s;
    }

    protected void onPostExecute(String result) {
        textView.setText(result);
    }

//    @Override
//    protected void onPostExecute(Bitmap bitmap) {
//
//
//        return;
//    }
}
