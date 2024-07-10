package com.baofeidyz.mybatis.pagehelper.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class IntegerUtil {

    /**
     * 如果num为非正数，则返回defaultNum
     *
     * @param num 数
     * @param defaultNum 默认数
     * @return 若num为非正数，则返回defaultNum
     */
    public int defaultIfNonPositive(Integer num, int defaultNum) {
        return null != num && num > 0 ? num : defaultNum;
    }

}
