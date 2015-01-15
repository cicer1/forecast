package com.example.cicerone.first.Factory;

import com.example.cicerone.first.Adapter.ItemAdapter;
import com.example.cicerone.first.Helper.AFetchWeatherTask;
import com.example.cicerone.first.Helper.FetchExtendedWeatherTask;
import com.example.cicerone.first.Helper.FetchReducedWeatherTask;

import java.util.HashMap;

/**
 * Created by francesco on 11/09/14.
 */
public class FetchWeatherTaskFactory {

    private static FetchWeatherTaskFactory _instance = null;

    /**
     * According to extendedData gives an instance of FetchReducedWeatherTask or FetchExtendedWeatherTask
     *
     * @param extendedData
     * @return
     */

    public AFetchWeatherTask makeFetchWeatherTask(Boolean extendedData, ItemAdapter adapter){
        return (extendedData)? new FetchExtendedWeatherTask(adapter) : new FetchReducedWeatherTask(adapter);
    }

    /**
     * Singleton
     * @return
     */
    public static FetchWeatherTaskFactory getInstance(){
        if(FetchWeatherTaskFactory._instance==null)
            FetchWeatherTaskFactory._instance = new FetchWeatherTaskFactory();

        return FetchWeatherTaskFactory._instance;
    }

}
