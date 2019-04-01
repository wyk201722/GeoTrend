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

import java.util.ArrayList;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author youkun
 * @create 2018/12/13
 * @since 1.0.0
 */
public class RTNode {
    public Rtree tree;
    public RTNode parent;
    public int maxChildren;
    public int minChildren;
    public int TweetCapacity;
    public ArrayList<RTNode> children;
    public Rectangle rectangle;
    public ArrayList<Tweet> tweets;


    public RTNode(Rtree tree, RTNode parent, ArrayList<RTNode> children) {
        this.tree = tree;
        this.parent = parent;
        this.maxChildren = tree.maxChildren;
        this.minChildren = tree.minChildren;
        this.TweetCapacity = tree.TweetCapacity;
        if(children == null || children.size() == 0) {
            this.children = new ArrayList<>();
        } else   {
            this.children.addAll(children);
        }
    }


    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return (this.children.size() != 0);
    }

    public boolean isIndex() {
        return false;
    }



    public void splitChildrenQuadatic(){

    }

    public RTNode splitNodeQuadratic() {
        RTNode newNode = new RTNode(this.tree, this.parent,null);
        this.parent.children.add(newNode);
        Tweet seed1 = null;
        Tweet seed2 = null;
        double Area = 0;
        ArrayList<Tweet> store = new ArrayList<>();

        for (Tweet tw1: this.tweets) {
            store.add(tw1);
            for (Tweet tw2: this.tweets) {
                if (this.getAreaofTweet(tw1,tw2) > Area) {
                    Area = this.getAreaofTweet(tw1,tw2);
                    seed1 = tw1;
                    seed2 = tw2;
                }
            }
        }
        this.tweets.clear();
        this.tweets.add(seed1);
        newNode.tweets.add(seed2);
        this.rectangle.maxX = seed1.Userlocation[0];
        this.rectangle.minX = seed1.Userlocation[0];
        this.rectangle.maxY = seed1.Userlocation[1];
        this.rectangle.minY = seed1.Userlocation[1];
        newNode.rectangle.maxX = seed2.Userlocation[0];
        newNode.rectangle.minX = seed2.Userlocation[0];
        newNode.rectangle.maxY = seed2.Userlocation[1];
        newNode.rectangle.minY = seed2.Userlocation[1];


        for (Tweet post : store) {
                if (this.enlargement(post) > newNode.enlargement(post)) {
                        newNode.tweets.add(post);
                        newNode.rectangle.updateRec(post);
                } else {
                        this.tweets.add(post);
                        this.rectangle.updateRec(post);
                }
            }
            if(newNode.tweets.size() != 0) {
                return newNode;
            }
        return null;
    }

    public void adjustTree(RTNode newnode){

            while(!this.isRoot()) {

            }
    }
    public double getAreaofTweet(Tweet tw1, Tweet tw2) {
        return Math.abs(tw1.Userlocation[0] - tw2.Userlocation[0])
                * Math.abs(tw1.Userlocation[1] - tw2.Userlocation[1]);
    }

    public RTNode chooseLeaf(Tweet post) {

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
            if ((post.Userlocation[0] - rectangle.maxX )* (post.Userlocation[0] - rectangle.minX) > 0) {
                if ((post.Userlocation[1] - rectangle.maxY) * (post.Userlocation[1] - rectangle.minY) > 0) {
                    return Math.max(Math.abs(post.Userlocation[0] - rectangle.maxX), Math.abs(post.Userlocation[0] - rectangle.minX))
                            * Math.max(Math.abs(post.Userlocation[1] - rectangle.maxY), Math.abs(post.Userlocation[1] - rectangle.minY) - this.rectangle.getArea());
                } else {
                        return Math.min(Math.abs(post.Userlocation[0] - rectangle.maxX), Math.abs(post.Userlocation[0] - rectangle.minX)) * (this.rectangle.maxY - this.rectangle.minY);
                }
            } else {
                return Math.min(Math.abs(post.Userlocation[1] - rectangle.maxY), Math.abs(post.Userlocation[1] - rectangle.minY)) * (this.rectangle.maxX - this.rectangle.minX);
            }
        }
    }
}
