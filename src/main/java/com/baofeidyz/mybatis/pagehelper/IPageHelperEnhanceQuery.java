package com.baofeidyz.mybatis.pagehelper;

import java.util.List;

public interface IPageHelperEnhanceQuery<T> {

    List<T> executeQuery();

}
