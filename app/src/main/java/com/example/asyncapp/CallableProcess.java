package com.example.asyncapp;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CallableProcess implements Callable<ArrayList> {

    private String chosenCurrency;
    ArrayList<String> currencyList= new ArrayList<String>();

    public CallableProcess(String currency){
        chosenCurrency=currency;
    }

    public ArrayList<String> Call() throws InvalidParameterException{
        currencyList.set(0,chosenCurrency);



        return currencyList;
    }

   @Override
    public ArrayList<String> call() throws Exception {
        ArrayList<String> name=new ArrayList();
        name.set(0, Thread.currentThread().getName());
        return name;
    }

}
