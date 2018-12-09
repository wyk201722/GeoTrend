/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: smallGrid
 * Author:   youkun
 * Date:     2018/12/9 15:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import java.util.ArrayList;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author youkun
 * @create 2018/12/9
 * @since 1.0.0
 */
public class smallGrid {
    public Grid parent;
    public int x;
    public int y;
    public ArrayList<Tweet> userList;

    public smallGrid(Grid parent, int x, int y, ArrayList<Tweet> userList) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.userList = userList;
    }
}
