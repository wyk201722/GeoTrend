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
import java.util.ArrayList;
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
    public int maxChildren;
    public int minChildren;
    public int TweetCapacity;

    public Rtree(int max,RTNode root) {
        this.maxChildren = max;
        this.minChildren = max/2;
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
                    this.insert(buildNewTweet(line));

                    line = br.readLine(); // 一次读入一行数据//line != null
                    count ++;
                }
                System.out.println(count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Tweet buildNewTweet(String line) {
        String recordID = "";
        String time = "";
        String ts = "";
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

    public void insert(Tweet post) {
        RTNode node = this.root.chooseLeaf(post);
        RTNode newNode = null;
        if(node.maxChildren == node.tweets.size() + 1) {
            node.tweets.add(post);
            newNode = node.splitNodeQuadratic();
            this.adjustTree(node,newNode);
        } else {
            node.tweets.add(post);
            node.rectangle.updateRec(post);
        }
    }

    public void adjustTree(RTNode node1,RTNode node2) {
        if(!node1.isRoot()) {
            RTNode parent = node1.parent;
            RTNode newNode = null;
            if(node2 != null && parent.maxChildren == parent.children.size()) {
                parent.children.add(node2);
                newNode = parent.splitNodeQuadratic();
                this.adjustTree(parent,newNode);
            } else {
                adjustTree(parent,null);
            }
        } else {
            if(node2 != null ) {
                RTNode root = new RTNode(this,null,null);
                root.children.add(node1);
                root.children.add(node2);
                node1.parent = root;
                node2.parent = root;
            }
        }
    }

    public ArrayList<Tweet> search() {
        return null;
    }



    public void chooseLeaf(Tweet t) {

    }


    public void delete(Tweet t){
            RTNode leaf = findLeaf(t,this.root);
            if (leaf != null) {
                for (Tweet tt:
                     leaf.tweets) {
                    if (tt.equals(t)) {
                        leaf.tweets.remove(t);
                    }
                }
                condenseTree(leaf,t);
            }
    }


    public RTNode findLeaf(Tweet t, RTNode node) {
        if (node.isLeaf()) {
            for (Tweet tt : node.tweets) {
                if (tt.equals(t)) return node;
            }
        } else {
            for (RTNode nodes : node.children) {
                if (nodes.rectangle.isUserHere(t)) {
                    return findLeaf(t, nodes);
                }
            }
        }
        return null;
    }



    public void condenseTree(RTNode leaf,Tweet tt) {
        ArrayList<Tweet> entry = new ArrayList<>();
        if(leaf.isRoot()) {
            for (Tweet t: entry){
                this.insert(t);
            }
        } else {
            RTNode parent = leaf.parent;
            if(leaf.tweets.size() < leaf.minChildren) {
                entry.addAll(leaf.tweets);
                leaf.tweets.clear();
            } else {
                if(parent.rectangle.minX == tt.Userlocation[0]) {
                    double record = 180;
                    for (RTNode child: parent.children) {
                        for (Tweet tw: child.tweets) {
                            if(tw.Userlocation[0] < record && tw.Userlocation[0] != tt.Userlocation[0]) {
                                record = tw.Userlocation[0];
                            }
                        }
                    }
                    parent.rectangle.minX = record;
                }
                if( parent.rectangle.maxX == tt.Userlocation[0]) {
                    double record = -180;
                    for (RTNode child: parent.children) {
                        for (Tweet tw: child.tweets) {
                            if(tw.Userlocation[0] > record && tw.Userlocation[0] != tt.Userlocation[0]) {
                                record = tw.Userlocation[0];
                            }
                        }
                    }
                    parent.rectangle.maxX = record;
                }
                if (parent.rectangle.minY == tt.Userlocation[1]) {
                    double record = 180;
                    for (RTNode child: parent.children) {
                        for (Tweet tw: child.tweets) {
                            if(tw.Userlocation[1] < record && tw.Userlocation[1] != tt.Userlocation[1]) {
                                record = tw.Userlocation[1];
                            }
                        }
                    }
                    parent.rectangle.minY = record;
                }
                if (parent.rectangle.maxY == tt.Userlocation[1]) {
                    double record = -180;
                    for (RTNode child: parent.children) {
                        for (Tweet tw: child.tweets) {
                            if(tw.Userlocation[1] > record && tw.Userlocation[1] != tt.Userlocation[1]) {
                                record = tw.Userlocation[1];
                            }
                        }
                    }
                    parent.rectangle.maxY = record;
                }
            }
            condenseTree(parent,tt);
        }
    }
}
