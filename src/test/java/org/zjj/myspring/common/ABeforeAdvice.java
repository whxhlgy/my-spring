package org.zjj.myspring.common;

import org.zjj.myspring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author derekyi
 * @date 2021/1/30
 */
public class ABeforeAdvice implements MethodBeforeAdvice {
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {

	}
}
