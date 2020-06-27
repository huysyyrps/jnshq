package com.sdjnshq.circle.ui.adapter;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

// 邀请和二度人脉
public class InviteFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private ArrayList<String> mTitles = new ArrayList<String>() {{
        add("我的邀请");
        add("二度人脉");
    }};

    public InviteFragmentAdapter(@NonNull FragmentManager fm, List<Fragment> mFragments) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mFragments = mFragments;

    }

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