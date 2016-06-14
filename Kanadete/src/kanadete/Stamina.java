package kanadete;

import java.util.LinkedList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Stamina {
	private int stamina;
	private Image tone, toneVoid;
	private LinkedList<Long> times;
	private int x, y, interval; 
	
	private final int TIME = 60; // スタミナがひとつ回復する時間 
	
	public Stamina(int x, int y, int interval) throws SlickException {
		stamina = 3;
		tone = new Image("res/stamina/img_tone.png");
		toneVoid = new Image("res/stamina/img_tone_void.png");
		this.x = x;
		this.y = y;
		this.interval = interval;
		
		times = new LinkedList<Long>();
	}
	
	/* スタミナを使う */
	public void use() {
		if (stamina>=0) {
			stamina--;
			times.offer(System.currentTimeMillis());
		}
	}
	
	/* スタミナ回復用の処理 */
	public void update() {
		if(stamina<3 && System.currentTimeMillis()-times.peek()>TIME*1000) {
			stamina++;
			times.poll();
		}
	}
	
	public boolean isAbleToUse() {
		if (stamina > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/* スタミナ音符を描画 */
	public void draw() {
		int tmp = stamina;
		
		for (int i=0; i<3; i++) {
			if (tmp>0) {
				tone.draw(x+i*interval, y);
				tmp--;
			} else {
				toneVoid.draw(x+i*interval, y);
			}
		}
	}
}
