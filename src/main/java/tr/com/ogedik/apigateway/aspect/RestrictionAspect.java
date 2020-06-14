///**
// * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
// */
//package tr.com.ogedik.apigateway.aspect;
//
//import java.lang.annotation.Annotation;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//
//import tr.com.ogedik.apigateway.annotations.Authorities;
//import tr.com.ogedik.apigateway.annotations.Restricted;
//import tr.com.ogedik.apigateway.constants.Permission;
//
///**
// * This class is an abstraction for aspect oriented programming in this project. All micro services should
// * call {@link #authorize(ProceedingJoinPoint, Restricted)}.
// *
// * @author orkun.gedik
// */
//@Aspect
//public class RestrictionAspect {
//  private static final Logger logger = LogManager.getLogger(RestrictionAspect.class);
//
//  public static void authorize(ProceedingJoinPoint point, Restricted restricted) {
//    Permission requiredPermission = restricted.permission();
//
//    if (requiredPermission.equals(Permission.DEFAULT)) {
//      return;
//    }
//
//    MethodSignature signature = (MethodSignature)point.getSignature();
//    Annotation[][] parameters = signature.getMethod().getParameterAnnotations();
//    Object[] args = point.getArgs();
//    Annotation[] annotations;
//
//    List<Permission> authorizedPermissions = null;
//
//    for (int i = 0; i < args.length; i++) {
//      annotations = parameters[i];
//      for (Annotation annotation : annotations) {
//        if (annotation instanceof Authorities) {
//          String authorizesHeader = (String)args[i];
//          String permissionNames[] = authorizesHeader.trim().split(",");
//
//          authorizedPermissions = Arrays.asList(permissionNames)
//              .stream()
//              .map(Permission::valueOf)
//              .collect(Collectors.toList());
//          break;
//        }
//      }
//    }
//
//    if (ListUtils.emptyIfNull(authorizedPermissions).contains(restricted.permission())) {
//      logger.info("{} authorization is successful for {}", restricted.permission().name());
//    } else {
//      logger.warn("{} authorization is failed for {}", restricted.permission().name());
//      throw new ErrorException(ErrorType.AUTHORIZATION_FAILED);
//    }
//  }
//}
