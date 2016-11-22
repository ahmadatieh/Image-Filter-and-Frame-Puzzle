package a7;

;

public class ColorPixel implements Pixel {

	private double red;
	private double green;
	private double blue;
	
	
	private static final double RED_INTENSITY_FACTOR = 0.299;
	private static final double GREEN_INTENSITY_FACTOR = 0.587;
	private static final double BLUE_INTENSITY_FACTOR = 0.114;

	private static final char[] PIXEL_CHAR_MAP = {'#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' ', ' '};
	
	public ColorPixel(double r, double g, double b) {
		if (r > 1.0 || r < 0.0) {
			throw new IllegalArgumentException("Red out of bounds");
		}
		if (g > 1.0 || g < 0.0) {
			throw new IllegalArgumentException("Green out of bounds");
		}
		if (b > 1.0 || b < 0.0) {
			throw new IllegalArgumentException("Blue out of bounds");
		}
		red = r;
		green = g;
		blue = b;
	}
	
	@Override
	public double getRed() {
		return red;
	}

	@Override
	public double getBlue() {
		return blue;
	}

	@Override
	public double getGreen() {
		return green;
	}

	@Override
	public double getIntensity() {
		return RED_INTENSITY_FACTOR*getRed() + 
				GREEN_INTENSITY_FACTOR*getGreen() + 
				BLUE_INTENSITY_FACTOR*getBlue();
	}
	
	@Override
	public char getChar() {
		int char_idx = (int) (getIntensity()*10.0);
		return PIXEL_CHAR_MAP[char_idx];
	}
	public Pixel blend(Pixel p, double weight) {
		double newRed = p.getRed()* (1.0 - weight) + getRed() * weight;
		double newGreen = p.getGreen() * (1.0 - weight) + getGreen() * weight;
		double newBlue = p.getBlue() * (1.0 -weight) + getBlue() * weight;
		Pixel blendedPixel = new ColorPixel(newRed, newGreen, newBlue);
		return blendedPixel;
	}

	
	public Pixel lighten(double factor) {
		Pixel lightenedPixel = new ColorPixel(1.0, 1.0, 1.0);
	    return blend(lightenedPixel, 1- factor);
	   
	}

	
	public Pixel darken(double factor) {
		Pixel darkenedPixel = new ColorPixel(0.0, 0.0, 0.0);
		return blend(darkenedPixel, 1- factor);
	}
	
	
}
