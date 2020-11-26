package com.example.asyncapp;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

public class DataManager {


    public static void getRateFromECB(String currencyCode) throws IOException, ParserConfigurationException, SAXException {

            String url = String.format("http://www.floatrates.com/daily/%s.xml",currencyCode);
            InputStream stream = downloadUrl(url);
        XmlParser.getRateFromECB(stream);

        try {

                XmlParser.getRateFromECB(stream);
            }
            finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        private static InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            return conn.getInputStream();
}}
