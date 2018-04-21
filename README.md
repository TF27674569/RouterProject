# Router

#一、router逻辑实现图

![  ](https://github.com/TF27674569/RouterProject/blob/master/router.bmp)




#二、编译器处理生成的类

####1. 需要在编译期扫描所有的的注解（Action,Interceptor）
####2. Action为modle对象给外界提供的访问接口定义为
```java
@Action(path = "login/action", threadMode = ThreadMode.MAIN)
public class LoginAction implements IRouterAction {
    @Override
    public RouterResult invokeAction(Context context, Map<String, Object> requestData) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        return new RouterResult.Builder().success().object(100).build();
    }
}
```
&nbsp;　　2.1 path为id 访问的唯一标识   
&nbsp;　　2.2 threadMode为回来的线程

####3. Interceptor 拦截器执行时会回调到拦截器的函数
```java
@Interceptor(priority = 6)
public class CircleInterceptor1 implements ActionInterceptor {

    @Override
    public void intercept(ActionChain chain) {
        ActionPost actionPost = chain.action();

        if (chain.actionPath().equals("circlemodule/test")) {

            // 拦截
            chain.onInterrupt();
        }

        // 继续向下转发
        chain.proceed(actionPost);
    }
}
```
&nbsp;　　3.1 priority 为优先级，优先级越高，越先执行  
&nbsp;　　3.2 所有的拦截器都会走













