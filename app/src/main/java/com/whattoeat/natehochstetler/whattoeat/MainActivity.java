package com.whattoeat.natehochstetler.whattoeat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.huxq17.swipecardsview.SwipeCardsView;
import com.whattoeat.natehochstetler.whattoeat.Adapter.CardAdapter;
import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AsyncResponse, LocationListener {

    private SwipeCardsView swipeCardsView;
    private ArrayList<Business> businessList = new ArrayList<>();
    private String latitude;
    private String longitude;
    protected LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // The ACCESS_COARSE_LOCATION is denied, then I request it and manage the result in
//            // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
//            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
//                        11);
//            }
//            // The ACCESS_FINE_LOCATION is denied, then I request it and manage the result in
//            // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
//            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
//                ActivityCompat.requestPermissions(this,
//                        new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
//                        12);
//            }
//        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, this);
//        }

        getData();

        swipeCardsView = (SwipeCardsView) findViewById(R.id.swipeCardsView);
        swipeCardsView.retainLastCard(true);
        swipeCardsView.enableSwipe(true);
        swipeCardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {

            @Override
            public void onShow(int index) {

            }

            @Override
            public void onCardVanish(int index, SwipeCardsView.SlideType type) {
                TextView direction = (TextView)findViewById(R.id.textView2);
                switch(type) {
                    case LEFT:
                        Log.d("TYPE", "LEFT");
                        direction.setText("LEFT");
                        break;
                    case RIGHT:
                        Log.d("TYPE", "RIGHT");
                        direction.setText("RIGHT");
                        break;
                    case NONE:
                        Log.d("TYPE", "NONE");
                        direction.setText("NONE");
                        break;
                }
            }

            @Override
            public void onItemClick(View cardImageView, int index) {

            }
        });
    }


    private void getData() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("limit","50");
        parameters.put("open_now","true");
        parameters.put("categories","restaurants");
        parameters.put("latitude","33.2003653");
        parameters.put("longitude","-87.5505516");
        new YelpCall(this, getResources().getString(R.string.clientId), getResources().getString(R.string.clientSecret)).execute(parameters);

    }

    @Override
    public void processFinish(ArrayList<Business> output) {
        businessList.addAll(output);
        CardAdapter cardAdapter = new CardAdapter(businessList, this);
        swipeCardsView.setAdapter(cardAdapter);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        latitude = Double.toString(location.getLatitude());
        longitude = Double.toString(location.getLongitude());
        Log.d("Coordinates", latitude + ", " + longitude);

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

}
