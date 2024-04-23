package org.zjj.myspring.util;

/**
 * author: ZhongJunJie
 */
public interface StringValueResolver {

    /**
     * Resolve the given String value, for example parsing placeholders.
     *
     * @param strVal
     * @return
     */
    String resolveStringValue(String strVal);
}
