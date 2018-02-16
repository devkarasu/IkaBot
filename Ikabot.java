package TeamIka;
import robocode.*;
import TeamIka.Territory;
import java.awt.geom.Point2D;
import robocode.util.Utils;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Ikabot - a robot by Kazuma Wada<br>
 * 創成工学実験で制作を行ったロボットです。<br>
 * ナワバリを持ち複雑な移動を行うことを特徴とします。
 * @author 和田 一真
 */
public class Ikabot extends AdvancedRobot
{
	/**
	 * ロボットのメインメソッドです。
	 * 次のような動作を行います。
	 * <ul>
	 * <li>Territoryクラスを使って新しいナワバリを生成します。</li>
	 * <li>大砲とレーダーと車体が独立して動くよう設定します</li>
	 * <li>ロボットのカラーを設定します</li>
	 * <li>レーダーの回転が止まっていれば回し始めます。</li>
	 * <li>ナワバリの頂点を順番に取り出し移動します。</li>
	 * <li>40%の確率でゲームフィールド内にランダムに座標点を設定し移動します。</li>
	 * </ul>
	 * @see Territory
	 */
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
				out.println("(" + a.x + "," + a.y + ")");
				goTo(a);
			}
		}
	}

	/**
	 * 指定した座標点までロボットを移動させます。
	 * @param point 目標座標
	 * @see <a href="https://docs.oracle.com/javase/jp/8/docs/api/java/awt/geom/Point2D.Double.html">java.awt.geom.Point2D.Double</a>
	 */
	private void goTo(Point2D.Double point) {
		double x = point.x;
		double y = point.y;
		double a;
		setTurnRightRadians(Math.tan(a = Math.atan2(x -= (int) getX(), y -= (int) getY()) - getHeadingRadians()));
		ahead(Math.hypot(x, y) * Math.cos(a));
	}

	/**
	 * レーダーで他ロボットを発見した時にこのメソッドは呼び出されます。
	 * 次のような動作を行います。
	 * <ul>
	 * <li>発見したロボットの方向にレーダーを向かせ続けます。</li>
	 * <li>線形予測を用いた標的合わせを行い、発見したロボットを攻撃します。</li>
	 * <li>このロボットが発見したロボットの方向にまっすぐ向かおうとした時、軌道を右に逸します。</li>
	 * </ul>
	 * @param e ゲームによって設定されるイベント
	 * @see ScannedRobotEvent
	 */
	public void onScannedRobot(ScannedRobotEvent e){
		double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
		double radar = absoluteBearing - getRadarHeadingRadians();
		setTurnRadarRightRadians(Utils.normalRelativeAngle(radar));

		double power;
		if ( e.getDistance() < 200)
			power = 3;
		else if(e.getDistance() < 600)
			power = 2;
		else
			power = 1;

		setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing + Math.asin(e.getVelocity() / Rules.getBulletSpeed(power) * Math.sin(e.getHeadingRadians() - absoluteBearing)) - getGunHeadingRadians()));	
		setFire(power);

		if(e.getBearingRadians() < 1)
			setTurnRight(100);
	}

	/**
	 * このロボットが壁にぶつかった時に壁から離れます。
	 * @param e ゲームによって設定されるイベント
	 */
	public void onHitWall(HitWallEvent e){
		back(50);
		ahead(30);
	}
}
