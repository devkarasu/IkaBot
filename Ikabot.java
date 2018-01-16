package TeamIka;
import robocode.*;
import TeamIka.Territory;
import java.awt.geom.Point2D;
import robocode.util.Utils;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Ikabot - a robot by Kazuma Wada
 */
public class Ikabot extends AdvancedRobot
{
	public void run() {
		Territory nawabari = new Territory(getX(), getY(), 3);
		setAdjustGunForRobotTurn(true);
		setTurnGunRightRadians(Double.POSITIVE_INFINITY);

		for(int i=0; i<100; i++){
			Point2D.Double a = nawabari.getNextPoint();
			out.println("(" + a.getX() + "," + a.getY() + ")");
			moveToPoint(a);
		}
	}

	private void moveToPoint(Point2D.Double point) {
		double dx = point.getX() - getX();
		double dy = point.getY() - getY();
		double angleToTarget = Math.atan2(dx, dy);
		double targetAngle = Utils.normalRelativeAngle(angleToTarget - getHeadingRadians());
		double distance = Math.hypot(dx, dy);
		double turnAngle = Math.atan(Math.tan(targetAngle));
		setTurnRightRadians(turnAngle);
		if(targetAngle == turnAngle){
			ahead(distance);
		} else {
			back(distance);
		}
	}

	public void onScannedRobot(ScannedRobotEvent e){
		double radar = getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians();
		setTurnGunRightRadians(Utils.normalRelativeAngle(radar));

		setFire(1);
	}

	public void onHitWall(HitWallEvent e){
		back(10);
	}
}
