package com.flair.springbootevents.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Component
@Aspect
public class AddMethod {

//    @Before("@annotation(com.flair.springbootevents.annotation.MyAnnotation)")
//    public void doBefore(JoinPoint joinPoint){
//        String s = joinPoint.getSignature().toShortString();
//        System.out.println("AddMethod...doBefore..."+s);
//    }

//    @After("@annotation(com.flair.springbootevents.annotation.MyAnnotation)")
//    public void doAfter(JoinPoint joinPoint){
//        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
//        System.out.println("AddMethod...doAfter..."+declaringTypeName);
//    }
    @AfterReturning(value = "@annotation(com.flair.springbootevents.annotation.MyAnnotation)",returning = "result")
    public void doAfterReturn(JoinPoint joinPoint,Object result){
//        String s = joinPoint.toLongString();
//        System.out.println(" joinPoint.toLongString()  "+s);//execution(public java.lang.String com.flair.springbootevents.controller.UserController.query())
//        System.out.println("AddMethod...doAfterReturn...  "+result);//123
//        Object target = joinPoint.getTarget();
//        System.out.println("target   "+target);//com.flair.springbootevents.controller.UserController@5f7424e6
//        String s1 = joinPoint.getSignature().toLongString();
//        System.out.println("joinPoint.getSignature().toLongString();  "+s1);//public java.lang.String com.flair.springbootevents.controller.UserController.query()
//        String name = joinPoint.getSignature().getName();
//        System.out.println("joinPoint.getSignature().getName();  "+name);// query
//
        Method method = getMethod(joinPoint);
        System.out.println(method.getName()+"执行了");
        MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
        String value = annotation.value();
        System.out.println(value);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println(headerNames);

//        String pathInfo = request.getPathInfo();
//        System.out.println(pathInfo);

//        System.out.println(method);




    }
    private Method getMethod(JoinPoint joinPoint) {
        try {
            Object target = joinPoint.getTarget();
            Signature signature = joinPoint.getSignature();
            String longMethodName = signature.toLongString();
            //public java.lang.String com.flair.springbootevents.controller.UserController.query()
            String name = signature.getName();
                int sIndex = longMethodName.indexOf("(");
                int eIndex = longMethodName.indexOf(")");
                if( sIndex != -1 && eIndex != -1 ){
                    String args = longMethodName.substring( sIndex + 1,eIndex );
                    if( args == null || "".equals( args.trim() ) ){
                        return target.getClass().getDeclaredMethod( name );
                    }else {
                        String[] params = args.split(",");
                        List<Class> classes = new ArrayList<>();
                        for ( String param : params ){
                            //获取类名称
                            Class c = Class.forName( param.trim() );
                            classes.add( c );
                        }
                        return target.getClass().getDeclaredMethod( name,classes.toArray( new Class[0] ) );
                    }
                }else {
                    return null;
                }

        }catch (Exception e){

            return null;
        }
    }
}
