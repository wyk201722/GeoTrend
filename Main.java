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
        /*QuadTree test part*/
        quadTree it = new quadTree(null,180,-180,90,-90,3000,0);
//        quadTree mbr1 = new quadTree(null,0.154372, 0,51.667789, 51.306926, 0,0);
//        quadTree mbr2 = new quadTree(null,139.814362,139.6571201,35.780916,35.619906,0 ,0 );
        it.readfileAndGenerateHashMap();
//        ArrayList<Tweet> q1 = new ArrayList<>();
//        it.query(q1, mbr2);
//        System.out.println(q1.size());
//        for(int i = 0; i < q1.size(); i++) {
//            System.out.println(q1.get(i).textstring);
//        }
//        System.out.println();
//        /* range   query*/
        PriorityQueue<Tweet> q2;
//        q2 = it.rangeQuery(0,0,6);
//        System.out.println(q2.size());
//        for(int i = 0; i < q2.size(); i++) {
//            System.out.println(Math.sqrt(Math.pow(q2.get(i).Userlocation[0] - 0,2) +  Math.pow(q2.get(i).Userlocation[1] - 0,2)));
//        }

        q2 = it.queryKNN(0,0,5);
        System.out.println(q2.size());
        for (Tweet t:
             q2) {
            System.out.println((Math.sqrt(Math.pow(t.Userlocation[0] - 0,2) +  Math.pow(t.Userlocation[1] - 0,2))));
        }
//        for(int i =0; i < q1.size(); i++) {
//            for(int j = 0; j < q1.get(i).text.size(); j++) {
//                System.out.print(q1.get(i).text.get(j) + " ");
//            }
//            System.out.println();
//        }
//        System.out.println(it.invertIndexHashMap.size());
//        for(int i = 0; i < 4; i++) {
//            System.out.println(it.children[i].invertIndexHashMap.size());
//        }

        //Grid test part
//        Grid g1 = new Grid(10,10);
//        System.out.println(g1.getX(0));
//        System.out.println(g1.getY(37));
//        g1.readfileAndGenerateHashMap();
       /*test insertion
        int total = 0;
        for(int i = 0; i < 10; i++) {
            if (g1.matrix.containsKey(i)) {
                for (int j = 0; j < 10 ; j++) {
                    if (g1.matrix.get(i).containsKey(j)) {
                        total += g1.matrix.get(i).get(j).userList.size();
                        System.out.println("x = " + i  + " y = "  + j  + "  " +  g1.matrix.get(i).get(j).userList.size());
                    }
                }
            }
        }
        System.out.println(total);*/

       /*test query*/

//       ArrayList<Tweet> re = g1.queryRectanglewithLocation(0.154372, 0,51.667789, 51.306926);

//        ArrayList<Tweet> re = g1.queryRectanglewithLocation(139.814362,139.6571201,35.780916,35.619906);
//                System.out.println(re.size());
//        for (Tweet t:
//             re) {
//            System.out.println( t.textstring);
//        }
//        ArrayList<Tweet> re2 = g1.rangeQuery(0,0,6);
//        for(int i = 0; i < re2.size(); i++) {
//            System.out.println(Math.sqrt(Math.pow(re2.get(i).Userlocation[0] - 0,2) +  Math.pow(q2.get(i).Userlocation[1] - 0,2)));
//        }
    }



}
