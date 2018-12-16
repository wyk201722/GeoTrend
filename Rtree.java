/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Rtree
 * Author:   youkun
 * Date:     2018/12/13 3:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author youkun
 * @create 2018/12/13
 * @since 1.0.0
 */
public class Rtree {
    private RTNode root;


    public Rtree(RTNode root) {
        this.root = root;
    }

    public RTNode getRoot() {
        return root;
    }

    public void setRoot(RTNode root) {
        this.root = root;
    }

    public boolean insert(Rectangle rec) {
        return false;
    }

    public int delete (Rectangle rec) {
        return 0;
    }

    public void readfileAndGenerateHashMap() {
        {
            try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
                /* 读入TXT文件 */
                String pathname = "C:\\Users\\youkun\\Downloads\\2016-06-12_02-26-46 (1).txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
                File filename = new File(pathname); // 要读取以上路径的input。txt文件
                InputStreamReader reader = new InputStreamReader(
                        new FileInputStream(filename)); // 建立一个输入流对象reader
                BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
                String line = "";
                line = br.readLine();
                int count = 0;
                while (line != null) {
                    this.addNewPost(buildNewTweet(line));

                    line = br.readLine(); // 一次读入一行数据//line != null
                    count ++;
                }
                System.out.println(count);
//                for(int j = 0; j < UserSet.size(); j++ ) {
//                    System.out.println(j);
//                    for(int u = 0; u < UserSet.get(j).text.size(); u++) {
//                        if(!invertIndexHashMap.containsKey(UserSet.get(j).text.get(u))) {// If the text shows first time, put the key(test) and value(Username)
//                            LinkedList<Text> Un = new LinkedList<>();
//                            Un.add(UserSet.get(j));
//                            invertIndexHashMap.put(UserSet.get(j).text.get(u), Un);
//                        }
//                        else {
//                            invertIndexHashMap.get(UserSet.get(j).text.get(u)).add(UserSet.get(j));// if the text is already exist, make the linkedlist add Username
//                        }
//                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Tweet buildNewTweet(String line) {
        String recordID = "";
        String time = "";
        String ts = "";
        String tempText = "";
        LinkedList<String> text = new LinkedList<>();
        String[] location = new String[4];
        String[] result = line.split(",");
        for (int i = 0; i < result.length; i++) {
            if (i < 4) {
                location[i] = result[i];
            }
            if (i == 4) {
                recordID = result[i];
                i++;
            }
            if (i == 5) {
                time = result[i];
                i++;
            }
            if (i > 5) {
                ts = ts + " " + result[i];
                text.add(result[i]);
            }
        }
        double[] loca = new double[4];
        for (int i = 0; i < 4; i++) {
            double d = Double.valueOf(location[i]);
            loca[i] = d;
        }
        double[] loca1 = new double[2];
        loca1[0] = (loca[0] + loca[1]) / 2;
        loca1[1] = (loca[2] + loca[3]) / 2;
        return new Tweet(loca1, recordID, time, text,ts);
    }

    public void addNewPost(Tweet post) {
        RTNode l = this.getRoot().chooseLeaf(post);
        RTNode newNode = null;
        if(l.getUserList().size() + 1 < l.getRecCapacity()) {
            l.addData(post);
            l.adjustTree(null);
        } else {
            l.addData(post);
            newNode = l.splitNodeQuadratic();
        }
    }

}
