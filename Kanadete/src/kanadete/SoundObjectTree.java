package kanadete;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SoundObjectTree extends Thread {
	private LinkedList<SoundObject> tree = new LinkedList<SoundObject>();
	private boolean isPlaying = false;
	private long startTime;
	static final float intervalList[] = {0.8f, 1f, 1.2f, 1.4f, 1.6f}; // 音間の距離
	
	private Iterator<SoundObject> iter;
	private SoundObject so;
	
	private int numKanaderu = 0;
	private int[] numKikaseru = {0,0,0,0,0};
	
	private float baseX, baseY; // 基準座標
     
	// コンストラクタ
	public SoundObjectTree(float baseX, float baseY) throws SlickException {
		this.baseX = baseX;
		this.baseY = baseY;
		
		int length = (int)(Math.random()*4);
		int pitch = (int)(Math.random()*8);
		int interval = (int)(Math.random()*5);
		int intensity = (int)(Math.random()*5);
		
		tree.add(new SoundObject(length, pitch, interval, intensity, baseX, baseY));
	}
	
	// 新しい音を生成する
	public void generate() throws SlickException {
		tree.add(decide(numKanaderu, numKikaseru)); 
		
		numKanaderu = 0;
		for (int i=0; i<numKikaseru.length; i++)
			numKikaseru[i] = 0;
	}
	
	public void moveBaseXY(float x, float y) {
		float dx = x - baseX;
		float dy = y - baseY;
		
		baseX = x;
		baseY = y;
		for (SoundObject so: tree) 
			so.moveBase(dx, dy);	
	}
	
	//次につくる音の決定 (arg0:奏でた回数, arg1[]:聞かせた回数)
	private SoundObject decide(int arg0, int arg1[]) throws SlickException{
		/* length 0,1,2,3 長さ
		 * pitch 0,1,2,3,4,5,6,7 高さ
		 * interval 0,1,2,3,4 間隔
		 * intensity 0,1,2,3,4 大きさ
		 */
		
		/* 次の音のパラメタを決定する係数 */
		float arg0_length = 0.6f;
		float arg0_pitch = 4.4f;
		float arg0_interval = 2.5f;
		float arg0_intensity = 4.9f;

		float arg1_length[]={1.0f,1.4f,2.3f,3.7f,0};
		float arg1_pitch[]={7.9f,6.8f,0,3.5f,1.8f};
		float arg1_interval[]={2.2f,4.9f,0,3.1f,1.1f};
		float arg1_intensity[]={0,1.7f,4.9f,0.7f,3.2f};

		int length = (int)((arg0_length*arg0+arg1[0]*arg1_length[0]+arg1[1]*arg1_length[1]+arg1[2]*arg1_length[2]
					+arg1[3]*arg1_length[3]+arg1[4]*arg1_length[4]) / (arg0+arg1[0]+arg1[1]+arg1[2]+arg1[3]+arg1[4]));
		if(length>=4)length=3;
		
		int pitch =(int)((arg0_pitch*arg0+arg1[0]*arg1_pitch[0]+arg1[1]*arg1_pitch[1]+arg1[2]*arg1_pitch[2]
					+arg1[3]*arg1_pitch[3]+arg1[4]*arg1_pitch[4]) / (arg0+arg1[0]+arg1[1]+arg1[2]+arg1[3]+arg1[4]));
		if(pitch>=8)pitch=7;
		
		int interval =(int)((arg0_interval*arg0+arg1[0]*arg1_interval[0]+arg1[1]*arg1_interval[1]+arg1[2]*arg1_interval[2]
					+arg1[3]*arg1_interval[3]+arg1[4]*arg1_interval[4]) / (arg0+arg1[0]+arg1[1]+arg1[2]+arg1[3]+arg1[4]));
		if(interval>=5)interval=4;
		
		int intensity =(int)((arg0_intensity*arg0+arg1[0]*arg1_intensity[0]+arg1[1]*arg1_intensity[1]+arg1[2]*arg1_intensity[2]
		 			+arg1[3]*arg1_intensity[3]+arg1[4]*arg1_intensity[4]) / (arg0+arg1[0]+arg1[1]+arg1[2]+arg1[3]+arg1[4]));
		if(intensity>=5)intensity=4;
		
		return new SoundObject(length,pitch,interval,intensity, tree.getLast().getBaseX(), tree.getLast().getBaseY());
	}
	
	// 描画
	public void draw(Graphics g) {
		for (SoundObject so: tree) {
			so.move();
			so.draw();
		}
	}
	
	// かなでるを一回行った
	public void increamentNumKanaderu() {
		++numKanaderu;
	}
	
	public int getNumKanaderu() {
		return numKanaderu;
	}
	
	// 聞かせるを一回行った
	public void increamentNumKikaseru(int n) {
		if (n>=0 && n<=4) {
			++(numKikaseru[n]);
		} else {
			System.err.println("SoundObjectTree: increamentKikaseru: bad n");
		}
	}
	
	public int getNumKikaseru(int n) {
		if (n>=0 && n<=4) {
			return numKikaseru[n];
		} else {
			System.err.println("SoundObjectTree: increamentKikaseru: bad n");
			return -1;
		}
	}
	
	// 音を追加
	public void add(SoundObject so) {
		this.add(so);
	}

	// 演奏中か
	public boolean isPlaying() {        
		return isPlaying;
	}
	
	// 演奏
	public void play() {
		isPlaying = true;
		iter = tree.iterator();
		so = iter.next();
		so.play();
		startTime = System.currentTimeMillis();
	
	}
	
	// 音の再生
	public void update() {
		if(Math.abs(System.currentTimeMillis() - startTime - so.getIntervalMills()) < 50) {
			if (iter.hasNext()) {
				so = iter.next();
				so.play();
				startTime = System.currentTimeMillis();
				
			} else {
				isPlaying = false;
			}
		} 
	}
	
	public int getNumTone() {
		return tree.size();
	}
}

