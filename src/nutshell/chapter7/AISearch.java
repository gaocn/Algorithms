package nutshell.chapter7;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by 高文文 on 2017/4/10.
 *
 * 状态树：博弈树和搜索树，状态树的根节点表示初始状态，边表示两个状态之间可以转换。
 */
public class AISearch {

    public static void main(String[] args) {

    }



}

/******* 博弈树寻路算法核心： IEvaluation ******/
//假设：对手走法相当完美，从当前局面状态的节点开始，计算出玩家的最优走法
interface IEvaluation {
    IGameMove bestMove(IGameState gameState, IPlayer player, IPlayer opponent);
}

/**  博弈树算法的核心概念：将算法和对局分开 **/

//指导局面状态的搜索
interface IGameState {
    boolean isDraw(); /** 计算当前局面是否会导致平局 */
    boolean isWin();  /** 计算是否在当前局面下，某个玩家已经获胜*/
    IGameState copy(); /**返回当前局面状态的副本，玩家的移动可以在不破坏原始局面状态*/
    boolean equivalent(IGameState state); /** 决定局面状态是否相等 */
}

interface IPlayer {
    int eval(IGameState state);
    void score(IGameState state);
    Collection<IGameState> validMoves(IGameState state);
}

/**
 * 使用BoardEvaluation计分函数计算“一字棋”得分，nc(gameState,p)返回一字棋局面下的行数、列数或对角数
 * gameState表示玩家p可能在一行连成三个的局面：
 *  1. 正无穷 如果玩家能够在当前局面gameState下赢得游戏
 *  2. 负无穷 如果玩家在当前局面gameState下输掉游戏
 *  3. nc(gameState, player) - nc(gameState, opponent) 如果当前局面会导致平局
 */
interface IGameScore {
    int score(IGameState state, IPlayer player);
}

/** 当前局面下的有效走法 */
interface IGameMove {
    boolean isValid(IGameState state);
    boolean execute(IGameState state);
    boolean undo(IGameState state);
}

