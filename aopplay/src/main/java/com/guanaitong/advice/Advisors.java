package com.guanaitong.advice;

public class Advisors {

    private BeforeAdvisor beforeAdvisor;

    private AfterAdvisor afterAdvisor;

    private AroundAdvisor aroundAdvisor;

    private AfterReturnAdvisor afterReturnAdvisor;

    private AfterThrowingAdvisor afterThrowingAdvisor;

    public BeforeAdvisor getBeforeAdvisor() {
        return beforeAdvisor;
    }

    public void setBeforeAdvisor(BeforeAdvisor beforeAdvisor) {
        this.beforeAdvisor = beforeAdvisor;
    }

    public AfterAdvisor getAfterAdvisor() {
        return afterAdvisor;
    }

    public void setAfterAdvisor(AfterAdvisor afterAdvisor) {
        this.afterAdvisor = afterAdvisor;
    }

    public AroundAdvisor getAroundAdvisor() {
        return aroundAdvisor;
    }

    public void setAroundAdvisor(AroundAdvisor aroundAdvisor) {
        this.aroundAdvisor = aroundAdvisor;
    }

    public AfterReturnAdvisor getAfterReturnAdvisor() {
        return afterReturnAdvisor;
    }

    public void setAfterReturnAdvisor(AfterReturnAdvisor afterReturnAdvisor) {
        this.afterReturnAdvisor = afterReturnAdvisor;
    }

    public AfterThrowingAdvisor getAfterThrowingAdvisor() {
        return afterThrowingAdvisor;
    }

    public void setAfterThrowingAdvisor(AfterThrowingAdvisor afterThrowingAdvisor) {
        this.afterThrowingAdvisor = afterThrowingAdvisor;
    }
}
