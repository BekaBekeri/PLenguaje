package moomaui.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import moomaui.domain.game.CharacterPlayer;
import moomaui.domain.game.Enemy;
import moomaui.presentation.WorldCanvas;

public class GameAIEnvironment implements IEnvironment {
	private HashMap<String, List<Runnable>> listeners;
	
	public GameAIEnvironment() {
		listeners = new HashMap<>();
	}
	
	public void notify(String state) {
		if (listeners.containsKey(state)) {
			listeners.get(state).forEach((Runnable action) -> action.run());
		}
	}
	
	public void addListener(String state, Runnable action) {
		if (!listeners.containsKey(state))
			listeners.put(state, new LinkedList<>());
		
		listeners.get(state).add(action);
	}
	
	public String translate(Object input) {
		return input.toString();
	}
	
	public String translate(Enemy me, CharacterPlayer ch) {
		double angle = WorldCanvas.angleBetweenPoints(ch.getX(), me.getX(), ch.getY(), me.getY()) * -1; // ??
		double distance = WorldCanvas.euclideanDistance(ch.getX(), me.getX(), ch.getY(), me.getY());
		
		if (distance >= CharacterPlayer.VISION_LENGTH)
			return "5";
		else if ((ch.getAngle() - CharacterPlayer.VISION_CONE / 2) <= angle && (ch.getAngle() + CharacterPlayer.VISION_CONE / 2) >= angle)
			return "1";
		else if (distance <= Enemy.ATTACK_RANGE)
			return "10";
		else
			return "2";
	}

}
