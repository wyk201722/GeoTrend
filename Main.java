/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Main
 * Author:   youkun
 * Date:     2018/11/17 12:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
import java.io.*;
import java.util.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author youkun
 * @create 2018/11/17
 * @since 1.0.0
 */
public class Main {
    public static void main(String [] args)
    {
        quadTree it = new quadTree(null,180,-180,90,-90,3000,0);
        quadTree mbr1 = new quadTree(null,180,0,90,0, 3000,0);
        it.readfileAndGenerateHashMap();
//        System.out.println(it.children[0] == null);

//        it.generateStagingFile();
//        quadTree it2;
//        it2= it.loadStagingFile();
//        it.invertIndexHashMap = hp;
//        it.printHashMap();
//        System.out.println(it2.invertIndexHashMap.size());

//        System.out.println(it.returnChildrenSize());
//        for(int i = 0; i < 4; i++){
//            System.out.println(it.children[i] != null);
//        }
//        for(int i = 0; i < 4; i++){
//            System.out.println(it.children[0].children[i].list.size());
//        }
        ArrayList<Tweet> q = new ArrayList<>();
        it.query(q,mbr1);
//        System.out.println(it.deeplevel);
        System.out.println("q.size() " + q.size());
//        System.out.println(it.invertIndexHashMap.size());
//        for(int i = 0; i < 4; i++) {
//            System.out.println(it.children[i].invertIndexHashMap.size());
//        }



    }



}
