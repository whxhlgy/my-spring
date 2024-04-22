package org.zjj.myspring.aop.aspect;

import org.aopalliance.aop.Advice;
import org.zjj.myspring.aop.Pointcut;
import org.zjj.myspring.aop.PointcutAdvisor;

import lombok.Setter;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut;

    @Setter
    private Advice advice;

    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (this.pointcut == null) {
            this.pointcut = new AspectJExpressionPointcut(this.expression);
        }
        return this.pointcut;
    }
}
