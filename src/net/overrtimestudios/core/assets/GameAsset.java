package net.overrtimestudios.core.assets;

public abstract class GameAsset implements Asset{
	
	//TODO implement asset types
	
	protected String path;
	protected Type type;
	
	public GameAsset(String name) {
		setPath("/" + name);
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public void obtainAsset() {
		
	}

}