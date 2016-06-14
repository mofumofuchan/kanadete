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

public class Credit extends BasicGameState {
	private int state;
	private Image back;
	private Music bgm;
	

	public Credit(int state) {
		this.state = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		back = new Image("res/credit/img_back_credit.png");
		bgm = new Music("res/credit/music_bgm_credit.ogg");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawImage(back, 0, 0);
		
		g.setColor(Color.white);
		g.drawString("press space to return menu", 10, 620);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			bgm.stop();
            sbg.enterState(State.MENU,
                    new FadeOutTransition(Color.black, 1000),
                    new FadeInTransition(Color.black, 1000)
            		);
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

