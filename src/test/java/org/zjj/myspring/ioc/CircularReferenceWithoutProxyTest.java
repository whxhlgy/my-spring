package org.zjj.myspring.ioc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.zjj.myspring.bean.A;
import org.zjj.myspring.bean.B;
import org.zjj.myspring.context.support.ClassPathXmlApplicationContext;

/**
 * author: ZhongJunJie
 */
public class CircularReferenceWithoutProxyTest {
    @Test
    public void testCircularReference() throws Exception {
        ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:circular-reference-without-proxy-bean.xml");
        A a = applicationContext.getBean("a", A.class);
        B b = applicationContext.getBean("b", B.class);
        assertThat(a.getB() == b).isTrue();
    }
}
