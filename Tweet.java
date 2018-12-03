/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Text
 * Author:   youkun
 * Date:     2018/11/17 12:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author youkun
 * @create 2018/11/17
 * @since 1.0.0
 */
public class Tweet implements Serializable{

        public String UserID;
        public double[] Userlocation;

        public LinkedList<String> text;
        public String time;

    Tweet(double[] UL,String uid, String time, LinkedList<String> tt ){
            this.Userlocation = UL;
            this.time = time;
            this.UserID = uid;
            this.text = tt;
        }


}
