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
        /*QuadTree test part
        quadTree it = new quadTree(null,180,-180,90,-90,3000,0);
        quadTree mbr1 = new quadTree(null,28.101051,40.036021,117.733459,105.305481, 0,0);
        it.readfileAndGenerateHashMap();
        ArrayList<Tweet> q1 = new ArrayList<>();
        it.queryMinimumBoundTree(q1);
        System.out.println("q1.size() " + q1.size());
        ArrayList<Tweet> q2;
        q2 = it.rangeQuery(0,0,6);
        System.out.println(q2.size());
        for(int i = 0; i < q2.size(); i++) {
            System.out.println(Math.sqrt(Math.pow(q2.get(i).Userlocation[0] - 0,2) +  Math.pow(q2.get(i).Userlocation[1] - 0,2)));
        }

//        q2 = it.queryKNN(0,0,3,5);
//        for(int i = 0; i < 3; i++){
//            System.out.println(Math.sqrt(Math.pow(q2.get(i).Userlocation[0] - 0,2) +  Math.pow(q2.get(i).Userlocation[1] - 0,2)));
//        }
//        for(int i =0; i < q1.size(); i++) {
//            for(int j = 0; j < q1.get(i).text.size(); j++) {
//                System.out.print(q1.get(i).text.get(j) + " ");
//            }
//            System.out.println();
//        }
//        System.out.println(it.invertIndexHashMap.size());
//        for(int i = 0; i < 4; i++) {
//            System.out.println(it.children[i].invertIndexHashMap.size());
//        }*/

        //Grid test part
        Grid g1 = new Grid(10,10,null);
        System.out.println(g1.getX(-180));
        System.out.println(g1.getY(90));
    }



}
