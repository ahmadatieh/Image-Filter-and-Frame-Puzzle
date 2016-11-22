package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PixelInspectorWidget extends JPanel implements MouseListener {



	private PictureView pictureView;
	
	private Picture picture;
	
	private JLabel xLabel;
	private JLabel yLabel;
	private JLabel redLabel;
	private JLabel greenLabel;
	private JLabel blueLabel;
	private JLabel brightnessLabel;
	
	

	
	// Constructor takes in a picture and then constructs a x, y, red, green, blue, and brightness label
	public PixelInspectorWidget(Picture picture) {
	
	this.picture = picture;

	
	setLayout(new BorderLayout());
	
	JPanel leftPanel = new JPanel();	
    leftPanel.setLayout(new GridLayout(6,1));
   	
    
    
   	pictureView = new PictureView(picture.createObservable());
	pictureView.addMouseListener(this);
	add(pictureView, BorderLayout.CENTER);
	add(leftPanel, BorderLayout.WEST);
   	
	this.pictureView = new PictureView(picture.createObservable());
		
	
	xLabel = new JLabel(" X: No Value");
	yLabel = new JLabel(" Y: No Value");
	redLabel = new JLabel ("Red: No Value");
    greenLabel = new JLabel ("Green: No Value");
	blueLabel = new JLabel ("Blue: No Value");
	brightnessLabel = new JLabel ("Brightness: No Value");
	
	leftPanel.add(xLabel);
	leftPanel.add(yLabel);
	leftPanel.add(redLabel);
	leftPanel.add(greenLabel);
	leftPanel.add(blueLabel);
	leftPanel.add(brightnessLabel);
	
	
	
	}
	
	
	//Mouse Click highlights a single pixel that gives all necessary information

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		xLabel.setText("X: " + x);
		yLabel.setText("Y: " + y);
		
		Pixel newPixel = picture.getPixel(x, y);
		double redValue = ((int)(newPixel.getRed() *100))/100.0;
		redLabel.setText("Red: " + redValue);
		
		double greenValue = ((int)(newPixel.getGreen() *100))/100.0;
		greenLabel.setText("Green: " + greenValue);
		
		double blueValue = ((int)(newPixel.getBlue() *100))/100.0;
		blueLabel.setText("Blue: " + blueValue);
		
		double brightnessValue = ((int)(newPixel.getIntensity() *100))/100.0;
		brightnessLabel.setText("Brightness: " + brightnessValue );
		
		System.out.println("You clicked on the frame at: " + e.getX() + "," + e.getY() +" with RGB values of: "
		+ redValue + "," + greenValue + "," + blueValue + " and a brightness of:" + brightnessValue);
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
	
}
