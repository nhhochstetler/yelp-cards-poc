package com.whattoeat.natehochstetler.whattoeat;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
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

public class MainActivity extends AppCompatActivity implements AsyncResponse, AAH_FabulousFragment.Callbacks {

    private SwipeCardsView swipeCardsView;
    private ArrayList<Business> businessList = new ArrayList<>();
    private String latitude;
    private String longitude;
    protected LocationManager locationManager;
    private CardAdapter cardAdapter;
    private boolean filterView = false;
    private Map<String, String> parameters = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterFragment dialogFrag = FilterFragment.newInstance();
                dialogFrag.setParentFab(fab);
                dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
            }
        });

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
//                TextView direction = (TextView)findViewById(R.id.textView2);
//                switch(type) {
//                    case LEFT:
//                        Log.d("TYPE", "LEFT");
//                        direction.setText("LEFT");
//                        break;
//                    case RIGHT:
//                        Log.d("TYPE", "RIGHT");
//                        direction.setText("RIGHT");
//                        break;
//                    case NONE:
//                        Log.d("TYPE", "NONE");
//                        direction.setText("NONE");
//                        break;
//                }
            }

            @Override
            public void onItemClick(View cardImageView, int index) {
//                TextView direction = (TextView)findViewById(R.id.textView2);
//
//                Log.d("TYPE", "CLICK");
//                direction.setText("CLICKED " + Integer.toString(index));
            }
        });
    }


    private void getData() {
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
        cardAdapter = new CardAdapter(businessList, this);
        swipeCardsView.setAdapter(cardAdapter);
    }

    @Override
    public void onResult(Object result) {
        parameters.put("open_now",result.toString());
    }
}
