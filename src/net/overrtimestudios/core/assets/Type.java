package net.overrtimestudios.core.assets;

public enum Type {
	
	TEXTURE(0), SOUNDCLIP(1), TEXT(2);
	
	protected int id;
	
	Type(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}

}
