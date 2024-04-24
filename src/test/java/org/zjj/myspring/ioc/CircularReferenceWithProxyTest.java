package org.zjj.myspring.ioc;

import org.junit.Test;
import org.zjj.myspring.bean.A;
import org.zjj.myspring.bean.B;
import org.zjj.myspring.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zhongjunjie on 2024/4/23
 */
public class CircularReferenceWithProxyTest {
    @Test
    public void testCircularReference() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:circular-reference-with-proxy-bean.xml");
        A a = applicationContext.getBean("a", A.class);
        B b = applicationContext.getBean("b", B.class);

        assertThat(b.getA() == a).isTrue();
    }
}
