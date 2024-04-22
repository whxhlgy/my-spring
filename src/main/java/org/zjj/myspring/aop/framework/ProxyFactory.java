package org.zjj.myspring.aop.framework;

import org.zjj.myspring.aop.AdvisedSupport;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProxyFactory {

    private final AdvisedSupport advised;

    public Object getProxy() {
        return createProxy().getProxy();
    }

    private AopProxy createProxy() {
        if (advised.isProxyTargetClass()) {
            return new CglibAopProxy(advised);
        } else {
            return new JdkDynamicAopProxy(advised);
        }
    }

}
