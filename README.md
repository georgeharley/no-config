# no-config
  
1. Run `./gradlew clean build`
2. Edit file `src/main/resources/application.properties` and comment out all of the external configuration properties (they were only needed in order to allow the test to pass)
3. Run `./gradlew bootRun`  
The application will fail to start up because of a NPE. Below is the final part of the stack trace:
```
. . .
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.cloud.config.server.environment.MultipleJGitEnvironmentRepository]: Factory method 'defaultEnvironmentRepository' threw exception; nested exception is java.lang.NullPointerException
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:185) ~[spring-beans-5.0.6.RELEASE.jar:5.0.6.RELEASE]
        at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:579) ~[spring-beans-5.0.6.RELEASE.jar:5.0.6.RELEASE]
        ... 111 common frames omitted
Caused by: java.lang.NullPointerException: null
        at org.springframework.cloud.config.server.environment.HttpClientConfigurableHttpConnectionFactory.addHttpClient(HttpClientConfigurableHttpConnectionFactory.java:60) ~[spring-cloud-config-server-2.0.0.RC2.jar:2.0.0.RC2]
        at org.springframework.cloud.config.server.environment.HttpClientConfigurableHttpConnectionFactory.addConfiguration(HttpClientConfigurableHttpConnectionFactory.java:43) ~[spring-cloud-config-server-2.0.0.RC2.jar:2.0.0.RC2]
        at org.springframework.cloud.config.server.environment.MultipleJGitEnvironmentRepositoryFactory.build(MultipleJGitEnvironmentRepositoryFactory.java:59) ~[spring-cloud-config-server-2.0.0.RC2.jar:2.0.0.RC2]
        at org.springframework.cloud.config.server.config.DefaultRepositoryConfiguration.defaultEnvironmentRepository(EnvironmentRepositoryConfiguration.java:209) ~[spring-cloud-config-server-2.0.0.RC2.jar:2.0.0.RC2]
        at org.springframework.cloud.config.server.config.DefaultRepositoryConfiguration$$EnhancerBySpringCGLIB$$527ad0e1.CGLIB$defaultEnvironmentRepository$0(<generated>) ~[spring-cloud-config-server-2.0.0.RC2.jar:2.0.0.RC2]
        at org.springframework.cloud.config.server.config.DefaultRepositoryConfiguration$$EnhancerBySpringCGLIB$$527ad0e1$$FastClassBySpringCGLIB$$a0f12160.invoke(<generated>) ~[spring-cloud-config-server-2.0.0.RC2.jar:2.0.0.RC2]
        at org.springframework.cglib.proxy.MethodProxy.invokeSuper(MethodProxy.java:228) ~[spring-core-5.0.6.RELEASE.jar:5.0.6.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassEnhancer$BeanMethodInterceptor.intercept(ConfigurationClassEnhancer.java:361) ~[spring-context-5.0.6.RELEASE.jar:5.0.6.RELEASE]
        at org.springframework.cloud.config.server.config.DefaultRepositoryConfiguration$$EnhancerBySpringCGLIB$$527ad0e1.defaultEnvironmentRepository(<generated>) ~[spring-cloud-config-server-2.0.0.RC2.jar:2.0.0.RC2]
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_121]
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_121]
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_121]
        at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_121]
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:154) ~[spring-beans-5.0.6.RELEASE.jar:5.0.6.RELEASE]
        ... 112 common frames omitted
```
4. Restore the properties in `src/main/resources/application.properties`
5. Edit `build.gradle` and change the value of the `springCloudVersion` property on line 29 from "Finchley.RC2" to "Finchley.RC1"
6. Run `./gradlew clean build`
7. Comment out the properties in `src/main/resources/application.properties` once again
8. Run `./gradlew bootRun`
The application fails to start up but this time rather than an NPE occurring the cause is a failed assertion because of a missing mandatory property:
```
. . .
Caused by: java.lang.IllegalStateException: You need to configure a uri for the git repository
        at org.springframework.util.Assert.state(Assert.java:73) ~[spring-core-5.0.6.RELEASE.jar:5.0.6.RELEASE]
        at org.springframework.cloud.config.server.environment.JGitEnvironmentRepository.afterPropertiesSet(JGitEnvironmentRepository.java:224) ~[spring-cloud-config-server-2.0.0.RC1.jar:2.0.0.RC1]
        at org.springframework.cloud.config.server.environment.MultipleJGitEnvironmentRepository.afterPropertiesSet(MultipleJGitEnvironmentRepository.java:72) ~[spring-cloud-config-server-2.0.0.RC1.jar:2.0.0.RC1]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1765) ~[spring-beans-5.0.6.RELEASE.jar:5.0.6.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1702) ~[spring-beans-5.0.6.RELEASE.jar:5.0.6.RELEASE]
        ... 109 common frames omitted
```
