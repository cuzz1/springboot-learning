# Spring注解驱动开发（二）

## 1. 声明周期@Bean指定初始化和销毁方法

###  1 ) Bean的生命周期

Bean的创建、初始化和销毁是由容器帮我们管理的

我们可以自定义初始化和销毁方法，容器在进行到当前生命周期的时候来调用我买自定义的初始化和销毁方法

构造（对象创建）

​	单实例： 在容器启动的时候创建

​	多实例： 在每次获取的时候创建对象

### 2 ) 指定初始化方法

**初始化：**对象创建完成后，并赋值化，调用初始化方法

**销毁：**单实例是在容器关闭的时候销毁，多实例容器不会管理这个Bean，容器不会调用销毁方法

编写一个Car类

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 21:20
 * @Description:
 */
public class Car {

    public Car () {
        System.out.println("car constructor...");
    }

    public void init() {
        System.out.println("car...init...");
    }

    public void destroy() {
        System.out.println("car...destroy...");
    }
}
```



在xml中我们可以指定`init-method`和`destroy-method`方法，如

```xml
<bean id="car" class="com.cuzz.bean.Car" init-method="init" destroy-method="destroy"></bean>
```

使用注解我们可以

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/24 12:49
 * @Description: 配置类
 */
@Configuration
public class MainConfigOfLifecycle {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }

}
```

测试

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/24 13:00
 * @Description:
 */
public class IOCTestLifeCycle {

    @Test
    public void test01() {
        // 创建ioc容器
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(MainConfigOfLifecycle.class);
        System.out.println("容器创建完成...");
        // 关闭容器
        System.out.println("--->开始关闭容器");
        applicationContext.close();
        System.out.println("--->已经关闭容器");
    }
}
```

可以看出先创建car，再调用init方法，在容器关闭时销毁实例

```
car constructor...
car...init...
容器创建完成...
--->开始关闭容器
car...destroy...
--->已经关闭容器
```

在配置数据源的时候，有很多属性赋值，销毁的时候要把连接给断开

## 2. 生命周期InitializingBean和DisposableBean

### 1 ) InitializingBean

可以通过Bean实现InitializingBean来定义初始化逻辑，是设置好所有属性会调用`afterPropertiesSet()`方法

```java
public interface InitializingBean {

	/**
	 * Invoked by a BeanFactory after it has set all bean properties supplied
	 * (and satisfied BeanFactoryAware and ApplicationContextAware).
	 * <p>This method allows the bean instance to perform initialization only
	 * possible when all bean properties have been set and to throw an
	 * exception in the event of misconfiguration.
	 * @throws Exception in the event of misconfiguration (such
	 * as failure to set an essential property) or if initialization fails.
	 */
	void afterPropertiesSet() throws Exception;

}
```

### 2）DisposableBean

可以通过Bean实现DisposableBean来定义销毁逻辑，会调用destroy()方法

```java
public interface DisposableBean {

	/**
	 * Invoked by a BeanFactory on destruction of a singleton.
	 * @throws Exception in case of shutdown errors.
	 * Exceptions will get logged but not rethrown to allow
	 * other beans to release their resources too.
	 */
	void destroy() throws Exception;

}
```

### 3）例子

编写一个Cat类

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/24 13:36
 * @Description:
 */
public class Cat implements InitializingBean, DisposableBean{

    public Cat() {
        System.out.println("cat constructor...");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat...init...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("cat...destroy...");
    }

}
```

测试

```
cat constructor...
cat...init...
容器创建完成...
--->开始关闭容器
cat...destroy...
--->已经关闭容器
```



## 3. 生命周期@PostContruct和@PreDestroy注解

@PostContruct在Bean创建完成并且属性赋值完成，来执行初始化

@PreDestroy在容器销毁Bean之前通知我们进行清理工作

编写一个Dog类，并把他注入到配置类中

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/24 14:03
 * @Description:
 */
public class Dog {

    public Dog() {
        System.out.println("dog constructor...");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("post construct...");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("pre destroy...");
    }
}
```

测试结果

```
dog constructor...
post construct...
容器创建完成...
--->开始关闭容器
pre destroy...
--->已经关闭容器
```

## 4. 生命周期BeanPostProscessor后置处理器

在Bean初始化前后做一些处理

```java
public interface BeanPostProcessor {
	// 在初始化之前工作
	Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;
	// 在初始化之后工作
	Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
```

编写一个MyBeanPostProcessor实现BeanPostProcessor接口

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/24 14:21
 * @Description: 后置处理器，初始化前后进行处理工作
 */
public class MyBeanPostProcessor implements BeanPostProcessor{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("--->postProcessBeforeInitialization..." + beanName +"==>" + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("--->postProcessAfterInitialization..." + beanName +"==>" + bean);
        return bean;
    }
}
```

测试

```
--->postProcessBeforeInitialization...org.springframework.context.event.internalEventListenerProcessor==>org.springframework.context.event.EventListenerMethodProcessor@1dc67c2
--->postProcessAfterInitialization...org.springframework.context.event.internalEventListenerProcessor==>org.springframework.context.event.EventListenerMethodProcessor@1dc67c2
--->postProcessBeforeInitialization...org.springframework.context.event.internalEventListenerFactory==>org.springframework.context.event.DefaultEventListenerFactory@2bd765
--->postProcessAfterInitialization...org.springframework.context.event.internalEventListenerFactory==>org.springframework.context.event.DefaultEventListenerFactory@2bd765
cat constructor...
--->postProcessBeforeInitialization...cat==>com.cuzz.bean.Cat@1d3b207
cat...init...
--->postProcessAfterInitialization...cat==>com.cuzz.bean.Cat@1d3b207
容器创建完成...
--->开始关闭容器
cat...destroy...
--->已经关闭容器
```

在实例创建之前后创建之后会被执行

## 5. 生命周期BeanPostProcessor原理

通过debug到populateBean，先给属性赋值在执行initializeBean方法

```java
try {
    populateBean(beanName, mbd, instanceWrapper);
    if (exposedObject != null) {
        exposedObject = initializeBean(beanName, exposedObject, mbd);
    }
}
```

initializeBean方法时，

```java
protected Object initializeBean(final String beanName, final Object bean, RootBeanDefinition mbd) {


    Object wrappedBean = bean;
    if (mbd == null || !mbd.isSynthetic()) {
        // 执行before方法
        wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
    }
	...
    try {
        // 执行初始化
        invokeInitMethods(beanName, wrappedBean, mbd);
    }

    if (mbd == null || !mbd.isSynthetic()) {
        // 执行after方法
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
    }
    return wrappedBean;
}
```



**Spring底层对`BeanPostProcessor`的使用**：

Bean赋值、注入其他组件、@Autowired、生命周期注解功能、@Async等等都使用到了BeanPostProcessor这个接口的实现类，很重要