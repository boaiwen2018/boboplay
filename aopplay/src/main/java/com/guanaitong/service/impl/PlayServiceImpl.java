package com.guanaitong.service.impl;

import com.guanaitong.anno.MyLog;
import com.guanaitong.anno.MyServiceAnno;
import com.guanaitong.service.PlayService;

@MyServiceAnno
public class PlayServiceImpl implements PlayService {

    @MyLog
    @Override
    public String play() {
        System.out.println("play......");
        if(true){
            System.out.println("报错啦......");
            throw new RuntimeException("报错啦～");
        }
        return "play return";
    }
}
