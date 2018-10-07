package com.guanaitong.service.impl;

import com.guanaitong.anno.MyServiceAnno;
import com.guanaitong.service.PlayService;

@MyServiceAnno
public class PlayServiceImpl implements PlayService {

    @Override
    public void play() {
        System.out.println("play......");
    }
}
