package kanadete;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SlickException;

public class SoundObject  {
	private int length; // 音の長さ
	private int pitch; // 音の高さ
	private int interval; // 次の音との間隔
	private int intensity; // 音の強さ
	
	private float baseX, baseY; 
	private float dx = 0f, dy = 0f;
	private float axAbs = 0.002f, ayAbs = 0.002f; // 加速度
	private float vx = -0.08f, vy = 0.05f; // 速度
	private float prevObjDx = 20f, prevObjDy = -80f;
	
	Sound sound;
	Image image;
	
	static final int lengthFileNameList[] = {0,1,2,3};
	static final float pitchList[] = {1.0f, 1.122462f, 1.259921f, 
			1.334840f, 1.498307f, 1.681793f, 1.887749f, 2.0f}; // 白鍵一オクターブ分の音程
	static final float intensityVolumeList[] = {0.25f, 1.50f, 1.00f, 2.00f, 4.00f};  // 音の大きさ
	static final int intensityImageSizeList[] = {40, 45, 50, 55, 65}; // 音符の大きさ
	static final float intervalList[] = {0.8f, 1f, 1.2f, 1.4f, 1.6f}; // 音間の距離
	
	// コンストラクタ
	public SoundObject(int length, int pitch, int interval, int intensity, float prevBaseX, float prevBaseY) throws SlickException {
		this.length = length;
		this.pitch = pitch;
		this.interval = interval;
		this.intensity = intensity;
		
		this.sound = new Sound("res/sound_object/se_tone"+length+".ogg");
		this.image = new Image("res/sound_object/img_tone_"+length+"_"+pitch+".png");
		this.baseX = prevBaseX + intervalList[interval]*prevObjDx; this.baseY = prevBaseY + intervalList[interval]*prevObjDy;
	}
	
	public float getSize() {
		return (float)(intensityImageSizeList[intensity]);
	}
	
	public long getIntervalMills() {
		return (long)intervalList[interval]*1000;
	}
	
	public void play() {
		sound.play(SoundObject.pitchList[pitch], SoundObject.intensityVolumeList[intensity]);
	}
	
	/* 音のみを動かす */
	public void move() {
		float ax, ay;
		
		if (dx > 0)
			ax = (-1)*axAbs;
		else 
			ax = axAbs;
		
		if (dy > 0)
			ay = (-1)*ayAbs;
		else
			ay = ayAbs;
		
		vx += ax;
		vy += ay;
		
		dx += vx;
		dy += vy;
	}
	
	public void draw() {
	
		image.draw(baseX+dx, baseY+dy, intensityImageSizeList[intensity], intensityImageSizeList[intensity]);
	}
	
	public float getBaseX() {
		return baseX;
	}
	
	public float getBaseY() {
		return baseY;
	}
	
	public void moveBase(float dx, float dy) {
		baseX += dx;
		baseY += dy;
	}
}
