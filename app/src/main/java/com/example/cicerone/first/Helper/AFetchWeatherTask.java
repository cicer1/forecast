package com.example.cicerone.first.Helper;

import android.os.AsyncTask;

import com.example.cicerone.first.Adapter.ItemAdapter;
import com.example.cicerone.first.Domain.Weather;

import org.json.JSONException;

import java.text.SimpleDateFormat;


/**
 * Created by francesco on 11/09/14.
 */
public abstract class AFetchWeatherTask extends AsyncTask<Object, Void, Weather[]> {

    protected final String LOG_TAG = AFetchWeatherTask.class.getSimpleName();
    protected ItemAdapter adapter;
    protected static String _format = "json";
    protected static String _units = "metric";

    /**
     *
     * Constructor.
     * The adapter will be used for create the weather list
     *
     * @param adapter
     */
    public AFetchWeatherTask(ItemAdapter adapter){
        this.adapter = adapter;
    }

    /**
     * Creates the list when the data is loaded and parsed
     * using the ListAdapter
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Weather[] result) {
        if (result!=null){
            this.adapter.clear();
            for (Weather singleEntry : result){
                this.adapter.addItem(singleEntry);
            }
            /* Ehi! I'm changed */
            this.adapter.notifyDataSetChanged();
        }
    }

    /**
     *
     * Formats date/time string in a human readable way
     *
     * @param time
     * @return
     */
    protected String getReadableDateString(long time, Boolean hhmm){
        /*Because the API returns a unix timestamp (measured in seconds),
          it must be converted to milliseconds in order to be converted to valid date.*/
        java.util.Date date = new java.util.Date(time * 1000);
        SimpleDateFormat format;
        if (!hhmm)
            format = new SimpleDateFormat("E d MMM");
        else
            format = new SimpleDateFormat("E d MMM hh:mm aaa");

        return format.format(date).toString();
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     *
     * NOTE: implemented in FetchExtendedWeatherTask and in FetchReducedWeatherTask
     */
    protected abstract Weather[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException;

    /**
     *
     * Do asynchronous task
     *
     * @param params
     * @return
     */
    @Override
    protected abstract Weather[] doInBackground(Object... params);


}