package com.zyyyl.common.utils;

import com.zyyyl.common.core.page.PageDomain;
import com.zyyyl.common.core.page.TableSupport;
import com.zyyyl.common.utils.sql.SqlUtil;
import com.github.pagehelper.PageHelper;

/**
 * 分页工具类
 * 
 * @author geek
 */
public class PageUtils extends PageHelper {
    /**
     * 设置请求分页数据
     */
    public static void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }
}
