package com.rul.gather8002.service;

import com.rul.gather8002.pojo.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MergeData {

    private static final Logger LOGGER = LoggerFactory.getLogger(MergeData.class);

    /**
     * 排序并汇总数据
     *
     * @param map1 map1
     * @param map2 map2
     * @return 汇总后的数据
     */
    public static HashMap<String, ArrayList<Span>> sortAndMerge(HashMap<String, ArrayList<Span>> map1,
                                                                HashMap<String, ArrayList<Span>> map2) {
        LOGGER.info("sort and merge data");

        HashMap<String, ArrayList<Span>> result = new HashMap<>();

        Iterator<Map.Entry<String, ArrayList<Span>>> iterator1 = map1.entrySet().iterator();
        while (iterator1.hasNext()) {
            Map.Entry<String, ArrayList<Span>> entry = iterator1.next();
            String key = entry.getKey();
            ArrayList<Span> list1 = entry.getValue();
            ArrayList<Span> list2 = map2.get(key);

            if (list2 == null) {
                result.put(key, list1);
                //iterator1.remove();
                continue;
            }
            result.put(key, sortList(list1, list2));
            //iterator1.remove();
            //map2.remove(key);
        }

        //判断map2是否还有剩余
        Iterator<Map.Entry<String, ArrayList<Span>>> iterator2 = map2.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, ArrayList<Span>> entry = iterator2.next();
            String key = entry.getKey();
            ArrayList<Span> list2 = entry.getValue();
            result.put(key, list2);
            //iterator2.remove(); //删除该元素
        }
        LOGGER.info("sort and merge data finished");
        return result;
    }

    /**
     * 合并有序ArrayList
     *
     * @param list1 list1
     * @param list2 list2
     * @return 合并后的有序ArrayList
     */
    public static ArrayList<Span> sortList(ArrayList<Span> list1, ArrayList<Span> list2) {
        int size1 = list1.size();
        int size2 = list2.size();
        ArrayList<Span> result = new ArrayList<>(size1 + size2);
        int index1 = 0, index2 = 2;

        //归并
        while (index1 < size1 && index2 < size2) {
            Span span1 = list1.get(index1);
            Span span2 = list2.get(index2);
            if (span1.getStartTime() <= span2.getStartTime()) {
                result.add(span1);
                index1++;
            } else {
                result.add(span2);
                index2++;
            }
        }
        if (index1 < size1) {
            result.addAll(index1, list1);
        }
        if (index2 < size2) {
            result.addAll(index2, list2);
        }
        return result;
    }
}
