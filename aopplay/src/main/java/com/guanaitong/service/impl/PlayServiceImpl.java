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
        return "play return";
    }


    @MyLog
    @Override
    public String play2() {
        System.out.println("play2......");
        if(true){
            System.out.println("报错啦......");
            throw new RuntimeException("报错啦～");
        }
        return "play return";
    }

}
