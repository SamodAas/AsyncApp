package com.example.asyncapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    private Spinner optionSpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private ArrayAdapter<String> listViewAdapter;
    public static ArrayList<String> currencyInfo = new ArrayList<>();
    public static String currencyOption;
    private ListView exchangeRateList;
    private TextView chosenCurrency;
    private String loading = "Fetching...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.exchangeRateList = findViewById(R.id.currencyListView);
        this.optionSpinner = findViewById(R.id.currencyOptions);
        this.chosenCurrency = findViewById(R.id.currencyDisplay);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.optionsForSpinner, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        optionSpinner.setAdapter(spinnerAdapter);

    }

    public void getExchangeRates(View view) throws InterruptedException, ExecutionException, TimeoutException, ParserConfigurationException, SAXException, IOException {

        currencyInfo.clear();
        currencyOption = optionSpinner.getSelectedItem().toString();
        ExecutorService backgroundProc = Executors.newSingleThreadExecutor();
        backgroundProc.submit(() -> {
            try {
                DataManager.getRateFromECB(currencyOption);
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            }

        });
        backgroundProc.shutdown();
        try {
            backgroundProc.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }

        listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,currencyInfo);
        exchangeRateList.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();
        chosenCurrency.setText(currencyOption);

        }

    }
