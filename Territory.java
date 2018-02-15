package TeamIka;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Point2D;

public class Territory{
    private Point2D.Double center;
    private ArrayList<Point2D.Double> points;
    private Iterator<Point2D.Double> it;
    private int NUM;

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

    public int getNum(){
        return NUM;
    }

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