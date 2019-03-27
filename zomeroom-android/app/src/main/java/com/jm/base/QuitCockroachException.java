package com.jm.base;

/**
 * Created by sky on 2017/8/7.
 * description: 全局Crash 组件
 * changed: 创建
 */

final class QuitCockroachException extends RuntimeException {
    public QuitCockroachException(String message) {
        super(message);
    }
}
