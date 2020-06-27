package com.sdjnshq.circle.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles = new ArrayList() {{
        add("资料");
        add("动态");
//        add("视频");
    }};

    public ViewPagerFragmentAdapter(@NonNull FragmentManager fm, List<Fragment> mFragments) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mFragments = mFragments;

    }

//    public ViewPagerFragmentAdapter(List<Fragment> fragmentList) {
//        super();
//        this.mFragments = fragmentList;
//    }

//
//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        Fragment fragment = (Fragment) object;
//
//        return Integer.valueOf(fragment.getArguments().getInt("position"));
//    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        View view = (View) object;
        return (int) view.getTag();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}