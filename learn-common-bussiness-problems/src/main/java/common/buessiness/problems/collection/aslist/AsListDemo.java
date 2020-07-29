package common.buessiness.problems.collection.aslist;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 *  
 * @since 2020/7/28
 * @author : weizc 
 */
@Slf4j
public class AsListDemo {
    public static void main(String[] args) {
        //asList =>对原生类型
        wrong();

        println();

        right();

        println();
        //asList  不可变
        wrong2();

        println();

        right2();
    }

    private static void println(){
        System.out.println("=========");
    }


    private static void wrong() {

        int[] arr={1,2,3};
        List list=Arrays.asList(arr);
        log.info("list:{},size:{},class:{}",list,list.size(),list.get(0).getClass());

    }

    private static void right(){
        int[] arr={1,2,3};

        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        log.info("list:{},size:{},class:{}",list,list.size(),list.get(0).getClass());

        Integer[] arr2={1,2,3};

        list=Arrays.asList(arr2);
        log.info("list:{},size:{},class:{}",list,list.size(),list.get(0).getClass());
    }

    private static void wrong2(){
         String[] arr = {"1", "2", "3"};
         List<String> list= Arrays.asList(arr);
         arr[1]="4";
         try {
             list.add("4");
         }catch (Exception e){
             log.error("",e);
         }
         log.info("arr:{},list:{}",Arrays.toString(arr),list);
    }

    private static void right2(){
        String[] arr = {"1", "2", "3"};
        List<String> list= new ArrayList<>(Arrays.asList(arr));
        arr[1]="4";

        try {
            list.add("4");
        }catch (Exception e){
            log.error("",e);
        }
        log.info("arr:{},list:{}",Arrays.toString(arr),list);

    }


}
