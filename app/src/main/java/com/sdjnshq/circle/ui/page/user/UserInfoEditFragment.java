package com.sdjnshq.circle.ui.page.user;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.tools.ScreenUtils;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.UserViewModel;
import com.sdjnshq.circle.data.MessageEvent;
import com.sdjnshq.circle.data.bean.Location;
import com.sdjnshq.circle.data.bean.MediaInfo;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.ui.adapter.SelectImageAdapter;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.utils.DialogHelper;
import com.sdjnshq.circle.utils.GlideEngine;
import com.sdjnshq.circle.utils.TimeUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.sdjnshq.circle.ui.adapter.SelectImageAdapter.TYPE_CAMERA;

public class UserInfoEditFragment extends BaseFragment {
    public static final int CHOOSE_REQUEST = 999;
    private static final int REQ_NICKNAME = 100;
    private static final int REQ_EMOTION = 101;
    private static final int REQ_HOMETOWN = 102;
    private static final int REQ_JOB = 103;
    private static final int REQ_SIGN = 104;
    private static final int REQ_SEX = 105;

    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_love)
    TextView tvLove;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_occ)
    TextView tvOcc;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    SelectImageAdapter mImageAdapter;
    UserViewModel mUserViewModel;
    User mUser;
    AlertDialog emotionDialog;
    BaseQuickAdapter.OnItemClickListener itemClickListener;
    // 拖拽
    private ItemTouchHelper mItemTouchHelper;
    // 非会员3张
    int maxSelectImg = 3;

    public static UserInfoEditFragment newInstance() {
        Bundle args = new Bundle();

        UserInfoEditFragment fragment = new UserInfoEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_user_edit;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(UserViewModel.class);
        mUserViewModel.getUserLive().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                initUser();
            }
        });

        mUserViewModel.getStatus().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == UserViewModel.EDIT_SUCCESS) {
                    mUserViewModel.getStatus().setValue(9990);
                    showLongToast("修改成功");
                    // 刷新
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setPage("refresh");
                    EventBus.getDefault().post(messageEvent);
                    pop();
                }
            }
        });

        rvImage.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rvImage.addItemDecoration(new GridSpacingItemDecoration(3,
                ScreenUtils.dip2px(getContext(), 4), false));
        mImageAdapter = new SelectImageAdapter();
        mImageAdapter.bindToRecyclerView(rvImage);
        mImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int index) {
                // 添加图片
                if (baseQuickAdapter.getItemViewType(index) == TYPE_CAMERA) {
                    PictureSelector.create(getActivity())
                            .openGallery(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java】
                            .compress(true)
                            .minimumCompressSize(1000)
                            .maxSelectNum(maxSelectImg - mImageAdapter.getData().size())
                            .enableCrop(true)
                            .withAspectRatio(1, 1)
                            .forResult(CHOOSE_REQUEST);
                }
            }
        });
        mImageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (view.getId() == R.id.iv_del) {
                    // 删除
                    mUserViewModel.removeHead(mImageAdapter.getItem(i).getId());
                    mImageAdapter.getData().remove(i);
                    mImageAdapter.notifyDataSetChanged();
                }
            }
        });
        mImageAdapter.setItemLongClickListener(new SelectImageAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder holder, int position, View v) {
                //如果item不是最后一个，则执行拖拽
                int size = mImageAdapter.getData().size();
                if (position <= size) {
                    mItemTouchHelper.startDrag(holder);
                    return;
                }
                return;
            }
        });

        titleBar.findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserViewModel.editUser(mImageAdapter.getData(), mUser,false);
            }
        });
        titleBar.getLeftTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        itemClickListener = new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int i) {
                mUser.setEmotion((String) adapter.getItem(i));
                tvLove.setText(mUser.getEmotion());
                if (emotionDialog != null) {
                    emotionDialog.dismiss();
                }
            }
        };
        initTouchHelper();
    }

    private void initTouchHelper() {
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != TYPE_CAMERA) {
                    viewHolder.itemView.setAlpha(0.7f);
                }
                return makeMovementFlags(ItemTouchHelper.DOWN | ItemTouchHelper.UP
                        | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //得到item原来的position
                try {
                    int fromPosition = viewHolder.getAdapterPosition();
                    //得到目标position
                    int toPosition = target.getAdapterPosition();
                    int itemViewType = target.getItemViewType();
                    if (itemViewType != TYPE_CAMERA) {

                        if (fromPosition < toPosition) {
                            for (int i = fromPosition; i < toPosition; i++) {
                                Collections.swap(mImageAdapter.getData(), i, i + 1);
                            }
                        } else {
                            for (int i = fromPosition; i > toPosition; i--) {
                                Collections.swap(mImageAdapter.getData(), i, i - 1);
                            }
                        }
                        mImageAdapter.notifyItemMoved(fromPosition, toPosition);
                        mUser.change(fromPosition,toPosition);
                        mUserViewModel.updateHeadImgOrder(mImageAdapter.getItem(toPosition).getId()+"",fromPosition+"");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                int itemViewType = viewHolder != null ? viewHolder.getItemViewType() : TYPE_CAMERA;
                if (itemViewType != TYPE_CAMERA) {
                    super.onSelectedChanged(viewHolder, actionState);
                }
            }

            @Override
            public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
                return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != TYPE_CAMERA) {
                    viewHolder.itemView.setAlpha(1.0f);
                    super.clearView(recyclerView, viewHolder);
                    mImageAdapter.notifyDataSetChanged();
                }
            }
        });

        // 绑定拖拽事件
        mItemTouchHelper.attachToRecyclerView(rvImage);
    }

    private void initUser() {
        User user = mUserViewModel.getUserLive().getValue();
        mUser = new User();
        mUser.setId(Integer.parseInt(user.getId()));
        mUser.setNickName(user.getNickName());
        mUser.setBirthday(user.getBirthday());
        mUser.setSex(user.getSex());
        mUser.setEmotion(user.getEmotion());
        mUser.setHomeTown(user.getHomeTown());
        mUser.setJob(user.getJob());
        mUser.setSign(user.getSign());
        mUser.setHeadimg1ID(user.getHeadimg1ID());
        mUser.setHeadimg1URL(user.getHeadimg1URL());
        mUser.setHeadimg1OrderNum(user.getHeadimg1OrderNum());
        mUser.setMobile(user.getMobile());

        mUser.setHeadimg2ID(user.getHeadimg2ID());
        mUser.setHeadimg2URL(user.getHeadimg2URL());
        mUser.setHeadimg2OrderNum(user.getHeadimg2OrderNum());

        mUser.setHeadimg3ID(user.getHeadimg3ID());
        mUser.setHeadimg3URL(user.getHeadimg3URL());
        mUser.setHeadimg3OrderNum(user.getHeadimg3OrderNum());

        if (mUser == null) {
            return;
        }
        tvNick.setText(mUser.getNickName());
        tvBirthday.setText(mUser.getBirthday());
        tvSex.setText(mUser.getSex().equals("1") ? "男" : "女");
        tvLove.setText(mUser.getEmotion());
        tvHome.setText(mUser.getHomeTown());
        tvOcc.setText(mUser.getJob());
        tvSign.setText(mUser.getSign());
        tvMobile.setText(mUser.getMobile());
        mImageAdapter.setNewData(mUser.getImageList());
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_NICKNAME && resultCode == RESULT_OK && data != null) {
            mUser.setNickName(data.getString("value"));
            tvNick.setText(mUser.getNickName());
        } else if (requestCode == REQ_EMOTION && resultCode == RESULT_OK && data != null) {
            mUser.setEmotion(data.getString("value"));
            tvLove.setText(mUser.getEmotion());
        } else if (requestCode == REQ_HOMETOWN && resultCode == RESULT_OK && data != null) {
            mUser.setHomeTown(data.getString("value"));
            tvHome.setText(mUser.getHomeTown());
        } else if (requestCode == REQ_JOB && resultCode == RESULT_OK && data != null) {
            mUser.setJob(data.getString("value"));
            tvOcc.setText(mUser.getJob());
        } else if (requestCode == REQ_SIGN && resultCode == RESULT_OK && data != null) {
            mUser.setSign(data.getString("value"));
            tvSign.setText(mUser.getSign());
        } else if (requestCode == REQ_SEX && resultCode == RESULT_OK && data != null) {
            mUser.setSex(data.getString("value"));
            tvSex.setText(mUser.isMale() ? "男" : "女");
        }
    }

    @OnClick({R.id.ll_nick, R.id.ll_birthday, R.id.ll_sex, R.id.ll_love, R.id.ll_home, R.id.ll_occ, R.id.ll_sign})
    public void onViewClicked(View view) {
        if (mUser == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_nick:
                startForResult(EditTextFragment.newInstance("昵称", mUser.getNickName(), 1), REQ_NICKNAME);
                break;
            case R.id.ll_birthday:
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        mUser.setBirthday(TimeUtils.date2String(date));
                        tvBirthday.setText(mUser.getBirthday());
                    }
                }).build();
                pvTime.setDate(TimeUtils.string2Calendar(mUser.getBirthday()));
                pvTime.show();
                break;
            case R.id.ll_sex:
                startForResult(MaleSelectFragment.newInstance(mUser.getSex()), REQ_SEX);
                break;
            case R.id.ll_love:
                ArrayList<String> itemList = new ArrayList<>();
                itemList.add("单身");
                itemList.add("恋爱中");
                itemList.add("保密");
                itemList.add("已婚");
                emotionDialog = DialogHelper.getRecyclerViewDialog(getContext(), itemList, itemClickListener).show();
                break;
            case R.id.ll_home:
                startForResult(EditTextFragment.newInstance("家乡", mUser.getHomeTown(), 1), REQ_HOMETOWN);
                break;
            case R.id.ll_occ:
                startForResult(EditTextFragment.newInstance("职业", mUser.getJob(), 6), REQ_JOB);
                break;
            case R.id.ll_sign:
                startForResult(EditTextFragment.newInstance("个性签名", mUser.getSign(), 6), REQ_SIGN);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent<List<LocalMedia>> messageEvent) {
        if (messageEvent.page != null && messageEvent.page.equals("UserInfoEditFragment")) {
            List<LocalMedia> list = messageEvent.getData();
            //上传图片
            for (int i = 0; i < list.size(); i++) {
                if (mImageAdapter.getData().size() == 3) {
                    mImageAdapter.getData().remove(2);
                }
                if (list.get(i).isCut()) {
                    mImageAdapter.addData(MediaInfo.newPathMediaInfo(list.get(i).getCutPath()));
                } else {
                    mImageAdapter.addData(MediaInfo.newPathMediaInfo(list.get(i).getPath()));
                }
            }
            mUserViewModel.editUser(mImageAdapter.getData(), mUser,true);
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
