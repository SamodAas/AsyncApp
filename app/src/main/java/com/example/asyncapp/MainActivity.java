package com.example.asyncapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    private Spinner optionSpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private ArrayAdapter<String> listViewAdapter;
    private ArrayList currencyInfo;
    private String currencyOption;
    private ListView exchangeRateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.exchangeRateList = findViewById(R.id.currencyListView);
        this.optionSpinner = findViewById(R.id.currencyOptions);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.optionsForSpinner, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        optionSpinner.setAdapter(spinnerAdapter);

    }

    public void getExchangeRates(View view) throws InterruptedException, ExecutionException, TimeoutException {
        currencyOption = optionSpinner.getSelectedItem().toString();
        CallableProcess getCur = new CallableProcess(currencyOption);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<ArrayList> future = executorService.submit(getCur);

        listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, future.get(100, TimeUnit.MILLISECONDS) );
        exchangeRateList.setAdapter(listViewAdapter);


    }
}