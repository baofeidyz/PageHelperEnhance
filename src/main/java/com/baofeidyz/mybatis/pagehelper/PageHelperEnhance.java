package com.baofeidyz.mybatis.pagehelper;

import com.baofeidyz.mybatis.pagehelper.pagenumber.IPageNumberStrategy;
import com.baofeidyz.mybatis.pagehelper.pagenumber.IncrementPageNumberStrategy;
import com.baofeidyz.mybatis.pagehelper.util.IntegerUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

/**
 * PageHelperEnhanceUtil
 * <p>
 * 基于Mybatis和PageHelper，对常用的一些分页操作，累赘的代码进行封装简化复用
 *
 * @author baofeidyz
 * @since v1.0.0
 */
@UtilityClass
public class PageHelperEnhance {

    private static final int defaultPageNum = 1;
    private static final int defaultPageSize = 100;

    /**
     * 基于Mybatis和PageHelper实现的分页查询模版封装，默认从第1页开始查询，每页大小为100条数据
     * 试图通过这个方法来解决代码中经常出现问题：
     * 1. sql查询不分页，当查询结果较多的时候，会导致堆内存溢出，系统卡顿问题；
     * 2. sql分页查询代码累赘，重复代码多；
     * java8使用示例：
     *
     * <pre>
     * public List &lt;Object&gt; list() {
     *      // xxxMapper指的是Mybatis的mapper接口
     *      return PageHelperUtil.executeQuery(() -&gt; xxxMapper.list());
     * }
     * </pre>
     *
     * @param queryWrapper 使用匿名内部类封装查询
     * @param <T> 集合中的固定范型声明
     * @return 经分页查询出的结果集
     */
    public <T> List<T> executeQuery(IPageHelperEnhanceQuery<T> queryWrapper) {
        return executeQuery(defaultPageNum, defaultPageSize, queryWrapper);
    }

    /**
     * 基于Mybatis和PageHelper实现的分页查询模版封装，默认从第1页开始查询，每页大小为100条数据
     * 试图通过这个方法来解决代码中经常出现问题：
     * 1. sql查询不分页，当查询结果较多的时候，会导致堆内存溢出，系统卡顿问题；
     * 2. sql分页查询代码累赘，重复代码多；
     * java8使用示例：
     *
     * <pre>
     * public List&lt;Object&gt; list() {
     *     // xxxMapper指的是Mybatis的mapper接口
     *     return PageHelperUtil.executeQuery(1, 100, () -&gt; xxxMapper.list());
     * }
     * </pre>
     *
     * @param pageNum 分页查询的开始页码，默认从第一页开始
     * @param pageSize 每页大小，默认100
     * @param query 使用匿名内部类封装查询
     * @param <T> 集合中的固定范型声明
     * @return 经分页查询出的结果集
     */
    public <T> List<T> executeQuery(int pageNum, int pageSize, IPageHelperEnhanceQuery<T> query) {
        pageNum = IntegerUtil.defaultIfNonPositive(pageNum, defaultPageNum);
        pageSize = IntegerUtil.defaultIfNonPositive(pageSize, defaultPageSize);
        List<T> resultList = new ArrayList<>();
        PageInfo<T> pageInfo;
        do {
            PageHelper.startPage(pageNum, pageSize);
            pageInfo = new PageInfo<>(query.executeQuery());
            resultList.addAll(pageInfo.getList());
            pageNum++;
        } while (pageInfo.isHasNextPage());
        return resultList;
    }

    /**
     * 基于Mybatis和PageHelper实现的分页操作的模版封装，默认从第1页开始查询，每页大小为100条数据
     * 试图通过这个方法来解决代码中经常出现问题：
     * 1. 系统中常见的根据数据库表数据进行分页操作的重复代码多的问题；
     * java8使用示例：
     *
     * <pre>
     * public void list() {
     *      PageHelperUtil.executeService(() -&gt; xxxMapper.list(), () -&gt; xxxService.do());
     * }
     * </pre>
     *
     * @param query 使用匿名内部类封装查询
     * @param service 分页操作方法
     * @param <T> 集合中的固定范型声明
     */
    public <T> void executeService(IPageHelperEnhanceQuery<T> query, IPageHelperEnhanceService<T> service) {
        executeService(defaultPageNum, defaultPageSize, query, service);
    }

    /**
     * 基于Mybatis和PageHelper实现的分页操作的模版封装，默认从第1页开始查询，每页大小为100条数据
     * 试图通过这个方法来解决代码中经常出现问题：
     * 1. 系统中常见的根据数据库表数据进行分页操作的重复代码多的问题；
     * java8使用示例：
     *
     * <pre>
     * public void list() {
     *      PageHelperUtil.executeService(1, 100, () -&gt; xxxMapper.list(), () -&gt; xxxService.do());
     * }
     * </pre>
     *
     * @param pageNum 分页查询的开始页码，默认从第一页开始
     * @param pageSize 每页大小，默认100
     * @param queryWrapper 使用匿名内部类封装查询
     * @param service 分页操作方法
     * @param <T> 集合中的固定范型声明
     */
    public <T> void executeService(int pageNum, int pageSize, IPageHelperEnhanceQuery<T> queryWrapper,
            IPageHelperEnhanceService<T> service) {
        executeService(pageNum, pageSize, new IncrementPageNumberStrategy(), queryWrapper, service);
    }

    /**
     * 基于Mybatis和PageHelper实现的分页操作的模版封装，默认从第1页开始查询，每页大小为100条数据
     * 试图通过这个方法来解决代码中经常出现问题：
     * <ol>
     *     <li>系统中常见的根据数据库表数据进行分页操作的重复代码多的问题；</li>
     *     <li>查询结果的数据会随时service的处理而变少，此时就可以使用new AlwaysPageNumberStrategy()策略来处理；</li>
     * </ol>
     * java8使用示例：
     *
     * <pre>
     * public void list() {
     *      PageHelperUtil.executeService(1, 100, new AlwaysPageNumberStrategy(), () -&gt; xxxMapper.list(), () -&gt; xxxService.do());
     * }
     * </pre>
     *
     * @param pageNum 分页查询的开始页码，默认从第一页开始
     * @param pageSize 每页大小，默认100
     * @param pageNumberStrategy 页码策略
     * @param queryWrapper 使用匿名内部类封装查询
     * @param service 分页操作方法
     * @param <T> 集合中的固定范型声明
     */
    public <T> void executeService(int pageNum, int pageSize, IPageNumberStrategy pageNumberStrategy,
            IPageHelperEnhanceQuery<T> queryWrapper,
            IPageHelperEnhanceService<T> service) {
        pageNum = IntegerUtil.defaultIfNonPositive(pageNum, defaultPageNum);
        pageSize = IntegerUtil.defaultIfNonPositive(pageSize, defaultPageSize);
        PageInfo<T> pageInfo;
        do {
            PageHelper.startPage(pageNum, pageSize);
            pageInfo = new PageInfo<>(queryWrapper.executeQuery());
            service.execute(pageInfo.getList());
            pageNum = pageNumberStrategy.getPageNumber(pageNum);
        } while (pageInfo.isHasNextPage());
    }

}