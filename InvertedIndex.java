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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

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
                HashMap<String, LinkedList<String>> invertIndex = new HashMap<>();
                while (line != null) {
                    String recordID = "";
                    String tempText = "";
                    LinkedList<String> text = new LinkedList<>();
                    int i = line.length();
                    int cout = 0;
//                        System.out.println(i);

                    for(int j = 0; j < i; j++) {

                        if(line.charAt(j) == ',') {
                            cout++;
                            j++;
                        }
                        if(cout == 4) {
                            recordID = recordID.concat(line.substring(j, j+1));
                        }
                        if(cout >= 6) {

                            tempText = tempText.concat(line.substring(j, i));
                            break;
                        }
                    }//recordID: get UserId; temptext get the whole text;
//                        System.out.println(tempText);
                    String eachT = "";
                    for(int k = 0; k < tempText.length(); k++) {

                        if(tempText.charAt(k) != ',') {
                            eachT = eachT.concat(tempText.substring(k, k + 1));
                        }
                        if(tempText.charAt(k) == ',') {
                            text.add(eachT);
                            eachT = "";
                        }
                    }//make the temptext saparated by "," and put them into the Text(linkedList)

//                        for(int temp = 0; temp < text.size(); temp++) {
//                            System.out.println(text.get(temp));
//                        }//every user's text



                    UserSet.add(new Text(recordID, text));

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
                    System.out.println(j);
                    System.out.println(UserSet.get(j).UserID);
                    for(int u = 0; u < UserSet.get(j).text.size(); u++) {
                        System.out.print(UserSet.get(j).text.get(u) + " ");
                        if(!invertIndex.containsKey(UserSet.get(j).text.get(u))) {// If the text shows first time, put the key(test) and value(Username)

                            LinkedList<String> Un = new LinkedList<>();
                            Un.add(UserSet.get(j).UserID);

                            invertIndex.put(UserSet.get(j).text.get(u), Un);
                        }
                        else {
                            invertIndex.get(UserSet.get(j).text.get(u)).add(UserSet.get(j).UserID);// if the text is already exist, make the linkedlist add Username
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
    Text(String un, LinkedList<String> tt){
        this.UserID = un;
        this.text = tt;
    }
    public void addtext(String t) {
        text.add(t);
    }
}


