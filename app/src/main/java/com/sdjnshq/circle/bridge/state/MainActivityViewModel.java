/*
 * Copyright 2018-2019 KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sdjnshq.circle.bridge.state;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.sdjnshq.circle.bridge.BaseViewModel;
import com.sdjnshq.circle.data.http.RetrofitUtil;
import com.sdjnshq.circle.data.http.SObserver;
import com.sdjnshq.circle.data.repository.HomeRepository;
import com.sdjnshq.circle.utils.AppSP;

/**
 * TODO tip：每个页面都要单独准备一个 stateViewModel，
 * 来托管 DataBinding 绑定的临时状态，以及视图控制器重建时状态的恢复。
 *
 * 此外，stateViewModel 的职责仅限于 状态托管，不建议在此处理 UI 逻辑，
 * UI 逻辑只适合在 Activity/Fragment 等视图控制器中完成，是 “数据驱动” 的一部分，
 * 将来升级到 Jetpack Compose 更是如此。
 *
 * 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350
 *
 * Create by KunMinX at 19/10/29
 */
public class MainActivityViewModel extends BaseViewModel {

    //TODO 演示 LiveData 来用作 DataBinding 数据绑定的情况。
    // 记得在视图控制器中要加入 mBinding.setLifecycleOwner(this);
    //详见 https://xiaozhuanlan.com/topic/9816742350

    public final MutableLiveData<Boolean> openDrawer = new MutableLiveData<>();

    public final MutableLiveData<Boolean> allowDrawerOpen = new MutableLiveData<>();

    HomeRepository mHomeRepository = HomeRepository.getInstance();

    public void initState(){
        allowDrawerOpen.setValue(true);
        openDrawer.setValue(false);
    }

    public void updateLocation(double lat,double lon,String aoiName){
        if(!TextUtils.isEmpty(AppSP.getInstance().getUserId())) {
            RetrofitUtil.execute(mHomeRepository.updateLocation(AppSP.getInstance().getUserId(), 1, lat, lon,aoiName), new SObserver<String>() {
                @Override
                public void onSuccess(String o) {
                    Log.e("123", "定位发送成功");
                }
            });
        }
    }
}
