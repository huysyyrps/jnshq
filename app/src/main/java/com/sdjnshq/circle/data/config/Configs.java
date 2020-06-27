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

package com.sdjnshq.circle.data.config;

import android.os.Environment;

import com.sdjnshq.architecture.utils.Utils;

/**
 * Create by KunMinX at 18/9/28
 */
public class Configs {

    public static final String COVER_PATH = Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();

    // 登录方式
    public static  final int LOGIN_TYPE_PWD = 1;
    public static  final int LOGIN_TYPE_CODE = 2;
    // 定位
    public static  final int LOCATION_ALLOW = 1;
    public static  final int LOCATION_DIS = 0;
    // 动态
    public static  final int CIRCLE_INFO_TYPE_IMAGE= 1;
    public static  final int CIRCLE_INFO_TYPE_VEDIO= 2;
    public static  final int CIRCLE_DONE_TYPE_ADD= 1;
    public static  final int CIRCLE_DONE_TYPE_EDIT= 2;
    public static  final int CIRCLE_DONE_TYPE_DELETE= 3;

    // 请求page size
    public static final int PAGE_SIZE = 10;
}
