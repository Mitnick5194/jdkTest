package com.ajie.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations只支持基本类型、String及枚举类型。注释中所有的属性被定义成方法，并允许提供默认值。
 * 
 * @author niezhenjie
 * 
 */

// 作用在方法上
@Target(ElementType.METHOD)
// 什么时候使用改注解（SOURCE:在编译阶段丢弃。这些注解在编译结束之后就不再有任何意义，所以它们不会写入字节码。CLASS –
// 在类加载的时候丢弃。在字节码文件的处理中有用。注解默认使用这种方式。RUNTIME–
// 始终不会丢弃，运行期也保留该注解，因此可以使用反射机制读取该注解的信息。我们自定义的注解通常使用这种方式。）
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
	String name() default "Mitnick"; // name后面需要带括号 默认值为Mitnick

	// 如果有使用默认值 则可以在使用注解时不传入值 如果没有使用default 如果不传入 则会报错
	int age();
}
