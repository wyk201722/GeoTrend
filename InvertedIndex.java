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
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.jar.Attributes;

public class InvertedIndex {
    public static void main(String[] args) {

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
                LinkedList<Text> UserSet = new LinkedList<>();
                HashMap<String, LinkedList<Text>> invertIndex = new HashMap<>();


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

//                    for(int j = 0; j < text.size();j++){
//                        System.out.print(text.get(j) + " ");
//                    }
//                    System.out.println();
                    line = br.readLine(); // 一次读入一行数据
                }



                /*for(int k = 0; k < UserSet.size(); k++) {
                    System.out.println(k);
                    System.out.println(UserSet.get(k).Username);
                    for(int o = 0; o < UserSet.get(k).text.size(); o++) {
                        System.out.print(UserSet.get(k).text.get(o) + " ") ;
                    }
                    System.out.println();
                }//test UserSet, completed*/



                for(int j = 0; j < UserSet.size(); j++ ) {
//                    System.out.println(j);
//                    System.out.println(UserSet.get(j).time);
                    for(int u = 0; u < UserSet.get(j).text.size(); u++) {
//                        System.out.print(UserSet.get(j).text.get(u) + " ");
                        if(!invertIndex.containsKey(UserSet.get(j).text.get(u))) {// If the text shows first time, put the key(test) and value(Username)

                            LinkedList<Text> Un = new LinkedList<>();
                            Un.add(UserSet.get(j));

                            invertIndex.put(UserSet.get(j).text.get(u), Un);
                        }
                        else {
                            invertIndex.get(UserSet.get(j).text.get(u)).add(UserSet.get(j));// if the text is already exist, make the linkedlist add Username
                        }

                    }

//                        System.out.println();
                }

                /*test the HashMap*/
//                System.out.println(invertIndex.size());
                /*for(String key : invertIndex.keySet()){
                    System.out.println(key);
                    LinkedList<String> value = invertIndex.get(key);
                    for(int i = 0; i < value.size(); i++){
                        System.out.print(value.get(i) + " ");
                    }
                    System.out.println();
                }*/

//                /* 写入Txt文件 */
//                File writename = new File("C:\\Users\\youkun\\Downloads\\新建文件夹"); // 相对路径，如果没有则要建立一个新的output。txt文件
//                writename.createNewFile(); // 创建新文件
//                BufferedWriter out = new BufferedWriter(new FileWriter(writename));
//                out.write("我会写入文件啦\r\n"); // \r\n即为换行
//                out.flush(); // 把缓存区内容压入文件
//                out.close(); // 最后记得关闭文件

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Text{
    public String UserID;
    public String[] Userlocation;

    public LinkedList<String> text;
    public String time;
    Text(String[] UL,String uid, String time, LinkedList<String> tt ){
        this.Userlocation = UL;
        this.time = time;
        this.UserID = uid;
        this.text = tt;
    }
    public void addtext(String t) {
        text.add(t);
    }
}


