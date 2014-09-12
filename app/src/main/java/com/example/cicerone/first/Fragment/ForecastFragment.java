package com.example.cicerone.first.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cicerone.first.Activity.DetailActivity;
import com.example.cicerone.first.Adapter.ItemAdapter;
import com.example.cicerone.first.Domain.Weather;
import com.example.cicerone.first.Factory.FetchWeatherTaskFactory;
import com.example.cicerone.first.Helper.AFetchWeatherTask;
import com.example.cicerone.first.R;

/**
 * Encapsulates fetching the forecast and displaying it as a {@link ListView} layout.
 */
public class ForecastFragment extends Fragment {

    private ItemAdapter _mForecastAdapter;


    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment,menu);
    }

    public void goWeather(){
        AFetchWeatherTask fwt = FetchWeatherTaskFactory.getInstance().makeFetchWeatherTask(false,_mForecastAdapter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String lat = prefs.getString(getString(R.string.pref_latitude_key),getString(R.string.pref_latitude_default));
        String lon = prefs.getString(getString(R.string.pref_longitude_key),getString(R.string.pref_longitude_default));
        fwt.execute(lat,lon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            this.goWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        /* The ArrayAdapter will take data from a source (like json) and
           use it to populate the ListView it's attached to.*/
        _mForecastAdapter =
                new ItemAdapter(
                        getActivity()
                         );

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        /* Get a reference to the ListView, and attach this adapter to it. */
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(_mForecastAdapter);

        this.goWeather();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Weather weather = (Weather)_mForecastAdapter.getItem(i);

                /* Executed in an Activity, so 'this' is the Context
                   The fileUrl is a string URL, such as "http://www.example.com/image.png"*/
                Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                detailIntent.putExtra("DAY_FORECAST", weather.getDatetime()+" : "+weather.getDescription());
                startActivity(detailIntent);
            }
        });

        return rootView;
    }

}
