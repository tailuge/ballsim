package org.java.game;


public class Piece {

	public static final Piece NONE = new Piece(".",0) {
	};
	
	private final String name;
	private final short id;
	
	public Piece(String name, int id) {
		this.name = name;
		this.id = (short)id;
	}

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}


	public short getId() {
		return id;
	}
	
}
