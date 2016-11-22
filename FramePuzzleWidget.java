package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FramePuzzleWidget extends JPanel implements MouseListener, KeyListener, ChangeListener {

	private PictureView pictureView;
	private Picture picture;
	private int width; 
	private int height;
	private PictureView[][] pictureViewArray;
	private PictureImpl redTile;
	private PictureView redTileView;
	private SubPicture tile;
	private int xCount;
	private int yCount;
	private JPanel panel;
	private JPanel buttonPanel;
	private JButton reset;
	private JFrame frame;


    
	public FramePuzzleWidget(Picture picture, int width, int height) {
		this.picture = picture;
		this.width = width;
		this.height = height;

		xCount = width - 1;
		yCount = height - 1;

		
		//sets layout for the entire picture
		panel = new JPanel(); 
		setLayout(new GridLayout(width, height, 1, 1));
	  
		

//		panel = new JPanel();
//		setLayout(new GridLayout(width, height, 1, 1));
		
//		buttonPanel = new JPanel();
//		buttonPanel.add(reset);
//		add(buttonPanel, BorderLayout.SOUTH);
//		
		
	// Experimenting to create a reset button. Not done yet
		//frame.add(buttonPanel);
//		reset = new JButton("RESET");
//		buttonPanel.add(reset);
//		reset = new JButton("RESET");
//		panel.add(reset, BorderLayout.CENTER);
//		add(reset);

		int subWidth = picture.getWidth() / width;
		int subHeight = picture.getHeight() / height;

	
		//iterates through the array to create PictureViews from subPicturres
		pictureViewArray = new PictureView[width][height];
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				// constructs red tile at the bottom right position
				if(i == width -1 && j == height -1){	
					redTile = new PictureImpl(subWidth, subHeight);
					for(int x = 0; x < subWidth; x++ ){
						for(int y = 0; y < subHeight; y++){
							redTile.setPixel(x, y, new ColorPixel(1,0,0));	
						}
					}
					redTileView= new PictureView(redTile.createObservable());
					// redTileView is the pictureView version of the redTile subPicture/pictureImpl
					redTileView.setFocusable(true);
					redTileView.addKeyListener(this);
					redTileView.addMouseListener(this);
					
					add(redTileView);
					
					pictureViewArray[i][j] = redTileView;
				   
				}else{
					//uses extract method to make a pictureView
					tile = picture.extract(i * subWidth, j * subHeight, subWidth, subHeight);
					pictureViewArray[i][j] = new PictureView(tile.createObservable());
					pictureViewArray[i][j].addKeyListener(this);
					pictureViewArray[i][j].addMouseListener(this);
					
					
					
					add(pictureViewArray[i][j]);
					
				}
			}	
		}
	}



	@Override
	public void stateChanged(ChangeEvent e) {

	}



	@Override
	public void keyPressed(KeyEvent e) {

		redTileView = pictureViewArray [xCount][yCount];
		// key pressed for the left
		if(e.getKeyCode() == 37 && xCount > 0){
			// the print is to see what is pressed and what TO press if you want to reverse the movement and return the tile
		System.out.println("left--> right");
			PictureView temp = pictureViewArray[xCount-1][yCount];
			// uses temp to switch tiles upon left movement
			Picture p = redTileView.getPicture();
			redTileView.setPicture(temp.getPicture().createObservable());
			temp.setPicture(p.createObservable());
			

			xCount = xCount-1;

			//same thing occurs for other movements
		}	
		// key pressed for the upwards motion
		if(e.getKeyCode() == 38 && (yCount > 0 &&  yCount <= height) ){
		System.out.println("Up--> down");
			PictureView temp = pictureViewArray[xCount][yCount-1];

			Picture p = redTileView.getPicture();
			redTileView.setPicture(temp.getPicture().createObservable());
			temp.setPicture(p.createObservable());
			

			yCount = yCount-1;
		}
		// key pressed for the right motion
		if(e.getKeyCode() == 39 && (xCount >= 0 &&  xCount < width - 1) ){
			System.out.println("Right--> left");
				PictureView temp = pictureViewArray[xCount + 1][yCount];

				Picture p = redTileView.getPicture();
				redTileView.setPicture(temp.getPicture().createObservable());
				temp.setPicture(p.createObservable());
				

				xCount = xCount +1;
		}
		// key pressed for the downward motion
		if(e.getKeyCode() == 40 && (yCount >= 0 &&  yCount < height -1) ){
			System.out.println("Down--> up");
				PictureView temp = pictureViewArray[xCount][yCount +1];

				Picture p = redTileView.getPicture();
				redTileView.setPicture(temp.getPicture().createObservable());
				temp.setPicture(p.createObservable());
				

				yCount = yCount +1;
		}	
	}
	


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseClicked(MouseEvent e) {
		for(int j = 0; j < height; j ++ ){
			for(int i = 0; i < width; i++){
			
			if(e.getSource() == pictureViewArray[i][j]){
				int clickXCount = i;
				int clickYCount = j;
			// Mouse Click for left side
			if(clickXCount <= xCount && clickYCount == yCount){
				int distance = xCount - clickXCount;
				for(int z = 0; z < distance; z++){
				PictureView red = pictureViewArray [xCount][yCount];
				PictureView temp = pictureViewArray [xCount - 1][yCount];
				Picture p = red.getPicture();
				red.setPicture(temp.getPicture());
				temp.setPicture(p.createObservable());
				
				xCount = xCount - 1;
				
				System.out.println("Left");
				
				}
			}
			// Mouse clicked lets it go to Up
			if(clickYCount <= yCount && clickXCount == xCount){
				int distance = yCount - clickYCount;
				for(int z = 0; z < distance; z++){
				PictureView red = pictureViewArray [xCount][yCount];
				PictureView temp = pictureViewArray [xCount][yCount -1];
				Picture p = red.getPicture();
				red.setPicture(temp.getPicture());
				temp.setPicture(p.createObservable());
				
				yCount = yCount - 1;
				
				System.out.println("Up");
				
				}
			}
			// Mouse Click takes the tile to the right
			if(clickXCount >= xCount && clickYCount == yCount){
				int distance =  clickXCount - xCount;
				for(int z = 0; z < distance; z++){
				PictureView red = pictureViewArray [xCount][yCount];
				PictureView temp = pictureViewArray [xCount + 1][yCount];
				Picture p = red.getPicture();
				red.setPicture(temp.getPicture());
				temp.setPicture(p.createObservable());
				
				xCount = xCount + 1;
				
				}
			}
			// Mouse clicked takes the tile downwards
			if(clickYCount >= yCount && clickXCount == xCount){
				int distance =  clickYCount - yCount;
				for(int z = 0; z < distance; z++){
				PictureView red = pictureViewArray [xCount][yCount];
				PictureView temp = pictureViewArray [xCount][yCount + 1];
				Picture p = red.getPicture();
				red.setPicture(temp.getPicture());
				temp.setPicture(p.createObservable());
				
				yCount = yCount + 1;
				
				System.out.println("Down");
				
						}
					}
				}
			}
		}

	}

}
