package com.baofeidyz.mybatis.pagehelper;

import java.util.List;

public interface IPageHelperEnhanceService<T> {

    void execute(List<T> list);

}
