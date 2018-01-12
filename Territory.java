package TeamIka;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Point2D.Double;
import robocode.*;

public class Territory{
    private Point2D.Double center;
    private ArrayList<Point2D.Double> points;
    private Iterator<Point2D.Double> it;
    private int NUM;

    public Territory(double x, double y){
        center = new Point2D.Double(x, y);
        NUM = 10;
        points = new ArrayList<Point2D.Double>();

        for(int i = 0; i < NUM; i++){
            double ranDis = Math.random() * 50;
            double ranAng = Math.random() * 2 * Math.PI;

            points.add(new Point2D.Double(center.getX() + ranDis * cos(ranAng), center.getY() + ranDis * sin(ranAng)));
        }
        it = points.iterator();
    }

    public int getNum(){
        return NUM;
    }

    public Point2D.Double getNextPoint(){
        if(it.hasNext){
            return it.next();
        }
        else{
            it = points.iterator();
            return it.next();
        }
    }
}