/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 22-May-2013
 *  @author amd
 */
package com.snapdeal.task.service;

import java.util.List;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

/**
 * Uses JavaAssist library to alter byte code at runtime. 
 * 
 * @author amd
 *
 */
public class RuntimeUtils {

    /**
     * Applies given set of annotations to a class. 
     * 
     * @param className
     * @param annotations
     * @return
     * @throws Exception
     */
    public static Object addAnnotations(String className, List<Class<?>> annotations) throws Exception {

        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(RuntimeUtils.class));
        CtClass cc = pool.get(className);

        // create the annotation(s)
        ClassFile ccFile = cc.getClassFile();
        ConstPool constpool = ccFile.getConstPool();
        AnnotationsAttribute attr = (AnnotationsAttribute) ccFile.getAttribute(AnnotationsAttribute.visibleTag);
        if (attr == null) {
            attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
        }
        for (Class<?> annotation : annotations) {
            Annotation annot = new Annotation(annotation.getName(), constpool);
            attr.addAnnotation(annot);
        }
        ccFile.addAttribute(attr);
        return cc.toClass().newInstance();
    }
}