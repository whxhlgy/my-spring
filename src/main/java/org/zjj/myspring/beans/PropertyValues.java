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
        for (int i = 0; i < valueList.size(); i++) {
            PropertyValue current = valueList.get(i);
            // replace the value if there is a existing one
            if (current.getName().equals(v.getName())) {
                valueList.set(i, v);
                return;
            }
        }
        valueList.add(v);
    }

    public PropertyValue[] getPropertyValues() {
        return valueList.toArray(new PropertyValue[0]);
    }
}
