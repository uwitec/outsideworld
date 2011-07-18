/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.ttl.dit.common.annotations.FieldValidation.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jul 18, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Jul 18, 2011
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldValidation {
    String regx() default "";
    boolean isBlank() default true; 
}
