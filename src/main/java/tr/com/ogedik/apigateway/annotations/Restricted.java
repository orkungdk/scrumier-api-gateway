package tr.com.ogedik.apigateway.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tr.com.ogedik.apigateway.constants.Permission;

/**
 * @author orkun.gedik
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Restricted {

  Permission permission() default Permission.DEFAULT;

}
