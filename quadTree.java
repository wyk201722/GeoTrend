/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Inverted1Index
 * Author:   youkun
 * Date:     2018/11/17 7:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author youkun
 * @create 2018/11/17
 * @since 1.0.0
 */



import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeoutException;


public class quadTree implements Serializable {
    public  static int deeplevel ;
    double maxX;
    double minX;
    double maxY;
    double minY;
    int limitLevel;
    int currentLevel;
    public quadTree parent;
    public quadTree[] children;
    public double[] location;
    public int maxCapacity;
    public int currentCapacity;
    public HashMap<String, LinkedList<Tweet>> invertIndexHashMap;
    public ArrayList<Tweet> list;

    quadTree(quadTree pa, double maxX, double minX, double maxY, double minY, int maxCap, int level){

        this.maxX = maxX;
        this.maxY = maxY;
        this.minY = minY;
        this.minX = minX;
        list = new ArrayList<Tweet>();
        limitLevel = 15;
        currentLevel = level;
        deeplevel = Math.max(currentLevel, deeplevel);
        parent = pa;
        children = new quadTree[4];
        location = new double[4];
        this.location[0] = maxX;
        this.location[1] = minX;
        this.location[2] = maxY;
        this.location[3] = minY;
        maxCapacity = maxCap;
        currentCapacity = 0;
        invertIndexHashMap = new HashMap<>();
    };


    public quadTree loadStagingFile() {
        quadTree e = null;
        try
        {
            FileInputStream fileIn = new FileInputStream("C:\\Users\\youkun\\Desktop\\Inverted1Index\\ser\\map.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (quadTree) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Hashmap class not found");
            c.printStackTrace();
            return null;
        }
        return e;
    }

    public void generateStagingFile() {
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream("C:\\Users\\youkun\\Desktop\\Inverted1Index\\ser\\map.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }
    public void printHashMap(){
        int count = 0;
        String value = "";
        Iterator<Map.Entry<String, LinkedList<Tweet>>> iterator = invertIndexHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, LinkedList<Tweet>> entry = iterator.next();
            count = Math.max(count, entry.getValue().size());
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().size());
            for(int i = 0;  i < entry.getValue().size(); i++) {
                System.out.print(entry.getValue().get(i).UserID + " ");
            }
            System.out.println();
        }
        System.out.println(value);
        System.out.println(count);
    }

    public void readfileAndGenerateHashMap() {
        {
            try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
                /* 读入TXT文件 */
                String pathname = "./2016-06-12_02-26-46.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
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
    public boolean isUserInThisQuad(Tweet user, quadTree node) {
        return (user.Userlocation[0] > node.location[1]
                && user.Userlocation[0] < node.location[0]
                && user.Userlocation[1] > node.location[3]
                && user.Userlocation[1] < node.location[2]);
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
        if (this.currentLevel == limitLevel) {
            this.list.add(post);
            this.currentCapacity++;
        } else {
            if (this.currentCapacity < maxCapacity && (children[0] == null && children[1] == null && children[2] == null && children[3] == null)) {
                this.list.add(post);
                this.currentCapacity++;
            } else if (currentCapacity == maxCapacity) {
                this.createChildren();
                this.currentCapacity = 0;
                for (int i = 0; i < 4; i++) {
                    if (isUserInThisQuad(post, children[i])) {
                        this.children[i].addNewPost(post);
                    }
                }
            } else if (this.currentCapacity == 0 && !(children[0] == null && children[1] == null && children[2] == null && children[3] == null)){
                for (int i = 0; i < 4; i++) {
                    if (isUserInThisQuad(post, children[i])) {
                        this.children[i].addNewPost(post);
                    }
                }
            }
        }
    }


    public int returnChildrenSize() {
        int count = 0;

            for(int i = 0; i < 4; i++) {
                if(this.children[i] != null) {
                    count += this.children[i].returnChildrenSize();
                }
            }

        return this.currentCapacity+count;
    }


    public void queryMinimumBoundTree(ArrayList<Tweet> arr){
        quadTree minimumBoundTree = new quadTree(null, this.maxX,this.minX,this.maxY,this.minY,0,0);
        this.query(arr, minimumBoundTree);
    }

    public void createChildren() {

        this.children[0] = new quadTree(this, this.location[0], (this.location[0] + this.location[1]) / 2, this.location[2],(this.location[2] + location[3]) / 2,this.maxCapacity,this.currentLevel + 1);
        this.children[1] = new quadTree(this,(this.location[0] + this.location[1]) / 2, this.location[1], this.location[2],(this.location[2] + location[3]) / 2,this.maxCapacity,this.currentLevel + 1);
        this.children[2] = new quadTree(this,(this.location[0] + this.location[1]) / 2, this.location[1],(this.location[2] + location[3]) / 2, location[3],this.maxCapacity,this.currentLevel + 1);
        this.children[3] = new quadTree(this,this.location[0],(this.location[0] + this.location[1]) / 2,(this.location[2] + location[3]) / 2,location[3],this.maxCapacity,this.currentLevel + 1);
        for(int j = 0; j < 4; j++) {
            for (int i = 0; i < list.size(); i++) {
                if (isUserInThisQuad(list.get(i), this.children[j])) {
                    this.children[j].list.add(this.list.get(i));
                    this.children[j].currentCapacity++;

                }
            }
        }
        this.list.clear();


        /*
        Iterator<Map.Entry<String, LinkedList<Tweet>>> iterator = this.invertIndexHashMap.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<String, LinkedList<Tweet>> entry = iterator.next();

            for(int i = 0;  i < entry.getValue().size(); i++) {
                if (isUserInThisQuad(entry.getValue().get(i), children[0])) {
                    if (this.children[0].invertIndexHashMap.containsKey(entry.getKey())) {
                        this.children[0].invertIndexHashMap.get(entry.getKey()).add(entry.getValue().get(i));
                    } else {
                        LinkedList<Tweet> Un = new LinkedList<>();
                        Un.add(entry.getValue().get(i));
                        this.children[0].invertIndexHashMap.put(entry.getKey(), Un);
                    }
                }

                if (isUserInThisQuad(entry.getValue().get(i), children[1])) {
                    if (this.children[1].invertIndexHashMap.containsKey(entry.getKey())) {
                        this.children[1].invertIndexHashMap.get(entry.getKey()).add(entry.getValue().get(i));
                    } else {
                        LinkedList<Tweet> Un = new LinkedList<>();
                        Un.add(entry.getValue().get(i));
                        this.children[1].invertIndexHashMap.put(entry.getKey(), Un);
                    }

                }

                if (isUserInThisQuad(entry.getValue().get(i), children[2])) {
                    if (this.children[2].invertIndexHashMap.containsKey(entry.getKey())) {
                        this.children[2].invertIndexHashMap.get(entry.getKey()).add(entry.getValue().get(i));
                    } else {
                        LinkedList<Tweet> Un = new LinkedList<>();
                        Un.add(entry.getValue().get(i));
                        this.children[2].invertIndexHashMap.put(entry.getKey(), Un);
                    }
                }

                if (isUserInThisQuad(entry.getValue().get(i), children[3])) {
                    if (this.children[3].invertIndexHashMap.containsKey(entry.getKey())) {
                        this.children[3].invertIndexHashMap.get(entry.getKey()).add(entry.getValue().get(i));
                    } else {
                        LinkedList<Tweet> Un = new LinkedList<>();
                        Un.add(entry.getValue().get(i));
                        this.children[3].invertIndexHashMap.put(entry.getKey(), Un);
                    }
                }
            }
        }*/
    }


    public void query(ArrayList<Tweet> arr, quadTree qt) {


        if(this.list.size() == 0 ) {
            if(this.children[0] != null
                    && (qt.maxX >= this.children[0].minX && qt.maxY >= this.children[0].minY)
                    && (qt.maxX <= this.children[0].maxX && qt.maxY <= this.children[0].maxY)) {

                this.children[0].query(arr, new quadTree(null, qt.maxX,  Math.max(this.children[0].minX, qt.minX), qt.maxY,  Math.max(this.children[0].minY, qt.minY),0,0));

            }
            if (this.children[1] != null
                    && (qt.minX <= this.children[1].maxX && qt.maxY >= this.children[1].minY)
                    && (qt.minX >= this.children[1].minX && qt.maxY <= this.children[1].maxY)) {

                this.children[1].query(arr, new quadTree(null, Math.min(this.children[1].maxX, qt.maxX),  qt.minX, qt.maxY,  Math.max(this.children[1].minY, qt.minY),0,0));

            }
            if (this.children[2] != null
                    && (qt.minX <= this.children[2].maxX && qt.minY <= this.children[2].maxY)
                    && (qt.minX >= this.children[2].minX && qt.minY >= this.children[2].minY)) {

                this.children[2].query(arr, new quadTree(null, Math.min(this.children[2].maxX, qt.maxX),  qt.minX, Math.min(this.children[2].maxY, qt.maxY),  qt.minY,0,0));

            }
            if (this.children[3] != null
                    && (qt.maxX >= this.children[3].minX && qt.minY <= this.children[3].maxY)
                    && (qt.maxX <= this.children[3].maxX && qt.minY >= this.children[3].minY)) {

                this.children[3].query(arr, new quadTree(null, qt.maxX,  Math.max(this.children[3].minX, qt.minX), Math.min(this.children[3].maxY, qt.maxY),  qt.minY,0,0));
            }
        } else {

            for (int j = 0; j < this.currentCapacity; j++) {
                if (isUserInThisQuad(this.list.get(j), qt)) {
                    arr.add(this.list.get(j));
                }
            }
        }
    }

    public boolean isInRange(Tweet t, double x, double y, double range) {
        return ( Math.sqrt(Math.pow(t.Userlocation[0] - x,2) +  Math.pow(t.Userlocation[1] - y,2)) <= range);
    }

    public ArrayList<Tweet> rangeQuery(double x, double y, double range) {
        quadTree queryRectangle = new quadTree(null, x+range, x-range, y+range, y-range,0,0);
        ArrayList<Tweet> origin = new ArrayList<>();
        this.query(origin, queryRectangle);
        ArrayList<Tweet> result = new ArrayList<>();
        for(int i = 0; i < origin.size(); i++) {
            if(isInRange(origin.get(i),x,y, range)) {
                result.add(origin.get(i));
            }
        }
        return result;
    }

    public PriorityQueue<Tweet> queryKNN(double x, double y, int k) {
      PriorityQueue<Tweet> result = new PriorityQueue<Tweet>((o1, o2) ->(int) Math.ceil((Math.sqrt(Math.pow(o2.Userlocation[0] - x,2) +  Math.pow(o2.Userlocation[1] - y,2)) - Math.sqrt(Math.pow(o1.Userlocation[0] - x,2) +  Math.pow(o1.Userlocation[1] - y,2)))));
      PriorityQueue<Tweet> temp = new PriorityQueue<Tweet>((o1, o2) ->(int) Math.ceil((Math.sqrt(Math.pow(o2.Userlocation[0] - x,2) +  Math.pow(o2.Userlocation[1] - y,2)) - Math.sqrt(Math.pow(o1.Userlocation[0] - x,2) +  Math.pow(o1.Userlocation[1] - y,2)))));
        quadTree leaf = findleaf(this,x,y);
        if(leaf != null || leaf.list.size() > k) {

            if(leaf.list.size() > k) {
                for(int i = 0; i < 3; i++) {
                    result.add(leaf.list.get((int) (Math.random() * leaf.list.size())));
                }
            }
            Tweet third = result.peek();
            double radius = Math.sqrt(Math.pow(third.Userlocation[0] - x,2) +  Math.pow(third.Userlocation[1] - y,2));
            for (Tweet t: leaf.list) {
                if((Math.sqrt(Math.pow(t.Userlocation[0] - x,2) +  Math.pow(t.Userlocation[1] - y,2))) < radius){
                    result.add(t);
                }
            }

        } else {
            for (quadTree child:leaf.parent.children) {
                    result.addAll(child.queryKNN(x,y,k-leaf.list.size()));
            }
        }
        if(result.size() > k) {
            for (int i = result.size(); i > k;i--) {
                Tweet tre = result.remove();
            }
        }
        return result;
    }

    public quadTree findleaf(quadTree tr,double x, double y) {
        if (tr.list.size() != 0) {
                return   tr;
        } else {
            for (quadTree child:tr.children) {
                if (child.maxY >= y && child.minY <= y && child.maxX >= x && child.minX <= x) {
                    return tr.findleaf(child,x,y);
                }
            }
            return null;
        }
    }
}