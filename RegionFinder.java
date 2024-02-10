package Lab1videoproc.copy;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

import javax.swing.plaf.synth.Region;

/**
 * Reseun McClendon
 * 
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 * 
 * @author Chris Bailey-Kellogg, Winter 2014 (based on a very different structure from Fall 2012)
 * @author Travis W. Peters, Dartmouth CS 10, Updated Winter 2015
 * @author CBK, Spring 2015, updated for CamPaint
 */
public class RegionFinder {
	
	private static final int maxColorDiff = 20;				// how similar a pixel color must be to the target color, to belong to a region
	private static final int minRegion = 50; 				// how many points in a region to be worth considering

	private BufferedImage image;                            // the image in which to find regions
	private BufferedImage recoloredImage;                   // the image with identified regions recolored

	private ArrayList<ArrayList<Point>> regions;			// a region is a list of points
															// so the identified regions are in a list of lists of points

	public RegionFinder() {
		this.image = null;
	}

	public RegionFinder(BufferedImage image) {
		this.image = image;		
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public BufferedImage getRecoloredImage() {
		return recoloredImage;
	}

	/**
	 * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
	 */
	public void findRegions(Color targetColor) {
		
	
		BufferedImage visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		regions= new ArrayList<ArrayList<Point>>();
		
		//nested for loop to check 8 point adjacency 
			for (int x=0; x<image.getWidth(); x++){
				//image.getWidth();
				
				for (int y=0; y<image.getHeight(); y++){
					//image.getHeight();
					
						//create the new color object that takes the parameter of whatever the rgb of the pixel we are on
						Color color= new Color(image.getRGB(x,y));
						
					
						
						if ((visited.getRGB(x, y) == 0) && colorMatch(color, targetColor)){
							
						
							//visited.setRGB(x, y, 1);
							ArrayList<Point> region = new ArrayList<Point>();
							
							Point point= new Point(x,y);
							
							
							//keeping rack of those list of pixels of the correct color
							ArrayList<Point> toVisit = new ArrayList<Point>();
							toVisit.add(point);
							
							//so long asa there is still something to visit
							while (toVisit.size()>0){
								
								
								//System.out.println(region.size());
								
								//removed the last index visited in the array
								//Point visiting = new Point(x,y);
								
								Point visiting=toVisit.remove(toVisit.size()-1);
								
						
								//if the visited pixel from blank image has not been visited
								if (visited.getRGB(visiting.x, visiting.y) == 0){
	
									region.add(visiting);
									//change the color of the blank image to blue  
									visited.setRGB(visiting.x, visiting.y, 1); //changed to visitng.y and x instead of just x and y
									
									
									 //System.out.println(region.size());
									//get the neightbors at the pixel we are visiting ny nx neighbor x 
								/*	for (int ny = Math.max(0, visiting.y - 1); ny < Math.min(image.getHeight(), visiting.y + 2); 
											ny++) {*/
								
								//checks neighbors and adds the points to to visit
									if ((visiting.y-1 >= 0) && visited.getRGB(visiting.x, visiting.y-1) == 0 && (colorMatch(new Color(image.getRGB(visiting.x, visiting.y-1)), targetColor))){
										//Point adjpoint = new Point(visiting.x,visiting.y-1); 
										toVisit.add(new Point(visiting.x, visiting.y-1));
									}
									if ((visiting.x-1 >= 0) && visited.getRGB(visiting.x-1, visiting.y) == 0 && (colorMatch(new Color(image.getRGB(visiting.x-1, visiting.y)), targetColor))){
										
										toVisit.add(new Point(visiting.x-1, visiting.y));
										
										//do for each
									}
									if ((visiting.y+1 < image.getHeight()) && visited.getRGB(visiting.x, visiting.y+1) == 0 && (colorMatch(new Color(image.getRGB(visiting.x, visiting.y+1)), targetColor))){
										
										toVisit.add(new Point(visiting.x, visiting.y+1));
									}
									if ((visiting.x+1 < image.getWidth()) && visited.getRGB(visiting.x+1, visiting.y) == 0 && (colorMatch(new Color(image.getRGB(visiting.x+1, visiting.y)), targetColor))){
										
										toVisit.add(new Point(visiting.x+1, visiting.y));
									}
									
									
					
									//if visit.x-1>0
									//if visit.y+1<image.height
									//if visit.x+1<image width && this long
							/*
										System.out.println("g3");
										for (int nx = Math.max(0, visiting.x - 1); nx < Math.min(image.getWidth(), visiting.x + 2);
												nx++) {
											System.out.println("g4");
											if (visited.getRGB(nx, ny) == 0 && (colorMatch(new Color(image.getRGB(nx,ny)), targetColor))){
												Point adjpoint = new Point(visiting.x,visiting.y); 
												System.out.println("g5");
												//if the neightbor is the correct color add it to to visit but not the region
												//toVisit.add(adjpoint);
												 * 
*/												}
						
				}
						
						 if (region.size() >= minRegion){
								
							// System.out.println(region.size());
								regions.add(region);
						}
					
							
			}
				
							
							
											
										
							
									

						 }
			}
				}
			
									
	
									
	
							
							
				
			
		
	
	

	/**
	 * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
	 */
	private static boolean colorMatch(Color c1, Color c2) {
		boolean diffB=Math.abs(c2.getBlue()-c1.getBlue()) < maxColorDiff; 
		boolean diffR=Math.abs(c2.getRed()-c1.getRed()) < maxColorDiff;
		boolean diffG=Math.abs(c2.getGreen()-c1.getGreen()) < maxColorDiff;
		//System.out.println("doing");
		
		if (c1==null || c2==null){
			return false;
		}
		else{
			return diffB == true && diffR==true && diffG==true;
		
		}
		}
	
		
		
	
	
		

	/**
	 * Returns the largest region detected (if any region has been detected)
	 */
	public ArrayList<Point> largestRegion() {
		//if the regions size is greater than 0, proceed. if not return null
		if (regions.size()>0){
		ArrayList<Point> largestRegion = regions.get(0);
		//System.out.println(regions.get(0));
		for  (ArrayList<Point> region : regions){
			if (region.size() > largestRegion.size()){
				//System.out.println("dsad");
				largestRegion = region;
				
			}
		}	
				return largestRegion;
	}
		return null;
	}
		
				
				
	
		// TODO: YOUR CODE
	

	/**
	 * Sets recoloredImage to be a copy of image, 
	 * but with each region a uniform random color, 
	 * so we can see where they are
	 */
	public void recolorImage() {
		// First copy the original
		recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
		// Now recolor the regions in it
		Color c = new Color(((int) (Math.random()*255)),((int) (Math.random()*255)),((int) (Math.random()*255)));
		//Color c = Color.BLUE;
		//System.out.println("sda");
		
		for (int x=0; x < regions.size();x++){
			//System.out.println("f");
			//System.out.println(x);
			ArrayList<Point> region= regions.get(x);//fix this
			for (int pix=0; pix< region.size(); pix++){


				
		
			recoloredImage.setRGB((int) region.get(pix).getX(), (int) region.get(pix).getY(), c.getRGB());
		
				
			/*for(int i=0; i<region.size(); i++){*/
			/*	int xs=(int) region.get(i).getX();
				int ys=(int) region.get(i).getY();*/
				
		
			}
		
			}
		}
}

			
		// TODO: YOUR CODE HERE
	

