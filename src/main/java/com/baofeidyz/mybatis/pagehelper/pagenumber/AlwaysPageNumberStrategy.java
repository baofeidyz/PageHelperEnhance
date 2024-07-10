package com.baofeidyz.mybatis.pagehelper.pagenumber;

/**
 * pageNumber不变.
 * <p>适用于一些任务查询条件会随着任务的进行，数据量会逐渐减小的业务场景，保持分页页码不变</p>
 *
 * @author baofeidyz
 * @since v1.0.0
 */
public class AlwaysPageNumberStrategy implements IPageNumberStrategy {

    @Override
    public int getPageNumber(int pageNumber) {
        return pageNumber;
    }

}
