package moomaui.domain.game;

import moomaui.domain.IState;
import moomaui.domain.MachineController;
import moomaui.presentation.WorldCanvas;

public class Enemy extends Pawn {
	public static final int ATTACK_RANGE = (int) (WorldCanvas.STATE_RADIUS * 2.2);
	public static final int ATTACK_POWER_PER_SECOND = 5;
	public static final int SPEED = 17;
	
	private MachineController machine;
	private double time = 0.0;

	public MachineController getMachine() {
		return machine;
	}

	public void setMachine(MachineController machine) {
		this.machine = machine;
		machine.getEnvironment().addListener("Huir", this::onFlee);
		machine.getEnvironment().addListener("Parado", this::onStop);
		machine.getEnvironment().addListener("Andar", this::onWalk);
		machine.getEnvironment().addListener("Atacar", this::onAttack);
	}
	
	@Override
	public void onTick(double seconds) {
		time = seconds;
		String translation = machine.getEnvironment().translate(this, (CharacterPlayer) world.getPawnsFromClass(CharacterPlayer.class).get(0));
		IState transitioned = machine.addNewInput(translation);
		if (transitioned != null)
			transitioned.getOutput().accept(machine.getEnvironment());
	}
	
	private void onFlee() {
		System.out.format("ID %d: Huyendo\n", this.id);
		double[] movement = moveTowards((CharacterPlayer) world.getPawnsFromClass(CharacterPlayer.class).get(0));
		
		x -= movement[0];
		y -= movement[1];
	}
	
	private void onStop() {
		System.out.format("ID %d: Parado\n", this.id);	
	}
	
	private void onWalk() {
		System.out.format("ID %d: Andando\n", this.id);
		double[] movement = moveTowards((CharacterPlayer) world.getPawnsFromClass(CharacterPlayer.class).get(0));
		
		x += movement[0];
		y += movement[1];
	}
	
	private void onAttack() {
		System.out.format("ID %d: Atacando\n", this.id);
		Pawn ch = world.getPawnsFromClass(CharacterPlayer.class).get(0);
		
		ch.applyDamage(this, (int) (ATTACK_POWER_PER_SECOND * time));
	}
	
	private double[] moveTowards(CharacterPlayer ch) {
		double angle = WorldCanvas.angleBetweenPoints(this.getX(), ch.getX(), this.getY(), ch.getY());		
		double unitsToMove = SPEED * time;

		return new double[] {Math.cos(angle) * unitsToMove, Math.sin(angle) * unitsToMove};
	}
	
	
}
