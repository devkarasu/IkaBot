package TeamIka;
import robocode.*;
import TeamIka.Territory;
import java.awt.geom.Point2D;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Ikabot - a robot by Kazuma Wada
 */
public class Ikabot extends AdvancedRobot
{
	Territory nawabari = new Territory(getX(), getY());
	public void run() {
		while(true){
			moveToPoint(nawabari.getNextPoint());
		}
	}

	private void moveToPoint(Point2D.Double point) {
		double dx = point.getX() - getX();
		double dy = point.getY() - getY();
		double angle = Math.toDegrees(Math.atan2(dx, dy)) - getHeading();
	
		turnRight(angle);
		ahead(Math.sqrt(dx * dx + dy * dy));
	}
}
