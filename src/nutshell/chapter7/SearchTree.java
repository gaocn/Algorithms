package nutshell.chapter7;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by 高文文 on 2017/4/10.
 */
public class SearchTree {
    public static void main(String[] args) {

    }







}



/**************** *******************/

interface ISearch {
    Solution search(INode initial, INode goal);
}

/**************** 搜索树算法核心概念  *******************/

abstract class Solution {
    INode initial;
    INode goal;

    abstract LinkedList<IMove> moves();
    abstract boolean successed();
}


interface INode {
    LinkedList<IMove> validMoves(); /**当前局面下的有效走法*/
    void score(int s);             /**  给当前局面打一个整数分值*/
    int socre();                   /**  返回之前给当前局面的分值*/
    INode copy();
    boolean equivalent(INode state);
    Object key();                  /**  返回一个对象，若key返回值相同，则两个棋面状态相同*/
    Object storedData(Object obj); /** 得到和给定对象相关的棋面状态 */
    Object storedData();           /** 得到可能和给定对象相关的棋面状态 */
}

interface IMove {
    boolean isValid(INode s);
    boolean execute(INode s);
    boolean undo(INode s);
}

/** 抽象出INode集合组织形式的实现，有些算法需要队列、栈，AVL、Red-Black平衡搜索树 */
interface INodeSet {
    boolean isEnpty();
    int size();
    INode contains(INode s);
    INode remove(INode s);
    void insert(INode s);
    Iterator<INode> iterator();
}

