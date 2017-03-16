package net.overrtimestudios.core.assets;

public interface Asset {
	
	void setPath(String path);
	void setType(Type type);
	void obtainAsset();
	void dispose();

}