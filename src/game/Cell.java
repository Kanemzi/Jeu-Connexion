package game;

public class Cell {
	
	private int x, y, value;
	private Player owner;
	
	public Cell(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
		this.owner = null;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getValue() {
		return value;
	}
}
