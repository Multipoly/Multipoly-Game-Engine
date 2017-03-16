package net.overrtimestudios.core.graphics;

import java.util.ArrayList;

public class TextureRequest {
	
	private static ArrayList<Texture> textures = new ArrayList<Texture>();
	
	public static void registerTexture(Texture texture) {
		textures.add(texture);
	}
	
	public static Texture requestTexture(int id) {
		for(int i= 0; i < textures.size(); i++) {
			if(id == textures.get(i).id) {
				return textures.get(i);
			}
		}
		return null;
	}
	
	public static void disposeTextures() {
		textures.clear();
	}

}