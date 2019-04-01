/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Rectangle
 * Author:   youkun
 * Date:     2018/12/13 3:54
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
 * @create 2018/12/13
 * @since 1.0.0
 */
public class Rectangle {
    public double maxX;
    public double minX;
    public double maxY;
    public double minY;
    public ArrayList<Tweet> userList;

    public Rectangle(double maxX, double minX, double maxY, double minY) {
        this.maxX = maxX;
        this.minX = minX;
        this.maxY = maxY;
        this.minY = minY;
    }
    public double getArea() {
        return (maxX-minX) * (maxY - minY);
    }

    public void updateRec(Tweet post) {
        if(!this.isUserHere(post)) {
            if ((post.Userlocation[0] - this.maxX) * post.Userlocation[0] - this.minX > 0) {
                if ((post.Userlocation[1] - this.maxY) * post.Userlocation[1] - this.minY > 0) {
                    this.maxY = Math.max(this.maxY,post.Userlocation[1]);
                    this.minY = Math.min(this.minY, post.Userlocation[1]);
                    this.maxX = Math.max(this.maxX,post.Userlocation[0]);
                    this.minX = Math.min(this.minX, post.Userlocation[0]);
                } else {
                    this.maxX = Math.max(this.maxX,post.Userlocation[0]);
                    this.minX = Math.min(this.minX, post.Userlocation[0]);
                }
            } else {
                this.maxY = Math.max(this.maxY,post.Userlocation[1]);
                this.minY = Math.min(this.minY, post.Userlocation[1]);
            }
        }
    }

    public boolean isUserHere(Tweet post) {
        return (post.Userlocation[0] > this.minX)
                && (post.Userlocation[0] < this.maxX)
                && (post.Userlocation[1] > this.minY)
                && (post.Userlocation[1] < this.maxY);
    }

    public void updateRec2(Rectangle child,Rectangle parent) {

    }
}
