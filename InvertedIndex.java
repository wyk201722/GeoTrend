/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: InvertedIndex
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


public class InvertedIndex implements Serializable {
    private LinkedList<Text> UserSet = new LinkedList<>();

    public HashMap<String, LinkedList<Text>> invertIndexHashMap = new HashMap<>();

    InvertedIndex(){};


    public HashMap loadStagingFile() {
        HashMap e = null;
        try
        {
            FileInputStream fileIn = new FileInputStream("C:\\Users\\youkun\\Desktop\\InvertedIndex\\ser\\map.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (HashMap) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
        return e;
    }

    public void generateStagingFile() {
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream("C:\\Users\\youkun\\Desktop\\InvertedIndex\\ser\\map.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.invertIndexHashMap);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }
    public void printHashMap(){

        Iterator<Map.Entry<String, LinkedList<Text>>> iterator = invertIndexHashMap.entrySet().iterator();
        while (iterator.hasNext()) {

            Map.Entry<String, LinkedList<Text>> entry = iterator.next();
            System.out.println(entry.getKey());
            for(int i = 0;  i < entry.getValue().size(); i++) {
                System.out.print(entry.getValue().get(i).UserID + " ");
            }
            System.out.println();

        }
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
                while (line != null) {

                    String recordID = "";
                    String time ="";
                    String tempText = "";
                    LinkedList<String> text = new LinkedList<>();
                    String[] location = new String[4];
                    String[] result = line.split(",");
                    for(int i = 0; i < result.length; i++) {
                        if(i < 4) {
                            location[i] = result[i];
                            i++;
                        }
                        if(i == 4) {
                            recordID = result[i];
                            i++;
                        }
                        if(i == 5) {
                            time = result[i];
                            i++;
                        }
                        if(i > 5) {
                            text.add(result[i]);
                        }
                    }

                    UserSet.add(new Text(location,recordID,time,text));
                    line = br.readLine(); // 一次读入一行数据

                }

                for(int j = 0; j < UserSet.size(); j++ ) {
                    System.out.println(j);
                    for(int u = 0; u < UserSet.get(j).text.size(); u++) {
                        if(!invertIndexHashMap.containsKey(UserSet.get(j).text.get(u))) {// If the text shows first time, put the key(test) and value(Username)
                            LinkedList<Text> Un = new LinkedList<>();
                            Un.add(UserSet.get(j));
                            invertIndexHashMap.put(UserSet.get(j).text.get(u), Un);
                        }
                        else {
                            invertIndexHashMap.get(UserSet.get(j).text.get(u)).add(UserSet.get(j));// if the text is already exist, make the linkedlist add Username
                        }
                    }
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}