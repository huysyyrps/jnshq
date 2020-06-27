package com.sdjnshq.circle.ui.page.money;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.UserViewModel;
import com.sdjnshq.circle.bridge.money.MoneyViewModel;
import com.sdjnshq.circle.data.MessageEvent;
import com.sdjnshq.circle.data.bean.Rank;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.ui.adapter.RankAdapter;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.page.MainFragment;
import com.sdjnshq.circle.ui.page.user.VIPPayFragment;
import com.sdjnshq.circle.utils.AppSP;
import com.sdjnshq.circle.utils.XDiallog;
import com.sdjnshq.circle.utils.XUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MoneyFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.tv_friend_reward)
    TextView tvFriendReward;
    @BindView(R.id.tv_friend2_reward)
    TextView tvFriend2Reward;
    @BindView(R.id.tv_self_reward)
    TextView tvSelfreward;
    @BindView(R.id.tv_become_vip)
    TextView tvBecomeVip;

    MoneyViewModel moneyViewModel;
    UserViewModel userViewModel;
    RankAdapter mRankAdapter;

    @Override
    public int resourceId() {
        return R.layout.fragment_money;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRankAdapter = new RankAdapter();
        mRankAdapter.bindToRecyclerView(recyclerView);
        userViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(UserViewModel.class);
        moneyViewModel = getFragmentViewModelProvider(this).get(MoneyViewModel.class);
        moneyViewModel.rankLive.observe(getViewLifecycleOwner(), new Observer<List<Rank>>() {
            @Override
            public void onChanged(List<Rank> ranks) {
                mRankAdapter.setNewData(ranks);
                recyclerView.setVisibility(ranks.size() > 0 ? View.VISIBLE : View.INVISIBLE);
                llEmpty.setVisibility(ranks.size() == 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });


        moneyViewModel.basePriceLive.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvFriendReward.setText(AppSP.getInstance().getString("r_back", "") + "元");
                tvFriend2Reward.setText(AppSP.getInstance().getString("r2_back", "") + "元");
                tvSelfreward.setText(AppSP.getInstance().getString("vip_back", ""));
            }
        });
        moneyViewModel.initInviteRank();
        moneyViewModel.getBasePrice();
        userViewModel.getUserLive().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (userViewModel.getUser().getStatus() == 1) {
                    tvBecomeVip.setText("点击成为代言人");
                } else {
                    tvBecomeVip.setText("点击立即分享");
                }
            }

        });
        userViewModel.vipLive.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    tvBecomeVip.setText("点击立即分享");
                } else {
                    tvBecomeVip.setText("点击成为代言人");
                }
            }
        });
    }


    public static MoneyFragment newInstance() {
        MoneyFragment fragment = new MoneyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @OnClick({R.id.bt_my_invite, R.id.tv_friend, R.id.tv_friend_reward, R.id.iv_share, R.id.iv_become_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_my_invite:
                ((MainFragment) getParentFragment()).startBrotherFragment(MyInviteFragment.newInstance());
                break;
            case R.id.tv_friend:
            case R.id.tv_friend_reward:
                break;
            case R.id.iv_share:
                XDiallog.showShareDialog(getActivity(), userViewModel.getUser().getHead(), AppSP.getInstance().getUserName(), XUtils.getImagePath(userViewModel.getUser().getTelephone()), XUtils.getImagePath(userViewModel.getUser().getShopingTime())).show();
                break;
            case R.id.iv_become_vip:
                if(AppSP.getInstance().getBoolean("isVIP",false)){
                    XDiallog.showShareDialog(getActivity(), userViewModel.getUser().getHead(), AppSP.getInstance().getUserName(), XUtils.getImagePath(userViewModel.getUser().getTelephone()), XUtils.getImagePath(userViewModel.getUser().getShopingTime())).show();
                }else{
                    ((MainFragment) getParentFragment()).startBrotherFragment(VIPPayFragment.newInstance());
                }
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getPage().equals("vip")) {
            AppSP.getInstance().put("isVIP",true);
            userViewModel.vipLive.postValue(true);
            XDiallog.showShareDialog(getActivity(), userViewModel.getUser().getHead(), AppSP.getInstance().getUserName(), XUtils.getImagePath(userViewModel.getUser().getTelephone()), XUtils.getImagePath(userViewModel.getUser().getShopingTime())).show();
        }
    }
}
