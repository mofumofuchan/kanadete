package kanadete;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bar {
	private Image barBase;
	private Image bar;
	private float x, y;
	private int experiments = 0;
	private final int MAX_EXPERIMENTS = 24;
	
	public Bar(float x, float y) throws SlickException {
		this.x = x; // x座標
		this.y = y; // y座標
		
		barBase = new Image("res/bar/img_bar_base.png");
		bar = new Image("res/bar/img_bar.png");
	}
	
	public void draw() {
		barBase.draw(x, y);
		bar.draw(x+2, y+2, bar.getWidth()*((float)experiments/MAX_EXPERIMENTS), 10); // 一部切り取るのがうまく動かない
	}
	
	public void incrementExperiments(int day){
		experiments += 8/Math.pow(2, day-1);
	}
	
	public boolean isFullExperiments() {
		return experiments==MAX_EXPERIMENTS; 
	}
	
	public void reset() {
		experiments = 0;
	}
}
