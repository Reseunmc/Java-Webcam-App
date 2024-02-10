package Lab1videoproc.copy;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Webcam-based drawing 
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 * 
 * @author Chris Bailey-Kellogg, Spring 2015 (based on a different webcam app from previous terms)
 */
public class CamPaint extends Webcam {
	private char displayMode = 'w';			// what to display: 'w': live webcam, 'r': recolored image, 'p': painting
	private RegionFinder finder;			// handles the finding
	private Color targetColor;          	// color of regions of interest (set by mouse press)
	private Color paintColor = Color.blue;	// the color to put into the painting from the "brush"
	private BufferedImage painting;
	private boolean mouseP= false;  // the resulting masterpiece

	/**
	 * Initializes the region finder and the drawing
	 */
	public CamPaint() {
		finder = new RegionFinder();
		clearPainting();
	}

	/**
	 * Resets the painting to a blank image
	 */
	protected void clearPainting() {
		painting = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * DrawingGUI method, here drawing one of live webcam, recolored image, or painting, 
	 * depending on display variable ('w', 'r', or 'p')
	 */
	@Override
	public void draw(Graphics g) {
		// TODO: YOUR CODE HERE
		if (displayMode== 'w'){
			g.drawImage(image,0,0,null);
		}
		//mouseclick a place on the screen then click to use it as a brush
		else if (displayMode== 'r'){
			g.drawImage(finder.getRecoloredImage(),0,0,null);
		}
		else if (displayMode=='p'){
			g.drawImage(painting, 0, 0, null);
		}
	
		
	/*	public void drawSquare(int cx, int cy, int r, Color color) {
			// Nested loop over nearby pixels
			for (int y = Math.max(0, cy-r); y < Math.min(image.getHeight(), cy+r); y++) {
				for (int x = Math.max(0, cx-r); x < Math.min(image.getWidth(), cx+r); x++) {
					image.setRGB(x, y, color.getRGB());*/
		
		
		
	}

	/**
	 * Webcam method, here finding regions and updating the painting.
	 */
	@Override
	public void processImage() {
		// TODO: YOUR CODE HERE
		//the painting not being null makes mousep true
		if (mouseP){
		finder.setImage(image);
		finder.findRegions(targetColor);
		finder.recolorImage();
		//helps make sure the camera doesnt crash when the target goes out of range
		ArrayList<Point> largestr= finder.largestRegion(); 
		
		if (largestr !=null){
		for (Point p: finder.largestRegion()){
			int x=(int) p.getX();
			int y= (int) p.getY();
			if (x>0 && x<image.getWidth() && y>0 && y<image.getHeight()){
			painting.setRGB(x,y, paintColor.getRGB());
			}
		}
		
		}
		}
		
	}

	/**
	 * Overrides the DrawingGUI method to set the track color.
	 */
	@Override
	public void handleMousePress(int x, int y) {
		// TODO: YOUR CODE HERE
		
		if (painting != null){	
		mouseP=true;
		
		targetColor = new Color(image.getRGB(x, y));
		//System.out.println(image.getRGB(x, y));
		//System.out.println(y);
		}

				
	}

	/**
	 * DrawingGUI method, here doing various drawing commands
	 */
	@Override
	public void handleKeyPress(char k) {
		if (k == 'p' || k == 'r' || k == 'w') { // display: painting, recolored image, or webcam
			displayMode = k;
		}
		else if (k == 'c') { // clear
			clearPainting();
		}

		else if (k == 'o') { // save the recolored image
			saveImage(finder.getRecoloredImage(), "pictures/recolored.png", "png");
		}
		else if (k == 's') { // save the painting
			saveImage(painting, "pictures/painting.png", "png");
		}
		else {
			System.out.println("unexpected key "+k);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CamPaint();
			}
		});
	}
}
