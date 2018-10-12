package com.guanaitong.advice;

public class Advices {

    private BeforeAdvice beforeAdvice;

    private AfterAdvice afterAdvice;

    private AroundAdvice aroundAdvice;

    private AfterReturnAdvice afterReturnAdvice;

    private AfterThrowingAdvice afterThrowingAdvice;

    public BeforeAdvice getBeforeAdvice() {
        return beforeAdvice;
    }

    public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }

    public AfterAdvice getAfterAdvice() {
        return afterAdvice;
    }

    public void setAfterAdvice(AfterAdvice afterAdvice) {
        this.afterAdvice = afterAdvice;
    }

    public AroundAdvice getAroundAdvice() {
        return aroundAdvice;
    }

    public void setAroundAdvice(AroundAdvice aroundAdvice) {
        this.aroundAdvice = aroundAdvice;
    }

    public AfterReturnAdvice getAfterReturnAdvice() {
        return afterReturnAdvice;
    }

    public void setAfterReturnAdvice(AfterReturnAdvice afterReturnAdvice) {
        this.afterReturnAdvice = afterReturnAdvice;
    }

    public AfterThrowingAdvice getAfterThrowingAdvice() {
        return afterThrowingAdvice;
    }

    public void setAfterThrowingAdvice(AfterThrowingAdvice afterThrowingAdvice) {
        this.afterThrowingAdvice = afterThrowingAdvice;
    }
}
