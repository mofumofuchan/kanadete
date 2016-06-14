package kanadete;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Menu extends BasicGameState {
    private int state;
    
    /* 位置 */
    private final int button1_x = 10, button1_y = 440; // はじめるボタンの位置
    private int button2_x = button1_x, button2_y =0; // 終わるボタンの位置
    private int button3_x = 0, button3_y = button1_y; // 遊び方ボタンの位置
    private int button4_x = button3_x, button4_y = button2_y; // クレジットボタンの位置
       
    /*** リソース ***/
    /* 音楽 */
    private Music menuMusic; // BGM
    private Sound se; // 選択
    private Sound se2; // 決定
    private Sound se3; // 終了の決定

    /* 画像 */
    private Image background; // 背景
    private Image title_word; // タイトル文字
    
    private Image button_start, button_start_c; // 始めるボタン
    private boolean mouseOn1; // ボタンの上にカーソルがあるか
    
    private Image button_end, button_end_c; // 終わるボタン
    private boolean mouseOn2; 
    
    private Image button_howtoplay, button_howtoplay_c; // 遊び方ボタン
    private boolean mouseOn3; 
    
    private Image button_credit, button_credit_c; // クレジットボタン
    private boolean mouseOn4; 
    
    private int mouseX, mouseY; // カーソルの位置

    public Menu(int state){
        this.state = state;
    }

    /* 初期設定 */
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        menuMusic = new Music("res/menu/music_bgm_menu.ogg");
        se = new Sound("res/menu/se_choice.wav");
        se2 = new Sound("res/menu/se_decide.wav");
        se3 = new Sound("res/menu/se_quit.wav");

        background = new Image("res/menu/img_back_menu.jpg");
        title_word = new Image("res/menu/img_title_word.png");
        
        button_start = new Image("res/menu/img_button_hajimeru.png");
        button_start_c = new Image("res/menu/img_button_hajimeru_c.png");
        button_end = new Image("res/menu/img_button_owaru.png");
        button_end_c = new Image("res/menu/img_button_owaru_c.png");
        button_howtoplay = new Image("res/menu/img_button_asobikata.png");
        button_howtoplay_c = new Image("res/menu/img_button_asobikata_c.png");
        button_credit = new Image("res/menu/img_button_kurejitto.png");
        button_credit_c = new Image("res/menu/img_button_kurejitto_c.png");

        button4_y = button2_y = 630-button_start.getHeight();
        button4_x = button3_x = 470-button_start.getWidth();
    }

    /* 画面遷移時の処理 */
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) {
    	menuMusic.loop();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        g.drawImage(background, 0, 0); // 背景を描画
        g.drawImage(title_word, 30, -30); // タイトルを描画
        
        // はじめるボタン．マウスが置かれていたら画像を変化させる
        if (mouseOn1) {
        	g.drawImage(button_start_c, button1_x, button1_y);
        } else {
        	g.drawImage(button_start, button1_x, button1_y);
        }
        // おわるボタン
        if (mouseOn2) {
        	g.drawImage(button_end_c, button2_x, button2_y);
        } else {
        	g.drawImage(button_end, button2_x, button2_y);
        }
        // あそびかたボタン
        if (mouseOn3) {
        	g.drawImage(button_howtoplay_c, button3_x, button3_y);
        } else {
        	g.drawImage(button_howtoplay, button3_x, button3_y);
        }
        // クレジット
        if (mouseOn4) {
        	g.drawImage(button_credit_c, button4_x, button4_y);
        } else {
        	g.drawImage(button_credit, button4_x, button4_y);
        }

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
    	mouseX = gc.getInput().getMouseX();
    	mouseY = gc.getInput().getMouseY();

    	// はじめるボタンの処理
    	if (mouseX > button1_x && mouseX < button1_x+button_start.getWidth() && mouseY > button1_y && mouseY < button1_y+button_start.getHeight()) {
    		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) { // 始めるがクリックされたら
    			menuMusic.stop();
    			se2.play();
                sbg.enterState(State.PLAY,
                        new FadeOutTransition(Color.black, 1000),
                        new FadeInTransition(Color.black, 1000)
                		);
    		}
    		if (mouseOn1 == false) { // 始めるにマウスが乗ったら
    			mouseOn1 = true;
    			se.play();
    		}
    	} else {
    		mouseOn1 = false;
    	}

    	// 終了ボタン
    	if (mouseX > button2_x && mouseX < button2_x+button_start.getWidth() && mouseY > button2_y && mouseY < button2_y+button_start.getHeight()) {
    		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
    			menuMusic.stop();
    			se3.play(1.0f, 0.4f);
    			sbg.enterState(State.END,
                        new FadeOutTransition(Color.black, 3000),
                        new FadeInTransition(Color.black, 1)
                		);
    		}
    		if (mouseOn2 == false) {
    			mouseOn2 = true;
    			se.play();
    		}
    	} else {
    		mouseOn2 = false;
    	}

    	// あそびかたボタン
    	if (mouseX > button3_x && mouseX < button3_x+button_start.getWidth() && mouseY > button3_y && mouseY < button3_y+button_start.getHeight()) {
    		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) { // 始めるがクリックされたら
    			menuMusic.stop();
    			se2.play();
                sbg.enterState(State.HOWTOPLAY,
                        new FadeOutTransition(Color.black, 1000),
                        new FadeInTransition(Color.black, 1000)
                		);
    		}
    		if (mouseOn3 == false) { // 始めるにマウスが乗ったら
    			mouseOn3 = true;
    			se.play();
    		}
    	} else {
    		mouseOn3 = false;
    	}

    	// クレジットボタン
    	if (mouseX > button4_x && mouseX < button4_x+button_start.getWidth() && mouseY > button4_y && mouseY < button4_y+button_start.getHeight()) {
    		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) { // 始めるがクリックされたら
    			menuMusic.stop();
    			se2.play();
                sbg.enterState(State.CREDIT,
                        new FadeOutTransition(Color.black, 1000),
                        new FadeInTransition(Color.black, 1000)
                		);
    		}
    		if (mouseOn4 == false) { // 始めるにマウスが乗ったら
    			mouseOn4 = true;
    			se.play();
    		}
    	} else {
    		mouseOn4 = false;
    	}
    }

    @Override
    public int getID(){
       return this.state;
    }
}
