package simple.gui;

import java.awt.Color;

/** Provides the basic framework for other ScrollBox types. Essentially handles the background of the widget and the up/down buttons, and the side slider. 
 * <P>Like the Widget class, it is abstract as it is the framework for scrollboxes, not a functional scrollbox. **/
public abstract class ScrollBox extends Widget {
	/** Width of sidebar for all new ScrollBoxes. The default value of 20 is typically a good size in all cases, but this may be changed. **/
	protected static int BAR_WIDTH = 20;
	
	/** Returns the current bar width for all new ScrollBoxes. **/
	public static int getBarWidth() { return BAR_WIDTH; }
	/** Sets the new bar width for all future ScrollBoxes. **/
	public static void setBarWidth(int newBarWidth) { BAR_WIDTH = newBarWidth; }
	
	/** Slider object to navigate through the ScrollBox. **/
	protected Slider scrollBar;
	/** Up/Down buttons to navigate through the ScrollBox. **/
	protected Button scrollUp, scrollDown;
	
	/** Returns the Button object for scrolling up. **/
	public Button getScrollUpButton() { return scrollUp; }
	/** Returns the Button object for scrolling down. **/
	public Button getScrollDownButton() { return scrollDown; }
	/** Returns the Slider object for scrolling. **/
	public Slider getScrollBar() { return scrollBar; }
	
	/** Sets the size for the scrollBox, and resizes/repositions the scroll widgets. **/
	public void setSize(int newWidth, int newHeight) {
		super.setSize(newWidth, newHeight);
		setScrollWidgetPosition();
	}
	/** Sets the width for the scrollBox, and resizes/repositions the scroll widgets. **/
	public void setWidth(int newWidth) { setSize(newWidth, h); }
	/** Sets the height for the scrollBox, and resizes/repositions the scroll widgets. **/
	public void setHeight(int newHeight) { setSize(w, newHeight); }
	
	/** Sets the new position of the scrollBox, as well as moves the scroll widgets. **/
	public void setLocation(int newX, int newY) {
		super.setLocation(newX, newY);
		setScrollWidgetPosition();
	}
	/** Sets the new x position of the scrollBox, as well as moves the scroll widgets. **/
	public void setX(int newX) { setLocation(newX, y); }
	/** Sets the new y position of the scrollBox, as well as moves the scroll widgets. **/
	public void setY(int newY) { setLocation(x, newY); 
	}
	
	/** Creates a default ScrollBox object. **/
	public ScrollBox() {
		this(0, 0, 10, 10);
	}
	/** Creates a ScrollBox object with defined location and size. **/
	public ScrollBox(int x_, int y_, int w_, int h_) {
		super(x_, y_, w_, h_);
		scrollUp = new Button(x+w-BAR_WIDTH, y, BAR_WIDTH, BAR_WIDTH, "", null);
		scrollDown = new Button(x+w-BAR_WIDTH, y+h-BAR_WIDTH, BAR_WIDTH, BAR_WIDTH, "", null);
		scrollBar = new Slider(x+w-BAR_WIDTH, y+BAR_WIDTH, BAR_WIDTH, h-BAR_WIDTH*2, 0, 0, false, false);
		
		scrollUp.setCustomDraw(new CustomDraw() {
			public void customDraw(Widget w) {
				int[] x = new int[3]; 
				int[] y = new int[3];
				
				x[0] = w.getX()+4;
				x[1] = w.getX()+(w.getWidth()/2);
				x[2] = w.getX()+w.getWidth()-4;
				y[0] = w.getY()+w.getHeight()-4;
				y[1] = w.getY()+4;
				y[2] = w.getY()+w.getHeight()-4;
				
				draw.setFillColor(null);
				draw.setBorderColor(w.getBorderColor());
				draw.polygon(x, y, 3);
			}});
		scrollDown.setCustomDraw(new CustomDraw() {
			public void customDraw(Widget w) {
				int[] x = new int[3]; 
				int[] y = new int[3];
				
				x[0] = w.getX()+4;
				x[1] = w.getX()+(w.getWidth()/2);
				x[2] = w.getX()+w.getWidth()-4;
				y[0] = w.getY()+4;
				y[1] = w.getY()+w.getHeight()-4;
				y[2] = w.getY()+4;
				
				draw.setFillColor(null);
				draw.setBorderColor(w.getBorderColor());
				draw.polygon(x, y, 3);
			}});
	}
	
	/** Resets the position of the scroll widgets. 
	 * Note: takes into account what BAR_WIDTH is at the time of this call, regardless of what it was before. **/
	protected void setScrollWidgetPosition() {
		scrollUp.setLocation(x+w-BAR_WIDTH, y);
		scrollUp.setSize(BAR_WIDTH, BAR_WIDTH);
		scrollDown.setLocation(x+w-BAR_WIDTH, y+h-BAR_WIDTH);
		scrollDown.setSize(BAR_WIDTH, BAR_WIDTH);
		scrollBar.setLocation(x+w-BAR_WIDTH, y+BAR_WIDTH);
		scrollBar.setSize(BAR_WIDTH, h-BAR_WIDTH*2);
	}
	
	/** Calls Update() on all the scroll widgets. **/
	protected void updateScrollWidgets() {
		scrollUp.Update();
		scrollDown.Update();
		scrollBar.Update();
	}
	
	/** Calls Draw() on all the scroll widgets. **/
	protected void drawScrollWidgets() {
		scrollUp.Draw();
		scrollDown.Draw();
		scrollBar.Draw();
	}
	
	/** Update() must be defined for any subclass. **/
	public abstract void Update();
	/** Draw() must be defined for any subclass. **/
	public abstract void Draw();
}
