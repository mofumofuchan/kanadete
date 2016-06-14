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

public class Play extends BasicGameState {
	private int state;

	private long startTime; // プレイ開始時間

	private int mouseX, mouseY; // マウス位置
	private boolean isMusicSelect = false; // 音楽選択画面かどうか

	// かなでるがプレイ中か
	private boolean isKanaderuPlaying = false; // 今

	// 聞かせるがプレイ中か
	private boolean isKikaseruPlaying = false;
	private long kikaseruEndTime = 0; //

	/*** リソース ***/
	/* 音楽 */
	private Music bgm;
	private Sound seButtonOn; // press button
	private Sound sePlayMusic; // start playing menu
	private Sound seEvolute; // evolute SoundObjectTree
	private Sound seGetMusic;

	private Music[] melodies = new Music[5]; // 聞かせるメロディ
	private SoundObjectTree sot;	
	private long musicSecond[] = {13500, 13500, 18500, 10000, 10000}; // 音楽の時間(ミリ秒) 
	
	/* 位置のパラメータ */
	private final static int button_kanaderu_x = 300, button_kanaderu_y = 450; // かなでるボタンの位置
	private final static int button_kikaseru_x = button_kanaderu_x, button_kikaseru_y = 550; // きかせるボタンの位置
	private final static int button_return_x=10, button_return_y = button_kikaseru_y+10; // 戻るボタンの位置
	private final static int music_select_x = button_return_x, music_select_y = button_kikaseru_y - 120; // きかせるウィンドウの位置
	private final static int button_melody_x = 50, button_melody_y = music_select_y + 40, button_melody_x_interval = 75, button_melody_size = 50;
	private final static int bar_x = 300, bar_y = 90;
	private final static int stamina_x = 300, stamina_y = 50, stamina_interval = 50;	
	private final static float sot_x = 160f, sot_y = 500f; // 音の木の起点
	private final static int age_x = 50, age_y=50; // 文字表示位置
	private final static int play_time_x = age_x, play_time_y = 75; // プレイ時間
	
	/* 画像 */
	private Image background;

	/* ボタン */
	// かなでる
	private Image buttonKanaderu, buttonKanaderu_c, buttonKanaderu_invalid; // かなでるボタン
	private boolean onMouseButtonKanaderu;
	// 聞かせる
	private Image buttonKikaseru, buttonKikaseru_c, buttonKikaseru_invalid; // きかせるボタン
	private Image buttonTojiru, buttonTojiru_c;
	private boolean onMouseButtonKikaseru;
	// 戻る
	private Image buttonReturn, buttonReturn_c, buttonReturn_invalid;
	private boolean onMouseButtonReturn;

	// 音楽選択ウィンドウ
	private Image musicSelectBack; // ウィンドウ背景
	private Image[] buttonMelody = new Image[5];
	private boolean[] onMouseButtonMelody = new boolean[5];
	
	private Bar bar; // ゲージ

	private Stamina stamina; // スタミナ
	
	/* コンストラクタ */
	public Play(int state){
		this.state = state;
	}

	/* 初期設定 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		// リソースの読み込み
		// 音楽
		bgm = new Music("res/play/music_bgm_play.ogg");
		seButtonOn = new Sound("res/play/se_return_menu.wav");
		sePlayMusic = new Sound("res/play/se_select_music.wav");
		seGetMusic = new Sound("res/play/se_get_music.wav");
		seEvolute = new Sound("res/play/se_evolute.wav");

		// 聞かせるメロディ
		melodies[0] = new Music("res/play/music_music1.ogg");
		melodies[1] = new Music("res/play/music_music2.ogg");
		melodies[2] = new Music("res/play/music_music3.ogg");
		melodies[3] = new Music("res/play/music_music4.ogg");
		melodies[4] = new Music("res/play/music_music5.ogg");

		// 画像
		background = new Image("res/play/img_back.jpg");

		buttonKanaderu = new Image("res/play/img_button_kanaderu.png");
		buttonKanaderu_c = new Image("res/play/img_button_kanaderu_c.png");
		buttonKanaderu_invalid = new Image("res/play/img_button_kanaderu_invalid.png");

		buttonKikaseru = new Image("res/play/img_button_kikaseru.png");
		buttonKikaseru_c = new Image("res/play/img_button_kikaseru_c.png");
		buttonKikaseru_invalid = new Image("res/play/img_button_kikaseru_invalid.png");
		buttonTojiru = new Image("res/play/img_button_tojiru.png");
		buttonTojiru_c = new Image("res/play/img_button_tojiru_c.png");

		buttonReturn = new Image("res/play/img_button_return.png");
		buttonReturn_c = new Image("res/play/img_button_return_c.png");
		buttonReturn_invalid = new Image("res/play/img_button_return_invalid.png");

		buttonMelody[0] = new Image("res/play/img_button_melody1.png"); 
		buttonMelody[1] = new Image("res/play/img_button_melody2.png"); 
		buttonMelody[2] = new Image("res/play/img_button_melody3.png"); 
		buttonMelody[3] = new Image("res/play/img_button_melody4.png"); 
		buttonMelody[4] = new Image("res/play/img_button_melody5.png"); 

		musicSelectBack = new Image("res/play/img_music_select.png");

		// サウンドオブジェクトツリーを生成
		sot = new SoundObjectTree(sot_x, sot_y);

		// ゲージを作成
		bar = new Bar(bar_x, bar_y);
		// スタミナ音符を作成
		stamina = new Stamina(stamina_x, stamina_y, stamina_interval);
	}

	/* 画面遷移時 */
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		bgm.loop(); // BGM再生
		if (startTime == 0)
			startTime = System.currentTimeMillis();
	}

	/* 画面描画 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.drawImage(background,0,0);

		bar.draw(); // ゲージ
		stamina.draw(); // スタミナ
		sot.draw(g); // 音の木を描画

		/* ボタンを描画 */ 
		if (isKanaderuPlaying || isKikaseruPlaying) { // 音楽が再生中のとき，ボタンは無効に
			g.drawImage(buttonKanaderu_invalid, button_kanaderu_x, button_kanaderu_y);
			g.drawImage(buttonKikaseru_invalid, button_kikaseru_x, button_kikaseru_y);
			g.drawImage(buttonReturn_invalid, button_return_x, button_return_y);
		} else if (isMusicSelect){ // 音楽選択中のとき
			g.drawImage(musicSelectBack, music_select_x, music_select_y);
			buttonMelody[0].draw(button_melody_x, button_melody_y, button_melody_size, button_melody_size);
			buttonMelody[1].draw(button_melody_x+button_melody_x_interval, button_melody_y, button_melody_size, button_melody_size);
			buttonMelody[2].draw(button_melody_x+button_melody_x_interval*2, button_melody_y, button_melody_size, button_melody_size);
			buttonMelody[3].draw(button_melody_x+button_melody_x_interval*3, button_melody_y, button_melody_size, button_melody_size);
			buttonMelody[4].draw(button_melody_x+button_melody_x_interval*4, button_melody_y, button_melody_size, button_melody_size);        	
			if (onMouseButtonKikaseru) {
				buttonTojiru_c.draw(button_kikaseru_x, button_kikaseru_y, buttonKikaseru.getWidth(), buttonKikaseru.getHeight());
			} else {
				buttonTojiru.draw(button_kikaseru_x, button_kikaseru_y, buttonKikaseru.getWidth(), buttonKikaseru.getHeight());
			}
		} else  {
			if (!stamina.isAbleToUse()) {
				g.drawImage(buttonKanaderu_invalid, button_kanaderu_x, button_kanaderu_y);
				g.drawImage(buttonKikaseru_invalid, button_kikaseru_x, button_kikaseru_y);

			} else {	
				// かなでるボタン
				if (onMouseButtonKanaderu) {
					g.drawImage(buttonKanaderu_c, button_kanaderu_x, button_kanaderu_y);
				} else {
					g.drawImage(buttonKanaderu, button_kanaderu_x, button_kanaderu_y);
				}
				// 聞かせるボタン
				if (onMouseButtonKikaseru) {
					g.drawImage(buttonKikaseru_c, button_kikaseru_x, button_kikaseru_y);
				} else {
					g.drawImage(buttonKikaseru, button_kikaseru_x, button_kikaseru_y);
				}
			}
			// もどるボタン
			if (onMouseButtonReturn) {
				g.drawImage(buttonReturn_c, button_return_x, button_return_y);
			} else {
				g.drawImage(buttonReturn, button_return_x, button_return_y);
			}	
			
		}

		g.setColor(Color.white);
		g.drawString("age: " + sot.getNumTone(), age_x, age_y);
		int time = (int)(System.currentTimeMillis()-startTime)/1000;
		g.drawString("play time: "+String.format("%02d:%02d:%02d", time/3600, time/60, time%60), play_time_x,play_time_y);


	}

	/* 内部状態更新 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		stamina.update(); // スタミナ回復

		// 進化させる
		if (bar.isFullExperiments()) {
			bar.reset();
			seEvolute.play();
			this.generateTone();

			if (sot.getNumTone() == 5) { // 音の実が5つになったら育成終了
				((Finish)sbg.getState(State.FINISH)).setSoundObjectTree(sot);
				sbg.enterState(State.FINISH, // 画面反転
						new FadeOutTransition(Color.black, 50),
						new FadeInTransition(Color.black, 3000)
						);
			} else { // それ以外のときは進化
				sbg.enterState(State.PLAY, // 画面反転
						new FadeOutTransition(Color.white, 50),
						new FadeInTransition(Color.white, 3000)
						);
			}
		}

		// かなでる音を流しおわった後の処理
		if (isKanaderuPlaying && !(sot.isPlaying())) {
			bar.incrementExperiments(sot.getNumTone()); // バーを増やす
			isKanaderuPlaying = false;
			if (!bar.isFullExperiments()) { 
				seGetMusic.play();
				sbg.enterState(State.PLAY, // 画面反転
						new FadeOutTransition(Color.white, 50),
						new FadeInTransition(Color.white, 500)
						);
			}
		}

		// きかせる音を流し終わった後の処理
		if (isKikaseruPlaying && Math.abs(System.currentTimeMillis() - kikaseruEndTime) < 50) {
			bar.incrementExperiments(sot.getNumTone());
			isKikaseruPlaying = false;
			if (!bar.isFullExperiments()) {
				seGetMusic.play();
				sbg.enterState(State.PLAY, // 画面反転
						new FadeOutTransition(Color.white, 50),
						new FadeInTransition(Color.white, 500)
						);
			}
		}

		// 音を鳴らす
		if (sot.isPlaying()) { 
			sot.update();
		}

		

		
		
		/* マウス操作を受けた時の処理 */
		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();
		if (!isKanaderuPlaying && !isKikaseruPlaying) {			
			// きかせるウィンドウが開いているとき
			if (isMusicSelect) {

				// メロディボタン判定
				for (int i=0; i<5; i++) {
					if (mouseX > button_melody_x+button_melody_x_interval*i && mouseX < button_melody_x+button_melody_x_interval*i+button_melody_size && mouseY > button_melody_y && mouseY < button_melody_y+button_melody_size) {
						if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) { // かなでるがクリックされたら
							bgm.pause();
							sePlayMusic.play();
							sot.increamentNumKikaseru(i);
							melodies[i].play();
							isMusicSelect = false;
							isKikaseruPlaying = true;
							stamina.use();
							kikaseruEndTime = System.currentTimeMillis() + musicSecond[i];
						} else {
							onMouseButtonMelody[i] = true;
						}
					} else {
						onMouseButtonMelody[i] = false;
					}
				}

				// ウィンドウを開いてるときとじる（きかせる）クリック
				if (mouseX > button_kikaseru_x && mouseX < button_kikaseru_x+buttonTojiru.getWidth() && mouseY > button_kikaseru_y && mouseY < button_kikaseru_y+buttonTojiru.getHeight()) {
					if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) { // かなでるがクリックされたら	
						isMusicSelect = false;
						seButtonOn.play();
					} else {
						onMouseButtonKikaseru = true;
					}
				} else {
					onMouseButtonKikaseru = false;
				}
			} else { // ウィンドウが開いてないとき
				// かなでるクリック
				if (mouseX > button_kanaderu_x && mouseX < button_kanaderu_x+buttonKanaderu.getWidth() && mouseY > button_kanaderu_y && mouseY < button_kanaderu_y+buttonKanaderu.getHeight()) {
					if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) && stamina.isAbleToUse()) { // かなでるがクリックされたら
						if (sot.isPlaying() == false) {
							sot.increamentNumKanaderu();
							bgm.pause();
							sot.play();
							stamina.use();
							isKanaderuPlaying = true;
						}
					} else {
						onMouseButtonKanaderu = true;
					}
				} else {
					onMouseButtonKanaderu = false;
				}
				
				// きかせるクリック
				if (mouseX > button_kikaseru_x && mouseX < button_kikaseru_x+buttonKikaseru.getWidth() && mouseY > button_kikaseru_y && mouseY < button_kikaseru_y+buttonKikaseru.getHeight()) {
					if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) && !isMusicSelect && stamina.isAbleToUse()) { // かなでるがクリックされたら	
						isMusicSelect = true;
						seButtonOn.play();
					} else {
						onMouseButtonKikaseru = true;
					}
				} else {
					onMouseButtonKikaseru = false;
				}

				// メニューへ戻る
				if (mouseX > button_return_x && mouseX < button_return_x+buttonReturn.getWidth() && mouseY > button_return_y && mouseY < button_return_y+buttonReturn.getHeight()) {
					if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) { 
						bgm.pause();
						seButtonOn.play();
						sbg.enterState(State.MENU,
								new FadeOutTransition(Color.black, 1000),
								new FadeInTransition(Color.black, 1000)
								);
					} else {
						onMouseButtonReturn = true;
					}
				} else {
					onMouseButtonReturn = false;
				}
			}
		}
	}

	@Override
	public int getID(){
		return this.state;
	}

	// 音の実を新しく生み出す
	private void generateTone() throws SlickException {
		sot.generate();
	}

}
