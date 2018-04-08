/**
 *****************************************************************************
 * Copyright (C) 2005-2012 UCWEB Corporation. All rights reserved
 * File        : 2012-11-29
 *
 * Description : ReflectionHelper.java
 *
 * Creation    : 2012-11-29
 * Author      : liangcm@ucweb.com
 * History     : Creation, 2012-11-29, liangcm, Create the file
 *****************************************************************************
 */

package com.ehang.commonutils;

import com.ehang.commonutils.exception.ExceptionHandler;
import com.ehang.commonutils.ui.ResourcesUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public final class ReflectionHelper {
	
	// FIXME: -1也不是很好的做啊，不过不想加异常怀疑代码会增大，需要的时候改抛异常吧。
	public final static int INVALID_VALUE = -1;

    /**
     * 通过构造器取得实例
     * @param className 类的全限定名
     * @param intArgsClass 构造函数的参数类型
     * @param intArgs 构造函数的参数值
     * 
     * @return Object 
     */
	public static Object getObjectByConstructor(String className,Class[] intArgsClass,Object[] intArgs){

        Object returnObj= null;
        try {
            Class classType = Class.forName(className);
            Constructor constructor = classType.getDeclaredConstructor(intArgsClass); //找到指定的构造方法
            constructor.setAccessible(true);//设置安全检查，访问私有构造函数必须
            returnObj = constructor.newInstance(intArgs);
        } catch (NoSuchMethodException ex) {
            ExceptionHandler.processFatalException(ex);
        } catch (Exception ex) {
            ExceptionHandler.processFatalException(ex);
        }
        return returnObj;
    }
	
    /**
     * 修改成员变量的值
     * @param object 修改对象
     * @param filedName 指定成员变量名
     * @param filedValue 修改的值
     *
     * @return 
     */
    public static void modifyFileValue(Object object, String filedName,
                                       String filedValue) {
        Class classType = object.getClass();
        Field fild = null;
        try {
            fild = classType.getDeclaredField(filedName);
            fild.setAccessible(true);//设置安全检查，访问私有成员变量必须
            fild.set(object, filedValue);
        } catch (NoSuchFieldException ex) {
            ExceptionHandler.processFatalException(ex);
        } catch (Exception ex) {
            ExceptionHandler.processFatalException(ex);
        }
    }

    /**
     * 访问类成员变量
     * @param object 访问对象
     * @param fieldName 指定成员变量名
     * @return Object 取得的成员变量的值
     * */
    public static Object getFieldValue(Object object, String fieldName) {
        Class classType = object.getClass();
        Field fild = null;
        Object fildValue = null;
        try {
            fild = classType.getDeclaredField(fieldName);
            fild.setAccessible(true);//设置安全检查，访问私有成员变量必须
            fildValue = fild.get(object);

        } catch (NoSuchFieldException ex) {
            ExceptionHandler.processFatalException(ex);
        } catch (Exception ex) {
            ExceptionHandler.processFatalException(ex);
        }
        return fildValue;
    }
    
    public static Object getStaticFieldValue(Class cls, String fieldName) {
        Field fild = null;
        Object fildValue = null;
        try {
            fild = cls.getDeclaredField(fieldName);
            fild.setAccessible(true);//设置安全检查，访问私有成员变量必须
            fildValue = fild.get(null);
        } catch (NoSuchFieldException ex) {
            ExceptionHandler.processFatalException(ex);
        } catch (Exception ex) {
            ExceptionHandler.processFatalException(ex);
        }
        return fildValue;
    }
    
    /**
     * 访问类成员变量(int类型)
     * @param object 访问对象
     * @param filedName 指定成员变量名
     * @return int 取得的成员变量的值, 找不到就返回 INVALID_VALUE
     * */
    public static int getIntFileValue(Object object, String filedName) {
        Class classType = object.getClass();
        Field fild = null;
        int fildValue = INVALID_VALUE;
        try {
            fild = classType.getDeclaredField(filedName);
            fild.setAccessible(true);//设置安全检查，访问私有成员变量必须
            fildValue = fild.getInt(object);

        } catch (NoSuchFieldException ex) {
            ExceptionHandler.processFatalException(ex);
        } catch (Exception ex) {
            ExceptionHandler.processFatalException(ex);
        }
        return fildValue;
    }
    
    /**
     * 从类 访问类成员变量(int类型)
     * @param c 访问对象
     * @param filedName 指定成员变量名
     * @return int 取得的成员变量的值
     * */
    public static int getIntFileValueFromClass(Class c, String filedName) {
        Field fild = null;
        int fildValue = 0;
        try {
            fild = c.getDeclaredField(filedName);
            fild.setAccessible(true);//设置安全检查，访问私有成员变量必须
            fildValue = fild.getInt(c);

        } catch (NoSuchFieldException ex) {
//            ExceptionHandler.processFatalException(ex);
        } catch (Exception ex) {
//            ExceptionHandler.processFatalException(ex);
        }
        return fildValue;
    }
    
	public final static int SM_THEAD_POLICY = 0;
	public final static int SM_VM_POLICY = 1;
	public final static String POLICY_NAMES[] = {"android.os.StrictMode$ThreadPolicy", "android.os.StrictMode$VmPolicy"};
	public final static String SET_POLICY_NAMES[] = {"setThreadPolicy", "setVmPolicy"};

    public static void setStrictModePolicy(int type) {
		try {
			Class<?> clsStrictMode = Class.forName("android.os.StrictMode");
			Class<?> clsPolicy = Class.forName(POLICY_NAMES[type]);
			Class<?> clsBuilder = Class.forName(POLICY_NAMES[type] + "$Builder");

			Method mtdSetPolicy = clsStrictMode.getDeclaredMethod(SET_POLICY_NAMES[type], new Class[] { clsPolicy });
			Method mtdDetectAll = clsBuilder.getDeclaredMethod("detectAll");
			Method mtdPenaltyLog = clsBuilder.getDeclaredMethod("penaltyLog");
			Method mtdBuild = clsBuilder.getDeclaredMethod("build");

			Object objBuilder = mtdDetectAll.invoke(clsBuilder.newInstance());
			objBuilder = mtdPenaltyLog.invoke(objBuilder);
			Object objThreadPolicy = mtdBuild.invoke(objBuilder);
			
			mtdSetPolicy.invoke(null, objThreadPolicy);
		} catch (ClassNotFoundException e1) {
			ExceptionHandler.processFatalException(e1);
		} catch (SecurityException e) {
			ExceptionHandler.processFatalException(e);
		} catch (NoSuchMethodException e) {
			ExceptionHandler.processFatalException(e);
		} catch (IllegalArgumentException e) {
			ExceptionHandler.processFatalException(e);
		} catch (IllegalAccessException e) {
			ExceptionHandler.processFatalException(e);
		} catch (InvocationTargetException e) {
			ExceptionHandler.processFatalException(e);
		} catch (InstantiationException e) {
			ExceptionHandler.processFatalException(e);
		}
    }
    
    public static void setReflectField(Object obj, String field, Object value) {
        try {
            Field f = null;
            try {
                f = obj.getClass().getDeclaredField(field);
            } catch (Exception e) {
                f = obj.getClass().getField(field);
            }

            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception e) {
            if (ResourcesUtils.isDebugAble())
                ExceptionHandler.processFatalException(e);
        }
    }

    public static void setSuperClassReflectFieldValue(Object obj, String field, Object value) {
        try {
            Field f = null;
            Class<?> curClass = obj.getClass().getSuperclass();
            for (; curClass != null;) {
                try {
                    f = curClass.getDeclaredField(field);
                    if (f != null)
                        break;
                } catch (Exception e) {
                    curClass = curClass.getSuperclass();
                }
            }

            if (f != null) {
                f.setAccessible(true);
                f.set(obj, value);
            }
        } catch (Exception e) {
            if (ResourcesUtils.isDebugAble())
                ExceptionHandler.processFatalException(e);
        }
    }
    
    public static Object getSuperClassReflectFieldValue(Object obj, String field) {
        try {
            Field f = null;
            Class<?> curClass = obj.getClass().getSuperclass();
            for (; curClass != null;) {
                try {
                    f = curClass.getDeclaredField(field);
                    if (f != null)
                        break;
                } catch (Exception e) {
                    curClass = curClass.getSuperclass();
                }
            }
            if(f != null){
                f.setAccessible(true);
                return f.get(obj);
            }
        } catch (Exception e) {
            if (ResourcesUtils.isDebugAble())
                ExceptionHandler.processFatalException(e);
        }

        return null;
    }
    
    
    public static Object getReflectFieldValue(Object obj, String field) {
        try {
            Field f = null;

            try {
                f = obj.getClass().getDeclaredField(field);
            } catch (Exception e) {
                f = obj.getClass().getField(field);
            }

            f.setAccessible(true);
            return f.get(obj);
        } catch (Throwable e) {}

        return null;
    }
    
    public static Object invokeReflectFunction(Object obj, String method) {
        try {
            Method m = null;
            try {
                m = obj.getClass().getDeclaredMethod(method);
            } catch (Exception e) {
                m = obj.getClass().getMethod(method);
            }

            m.setAccessible(true);
            return m.invoke(obj);
        } catch (Exception e) {
            if (ResourcesUtils.isDebugAble())
                ExceptionHandler.processFatalException(e);
        }

        return null;
    }
    
    /**
     * invoke object's method with a single param   
     * @param o: Object to be use
     * @param methodName: method to be invoke
     * @param argsClass: parameter's type
     * @param args: arguments
     * by lixl
     */
    public static Object invokeObjectMethod(Object o, String methodName,Class[] argsClass,Object[] args)
    {
        Object returnValue = null;
        try {
            Class<?> c=o.getClass();
            Method method;
            method = c.getMethod(methodName, argsClass);
            returnValue = method.invoke(o, args);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            ExceptionHandler.processFatalException(e);
        }

        return returnValue;
    }
    
}