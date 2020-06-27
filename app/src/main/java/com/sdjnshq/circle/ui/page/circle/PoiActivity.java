package com.sdjnshq.circle.ui.page.circle;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.ui.adapter.PoiAdapter;
import com.sdjnshq.circle.ui.base.BaseActivity;
import com.sdjnshq.circle.utils.AppSP;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PoiActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    PoiAdapter poiAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        titleBar.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.getCenterSearchEditText().setHint("搜索位置");
        titleBar.getCenterSearchEditText().findFocus();
        titleBar.getCenterSearchEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PoiSearch.Query query = new PoiSearch.Query(s.toString(), "", "");
                //keyWord表示搜索字符串，

                //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
                //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
                query.setPageSize(50);// 设置每页最多返回多少条poiitem
                query.setPageNum(1);//设置查询页码


                PoiSearch poiSearch = new PoiSearch(PoiActivity.this, query);
                if (s.length() == 0) {
                    PoiSearch.SearchBound searchBound = new PoiSearch.SearchBound(new LatLonPoint(AppSP.getInstance().getLat(),
                            AppSP.getInstance().getLon()), 1000);
                    poiSearch.setBound(searchBound);
                }
                poiSearch.setOnPoiSearchListener(PoiActivity.this);
                poiSearch.searchPOIAsyn();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        PoiSearch.Query query = new PoiSearch.Query("", "", "");
        //keyWord表示搜索字符串，

        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码


        PoiSearch poiSearch = new PoiSearch(this, query);

        PoiSearch.SearchBound searchBound = new PoiSearch.SearchBound(new LatLonPoint(AppSP.getInstance().getLat(),
                AppSP.getInstance().getLon()), 1000);
        poiSearch.setBound(searchBound);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();

        poiAdapter = new PoiAdapter();
        poiAdapter.bindToRecyclerView(recyclerView);
        poiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent();
                intent.putExtra("location",(PoiItem)baseQuickAdapter.getItem(i));
                setResult(-1,intent);
                finish();
            }
        });
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        poiAdapter.setNewData(poiResult.getPois());
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
