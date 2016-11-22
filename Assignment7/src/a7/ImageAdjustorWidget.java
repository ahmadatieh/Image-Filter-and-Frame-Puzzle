package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageAdjustorWidget extends JPanel implements ChangeListener {

	private PictureView pictureView;

	private Picture picture;

	private JSlider blurSlider;
	private JSlider saturationSlider;
	private JSlider brightnessSlider;
	private JLabel brightnessLabel;
	private JLabel saturationLabel;
	private JLabel blurLabel;
	List<ChangeListener> change_listeners;

	// Constructor takes in a picture and then constructs a x, y, red, green,
	// blue, and brightness label
	
	public ImageAdjustorWidget(Picture picture) {

		this.picture = picture;

		setLayout(new BorderLayout());

		JPanel slider_panel = new JPanel();
		slider_panel.setLayout(new GridLayout(3, 1));

		pictureView = new PictureView(picture.createObservable());
		
		add(pictureView, BorderLayout.CENTER);
		add(slider_panel, BorderLayout.SOUTH);

		blurLabel = new JLabel("Blur:");
		blurSlider = new JSlider(0, 5);

		saturationLabel = new JLabel("Saturation:");
		saturationSlider = new JSlider(-100, 100);

		brightnessLabel = new JLabel("Brightness:");
		brightnessSlider = new JSlider(-100, 100);

		slider_panel.add(blurLabel);
		slider_panel.add(blurSlider);

		slider_panel.add(saturationLabel);
		slider_panel.add(saturationSlider);

		slider_panel.add(brightnessLabel);
		slider_panel.add(brightnessSlider);

		blurSlider.setPaintTicks(true);
		blurSlider.setValue(0);
		blurSlider.setMajorTickSpacing(1);
		blurSlider.setPaintLabels(true);
		blurSlider.addChangeListener(this);

		saturationSlider.setPaintTicks(true);
		saturationSlider.setValue(0);
		saturationSlider.setMajorTickSpacing(25);
		saturationSlider.setPaintLabels(true);
		saturationSlider.addChangeListener(this);

		brightnessSlider.setPaintTicks(true);
		brightnessSlider.setValue(0);
		brightnessSlider.setMajorTickSpacing(25);
		brightnessSlider.setPaintLabels(true);
		brightnessSlider.addChangeListener(this);

		slider_panel.setPreferredSize(new Dimension(50, 250));
		// blurLabel.setHorizontalAlignment(SwingConstants.WEST);
		// saturationLabel.setHorizontalAlignment(SwingConstants.WEST);
		// brightnessLabel.setHorizontalAlignment(SwingConstants.WEST);
        
		change_listeners = new ArrayList<ChangeListener>();
	}

	public Picture setBlur(Picture picture) {
		Pixel p;
		Picture blurredPic = new PictureImpl(picture.getWidth(), picture.getHeight());
		double avgRed = 0.0;
		double avgGreen = 0.0;
		double avgBlue = 0.0;
		int count = 0;

		for (int i = 0; i < picture.getWidth(); i++) {
			for (int j = 0; j < picture.getHeight(); j++) {

				avgRed = 0.0;
				avgGreen = 0.0;
				avgBlue = 0.0;
				count = 0;

				for (int x = -1 * blurSlider.getValue(); x <= blurSlider.getValue(); x++) {
					for (int y = -1 * blurSlider.getValue(); y <= blurSlider.getValue(); y++) {

						if ((i + x < picture.getWidth() && i + x >= 0) && (j + y < picture.getHeight() && j + y >= 0)) {

							avgRed += picture.getPixel(x + i, y + j).getRed();
							avgGreen += picture.getPixel(x + i, y + j).getGreen();
							avgBlue += picture.getPixel(x + i, y + j).getBlue();
							count++;
							
						}

					}
				}
				avgRed /= count;
				avgGreen /= count;
				avgBlue /= count;

				p = new ColorPixel(avgRed, avgGreen, avgBlue);
				blurredPic.setPixel(i, j, p);

			}
		}
		return blurredPic;

	}

	
	public Picture setBrightness(Picture picture){
	 
	  Picture brightenedPic = new PictureImpl(picture.getWidth(), picture.getHeight());
	  
	  
		for (int i = 0; i < picture.getWidth(); i++) {
			for (int j = 0; j < picture.getHeight(); j++) {
           
		  
		  Pixel newPixel = picture.getPixel(i, j);
		
		  if(brightnessSlider.getValue() > 0){
			  newPixel = picture.getPixel(i, j).lighten(brightnessSlider.getValue() / 100.0);
			  brightenedPic.setPixel(i, j, newPixel);
		  }else{
			  newPixel = picture.getPixel(i, j).darken(brightnessSlider.getValue() / -100.0);
			  brightenedPic.setPixel(i ,j, newPixel);
		  }
		  
		}
			
	}
     
		return brightenedPic;
}
	
	public Picture setSaturation(Picture picture){
		
	Picture saturatedPic = new PictureImpl(picture.getWidth(), picture.getHeight());
	double newRed;
	double newGreen;
	double newBlue;
	
	  for (int i = 0; i < picture.getWidth(); i++){
		  for(int j = 0; j < picture.getHeight(); j++){
			
	     if( picture.getPixel(i, j).getRed() == 0 && picture.getPixel(i, j).getGreen() == 0 && picture.getPixel(i, j).getBlue() == 0){
	    	Pixel newPixel = new ColorPixel(0 , 0 , 0);
	    	saturatedPic.setPixel(i,  j, newPixel);
	     }
			  
			  
			  
		  Pixel oldPixel = picture.getPixel(i, j);
		  double brightness = oldPixel.getIntensity();
		  int saturationValue = saturationSlider.getValue();
		  
		  if(saturationValue<= 0){
			  newRed = oldPixel.getRed() * (1.0 + (saturationValue / 100.0)) - (brightness * saturationValue / 100.0);
			  newGreen = oldPixel.getGreen() * (1.0 + (saturationValue / 100.0)) - (brightness * saturationValue / 100.0);
			  newBlue = oldPixel.getBlue() * (1.0 + (saturationValue / 100.0)) - (brightness * saturationValue / 100.0);    
		      Pixel newPixel = new ColorPixel(newRed, newGreen, newBlue);
		      saturatedPic.setPixel(i, j, newPixel);
		  }else{
			double max = 0.0;
			double oldRed = oldPixel.getRed();
			double oldGreen = oldPixel.getGreen();
			double oldBlue = oldPixel.getBlue();
			if((oldRed >= oldGreen) && (oldRed >= oldBlue)){
			  max = oldRed;
			  
			}else if((oldGreen >= oldRed) && (oldGreen >= oldBlue)){
			  max = oldGreen;
			}else{
			  max = oldBlue;
			}
		    
			newRed = oldRed* ((max + ((1.0 - max) * (saturationValue / 100.0))) / max);
			newGreen = oldGreen* ((max + ((1.0 - max) * (saturationValue / 100.0))) / max);
			newBlue = oldBlue* ((max + ((1.0 - max) * (saturationValue / 100.0))) / max); 
			Pixel newPixel = new ColorPixel(newRed, newGreen, newBlue);
			saturatedPic.setPixel(i,  j, newPixel);
		  }
		 }
	    }
	     return saturatedPic;
	   }

	public void stateChanged(ChangeEvent e) {

		if (!((JSlider) e.getSource()).getValueIsAdjusting() ) {
			Picture x = setBlur(this.picture);
			Picture y = setBrightness(x);
			Picture z = setSaturation(y);
			pictureView.setPicture(z.createObservable());
		}
	}
	
//	public void stateBrightnessChanged(ChangeEvent e) {
//
//		if (!((JSlider) e.getSource()).getValueIsAdjusting()) {
//			pictureView.setPicture(setBrightness(this.picture).createObservable());
//			}
//		}

//	public void addChangeListener(ChangeListener l) {
//		change_listeners.add(l);
//	}
//
//	public void removeChangeListener(ChangeListener l) {
//		change_listeners.remove(l);
//	}

	
		
	}


