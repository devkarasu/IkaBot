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
		Territory nawabari = new Territory(getX(), getY(), 20);
		setAdjustGunForRobotTurn(true);
		setTurnGunRightRadians(Double.POSITIVE_INFINITY);

		while(true){
			if(getDistanceRemaining() == 0){
				Point2D.Double a = nawabari.getNextPoint();
				out.println("(" + a.getX() + "," + a.getY() + ")");
				goTo(a);
			}
		}
	}

	private void goTo(Point2D.Double point) {
		double x = point.getX();
		double y = point.getY();
		double a;
		setTurnRightRadians(Math.tan(a = Math.atan2(x -= (int) getX(), y -= (int) getY()) - getHeadingRadians()));
		ahead(Math.hypot(x, y) * Math.cos(a));
	}

	public void onScannedRobot(ScannedRobotEvent e){
		double radar = getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians();
		setTurnGunRightRadians(Utils.normalRelativeAngle(radar));

		if ( e.getDistance() < 200)
			setFire(3);
		else if(e.getDistance() < 600)
			setFire(2);
		else
			setFire(1);
	}

	public void onHitWall(HitWallEvent e){
		back(10);
	}
}
