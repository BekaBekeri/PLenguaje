package moomaui.domain.game;

import java.util.LinkedList;
import java.util.function.BiConsumer;

public class Pawn {
	protected int id;
	protected World world;
	
	protected int x;
	protected int y;
	protected int health;
	
	protected LinkedList<BiConsumer<Pawn, Pawn>> deathListeners = new LinkedList<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void onTick(double seconds) {
		
	}
	
	public void addDeathListener(BiConsumer<Pawn, Pawn> listener) {
		deathListeners.add(listener);
	}
	
	public void applyDamage(Pawn instigator, int damage) {
		health -= damage;
		/* Do not call onDeath here, if the Pawn is killed
		 * and thus removed from the world pawn list we
		 * will run in a ConcurrentModificationException.
		 * Perform this task when world has finished ticking.
		 */
		if (health <= 0)
			world.addNewDeath(instigator, this);
	}
	
	public void onDeath(Pawn instigator) {
		deathListeners.forEach((BiConsumer<Pawn, Pawn> consumer) -> consumer.accept(this, instigator));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pawn && ((Pawn) obj).id == this.id)
			return true;
		return false;
	}
}
