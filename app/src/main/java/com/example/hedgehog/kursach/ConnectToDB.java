package com.example.hedgehog.kursach;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hedgehog.kursach.database.Films;

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

public class ConnectToDB extends AsyncTask<URL, Void, ArrayList<Films>> {

    private ArrayList<Films> films;

    public ConnectToDB(ArrayList<Films> films) throws IOException {
        this.films = films;
    }

    @Override
    protected ArrayList<Films> doInBackground(URL... params) {
        String imageUrl;
        String s = "";
        String name;
        String description;
        int year;
        String genres = "";
        int ageLimit;
//        Long idCount = Long.valueOf(50);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String imageUrl;
                String name;
                String description;
                int year;
                String genres = "";
                int ageLimit;
                try {
                    Document document = Jsoup.connect("https://www.kinopoisk.ru/top/").get();
                    Elements elements = document.select("tr[id^=top250]");
                    for (Element e : elements) {
                        Thread.sleep(300);
                        Document doc = Jsoup.connect("https://www.kinopoisk.ru" + e.select("[href^=/film]").attr("href")).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                                .referrer("http://www.google.com")
                                .ignoreHttpErrors(true)
                                .get();
                        name = doc.select("h1").text();
                        Element element = doc.select("table[class=info]").first();
                        String yearStr;
                        if (element.select("tr").first().select("a").text() == null) {
                            yearStr = element.select("tr").first().select("a").text();
                            year = Integer.parseInt(yearStr);
                        } else {
                            year = 0;
                        }
                        description = doc.select("div[itemprop=description").text();
                        String ageLimitStr = doc.select("[class^=ageLimit]").attr("class");
                        String[] asd = ageLimitStr.split("age");
                        if (asd.length > 3) {
                            ageLimit = Integer.parseInt(asd[2]);
                        } else {
                            ageLimit = 0;
                        }
                        imageUrl = doc.select("div[id=photoBlock]").select("img").first().attr("src");
                        Films f1 = new Films(null, name, genres, description, year, ageLimit, 0, imageUrl);
                        films.add(f1);
                        Elements elements1 = doc.select("span[itemprop=genre]").select("a");
                        genres = "";
                        for (Element e1 : elements1
                                ) {
                            genres += e1.text() + ", ";
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        try {
//            Document document = Jsoup.connect("https://www.kinopoisk.ru/top/").get();
////            Thread.sleep(5000);
////            Element element = document.select("tr[id^=top250]").first();
////                Document doc = Jsoup.connect("https://www.kinopoisk.ru/film/32346/").get();
//
////            Log.d("Doc parse", "doInBackground: " + doc.toString());
//            Elements elements = document.select("tr[id^=top250]");
//            for (Element e : elements) {
//                Document doc = Jsoup.connect("https://www.kinopoisk.ru" + e.select("[href^=/film]").attr("href")).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                    .referrer("http://www.google.com")
//                    .ignoreHttpErrors(true)
//                        .get();
//                name = doc.select("h1").text();
//                Element element = doc.select("table[class=info]").first();
//                String yearStr = element.select("tr").first().select("a").text();
//                year = Integer.parseInt(yearStr);
//                description = doc.select("div[itemprop=description").text();
//                String ageLimitStr = doc.select("[class^=ageLimit]").attr("class");
//                String[] asd = ageLimitStr.split("age");
//                if (asd.length > 3) {
//                    ageLimit = Integer.parseInt(asd[2]);
//                } else {
//                    ageLimit = 0;
//                }
//                imageUrl = doc.select("div[id=photoBlock]").select("img").first().attr("src");
//                Films f1 = new Films(null, name, genres, description, year, ageLimit, 0, imageUrl);
//                films.add(f1);
////                idCount = Long.valueOf(idCount + 1);
////                s += "Image URL: " + imageUrl + "\n";
////                s += "Name: " + name + "\n";
////                s += "Year: " + year + "\n";
//                Elements elements1 = doc.select("span[itemprop=genre]").select("a");
//                genres = "";
//                for (Element e1 : elements1
//                        ) {
//                    genres += e1.text() + ", ";
//                }
////            s += genres.get(1) + "\n";
//                s += "Genres: " + genres + "\n";
//                s += "Age limit: " + ageLimit + "\n";
//                s += "Description: " + description + "\n\n";
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } //catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        try {
//            Document doc = Jsoup.connect("https://www.kinopoisk.ru/film/326/").userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                    .referrer("http://www.google.com")
//                    .ignoreHttpErrors(true)
//                    .get();
////            Log.d(doc.toString(), "doInBackground: ");
//            name = doc.select("h1").text();
//            Element element = doc.select("table[class=info]").first();
//
//            description = doc.select("div[itemprop=description").text();
////            Log.d(description, "doInBackground: ");
//            String ageLimitStr = doc.select("[class^=ageLimit]").attr("class");
//            String[] asd = ageLimitStr.split("age");
//            String yearStr = element.select("tr").first().select("a").text();
//            year = Integer.parseInt(yearStr);
//            ageLimit = Integer.parseInt(asd[2]);
//            imageUrl = doc.select("div[id=photoBlock]").select("img").first().attr("src");
//            s += "Image URL: " + imageUrl + "\n";
//            s += "Name: " + name + "\n";
//            s += "Year: " + year + "\n";
//            Elements elements = doc.select("span[itemprop=genre]").select("a");
//            for (Element e : elements
//                    ) {
//                genres += e.text() + "";
//            }
////            s += genres.get(1) + "\n";
//            s += "Genres: " + genres + "\n";
//            s += "Age limit: " + ageLimit + "\n";
//            s += "Description: " + description + "\n\n";
//            Films f1 = new Films(null, name, genres, description, year, ageLimit, 0, imageUrl);
//            films.add(f1);
//            Log.d("Parse", "doInBackground: " + "Name: " + f1.getName() + "\nGenres: " + f1.getGenres() + "\nDescription: " + description + "\nYear: " + f1.getYear() + "\nAge limit: " + f1.getAgeLimit());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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


        return films;
    }

//    @Override
//    protected String doInBackground(ArrayList<Films>... params) {
//        return null;
//    }

    protected void onPostExecute(ArrayList<Films> result) {
        Log.d("Result size = " + result.size(), "onPostExecute: ");
        films.addAll(result);
        Log.d("Films in result size = " + films.size(), "onPostExecute: ");
    }

//    @Override
//    protected void onPostExecute(Bitmap bitmap) {
//
//
//        return;
//    }
}
