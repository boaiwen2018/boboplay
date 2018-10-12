package com.guanaitong.service.impl;

import com.guanaitong.anno.MyLog2;
import com.guanaitong.anno.MyServiceAnno;
import com.guanaitong.service.SleepService;

@MyServiceAnno
public class SleepServiceImpl implements SleepService {

    @MyLog2
    @Override
    public void sleep() {
        System.out.println("sleep......");
    }
}
