/**
 * ====================================================================
 * <p>
 * 版 权: Travelsky 版权所有 (c) 2006
 * </p>
 * <p>
 * 文 件: com.ttl.dit.exception.BusinessHandleException
 * </p>
 * <p>
 * 所含类: BusinessHandleException
 * </p>
 * 
 * <p>
 * 修改记录:
 * </p>
 * 日期 作者 内容
 * =====================================================================
 * 2011-4-28 zhdwang 建立,完成基本内容．
 * =====================================================================
 */
package com.pss.exception;

public class EntityNotExistedException extends Exception {

	private static final long serialVersionUID = 1L;

	public EntityNotExistedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EntityNotExistedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EntityNotExistedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EntityNotExistedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
