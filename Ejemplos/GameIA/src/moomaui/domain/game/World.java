package moomaui.domain.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class World {
	private LinkedList<Pawn> pawnsInWorld;
	private HashMap<Pawn, Pawn> deathRegister = new HashMap<>();
	private int globalIdCounter = 0;
	
	public World() {
		this.setPawnsInWorld(new LinkedList<Pawn>());
	}
	
	public Pawn spawnPawn(Class<? extends Pawn> pawnClass) {
		try {
			Pawn createdPawn = pawnClass.newInstance();
			createdPawn.setWorld(this);
			createdPawn.setId(globalIdCounter++);
			
			getPawnsInWorld().add(createdPawn);
			return createdPawn;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void addNewDeath(Pawn instigator, Pawn killed) {
		if (!deathRegister.containsKey(instigator)) // Only register the first kill
			deathRegister.put(instigator, killed);
	}
	
	public boolean kill(Pawn pawn) {
		return pawnsInWorld.remove(pawn);
	}
	
	public void tick(double seconds) {
		getPawnsInWorld().forEach((Pawn pawn) -> pawn.onTick(seconds));
		deathRegister.forEach((Pawn instigator, Pawn killed) -> killed.onDeath(instigator));
		deathRegister.clear();
	}
	
	public LinkedList<Pawn> getPawnsInWorld() {
		return pawnsInWorld;
	}
	
	public Pawn getPawnFromId(int id) {
		return pawnsInWorld.stream().filter((Pawn pawn) -> pawn.getId() == id).findFirst().orElse(null);
	}
	
	public List<Pawn> getPawnsFromClass(Class<? extends Pawn> cls) {
		return pawnsInWorld.stream().filter((Pawn pawn) -> pawn.getClass().equals(cls)).collect(Collectors.toList());
	}

	public void setPawnsInWorld(LinkedList<Pawn> pawnsInWorld) {
		this.pawnsInWorld = pawnsInWorld;
	}

	
}
