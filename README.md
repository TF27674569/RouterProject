# Router

### **一、router逻辑实现图**

![  ](https://github.com/TF27674569/RouterProject/blob/master/router.bmp)  

### **二、配置**
  
    目前没有上传jcenter仓库，使用时需要，先下载项目将三个router-module导入项目
  
&nbsp;　　1.lib-base层添加依赖
   ```java
    compile project(':router-api')
```
&nbsp;　　   &nbsp;　　   注意使用 compile 不要使用  implementation     
&nbsp;　　 2.在需要使用注解的module中添加依赖
```java
     annotationProcessor project(':router-compiler')
```
&nbsp;　　   &nbsp;　　 并在 build.gradle  中添加
```java
   defaultConfig {
           .....
           aCompileOptions {
               annotationProcessorOptions {
                   arguments = [moduleName: project.getName()]
               }
           }
       }

```
&nbsp;　　   &nbsp;　　moduleName 是在编译期间获取每个module的名字
  
### **三、注解类使用格式**

1. 需要在编译期扫描所有的的注解（Action,Interceptor）
2. Action为modle对象给外界提供的访问接口定义为
```java
@Action(path = "libhome/homeActivity")
public class HomeAction implements IRouterAction{
    /**
     * 回调函数 跳转是对外提供的接口
     *
     * @param context     上下文
     * @param requestData 请求参数
     * @return 路由返回结果
     */
    @Override
    public RouterResult invokeAction(Context context, Map<String, Object> requestData) {
        Intent intent = new Intent(context,HomeActivity.class);
        intent.putExtra("key","value");
        context.startActivity(intent);
        return new RouterResult.Builder().success().build();
    }
}
```
&nbsp;　　2.1 path为id 访问的唯一标识   
&nbsp;　　2.2 threadMode为回来的线程

3. Interceptor 拦截器执行时会回调到拦截器的函数
```java
@Intercepter(priority = 18)
public class HomeIntercept implements ActionInterceptor{


    @Override
    public void intercept(ActionChain chain) {
        ActionPost actionPost = chain.action();

        // 判断path 如果需要拦截  则处理拦截事件
        if (chain.actionPath().equals("libhome/homeActivity")) {
            Toast.makeText(actionPost.context, "拦截首页，跳转到登录", Toast.LENGTH_LONG).show();
            // 拦截
            chain.onInterrupt();
            // 跳转到登录页面
            Router.get()
                    .action("login/action")
                    .context(actionPost.context)
                    .invokeAction();
        }
        // 继续向下转发
        chain.proceed(actionPost);
    }
}
```
&nbsp;　　3.1 priority 为优先级，优先级越高，越先执行  
&nbsp;　　3.2 所有的拦截器都会走


### **四、使用**
**1.** 初始化 时序早，建议放在application
```java
   // 初始化
  Router.get().init(this);
  // 开启调试模式 
  Router.openDebug();
```
**2.** 函数调用
```java
        Router.get()
                .action("libhome/homeActivity")// 路径 唯一
                .context(this)// 访问需要上下文时传入
                .invokeAction(new ActionCallback() {
                    @Override
                    public void onInterrupt() {
                        Toast.makeText(MineActivity.this, "拦截了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResult(RouterResult result) {
                        Log.e("TAG", "onResult: "+result.toString());
                    }
                });
        // ActionCallback 可以不传
```

### **五、最后本demo逻辑图关系**

![  ](https://github.com/TF27674569/RouterProject/blob/master/project.bmp)  






