package com.guanaitong.service.impl;

import com.guanaitong.anno.MyLog;
import com.guanaitong.anno.MyServiceAnno;
import com.guanaitong.service.PlayService;

@MyServiceAnno
public class PlayServiceImpl implements PlayService {

    @MyLog
    @Override
    public void play() {
        System.out.println("play......");
    }
}
