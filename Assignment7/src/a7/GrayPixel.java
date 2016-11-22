package a7;



public class GrayPixel implements Pixel {

	private double intensity;

	private static final char[] PIXEL_CHAR_MAP = {'#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' '};


	public GrayPixel(double intensity) {
		if (intensity < 0.0 || intensity > 1.0) {
			throw new IllegalArgumentException("Intensity of gray pixel is out of bounds.");
		}
		this.intensity = intensity;
	}

	@Override
	public double getRed() {
		return getIntensity();
	}

	@Override
	public double getBlue() {
		return getIntensity();
	}

	@Override
	public double getGreen() {
		return getIntensity();
	}

	@Override
	public double getIntensity() {
		return intensity;
	}

	@Override
	public char getChar() {
		return PIXEL_CHAR_MAP[(int) (getIntensity()*10.0)];
	}	

	public Pixel blend(Pixel p, double weight) {
		double newIntensity = p.getIntensity()* (1.0 - weight) + getIntensity()* weight;
		Pixel blendedPixel = new GrayPixel(newIntensity);
		return blendedPixel;
	}
	
	public Pixel lighten(double factor) {
		Pixel lightenedPixel = new GrayPixel(1.0);
	    return blend(lightenedPixel, 1- factor);
	}
	
	public Pixel darken(double factor) {
		Pixel darkenedPixel = new GrayPixel(0.0);
		return blend(darkenedPixel, 1- factor);
	}

	
	
}
