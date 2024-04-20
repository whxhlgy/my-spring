package org.zjj.myspring.context;

import org.zjj.myspring.beans.factory.HierarchicalBeanFactory;
import org.zjj.myspring.beans.factory.ListableBeanFactory;
import org.zjj.myspring.core.io.ResourceLoader;

public interface ApplicationContext
extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {
}
