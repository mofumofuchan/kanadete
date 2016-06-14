package kanadete;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Finish extends BasicGameState {
	private int state;
	
	/* 位置 */
	private final static float sot_x = 200, sot_y = 500; // 音の木の起点
	private final static float button_kanaderu_x = 340, button_kanaderu_y = 400; // かなでるボタンの位置
	private final static float button_owaru_x = 190, button_owaru_y = 570;
	
	// 画像
	private Image back;
	private Image buttonKanaderu, buttonKanaderu_c, buttonKanaderu_invalid;
	private Image buttonOwaru, buttonOwaru_c;
	private boolean onMouseButtonKanaderu = false;
	private boolean onMouseButtonOwaru = false;
	private boolean isKanaderuPlaying = false;
	
	// 音楽
	private Music bgm;
	
	private SoundObjectTree sot;
	
	public Finish(int state) {
		this.state = state;
	}
	
	public void setSoundObjectTree(SoundObjectTree sot) {
		this.sot = sot;
		sot.moveBaseXY((float)sot_x, (float)sot_y);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		back = new Image("res/finish/img_back.png");
		bgm = new Music("res/finish/music_bgm_finish.ogg");
		buttonKanaderu = new Image("res/finish/img_button_kanaderu.png");
		buttonKanaderu_c = new Image("res/finish/img_button_kanaderu_c.png");
		buttonKanaderu_invalid = new Image("res/finish/img_button_kanaderu_invalid.png");
		buttonOwaru = new Image("res/finish/img_button_owaru.png");
		buttonOwaru_c = new Image("res/finish/img_button_owaru_c.png");
	
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		g.drawImage(back, 0, 0);
		sot.draw(g);
		
		// かなでるボタン
		if (isKanaderuPlaying) {
			buttonKanaderu_invalid.draw(button_kanaderu_x, button_kanaderu_y, 120, 45);
		} else if (onMouseButtonKanaderu) {
			buttonKanaderu_c.draw(button_kanaderu_x, button_kanaderu_y, 120, 45);
		} else {
			buttonKanaderu.draw(button_kanaderu_x, button_kanaderu_y, 120, 45);
		}
		
		// 終わるボタン
		if (onMouseButtonOwaru) {
			buttonOwaru_c.draw(button_owaru_x, button_owaru_y, 100, 50);
		} else {
			buttonOwaru.draw(button_owaru_x, button_owaru_y, 100, 50);
		}
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {	
	  	float mouseX = gc.getInput().getMouseX();
    	float mouseY = gc.getInput().getMouseY();
		// 終わるボタン
    	if (mouseX > button_owaru_x && mouseX < button_owaru_x + 120 && mouseY > button_owaru_y && mouseY < button_owaru_y+45) {
    		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) { // 始めるがクリックされたら
    			bgm.stop();
                sbg.enterState(State.END,
                        new FadeOutTransition(Color.black, 3000),
                        new FadeInTransition(Color.black, 1)
                		);
    		}
    		if (onMouseButtonOwaru == false) { // 始めるにマウスが乗ったら
    			onMouseButtonOwaru = true;
    		}
    	} else {
    		onMouseButtonOwaru = false;
    	}
        
		// かなでるクリック
		if (mouseX > button_kanaderu_x && mouseX < button_kanaderu_x+100 && mouseY > button_kanaderu_y && mouseY < button_kanaderu_y+50) {
			if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) && !isKanaderuPlaying) { // かなでるがクリックされたら
				sot.play();
				isKanaderuPlaying = true;
			
			} else {
				onMouseButtonKanaderu = true;
			}
		} else {
			onMouseButtonKanaderu = false;
		}
		// 音を鳴らす
		if (sot.isPlaying()) { 
			sot.update();
		}
		// かなでる音を流しおわった後の処理
		if (isKanaderuPlaying && !(sot.isPlaying())) {
			isKanaderuPlaying = false;
		}
		
	}

	@Override
	public int getID() {
		return this.state;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		bgm.loop();
	}
}
