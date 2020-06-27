package com.sdjnshq.circle.ui.page;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public abstract class RecyclerFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    protected BaseQuickAdapter mAdapter;
    private DividerItemDecoration dividerItemDecoration;

    protected int position = 0;



    @Override
    public int resourceId() {
        return R.layout.lay_refresh;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mAdapter == null) {
            mAdapter = initAdapter();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
        }

        smartRefreshLayout.setTag(position);
        smartRefreshLayout.setEnableRefresh(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        if (dividerItemDecoration != null) {
            recyclerView.addItemDecoration(dividerItemDecoration);
        }

        mAdapter.bindToRecyclerView(recyclerView);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter!=null){
            Log.e("123",mAdapter.toString()+mAdapter.getData().size()+"这是东大泰");
        }
    }

    protected SmartRefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }

    public void setDividerItemDecoration(DividerItemDecoration dividerItemDecoration) {
        this.dividerItemDecoration = dividerItemDecoration;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", position);
        super.onSaveInstanceState(outState);
    }

    public abstract BaseQuickAdapter initAdapter();

    public void addAll(List list) {
        if (mAdapter == null) {
            mAdapter = initAdapter();
        }
        Log.e("123",list.size() +"ssss"+mAdapter.toString()+mAdapter.getData().size()+"这是东大泰");
        mAdapter.addData(list);
    }

    public void newData(List list) {
        if (mAdapter == null) {
            mAdapter = initAdapter();
        }
        mAdapter.setNewData(list);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
