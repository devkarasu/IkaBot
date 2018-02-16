package TeamIka;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Point2D;

/**
 * ロボットのナワバリに関する機能を提供するクラスです。
 * @author 和田 一真
 */

public class Territory{
    /**
     * ナワバリの中心点。
     * この点を中心にナワバリを構成する頂点が決定されます。
     */
    private Point2D.Double center;
    /**
     * ナワバリを構成する頂点座標のリスト。
     */
    private ArrayList<Point2D.Double> points;
    /**
     * ナワバリを構成する頂点を順に辿るためのイテレータ。
     * @see <a href="https://docs.oracle.com/javase/jp/8/docs/api/java/util/Iterator.html">Java.util.Iterator</a>
     */
    private Iterator<Point2D.Double> it;
    /**
     * ナワバリの頂点数。
     */
    private int NUM;

    /**
     * 新しいナワバリを生成します。
     * @param x 生成するナワバリの中心となるX座標
     * @param y 生成するナワバリの中心となるy座標
     * @param n 生成するナワバリの頂点数
     * @see #center
     * @see #points
     */
    public Territory(double x, double y, int n){
        center = new Point2D.Double(x, y);
        NUM = n;
        points = new ArrayList<Point2D.Double>();

        for(int i = 0; i < NUM; i++){
            double ranDis = Math.random() * 100;
            double angle = Math.toRadians(360 * i / NUM);

            points.add(new Point2D.Double(center.x + ranDis * Math.sin(angle), center.y + ranDis * Math.cos(angle)));
        }
        it = points.iterator();
    }

    /**
     * ナワバリの頂点数を戻します。
     * @return ナワバリの頂点数
     * @see #NUM
     */
    public int getNum(){
        return NUM;
    }

    /**
     * ナワバリの頂点座標を順番に戻します。
     * 最後の頂点を戻した後は、一番最初に戻ります。
     * @return ナワバリを構成する頂点のうち一つの座標
     * @see <a href="https://docs.oracle.com/javase/jp/8/docs/api/java/awt/geom/Point2D.Double.html">Point2D.Double</a>
     */
    public Point2D.Double getNextPoint(){
        if(it.hasNext()){
            return it.next();
        }
        else{
            it = points.iterator();
            return it.next();
        }
    }
}
