package com.whattoeat.natehochstetler.whattoeat.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huxq17.swipecardsview.BaseCardAdapter;
import com.squareup.picasso.Picasso;
import com.whattoeat.natehochstetler.whattoeat.R;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.Category;

import java.util.List;

import me.grantland.widget.AutofitHelper;
import me.grantland.widget.AutofitTextView;

public class CardAdapter extends BaseCardAdapter {

    private List<Business> businessList;
    private Context context;

    public CardAdapter(List<Business> businessList, Context context) {
        this.businessList = businessList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return businessList.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.card_view;
    }

    @Override
    public void onBindData(int position, View cardview) {
        if (businessList == null || businessList.size() == 0) {
            return;
        }

        ImageView imageView = (ImageView) cardview.findViewById(R.id.imageView);
        AutofitTextView textView = (AutofitTextView) cardview.findViewById(R.id.textView);
        AutofitTextView textType = (AutofitTextView) cardview.findViewById(R.id.textType);

        Business business = businessList.get(position);

        String type = "";
        for (Category c: business.getCategories()) {
            type += c.getTitle() + ", ";
        }
        type = type.substring(0, type.length() - 2);

        textView.setText(business.getName());
        textType.setText(type);

        String imageUrl = business.getImageUrl();
        Picasso.with(context).setLoggingEnabled(true);
        if (imageUrl == null || imageUrl.equals("")) {
            imageUrl = "http://i.dailymail.co.uk/i/pix/2014/10/23/1414051439470_Image_galleryImage_Homer_Simpson_eating_a_do.JPG";
        }
        Picasso.with(context).load(imageUrl).into(imageView);

    }
}
