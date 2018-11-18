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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

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
        InvertedIndex it = new InvertedIndex();
//        it.readfileAndGenerateHashMap();
//        it.generateStagingFile();
        HashMap hp = null;
        hp = it.loadStagingFile();
        it.invertIndexHashMap = hp;
        it.printHashMap();
    }


//    public static void main(String[] args) {
//        InvertedIndex it = new InvertedIndex();
//        it.readfileAndGenerateHashMap();
//        it.generateStagingFile();
//
//    }
}
