package com.example.cicerone.first.Helper;

import com.example.cicerone.first.Adapter.ItemAdapter;
import com.example.cicerone.first.Domain.Weather;

import org.json.JSONException;


/**
 * Created by francesco on 11/09/14.
 */
public class FetchExtendedWeatherTask extends AFetchWeatherTask {

    /**
     * Constructor
     * @param adapter
     */
    public FetchExtendedWeatherTask(ItemAdapter adapter){
        super(adapter);
    }


    /**
     *
     * Parses Data loaded from OpenWeatherMap
     *
     * @param forecastJsonStr
     * @param numDays
     * @return
     * @throws JSONException
     */
    @Override
    protected Weather[] getWeatherDataFromJson(String forecastJsonStr, int numDays) throws JSONException {

        return null;
    }

    /**
     *
     * Loads data asynchronously from OpenWeatherMap
     *
     * @param params
     * @return
     */
    @Override
    protected Weather[] doInBackground(Object... params) {

        return null;

    }
}
