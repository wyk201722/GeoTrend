/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: RTNode
 * Author:   youkun
 * Date:     2018/12/13 3:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author youkun
 * @create 2018/12/13
 * @since 1.0.0
 */
public class RTNode {
    private Rtree tree;
    private RTNode parent;

    public int insertIndex;
    public int deleteIndex;
    private int nodeCapacity;
    private int recCapacity;
    public int usedSpace;
    private ArrayList<RTNode> children;
    private Rectangle rectangle;
    private ArrayList<Tweet> userList;


    public RTNode(Rtree tree, RTNode parent, int nodeCapacity, int recCapacity, ArrayList<RTNode> children) {
        this.tree = tree;
        this.parent = parent;
        this.nodeCapacity = nodeCapacity;
        this.recCapacity = recCapacity;
        this.usedSpace = 0;


        if(children == null || children.size() == 0) {
            this.children = new ArrayList<>();
        } else   {
            this.children.addAll(children);
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public ArrayList<Tweet> getUserList() {
        return userList;
    }

    public Rtree getTree() {
        return tree;
    }



    public int getNodeCapacity() {
        return nodeCapacity;
    }

    public int getRecCapacity() {
        return recCapacity;
    }



    public RTNode getParent() {
        return parent;
    }

    public ArrayList<RTNode> getChildren() {
        return children;
    }

    public boolean isRoot() {
        return (this.getParent() == null);
    }

    public boolean isLeaf() {
        return (this.userList.size() != 0);
    }

    public boolean isIndex() {
        return false;
    }

    public void addData(Tweet post) {
        this.userList.add(post);
        this.usedSpace++;
    }

    public RTNode splitNodeQuadratic() {
        RTNode newNode = new RTNode(this.tree, this.parent,this.getNodeCapacity(),this.recCapacity,null);
        this.parent.getChildren().add(newNode);
        Tweet seed1 = null;
        Tweet seed2 = null;
        double Area = 0;
        ArrayList<Tweet> store = new ArrayList<>();

        for (Tweet tw1: this.userList) {
            store.add(tw1);
            for (Tweet tw2: this.userList) {
                if (this.getAreaofTweet(tw1,tw2) > Area) {
                    Area = this.getAreaofTweet(tw1,tw2);
                    seed1 = tw1;
                    seed2 = tw2;
                }
            }
        }
        this.userList.clear();
        this.userList.add(seed1);
        newNode.userList.add(seed2);
        this.rectangle.setMaxX(seed1.Userlocation[0]);
        this.rectangle.setMinX(seed1.Userlocation[0]);
        this.rectangle.setMaxY(seed1.Userlocation[1]);
        this.rectangle.setMinY(seed1.Userlocation[1]);
        newNode.rectangle.setMaxX(seed2.Userlocation[0]);
        newNode.rectangle.setMinX(seed2.Userlocation[0]);
        newNode.rectangle.setMaxY(seed2.Userlocation[1]);
        newNode.rectangle.setMinY(seed2.Userlocation[1]);


        for (Tweet post : store) {
                if (this.enlargement(post) > newNode.enlargement(post)) {
                        newNode.userList.add(post);
                        newNode.rectangle.updateRec(post);
                } else {
                        this.userList.add(post);
                        this.rectangle.updateRec(post);
                }
            }
            if(newNode.getUserList().size() != 0) {
                return newNode;
            }
            return null;
    }

    public void adjustTree(RTNode newnode){

            while(!this.isRoot()) {
                RTNode parent = this.parent;
                for ( Tweet post: this.userList  ) {
                    parent.rectangle.updateRec(post);
                }
                if(parent.getChildren().size() >= nodeCapacity) {

                }
            }
    }
    public double getAreaofTweet(Tweet tw1, Tweet tw2) {
        return Math.abs(tw1.Userlocation[0] - tw2.Userlocation[0])
                * Math.abs(tw1.Userlocation[1] - tw2.Userlocation[1]);
    }

    public RTNode chooseLeaf(Tweet post) {
        if(this.isLeaf() && this.isRoot()) {
            return this;
        }
        if(this.isLeaf()) {
            return this;
        }
        else {
            double Area = 360 * 180;
            RTNode record = this;

            for (RTNode rt : children) {
                if(Area > rt.enlargement(post)) {
                    Area = rt.enlargement(post);
                    record = rt;
                }
            }
            record.rectangle.updateRec(post);
            return record.chooseLeaf(post);
        }
    }

    public void deleteData() {}

    public double enlargement(Tweet post) {
        if(this.rectangle.isUserHere(post)) {
            return 0;
        } else {
            if ((post.Userlocation[0] - rectangle.getMaxX()) * post.Userlocation[0] - rectangle.getMinX() > 0) {
                if ((post.Userlocation[1] - rectangle.getMaxY()) * post.Userlocation[1] - rectangle.getMinY() > 0) {
                    return Math.max(Math.abs(post.Userlocation[0] - rectangle.getMaxX()), Math.abs(post.Userlocation[0] - rectangle.getMinX()))
                            * Math.max(Math.abs(post.Userlocation[1] - rectangle.getMaxY()), Math.abs(post.Userlocation[1] - rectangle.getMinY())) - this.rectangle.getArea();
                } else {
                        return Math.min(Math.abs(post.Userlocation[0] - rectangle.getMaxX()), Math.abs(post.Userlocation[0] - rectangle.getMinX())) * (this.rectangle.getMaxY() - this.rectangle.getMinY());
                }
            } else {
                return Math.min(Math.abs(post.Userlocation[1] - rectangle.getMaxY()), Math.abs(post.Userlocation[1] - rectangle.getMinY())) * (this.rectangle.getMaxX() - this.rectangle.getMinX());
            }
        }
    }
    public void condenseTree() {}

    public int[][] quadrasticSplit() {return null;}

    public int[] pickSeed() {return null;}


}
