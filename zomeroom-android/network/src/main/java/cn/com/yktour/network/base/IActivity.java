/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.yktour.network.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by sky on 2017/11/7.
 * description: APP Activity接口 框架要求框架中的每个 {@link Activity} 都需要实现此类,以满足规范
 * changed: 创建
 */
public interface IActivity<P extends IPresenter> {

    /**
     * 初始化 View,如果initView返回0,框架则不会调用{@link Activity#setContentView(int)}
     *
     * @param savedInstanceState
     * @return
     */
    int initView(Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initData(Bundle savedInstanceState);
    /**
     * 页面重建时，P层 重新赋值
     */
    P obtainPresenter();

    void setPresenter(P presenter);
}
