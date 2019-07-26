package com.ais.brm.study.brmTest.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-6-4</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class ListLearn {
    @Test
    public void arrayList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("f");
          list.add("e");

        list.forEach(t->{
            if(t.equals("c"))return;
            System.out.println(t);
        });
        System.out.println("list=>"+list);

      /*  iterator.forEachRemaining(t->{
            if(t.equals("c"))iterator.remove();
            System.out.println(t);
        });*/
    }

    @Test
    public void hashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("a", "b");
        map.put("a", "b");

    }

    //http://www.importnew.com/28263.html
    @Test
    public void ConcurrentHashMap() {
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("a", "b");
        map.put("a", "b");

    }
}
