/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.ttl.dit.common.FieldUtil.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jul 18, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.common;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.pss.common.annotation.FieldValidation;

/**
 * <p>
 * 类说明
 * </p>
 * <p>
 * Copyright: 版权所有 (c) 2010 - 2030
 * </p>
 * <p>
 * Company: Travelsky
 * </p>
 * 
 * @author Travelsky
 * @version 1.0
 * @since Jul 18, 2011
 */
public class FieldUtil {
	public static boolean validate(Object obj, String fieldName) {
		if (obj == null)
			return false;

		Field field = null;
		try {
			field = obj.getClass().getDeclaredField(fieldName);
			if (field.isAnnotationPresent(FieldValidation.class)) {
				field.setAccessible(true);
				String value = (String) field.get(obj);
				FieldValidation annotation = field
						.getAnnotation(FieldValidation.class);
				String regx = annotation.regx();
				boolean isBlank = annotation.isBlank();
				if (!isBlank && StringUtils.isBlank(value)) {
					return false;
				}
				Pattern pattern = Pattern.compile(regx);
				Matcher mather = pattern.matcher(value);
				if (!StringUtils.isBlank(value) && !StringUtils.isBlank(regx)
						&& !mather.matches()) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
