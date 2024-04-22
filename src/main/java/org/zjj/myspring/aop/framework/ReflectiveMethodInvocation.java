package org.zjj.myspring.aop.framework;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import lombok.RequiredArgsConstructor;

/**
 * Description of a method invocation, including a target object and a method to invoke.
 */
@RequiredArgsConstructor
public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object target;

    protected final Method method;

    protected final Object[] arguments;

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * Returns the static part of this joinpoint.
     *
     * <p>The static part is an accessible object on which a chain of
     * interceptors are installed.
     */
    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target, arguments);
    }

    @Override
    public Method getMethod() {
        return method;
    }

}

