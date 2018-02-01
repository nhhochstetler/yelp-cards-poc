package com.whattoeat.natehochstetler.whattoeat;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class YelpCall extends AsyncTask<Map<String, String>, String, ArrayList<Business>> {

    AsyncResponse activity;
    String clientId;
    String clientSecret;

    public YelpCall(Activity activity, String clientId, String clientSecret) {
        this.activity = (AsyncResponse) activity;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    protected ArrayList<Business> doInBackground(Map<String, String>... parameters) {

        try {
            YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
            YelpFusionApi yelpFusionApi = apiFactory.createAPI(clientId, clientSecret);

            Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(parameters[0]);

            Response<SearchResponse> response = call.execute();
            return response.body().getBusinesses();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Business> result) {
        super.onPostExecute(result);
        activity.processFinish(result);
    }



}
