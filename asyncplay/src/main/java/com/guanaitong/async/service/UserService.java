package com.guanaitong.async.service;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Administrator
 * @date 2018/8/26 9:18
 */
public interface UserService {
    void play() throws InterruptedException;
    Future<String> play2() throws InterruptedException;
//    void play3();
}
