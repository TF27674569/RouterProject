package org.router.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.router.annoation.Action;
import org.router.annoation.Intercepter;
import org.router.compiler.util.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import static org.router.compiler.Consts.*;

/**
 * Description : Mode扫描生成进程
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/21
 * Email : 27674569@qq.com
 * Version : 1.0
 */
@AutoService(Process.class)
public class ModuleProcessor extends AbstractProcessor{
    /**
     * MODULE 名称对应的KEY
     */
    private final String KEY_MODULE_NAME = "moduleName";

    /**
     * 类文件的筛选器 用于创建文件、类和辅助文件。
     */
    private Filer mFiler;

    /**
     * 包的信息，类的信息获取对象
     */
    private Elements mElementUtils;

    /**
     *  反射状态下类的自身信息
     */
    private TypeMirror iRouterAction = null;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        iRouterAction = mElementUtils.getTypeElement(ROUTERACTION).asType();
    }

    /**
     * 用来指定你使用的Java版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        /**
         * 返回能支持的最高版本
         */
        return SourceVersion.latestSupported();
    }

    /**
     * 2. 指定需要扫描哪些注解（自定义的）
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        // 这里使用的是每一个对象建一个类 这里使用单个
        types.add(Action.class.getCanonicalName());
        return types;
    }


    /**
     * 扫描回调
     *Java官方文档给出的注解处理过程的定义:注解处理过程是一个有序的循环过程。
     * 在每次循环中,一个处理器可能被要求去处理那些在上一次循环中产生的源文件和类文件中的注解。
     * 第一次循环的输入是运行此工具的初始输入。这些初始输入,可以看成是虚拟的第0次的循环的输出。
     * 这也就是说我们实现的process方法有可能会被调用多次,因为我们生成的文件也有可能会包含相应的注解。
     * 例如,我们的源文件为SourceActivity.class,生成的文件为Generated.class,这样就会有三次循环,
     * 第一次输入为SourceActivity.class,输出为Generated.class;第二次输入为Generated.class,
     * 输出并没有产生新文件;第三次输入为空,输出为空。每次循环都会调用process方法,process方法提供了两个参数,
     * 第一个是我们请求处理注解类型的集合(也就是我们通过重写getSupportedAnnotationTypes方法所指定的注解类型),
     * 第二个是有关当前和上一次 循环的信息的环境。返回值表示这些注解是否由此 Processor 声明,如果返回 true,
     * 则这些注解已声明并且不要求后续 Processor 处理它们;如果返回 false,
     * 则这些注解未声明并且可能要求后续 Processor 处理它们。
     *
     * @param set 注解集合
     * @param roundEnvironment  表示当前或是之前的运行环境,可以通过该对象查找找到的注解
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 1. 我们按modlue扫描处理 获取module的名字
        String moduleName = "";
        //  指定的参数选项
        Map<String, String> options = processingEnv.getOptions();

        // 如果不为空就去拿 module的名字
        if (isNotEmpty(options)){
            moduleName = options.get(KEY_MODULE_NAME);
        }

        // 判断module名称是不是为空
        if (TextUtils.isEmpty(moduleName)){
            String errorMessage = "These no module name, at 'build.gradle', like :\n" +
                    "apt {\n" +
                    "    arguments {\n" +
                    "        moduleName project.getName();\n" +
                    "    }\n" +
                    "}\n";
            throw new RuntimeException("Router::Compiler >>> No module name, for more information, look at gradle log.\n" + errorMessage);
        }else {
            // 不为空 吧module中间的 "-"  去掉
            moduleName = moduleName.replaceAll("[^0-9a-zA-Z_]+", "");
        }


        // 生成类继承和实现接口 父类类名
        ClassName routerAssistClassName = ClassName.get("org.drouter.api.action", "IRouterModule");
        // map包
        ClassName mapClassName = ClassName.get("java.util", "Map");

        // 生成类 "Router$$Interceptor$$ + moduleName的名字
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder("Router$$Module$$" + moduleName)
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC) // final类 公共的
                .addSuperinterface(routerAssistClassName) // 父类接口名 自动导包
                .addField(mapClassName, "actions", Modifier.PRIVATE);// 私有的成员属性map 并自动导包

        // 构造函数
        MethodSpec.Builder constructorMethodBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);
        // 创建构造函数并初始化TreeMap 且自动导包
        constructorMethodBuilder.addStatement("interceptors = new $T<>()", ClassName.get("java.util", "HashMap"));



        // 2. 解析到所有的 Action 信息  getElementsAnnotatedWith 返回被指定注解类型注解的元素集合。
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Action.class);
        // 有多少个 Intercepter 注解就创建多大的map 也就是说这个module中有多少个拦截器
        Map<String, String> modules = new HashMap<>(elements.size());

        ClassName actionWrapperClassName = ClassName.get("org.drouter.api.extra", "ActionWrapper");
        ClassName threadModeClassName = ClassName.get("org.drouter.annotation", "ThreadMode");

        // 开始遍历
        for (Element element : elements) {
            // 获取注解上面的 path
            Action actionAnnotation = element.getAnnotation(Action.class);
            // 拿优先级
            String actionName = actionAnnotation.path();

            // 必须以配置的 gradle 包名开头
            if (!actionName.startsWith(moduleName + "/")) {
                error(element, "Path name of the action must begin with %s%s", moduleName, "/");
            }

            // 获取 Action 的 ClassName
            Element enclosingElement = element.getEnclosingElement();
            String packageName = mElementUtils.getPackageOf(enclosingElement).getQualifiedName().toString();
            String actionClassName = packageName + "." + element.getSimpleName();

            // 判断 Interceptor 注解类是否实现了 ActionInterceptor
            if (!((TypeElement) element).getInterfaces().contains(iRouterAction)) {
                error(element, "%s verify failed, @Action must be implements %s", element.getSimpleName().toString(), Consts.ROUTERACTION);
            }

            // path重复
            if (modules.containsKey(actionName)) {
                // 输出错误，Action 名称冲突重复了
                error(element, "%s path name already exists", actionName);
            }

            // 添加到集合
            modules.put(actionName, actionClassName);

            // 函数参数传进去
            constructorMethodBuilder.addStatement("this.actions.put($S,$T.build($T.class, $S, "
                            + actionAnnotation.extraProcess() + ", $T." + actionAnnotation.threadMode() + "))",
                    actionName, actionWrapperClassName, ClassName.bestGuess(actionClassName), actionName, threadModeClassName);
        }

        // 实现方法
        MethodSpec.Builder unbindMethodBuilder = MethodSpec.methodBuilder("findAction")
                .addParameter(String.class, "actionName")
                .addAnnotation(Override.class)
                .returns(actionWrapperClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        unbindMethodBuilder.addStatement("return (ActionWrapper)actions.get(actionName)");


        // 将两个函数添加在类中去
        classBuilder.addMethod(constructorMethodBuilder.build());
        classBuilder.addMethod(unbindMethodBuilder.build());

        // 生成java文件
        try {
            // 包名
            JavaFile.builder(Consts.ROUTER_MODULE_PACK_NAME, classBuilder.build())
                    .addFileComment("javax is niubility!!")
                    .build().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("module biu  biu biu !!!!");
        }


        return false;
    }

    private void error(Element element, String message, String... args) {
        printMessage(Diagnostic.Kind.ERROR, element, message, args);
    }

    private void printMessage(Diagnostic.Kind kind, Element element, String message, Object[] args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }

        processingEnv.getMessager().printMessage(kind, message, element);
    }

    private boolean isNotEmpty(Map<String, String> options) {
        return options != null && !options.isEmpty();
    }
}
