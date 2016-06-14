package kanadete;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {
	
    public static final int WIDTH  = 480;
    public static final int HEIGHT = 640;
    private static final int FPS = 60;
    private static final String GAMENAME = "かなでて";

    public Main(String title) {
        super(GAMENAME);
        // 画面を登録
        this.addState(new Menu(State.MENU));
        this.addState(new Play(State.PLAY));
        this.addState(new HowToPlay(State.HOWTOPLAY));
        this.addState(new Credit(State.CREDIT));
        this.addState(new Finish(State.FINISH));
        this.addState(new End(State.END));
    }

    // 初期設定
    @Override
    public void initStatesList(GameContainer gc) throws SlickException{
        this.getState(State.MENU).init(gc, this);
        this.getState(State.PLAY).init(gc, this);
        this.getState(State.HOWTOPLAY).init(gc, this);
        this.getState(State.CREDIT).init(gc, this);
        this.getState(State.FINISH).init(gc, this);
        this.getState(State.END).init(gc, this);    
        gc.setShowFPS(false); // FPSを表示しない
        this.enterState(State.MENU); // MENU画面をまず表示
    }
        
    //エントリーポイント
    public static void main(String[] args) throws SlickException {
        AppGameContainer app;
        try{
            app = new AppGameContainer(new Main(GAMENAME));
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setTargetFrameRate(FPS);
            app.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }

}
