package com.whattoeat.natehochstetler.whattoeat;

import com.yelp.fusion.client.models.Business;

import java.util.ArrayList;

public interface AsyncResponse {
    void processFinish(ArrayList<Business> output);
}