package com.example.cicerone.first.Helper;

import android.net.Uri;
import android.util.Log;

import com.example.cicerone.first.Adapter.ItemAdapter;
import com.example.cicerone.first.Domain.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by francesco on 11/09/14.
 */
public class FetchReducedWeatherTask extends AFetchWeatherTask {


    private static int _numDays = 7;

    /**
     * Constructor
     * @param adapter
     */
    public FetchReducedWeatherTask(ItemAdapter adapter){
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
        /* These are the names of the JSON objects that need to be extracted. */
        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_DATETIME = "dt";
        final String OWM_DESCRIPTION = "main";
        final String OWM_ICON = "icon";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        Weather[] result_data = new Weather[numDays];
        Weather weather;
        for(int i = 0; i < weatherArray.length(); i++) {
            /* For now, using the format "Day, description, hi/low" */
            weather = new Weather();

            /* Get the JSON object representing the day */
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            /* The date/time is returned as a long.  We need to convert that
               into something human-readable, since most people won't read "1400356800" as
               "this saturday". */
            long dateTime = dayForecast.getLong(OWM_DATETIME);
            weather.setDatetime(getReadableDateString(dateTime,false));

            /* description is in a child array called "weather", which is 1 element long. */
            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            weather.setDescription(weatherObject.getString(OWM_DESCRIPTION));

            /* icon is in a child array called "weather" */
            JSONObject IconObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            weather.setImage(IconObject.getString(OWM_ICON));

            result_data[i] = weather;

        }



        return result_data;
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

        /* These two need to be declared outside the try/catch
           so that they can be closed in the finally block. */
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        /* Will contain the raw JSON response as a string. */
        String forecastJsonStr = null;

        try {

            final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
            final String LAT_PARAM = "lat";
            final String LON_PARAM = "lon";
            final String FORMAT_PARAM = "mode";
            final String UNITS_PARAM = "units";
            final String DAYS_PARAM = "cnt";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(LAT_PARAM,String.valueOf(params[0]))
                    .appendQueryParameter(LON_PARAM,String.valueOf(params[1]))
                    .appendQueryParameter(FORMAT_PARAM, FetchReducedWeatherTask._format)
                    .appendQueryParameter(UNITS_PARAM, FetchReducedWeatherTask._units)
                    .appendQueryParameter(DAYS_PARAM, Integer.toString(FetchReducedWeatherTask._numDays))
                    .build();

            URL url = new URL(builtUri.toString());

            /* Create the request to OpenWeatherMap, and open the connection */
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            /* Read the input stream into a String */
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                /* Nothing to do. */
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                /* Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                   But it does make debugging a *lot* easier if you print out the completed
                   buffer for debugging.*/
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                /* Stream was empty.  No point in parsing. */
                return null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            /*If the code didn't successfully get the weather data, there's no point in attemping
              to parse it.*/
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return getWeatherDataFromJson(forecastJsonStr, FetchReducedWeatherTask._numDays);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
