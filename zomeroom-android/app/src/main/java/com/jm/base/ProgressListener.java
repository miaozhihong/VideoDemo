package com.jm.base;

/**
 * Created by sky on 2017/8/7.
 * description: 网络下载进度监听
 * changed: 创建
 */

public interface ProgressListener {
    /**
     * @param progress     已经下载或上传字节数
     * @param total        总字节数
     * @param done         是否完成
     */
    void onProgress(long progress, long total, boolean done);
}