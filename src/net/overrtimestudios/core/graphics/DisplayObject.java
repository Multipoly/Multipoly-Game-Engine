package net.overrtimestudios.core.graphics;

import java.util.ArrayList;

public class DisplayObject {
	
	/*
	 * TODO implement a font system!
	 * TODO implement the ability to acquire red, green, and blue values from a pixel
	 * TODO implement circle rendering
	 * TODO shift integers to floats!
	 * TODO optimize rendering
	 * TODO implement watermark for unregistered versions of the engine!
	 * 
	 * DISPLAY OBJECT CLASS CREATED BY:
	 * 		CARTER ROGERS 2017
	 */
	
	public int[] pixels, zb, lm, lb;
	protected int width, height;

	private ArrayList<ImageRequest> imageRequest = new ArrayList<ImageRequest>();
	private ArrayList<LightRequest> lightRequest = new ArrayList<LightRequest>();
	private boolean processing = false;
	private boolean useLights = false; //enable or disable lighting	
	
	private int zDepth = 0;
	
	private int ambientColor = 0xff232323;

	public DisplayObject(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		this.zb = new int[pixels.length];
		this.lm = new int[pixels.length];
		this.lb = new int[pixels.length];
	}
	
	public void drawObject(DisplayObject object, int xOff, int yOff) {
		for(int y = 0; y < object.height; y++) {
			int yPix = y + yOff;
			
			if(yPix < 0 || yPix > height)
				return;
			
			for(int x = 0; x < object.width; x++) {
				int xPix = x + xOff;
				
				if(xPix < 0 || xPix > width)
					return;
				
				int alpha = object.pixels[x + y * object.width];
				
				if(alpha > 1)
					pixels[xPix + yPix * width] = alpha;
					
				
				
				
			}
		}

	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	//TODO organize manipulation code!
	
	public void setPixel(int x, int y, int value) {
		int alpha = ((value >> 24) & 0xff);
		
		if(x < 0 || x > width || y < 0 || y > height || alpha == 0) {
			
		}else {			
			try {
				if(zb[x + y * width] > zDepth)
					return;
			

				if(alpha == 255) { //transparency
					pixels[x + y * width] = value;
					
				}else {
					int pixelvalue = pixels[x + y * width];
					
					int newRed = ((pixelvalue >> 16) & 0xff) - (int) ((((pixelvalue >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
					int newGreen = ((pixelvalue >> 8) & 0xff) - (int) ((((pixelvalue >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
					int newBlue = (pixelvalue & 0xff) - (int)(((pixelvalue & 0xff) - (value & 0xff)) * (alpha / 255f));
					
					pixels[x + y * width] = (newRed << 16 | newGreen << 8 | newBlue);
				}
			}catch(Exception e) {
				return;
			}
			
						
		}
		
	}
	
	public void setLightMap(int x, int y, int value) {
		if(x < 0 || x >= width || y < 0 || y >= height)
			return;
		
		int baseColor = lm[x + y * width];
		
		int maxRed = Math.max((baseColor >> 16) & 0xff, (value >> 16) & 0xff);
		int maxGreen = Math.max((baseColor >> 8) & 0xff, (value >> 8) & 0xff);
		int maxBlue = Math.max(baseColor & 0xff, value & 0xff);

		
		lm[x + y * width] = (maxRed << 16 | maxGreen << 8 | maxBlue);
	}
	
	public void setLightBlock(int x, int y, int value) {
		if(x < 0 || x >= width || y < 0 || y >= height)
			return;
		

		if(zb[x + y * width] > zDepth)
			return;
		

		lb[x + y * width] = value;
	}
	
	public void fillRect(int x, int y, int width, int height, int value) {
		for(int yy = 0; yy < height; yy++) {
			int yPix = yy + y;
			
			for(int xx = 0; xx < width; xx++) {
				int xPix = xx + x;
				
				setPixel(xPix, yPix, value);
			}
		}
	}
	
	public void drawRect(int x, int y, int width, int height, int value) {
		for(int yy = 0; yy <= height; yy++) {
			int yPix = yy + y;
			
			setPixel(x + width, yPix, value);
			setPixel(x, yPix, value);
		}
		
		for(int xx = 0; xx <= width; xx++) {
			int xPix = xx + x;
			
			setPixel(xPix, y + height, value);
			setPixel(xPix, y, value);
		}
	}
	
	//TODO implement texture requests(performance stuff)
	
	public void drawTexture(Texture t, int x, int y) {
		if(t != null) {			
			
			if(t.isAlpha && !processing) {
				imageRequest.add(new ImageRequest(t, zDepth, x, y));
				return;
			}
			
			for(int yy = 0; yy < t.getHeight(); yy++) {
				int yPix = yy + y;
				for(int xx = 0; xx < t.getWidth(); xx++) {
					int xPix = xx + x;
					
					setPixel(xPix, yPix, t.pixels[xx + yy * t.getWidth()]);
					setLightBlock(xPix, yPix, t.getLightBlock());
				}
			}
		}
	}
	
	public void drawLight(Light light, int x, int y) {
		lightRequest.add(new LightRequest(light, x, y));
	}
	
	
	private void drawLightRequest(Light l, int x, int y) {
		for(int i = 0; i < l.getDiameter(); i++) {
			drawLightLine(l, l.getRadius(), l.getRadius(), i, 0, x, y);
			drawLightLine(l, l.getRadius(), l.getRadius(), i, l.getDiameter(), x, y);
			drawLightLine(l, l.getRadius(), l.getRadius(), 0, i, x, y);
			drawLightLine(l, l.getRadius(), l.getRadius(), l.getDiameter(), i, x, y);
		}
	}
	
	private void drawLightLine(Light l, int x0, int y0, int x1, int y1, int x, int y) {
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1; //first quadrant, second, third, fourth?
		
		int err = dx - dy;
		int e2;
		
		while(true) {
			
			int screenX = x0 - l.getRadius() + x;
			int screenY = y0 - l.getRadius() + y;
			
			if(screenX < 0 || screenX >= width || screenY < 0 || screenY >= height)
				return;
			
			int lightColour = l.getLightValue(x0, y0);
			
			if(lightColour == 0)
				return;
			
			if(lb[screenX + screenY * width] == Light.TOTAL)
				return;
			
			setLightMap(screenX, screenY, lightColour);
			
			if(x0 == x1 && y0 == y1) 			//if hits location(part of shadows)
				break;
			
			e2 = 2 * err;
			
			if(e2 > -1 * dy) {
				err -= dy;
				x0 += sx; //move on the x-axis;
			}
			
			if(e2 < dx) {
				err += dx;
				y0 += sy; //move on the y-axis
			}
		}
		
	}
	
	public void combineMaps() {
		for(int i = 0; i < pixels.length; i++) { //light map combine
			float red = ((lm[i] >> 16) & 0xff) / 255f;
			float green = ((lm[i] >> 8) & 0xff) / 255f;
			float blue = (lm[i]& 0xff) / 255f;
			
			pixels[i] = ((int)(((pixels[i] >> 16) & 0xff) * red)) << 16| ((int)(((pixels[i] >> 8) & 0xff) * green)) << 8 | ((int)((pixels[i] & 0xff) * blue));
		}
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
			zb[i] = 0;
			lm[i] = ambientColor;
			lb[i] = 0;
		}
		
	}
	

	public void process() {
		processing = true;
		for(int i = 0; i < imageRequest.size(); i++) {
			ImageRequest t = imageRequest.get(i);
			setzDepth(t.zDepth);
			t.t.setAlpha(false);
			drawTexture(t.t, t.x, t.y);
		}
		
		if(useLights) {			
			for(int i = 0; i < lightRequest.size(); i++) {
				LightRequest l = lightRequest.get(i);
				drawLightRequest(l.light, l.locX, l.locY);
			}
			
			combineMaps(); // draw lighting
			
		}
		imageRequest.clear();
		lightRequest.clear();
		processing = false;
	}

	public int getzDepth() {
		return zDepth;
	}
	
	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}

	public boolean isLightingEnabled() {
		return useLights;
	}
	
	public void enableLighting(boolean useLights) {
		this.useLights = useLights;
	}

	public int getAmbientColor() {
		return ambientColor;
	}
	
	public void setAmbientColor(int ambientColor) {
		this.ambientColor = ambientColor;
	}
	
	// TODO Finish Wrappers!
	


	public void setPixel(int x, int y){setPixel(x,y,0xffccffcc);}
	public void fillRect(int x, int y, int width, int height) {fillRect(x,y,width,height,0xffccffcc);}
	public void drawRect(int x, int y, int width, int height) {drawRect(x,y,width,height,0xffccffcc);}

	
}
