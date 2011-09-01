/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.dao.purchase.GoodMapper.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Aug 29, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.dao.purchase;

import java.util.List;

import com.pss.dao.BaseMapper;
import com.pss.domain.model.entity.purchase.Good;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Aug 29, 2011
 */
public interface GoodMapper extends BaseMapper<Good> {
    public List<Good> findByLogic(Good good);
}
