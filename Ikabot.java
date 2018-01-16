package TeamIka;
import robocode.*;
import TeamIka.Territory;
import java.awt.geom.Point2D;
import robocode.util.Utils;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Ikabot - a robot by Kazuma Wada
 */
public class Ikabot extends AdvancedRobot
{
	public void run() {
		Territory nawabari = new Territory(getX(), getY(), 20);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);

		setColors(new Color(194, 87, 121), Color.white, new Color(194, 87, 121));

		Point2D.Double a;
		while(true){
			if(getRadarTurnRemaining() == 0){
				setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
			}

			if(getDistanceRemaining() == 0){
				if(Math.random() < 0.4)
					a = new Point2D.Double(Math.random() * 1000, Math.random() * 1000);					
				else
					a = nawabari.getNextPoint();
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
		double radar = getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians();
		setTurnRadarRightRadians(Utils.normalRelativeAngle(radar));

		double power;
		if ( e.getDistance() < 200)
			power = 3;
		else if(e.getDistance() < 600)
			power = 2;
		else
			power = 1;

		setTurnGunRightRadians(Utils.normalRelativeAngle(getHeadingRadians() + e.getBearingRadians() + Math.asin(e.getVelocity() / Rules.getBulletSpeed(power) * Math.sin(e.getHeadingRadians() - getHeadingRadians() + e.getBearingRadians())) - getGunHeadingRadians()));	
		setFire(power);
	}

	public void onHitWall(HitWallEvent e){
		back(50);
		ahead(30);
	}
}
