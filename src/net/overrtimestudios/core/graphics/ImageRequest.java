package net.overrtimestudios.core.graphics;

public class ImageRequest {

	public Texture t;
	public int zDepth, x, y;
	
	public ImageRequest(Texture t, int zDepth, int x, int y) {
		this.t = t;
		this.zDepth = zDepth;
		this.x = x;
		this.y = y;
	}
	
}
