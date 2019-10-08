package com.ais.brm.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zhaocw on 2016/6/22.
 *
 * @author zhaocw
 */
public class CollectionUtils {
    /**
     * .
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(Collection list) {
        return list != null && list.size() > 0;
    }

    /**
     * .
     *
     * @param list .
     * @return
     */
    public static boolean isEmpty(Collection list) {
        return !isNotEmpty(list);
    }

    /**
     * .
     *
     * @param objects
     * @return
     */
    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    /**
     * @param theList
     * @param batchSize 每个小批次内的元素数量.
     * @return
     */
    public static List<List> split2parts(List theList, int batchSize) {
        //将List划分为多个批次.
        if (isEmpty(theList) || batchSize == 0) {
            //throw new IllegalArgumentException("bad argument."+batchSize);
            return theList;
        }
        List<List> result = new ArrayList<>();
        int partCount = theList.size() / batchSize + (theList.size() % batchSize == 0 ? 0 : 1);
        for (int i = 0; i < partCount; i++) {
            List temp = new ArrayList();
            for (int j = i * batchSize; j < i * batchSize + batchSize; j++) {
                if (j < theList.size()) {
                    temp.add(theList.get(j));
                }
            }
            result.add(temp);
        }
        return result;
    }
}
