package com.baofeidyz.mybatis.pagehelper.pagenumber;

/**
 * pageNumber自加1.
 * <p>默认策略，适用于一些执行过程中，不会影响查询结果的任务</p>
 *
 * @author baofeidyz
 * @since v1.0.0
 */
public class IncrementPageNumberStrategy implements IPageNumberStrategy {

    @Override
    public int getPageNumber(int pageNumber) {
        return ++pageNumber;
    }

}
