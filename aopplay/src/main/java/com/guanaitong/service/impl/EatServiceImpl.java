package com.guanaitong.service.impl;

import com.guanaitong.anno.MyServiceAnno;
import com.guanaitong.service.EatService;

@MyServiceAnno
public class EatServiceImpl implements EatService {

    @Override
    public void eat() {
        System.out.println("eat......");
    }
}
