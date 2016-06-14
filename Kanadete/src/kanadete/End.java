package kanadete;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class End extends BasicGameState{
	private int state;

	public End(int state) {
		this.state = state;
	}

	public void enter(GameContainer gc, StateBasedGame sbg){
		System.exit(0);
	}
	
	@Override
	public void init(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame) throws SlickException {
		;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.cyan);

	}

	@Override
	public void update(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame, int paramInt)
			throws SlickException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public int getID() {
		return this.state;
	}

}
