# Spring注解驱动开发（一）

## 1. 组件注册@Configuration和@Bean的注入

### 1）使用xml方式

我们一起注入一个bean使用xml来配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <bean id="person" class="com.cuzz.bean.Person">
        <property name="name" value="cuzz"></property>
        <property name="age" value="18"></property>
    </bean>
    
</beans>
```

我可以使用`ClassPathXmlApplicationContext`来获取

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 10:48
 * @Description:
 */
public class MainTest {
    public static void main(String[] args) {
        ApplicationContext  applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        // 用id获取
        Person bean = (Person) applicationContext.getBean("person");
        System.out.println(bean);
    }
}
```

输出`Person(name=cuzz, age=18)`

### 2 ) 注解

编写一个配置类

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 10:55
 * @Description: 配置类
 */
@Configuration // 告诉Spring这是一个配置类
public class MainConfig {
    // 给容器中注册一个Bean,类型为返回值类型,id默认用方法名
    // 也可以指定id
    @Bean(value = "person01")
    public Person person() {
        return new Person("vhsj", 16);
    }
}
```

可以通过`AnnotationConfigApplicationContext`来获取，并且获取id

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 10:59
 * @Description:
 */
public class MainTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = (Person) context.getBean(Person.class);
        System.out.println(person);

        String[] names = context.getBeanNamesForType(Person.class);
        for (String name: names) {
            System.out.println(name);
        }
    }
}
```

输出

```
Person(name=vhsj, age=16)
person01
```

由于给bean添加一个一个value，可以改变默认id

## 2. 组件注册@ComponentScan

### 1） 使用xml

只要标注了注解就能扫描到如:@Controller @Service @Repository @component

```xml
<context:component-scan base-package="com.cuzz"></context:component-scan>
```

### 2） 注解

在配置类中添加

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 10:55
 * @Description: 配置类
 */
@Configuration // 告诉Spring这是一个配置类
@ComponentScan(value = "com.cuzz") // 指定包
public class MainConfig {
    
}
```

添加controller、service等

测试

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 13:03
 * @Description:
 */
public class IOCTest {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        // 获取所有bean定义的名字
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanNames) {
            System.out.println(name);
        }
    }
}
```

输出结果

```
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig
bookController
bookDao
bookService
person01
```

可以看出添加@Controller @Service @Repository @component注解的都可以扫描到



还可以指定添加某些类，和排除某些类，进入ComponentScan注解中有下面两个方法

```java
ComponentScan.Filter[] includeFilters() default {};
ComponentScan.Filter[] excludeFilters() default {};

includeFilters = Filter[] ：指定扫描的时候只需要包含哪些组件
excludeFilters = Filter[] ：指定扫描的时候按照什么规则排除那些组件
```

配置类，排除Controller

```java
@Configuration // 告诉Spring这是一个配置类
@ComponentScan(value = "com.cuzz", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
public class MainConfig {

}
```

运行测试方法，可以得出没有Controller类的

```
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig
bookDao
bookService
person01
```

### 3 ) 自定义TypeFilter指定过滤规则

第一和第二比较常用

```
FilterType.ANNOTATION：按照注解
FilterType.ASSIGNABLE_TYPE：按照给定的类型；
FilterType.ASPECTJ：使用ASPECTJ表达式
FilterType.REGEX：使用正则指定
FilterType.CUSTOM：使用自定义规则
```

新建一个MyTypeFilte类实现TypeFilter接口

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 15:03
 * @Description:
 */
public class MyTypeFilter implements TypeFilter{
    /**
     * metadataReader：读取到的当前正在扫描的类的信息
     * metadataReaderFactory:可以获取到其他任何类信息的
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
            throws IOException {
        //获取当前类注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类资源（类的路径）
        Resource resource = metadataReader.getResource();

        String className = classMetadata.getClassName();
        System.out.println("--->"+className);
        // 这些类名中包含er就返回true
        if(className.contains("er")){
            return true;
        }
        return false;
    }
}
```

使用自定义注解记得需要关闭默认过滤器`useDefaultFilters = false`

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 10:55
 * @Description: 配置类
 */
@Configuration 
@ComponentScan(value = "com.cuzz",
        includeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM,
                classes = MyTypeFilter.class),
        useDefaultFilters = false)
public class MainConfig {
    // 给容器中注册一个Bean,类型为返回值类型,id默认用方法名
    // 也可以指定id
    @Bean(value = "person01")
    public Person person() {
        return new Person("vhsj", 16);
    }
}
```
测试
```
--->com.cuzz.AppTest
--->com.cuzz.bean.MainTest
--->com.cuzz.config.IOCTest
--->com.cuzz.config.MainTest
--->com.cuzz.App
--->com.cuzz.bean.Person
--->com.cuzz.config.MyTypeFilter
--->com.cuzz.controller.BookController
--->com.cuzz.dao.BookDao
--->com.cuzz.sevice.BookService
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig     // 不是扫描的 
person		   // 这个是在bean中
myTypeFilter   // 有er
bookController // 有er
bookService    // 有er
person01       // 这个是在bean中
```

## 3. 组件注册@Scope设置作用域

### 1）Spring的bean默认是单例的

```java
    @Test
    public void test02() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        // 获取所有bean定义的名字
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanNames) {
            System.out.println(name);
        }
        Object bean = applicationContext.getBean("person");
        Object bean2 = applicationContext.getBean("person");
        System.out.println(bean == bean2);   // 输出true
    }
```

### 2）Scope的四个范围

```
ConfigurableBeanFactory#SCOPE_PROTOTYPE   // 多实例 每次获取时创建对象，不会放在ioc容器中
ConfigurableBeanFactory#SCOPE_SINGLETON   // 单实例 ioc容器启动是创建对象，以后从容器中获取
WebApplicationContext#SCOPE_REQUEST       // web同一次请求创建一个实例
WebApplicationContext#SCOPE_SESSION       // web同一个session创建一个实例
```


如果我们把Scope修改

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 15:40
 * @Description:
 */
@Configuration
public class MainConfig2 {

    @Scope(value = "prototype")
    @Bean
    public Person person() {
        return new Person("vhuj", 25);
    }
}
```

则测试输出false

## 4. 组件注册@Lazy-bean懒加载

### 1）懒加载

懒加载的是针对单实例Bean，默认是在容器启动的时创建的，我们可以设置懒加载容器启动是不创建对象，在第一次使用（获取）Bean创建对象，并初始化

### 2 ) 测试

先给添加一个@Lazy注解

```java
@Configuration
public class MainConfig2 {

    @Lazy
    @Bean
    public Person person() {
        System.out.println("给容器中添加Person...");
        return new Person("vhuj", 25);
    }
}
```

编写一个测试方法

```java
    @Test
    public void test03() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        System.out.println("ioc容器创建完成...");
        Object bean = applicationContext.getBean("person");
    }
```

输出

```
ioc容器创建完成...
给容器中添加Person...
```

添加一个@Lazy是在第一次获取时，创建对象，以后获取就不需要创建了，直接从容器中获取，因为它是单实例

## 5. 组件注册@Conditional按条件注册

按照一定条件进行判断，满足条件给容器中注册Bean

### 1 ) 编写自己的Condition类

如果系统是windows，给容器中注入"bill"

如果系统是linux，给容器中注入"linus"



编写WindowCondition类并重写matches方法

  ```java
/**
   * @Author: cuzz
   * @Date: 2018/9/23 20:30
   * @Description: 判断是否是windows
   */
  public class WindowCondition implements Condition{
  
      /**
       * @param context 判断条件
       * @param metadata 注释信息
       * @return boolean
       */
      @Override
      public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
          Environment environment = context.getEnvironment();
          String property = environment.getProperty("os.name");
          if (property.contains("Windows")) {
              return true;
          }
          return false;
      }
  }
  ```

context有以下方法

  ```
  // 能获取ioc使用的beanfactory
  ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
  // 能获取到类加载器
  ClassLoader classLoader = context.getClassLoader();
  // 获取到环境变量
  Environment environment = context.getEnvironment();
  // 获取到Bean定义的注册类
  BeanDefinitionRegistry registry = context.getRegistry();
  ```

 ### 2）配置类

添加Bean添加Condition条件

```java
@Configuration
public class MainConfig2 {

    @Conditional({WindowCondition.class})
    @Bean("bill")
    public Person person01() {
        return new Person("Bill Gates", 60);
    }
    @Conditional({LinuxCondition.class})
    @Bean("linux")
    public Person person02() {
        return new Person("linus", 45);
    }

}
```



### 3 ) 测试

```java
    @Test
    public void test04() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        // 获取环境变量
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("os.name");
        System.out.println(property);

        // 获取所有bean定义的名字
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanNames) {
            System.out.println(name);
        }

        // key 是id
        Map<String, Person> map = applicationContext.getBeansOfType(Person.class);
        System.out.println(map);
    }
```

发现只有“bill”这个Bean被注入

```
Windows 7
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig2
bill
{bill=Person(name=Bill Gates, age=60)}
```

## 6. 组件注册@Improt给容器中快速导入一个组件

### 1 ) @Import导入

@Import可以导入第三方包，或则自己写的类，比较方便，Id默认为全类名

比如我们新建一个类

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 21:08
 * @Description:
 */
public class Color {
}
```

我们只需要在配置类添加一个@Import把这个类导入

```java
@Import({Color.class})
@Configuration
public class MainConfig2 {}
```

### 2 ) ImportSelector接口导入的选择器

返回导入组件需要的全类名的数组

```java
public interface ImportSelector {

	/**
	 * Select and return the names of which class(es) should be imported based on
	 * the {@link AnnotationMetadata} of the importing @{@link Configuration} class.
	 */
	String[] selectImports(AnnotationMetadata importingClassMetadata);

}
```

编写一个MyImportSelector类实现ImportSelector接口

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 21:15
 * @Description:
 */
public class MyImportSelector implements ImportSelector{

    // 返回值就导入容器组件的全类名
    // AnnotationMetadata:当前类标注的@Import注解类的所有注解信息
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {"com.cuzz.bean.Car"};
    }
}
```

在配置类中，通过@Import导入

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 15:40
 * @Description: 配置类
 */
@Import({Color.class, MyImportSelector.class})
@Configuration
public class MainConfig2 {}
```

测试结果，`com.cuzz.bean.Car`注入了

```
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig2
com.cuzz.bean.Color
com.cuzz.bean.Car
```

### 3 ) ImportBeanDefinitionRegistrar接口选择器

```java
public interface ImportBeanDefinitionRegistrar {

	/**
	 * Register bean definitions as necessary based on the given annotation metadata of
	 * the importing {@code @Configuration} class.
	 * <p>Note that {@link BeanDefinitionRegistryPostProcessor} types may <em>not</em> be
	 * registered here, due to lifecycle constraints related to {@code @Configuration}
	 * class processing.
	 * @param importingClassMetadata annotation metadata of the importing class
	 * @param registry current bean definition registry
	 */
	public void registerBeanDefinitions(
			AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry);

}
```

编写一个ImportBeanDefinitionRegistrar实现类

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 21:29
 * @Description:
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * @param importingClassMetadata 当前类的注解信息
     * @param registry 注册类
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 查询容器
        boolean b = registry.containsBeanDefinition("com.cuzz.bean.Car");
        // 如果有car, 注册一个汽油类
        if (b == true) {
            // 需要添加一个bean的定义信息
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Petrol.class);
            // 注册一个bean, 指定bean名
            registry.registerBeanDefinition("petrol", rootBeanDefinition);
        }

    }
}
```

配置类

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 15:40
 * @Description: 配置类
 */
@Import({Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
@Configuration
public class MainConfig2 {}
```

测试结果，**出现了petrol**

```
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig2
com.cuzz.bean.Color
com.cuzz.bean.Car 
petrol
```

## 7. 组件注册使用FactoryBean注册组件

编写一个ColorFactoryBean类

```java
/**
 * @Author: cuzz
 * @Date: 2018/9/23 21:55
 * @Description: Spring定义的工厂Bean
 */
public class ColorFactoryBean implements FactoryBean<Color> {
    // 返回一个Color对象
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }
    // 是否为单例
    @Override
    public boolean isSingleton() {
        return true;
    }
}
```

注入到容器中

```java
    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }
```

测试

```java
    @Test
    public void test05() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        Object bean = applicationContext.getBean("colorFactoryBean");
        // 工厂bean调用的是getClass()方法
        System.out.println("colorFactoryBean的类型是: " + bean.getClass());
    }
```

输出，**发现此时的bean调用的方法是getObjectType方法**

```
colorFactoryBean的类型是: class com.cuzz.bean.Color
```



**如果需要获取BeanFactory本身，可以在id前面加一个“&”标识**

```java
    @Test
    public void test05() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        Object bean = applicationContext.getBean("colorFactoryBean");
        // 工厂bean调用的是getClass()方法
        System.out.println("colorFactoryBean的类型是: " + bean.getClass());
        Object bean2 = applicationContext.getBean("&colorFactoryBean");
        // 工厂bean调用的是getClass()方法
        System.out.println("colorFactoryBean的类型是: " + bean2.getClass());
    }
```

此时输出

```
colorFactoryBean的类型是: class com.cuzz.bean.Color
colorFactoryBean的类型是: class com.cuzz.bean.ColorFactoryBean
```

