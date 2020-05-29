package com.rul.gather8002.clientprocess;

import java.util.ArrayList;

public class SortData {

    /**
     * 合并两链并排序
     *
     * @return 合并后的有序链
     */
    public static ArrayList<String> sortAndMergeTrace(ArrayList<String> list1, ArrayList<String> list2) {
        int size1 = 0, size2 = 0;
        if (list1 != null) {
            size1 = list1.size();
            list1.sort((t1, t2) -> (int) (parseStartTime(t2) - parseStartTime(t1)));
        }
        if (list2 != null) {
            size2 = list2.size();
            list2.sort((t1, t2) -> (int) (parseStartTime(t2) - parseStartTime(t1)));
        }
        ArrayList<String> sorted = new ArrayList<>(size1 + size2);

        //合并两条有序链
        int index1 = 0, index2 = 0;
        if(list1!=null&&list2!=null) {
            while (index1 < size1 && index2 < size2) {
                String trace1 = list1.get(index1);
                String trace2 = list2.get(index2);
                if (parseStartTime(trace1) <= parseStartTime(trace2)) {
                    sorted.add(trace1);
                    index1++;
                } else {
                    sorted.add(trace2);
                    index2++;
                }
            }
        }
        if(list1!=null) {
            while (index1 < size1) {
                sorted.add(list1.get(index1++));
            }
        }
        if(list2!=null) {
            while (index2 < size2) {
                sorted.add(list2.get(index2++));
            }
        }
        return sorted;
    }


    /**
     * 解析数据startTime
     */
    public static long parseStartTime(String line) {
        return Long.parseLong(line.split("\\|")[1]);
    }
}
