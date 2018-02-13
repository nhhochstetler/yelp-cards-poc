package com.whattoeat.natehochstetler.whattoeat;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class FilterFragment extends AAH_FabulousFragment {

    private boolean openNowSwitch;
    private TabLayout tabs_types;
    private SectionsPagerAdapter mAdapter;
    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    private List<TextView> textviews = new ArrayList<>();



    public static FilterFragment newInstance() {
        FilterFragment f = new FilterFragment();
        return f;
    }

    @Override

    public void setupDialog(Dialog dialog, int style) {
        final View contentView = View.inflate(getContext(), R.layout.filter_view, null);
        RelativeLayout rl_content = (RelativeLayout) contentView.findViewById(R.id.rl_content);
        LinearLayout ll_buttons = (LinearLayout) contentView.findViewById(R.id.ll_buttons);

//        Switch openNow = contentView.findViewById(R.id.switch1);
//        openNow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
//                if (isChecked) {
//                    openNowSwitch = true;
//                } else {
//                    openNowSwitch = false;
//                }
//
//            }
//        });
        contentView.findViewById(R.id.imgbtn_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFilter(openNowSwitch);
            }
        });

        ViewPager vp_types = (ViewPager) contentView.findViewById(R.id.vp_types);
        tabs_types = (TabLayout) contentView.findViewById(R.id.tabs_types);

        mAdapter = new SectionsPagerAdapter();
        vp_types.setOffscreenPageLimit(4);
        vp_types.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tabs_types.setupWithViewPager(vp_types);

        //params to set
        setAnimationDuration(300); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
//        setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
//        setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
        setViewgroupStatic(ll_buttons); // optional; layout to stick at bottom on slide
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }

    public class SectionsPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_filters_sorters, collection, false);
            FlexboxLayout fbl = (FlexboxLayout) layout.findViewById(R.id.fbl);

            switch (position) {
                case 0:
                    inflateLayoutWithFilters("general", fbl);
                    break;
                case 1:
                    inflateLayoutWithFilters("categories", fbl);
                    break;
                case 2:
                    inflateLayoutWithFilters("attributes", fbl);
                    break;
            }
            collection.addView(layout);
            return layout;

        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "GENERAL";
                case 1:
                    return "CATEGORIES";
                case 2:
                    return "ATTRIBUTES";

            }
            return "";
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    private void inflateLayoutWithFilters(final String filter_category, FlexboxLayout fbl) {
        List<String> keys = new ArrayList<>();
        View subchild;
        switch (filter_category) {
            case "general":
//                keys = ((MainActivity) getActivity()).getmData().getUniqueGenreKeys();
                subchild = getActivity().getLayoutInflater().inflate(R.layout.general_filter, null);
                fbl.addView(subchild);
                break;
            case "categories":
//                keys = ((MainActivity) getActivity()).getmData().getUniqueRatingKeys();
                keys.add("italian");
                keys.add("bbq");
                keys.add("buffets");
                keys.add("cajun");
                keys.add("chinese");
                keys.add("greek");
                keys.add("italian");
                keys.add("japanese");
                for (int i = 0; i < keys.size(); i++) {
                    subchild = getActivity().getLayoutInflater().inflate(R.layout.single_chip, null);
                    final TextView tv = ((TextView) subchild.findViewById(R.id.txt_title));
                    tv.setText(keys.get(i));
                    final int finalI = i;
                    final List<String> finalKeys = keys;
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (tv.getTag() != null && tv.getTag().equals("selected")) {
                                tv.setTag("unselected");
                                tv.setBackgroundResource(R.drawable.chip_unselected);
                                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
                                removeFromSelectedMap(filter_category, finalKeys.get(finalI));
                            } else {
                                tv.setTag("selected");
                                tv.setBackgroundResource(R.drawable.chip_selected);
                                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_header));
                                addToSelectedMap(filter_category, finalKeys.get(finalI));
                            }
                        }
                    });
                    try {
                        Log.d("k9res", "key: " + filter_category + " |val:" + keys.get(finalI));
                        Log.d("k9res", "applied_filters != null: " + (applied_filters != null));
                        Log.d("k9res", "applied_filters.get(key) != null: " + (applied_filters.get(filter_category) != null));
                        Log.d("k9res", "applied_filters.get(key).contains(keys.get(finalI)): " + (applied_filters.get(filter_category).contains(keys.get(finalI))));
                    } catch (Exception e) {

                    }
                    if (applied_filters != null && applied_filters.get(filter_category) != null && applied_filters.get(filter_category).contains(keys.get(finalI))) {
                        tv.setTag("selected");
                        tv.setBackgroundResource(R.drawable.chip_selected);
                        tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_header));
                    } else {
                        tv.setBackgroundResource(R.drawable.chip_unselected);
                        tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
                    }
                    textviews.add(tv);

                    fbl.addView(subchild);
                }
                break;
            case "attributes":
//                keys = ((MainActivity) getActivity()).getmData().getUniqueYearKeys();
                break;
        }


    }

    private void addToSelectedMap(String key, String value) {
        if (applied_filters.get(key) != null && !applied_filters.get(key).contains(value)) {
            applied_filters.get(key).add(value);
        } else {
            List<String> temp = new ArrayList<>();
            temp.add(value);
            applied_filters.put(key, temp);
        }
    }

    private void removeFromSelectedMap(String key, String value) {
        if (applied_filters.get(key).size() == 1) {
            applied_filters.remove(key);
        } else {
            applied_filters.get(key).remove(value);
        }
    }
}
