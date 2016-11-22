package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	private Coordinate redTileCoordinate;
	private int xCount;
	private int yCount;
	private JPanel panel;



	public FramePuzzleWidget(Picture picture, int width, int height) {
		redTileCoordinate = new Coordinate(4,4);
		this.picture = picture;
		this.width = width;
		this.height = height;

		xCount = 4;
		yCount = 4;

		panel = new JPanel();
		setLayout(new GridLayout(width, height, 1, 1));

		int subWidth = picture.getWidth() / width;
		int subHeight = picture.getHeight() / height;

		pictureView = new PictureView(picture.createObservable());
		pictureView.addMouseListener(this);
		pictureView.addKeyListener(this);

		pictureViewArray = new PictureView[width][height];
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if(i == width -1 && j == height -1){	
					redTile = new PictureImpl(subWidth, subHeight);
					for(int x = 0; x < subWidth; x++ ){
						for(int y = 0; y < subHeight; y++){
							redTile.setPixel(x, y, new ColorPixel(1,0,0));	
						}
					}
					redTileView= new PictureView(redTile.createObservable());
					redTileView.addKeyListener(this);
					add(redTileView);
					pictureViewArray[i][j] = redTileView;
				}else{
					tile = picture.extract(i * subWidth, j * subHeight, subWidth, subHeight);
					pictureViewArray[i][j] = new PictureView(tile.createObservable());
					pictureViewArray[i][j].addKeyListener(this);
					add(pictureViewArray[i][j]);
				}
			}	
		}
	}


	//		PictureView[][] redTileArray = new PictureView[width][height];
	//		PictureImpl redTile = new PictureImpl(subWidth, subHeight);
	//		for(int i = 0; i < redTile.getWidth(); i++ ){
	//			for(int j = 0; j < redTile.getHeight(); i++){
	//				redTile.setPixel(i, j, new ColorPixel(1,1,1));
	//
	//		}
	//		}
	//	}


	@Override
	public void stateChanged(ChangeEvent e) {
		//if(JPanel)

	}



	@Override
	public void keyPressed(KeyEvent e) {

		redTileView = pictureViewArray [xCount][yCount];	
		if(e.getKeyCode() == 37 && xCount > 0){
		System.out.println("left");
			PictureView temp = pictureViewArray[xCount-1][yCount];

			Picture p = redTileView.getPicture();
			redTileView.setPicture(temp.getPicture().createObservable());
			temp.setPicture(p.createObservable());

			xCount = xCount-1;

		}	
		if(e.getKeyCode() == 38 && (yCount > 0 &&  yCount <= height) ){
		System.out.println("Up");
			PictureView temp = pictureViewArray[xCount][yCount-1];

			Picture p = redTileView.getPicture();
			redTileView.setPicture(temp.getPicture().createObservable());
			temp.setPicture(p.createObservable());

			yCount = yCount-1;
		}
		if(e.getKeyCode() == 39 && (xCount >= 0 &&  xCount < width - 1) ){
			System.out.println("Right");
				PictureView temp = pictureViewArray[xCount + 1][yCount];

				Picture p = redTileView.getPicture();
				redTileView.setPicture(temp.getPicture().createObservable());
				temp.setPicture(p.createObservable());

				xCount = xCount +1;
		}
		if(e.getKeyCode() == 40 && (yCount >= 0 &&  yCount < height -1) ){
			System.out.println("Down");
				PictureView temp = pictureViewArray[xCount][yCount +1];

				Picture p = redTileView.getPicture();
				redTileView.setPicture(temp.getPicture().createObservable());
				temp.setPicture(p.createObservable());

				yCount = yCount +1;
		}	
	}
	//     public void swap(Coordinate blank, Coordinate other){
	//    	 pictureViewArray[blank.getX()][blank.getY()] = pictureViewArray[other.getX()][other.getY()];
	//    	 pictureViewArray[other.getX()][other.getY()] = redTileView;
	//    	 this.redTileCoordinate = other;
	//     }
	//	
	//	 public void updatePanel(){
	//		panel.removeAll();
	//		 for(int i = 0; i < width; i++){
	//			 for(int j = 0; j < height; j++){
	//				 if(i == redTileCoordinate.getX() && j == redTileCoordinate.getY()){
	//					 panel.add(redTileView);
	//					 panel.add(pictureViewArray[i][j]);
	//				 }else{
	//					 panel.add(pictureViewArray[i][j]);
	//
	//				 }
	//			 }
	//		 }
	//		 this.revalidate();
	//	 }
	//     
	//     



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
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
