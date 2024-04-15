# My Spring

This is a tory project to simulate basic spring framework functionality.

## Core: IOC

[//]: # (### Factory)

[//]: # ()
[//]: # (![Factory]&#40;./assets/README-1712649520345.png&#41;)

[//]: # ()
[//]: # (#### BeanFactory)

[//]: # ()
[//]: # (`BeanFactory` is a root interface to access bean container. It is implemented by objects which hold a number of `beanDefinition`.)

[//]: # ()
[//]: # (`beanDefinition` contains all the information that create a bean needed. Such as the type of bean &#40;Singleton or independent&#41; // FIX ME)

[//]: # ()
[//]: # (```java)

[//]: # (// get bean)

[//]: # (Object getBean&#40;String name&#41; throws BeansException;)

[//]: # (```)

[//]: # ()
[//]: # (#### AbstractBeanFactory)

[//]: # (I implement `BeanFactory` by `AbstractBeanFactory`, which also extends the `DefaultSingletonBeanRegister`,indicates )

[//]: # (that this has a singleton cache to provide shared instance. )

[//]: # ()
[//]: # (This is a base factory class implemented by beanFactory implementations that can obtain bean definition from some resources.)

[//]: # ()
[//]: # (#### AbstractAutowiredCapableBeanFactory)

[//]: # (Implement the AbstractBeanFactory.)

[//]: # (- It can create a bean from bean definition &#40;involves creation, populating properties, and initialization&#41;)

[//]: # (- It can also resolve bean reference)

[//]: # ()
[//]: # (#### BeanDefinitionRegistry )

[//]: # ()
[//]: # (Interface implemented by objects which can register bean definition. )

[//]: # ()
[//]: # (#### DefaultListableFactory)

[//]: # ()
[//]: # (This factory is responsible for registering bean definition and create bean from bean definition.)

[//]: # ()

![](./assets/README-1713152994708.png)

### BeanFactory
A bean container

### ListableBeanFactory
extension able to enumerate all defined beans
```java
public interface ListableBeanFactory extends BeanFactory {
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    String[] getBeanDefinitionNames();
}
```
### AutowireCapableBeanFactory
extension able to autowire

also capable of applying bean postProcessor

### ConfigurableBeanFactory
Provides facilities to configure a bean factory

```java
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonRegistry {
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
```

### ConfigurableListableBeanFactory

In addition to ConfigurableBeanFactory, it provides facilities to analyze and modify bean definitions, and to pre-instantiate singletons.

```java
    void preInstantiateSingletons() throws BeansException;
```
### SingletonRegistry and Default SingletonRegistry
Interface: define a registry for shared beans

### AbstractBeanFactory

base class for BeanFactory implementation, 
implements the `getBean` method

### AbstractAutowireCapableBeanFactory
implement the `createBean` method, using simple instantiation strategy default.

### BeanDefinitionRegistry
provide facilities to register bd.
```java
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    BeanDefinition getBeanDefinition(String beanName);

    boolean containsBeanDefinition(String beanName);

    String[] getBeanDefinitionNames();
```
