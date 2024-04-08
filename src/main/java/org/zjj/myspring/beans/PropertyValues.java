package org.zjj.myspring.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongjunjie on 2024/4/8
 */
@Data
public class PropertyValues {
    private List<PropertyValue> valueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue v) {
        valueList.add(v);
    }
}
