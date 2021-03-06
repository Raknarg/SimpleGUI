package simple.gui;

import java.awt.*;

import simple.run.Mouse;
import simple.run.ScreenPanel;

/**
 * Generic Widget class. All widgets inherit from this class.
 * <P>The idea behind widgets is to create interfaceable components to perform tasks. These could be buttons, scrollboxes, textboxes, or possibly panels to contain other widgets.
 * Many generic widgets are defined here, but the goal behind this class was to create a system to easily allow you to make your own components.
 * <P>Note that if you decide to make your own Widget object, they have access to a DrawObject named draw. Drawing should be done with this object.
 * <P>Widget is an abstract class, as it's meant to be the framework for other widgets. 
 * @author Austin
 *
 */
public abstract class Widget {
	/** Instance of DrawObject used by the entire program. Uses the same Graphics2D object as the ScreenPanel. 
	 * <P>Only usable by classes which implement Widget. For the main program, use the drawObject for ScreenPanel. **/
	protected static DrawObject draw = new DrawObject();
	
	private static Color DEFAULT_FILLCOLOR = Color.WHITE;
	private static Color DEFAULT_BORDERCOLOR = Color.BLACK;
	private static Color DEFAULT_TEXTAREACOLOR = Color.WHITE;
	private static Color DEFAULT_TEXTCOLOR = Color.BLACK;
	private static Font DEFAULT_TEXTFONT = new Font("Consolas", Font.PLAIN, 12);
	
	/** Returns the default fillColor for all widgets. **/
	public static Color getDefaultFillColor() { return DEFAULT_FILLCOLOR; }
	/** Returns the default borderColor for all widgets. **/
	public static Color getDefaultBorderColor() { return DEFAULT_BORDERCOLOR; }
	/** Returns the default textAreaColor for all widgets. **/
	public static Color getDefaultTextAreaColor() { return DEFAULT_TEXTAREACOLOR; }
	/** Returns the default textColor for all widgets. **/
	public static Color getDefaultTextColor() { return DEFAULT_TEXTCOLOR; }
	/** Returns the default textFont for all widgets. **/
	public static Font getDefaultTextFont() { return DEFAULT_TEXTFONT; }
	
	/** Sets the default fillColor for all widgets. **/
	public static void setDefaultFillColor(Color newFillColor) { DEFAULT_FILLCOLOR = newFillColor; }
	/** Sets the default borderColor for all widgets. **/
	public static void setDefaultBorderColor(Color newBorderColor) { DEFAULT_BORDERCOLOR = newBorderColor; }
	/** Sets the default textAreaColor for all widgets. **/
	public static void setDefaultTextAreaColor(Color newTextAreaColor) { DEFAULT_TEXTAREACOLOR = newTextAreaColor; }
	/** Sets the default textColor for all widgets. **/
	public static void setDefaultTextColor(Color newTextColor) { DEFAULT_TEXTCOLOR = newTextColor; }
	/** Sets the default color variables. **/
	public static void setDefaultWidgetColors(Color newFillColor, Color newBorderColor, Color newTextAreaColor, Color newTextColor) { 
		DEFAULT_FILLCOLOR = newFillColor; 
		DEFAULT_BORDERCOLOR = newBorderColor; 
		DEFAULT_TEXTAREACOLOR = newTextAreaColor;
		DEFAULT_TEXTCOLOR = newTextColor; 
	}
	/** Sets the default textFont for all widgets. **/
	public static void setDefaultTextFont(Font f_) { DEFAULT_TEXTFONT = f_; }
	
	/** Method to determine whether two widgets intersect within a bounding box by default. Behaviour may be changed within individual widget classes with the non-static method. 
	 * Note that as it stands, given two widgets a and b, Widget.intersectsWith(a, b) and Widget.intersectsWith(b, a) may give different results, as w1 does not take into account w2's behaviour for intersectsWith(w1).
	 * @param w1		Widget object whose intersectsWith(Widget w) will be called. 
	 * @param w2		Widget object whose only relevant pieces of information in this method are its x, y, w, and h values. **/
	public static boolean intersectsWith(Widget w1, Widget w2) {
		return w1.intersectsWith(w2);
	}
	
	/** Bottom-left (visually top-left) coordinate of the widget. **/
	protected int x, y; 
	/** Width (w) and height (h) of the widget. **/
	protected int w, h;
	/** Widget's states relative to the mouse. **/
	protected boolean hovering, clicking, clicked, hasEntered;
	/** Widget's state of allowed interaction (e.g. Mouse, Keyboard). **/
	protected boolean enabled; 
	/** Widget's state of visibility. Typically just affects whether the Draw() method does anything or not. **/
	protected boolean visible;
	/** Another state of allowed interaction specifically with the mouse. Note the difference between isBlocked and enabled, apparent when you draw a disabled button vs. a blocked button. **/
	protected boolean blocked;
	
	/** Widget's current color for filling miscellaneous space occupied with nothing else, and other defined areas. **/
	protected Color fillColor;
	/** Widget's current bordering color for the widget's bounding box and text area, and other defined areas. **/
	protected Color borderColor;
	/** Widget's current color for the area which will ahve text rendered upon, and other defined areas.. **/
	protected Color textAreaColor;
	/** Widget's current color for rendering text, and other defined areas. **/
	protected Color textColor;
	/** Widget's current font for rendering text. **/
	protected Font textFont;
	
	/** Allows the button to implement a specified method for drawing. Will be done after drawing the image. **/
	protected CustomDraw customDrawObject;
	
	/** Returns the widget's x variable. **/
	public int getX() { return x; }
	/** Returns the widget's y variable. **/
	public int getY() { return y; }
	/** Returns the widget's w variable. **/
	public int getWidth() { return w; }
	/** Returns the widget's h variable. **/
	public int getHeight() { return h; }
	/** Returns the widget's enabled variable. **/
	public boolean isEnabled() { return enabled; }
	/** Returns the widget's visible variable. **/
	public boolean isVisible() { return visible; }
	/** Returns the widget's blocked variable. **/
	public boolean isBlocked() { return blocked; }
	
	/** Returns the widget's fillColor variable **/
	public Color getFillColor() { return fillColor; }
	/** Returns the widget's borderColor variable **/
	public Color getBorderColor() { return borderColor; }
	/** Returns the widget's textAreaColor variable **/
	public Color getTextAreaColor() { return textAreaColor; }
	/** Returns the widget's textColor variable **/
	public Color getTextColor() { return textColor; }
	/** Returns the widget's textFont variable **/
	public Font getTextFont() { return textFont; }

	/** Sets the widget's x variable **/
	public void setX(int newX) { x=newX; }
	/** Sets the widget's y variable **/
	public void setY(int newY) { y=newY; }
	/** Adds to the widget's x variable **/
	public void addX(int ix) { setX(x+ix); }
	/** Adds to the widget's y variable **/
	public void addY(int iy) { setY(y+iy); }
	/** Sets the widget's x and y variables **/
	public void setLocation(int newX, int newY) { x=newX; y=newY; }
	/** Sets the widget's w variable **/
	public void setWidth(int newWidth) { w=newWidth; }
	/** Sets the widget's h variable **/
	public void setHeight(int newHeight) { h=newHeight; }
	/** Sets the widget's w and h variables **/
	public void setSize(int newWidth, int newHeight) { w=newWidth; h=newHeight; }
	/** Sets the widget's enabled variable, and sets false for all mouse interaction variables **/
	public void setEnabled(boolean newEnabled) { 
		enabled = newEnabled; 
		hovering = false;
		clicking = false;
		clicked = false;
	}
	/** Sets the widget's visble variable, and sets false for all mouse interaction variables **/
	public void setVisible(boolean newVisible) { 
		visible = newVisible;
		hovering = false;
		clicking = false;
		clicked = false;
	}
	public void blockWidget() { 
		blocked = true;
	}
	
	/** Sets the widget's fillColor variable **/
	public void setFillColor(Color newFillColor) { fillColor = newFillColor;}
	/** Sets the widget's borderColor variable **/
	public void setBorderColor(Color newBorderColor) { borderColor = newBorderColor; }
	/** Sets the widget's textAreaColor variable **/
	public void setTextAreaColor(Color newTextAreaColor) { textAreaColor = newTextAreaColor; }
	/** Sets the widget's textColor variable **/
	public void setTextColor(Color newTextColor) { textColor = newTextColor; }
	/** Sets all the widget's color variables **/
	public void setWidgetColors(Color newFillColor, Color newBorderColor, Color newTextAreaColor, Color newTextColor) {
		setFillColor(newFillColor);
		setBorderColor(newBorderColor);
		setTextAreaColor(newTextAreaColor);
		setTextColor(newTextColor);
	}
	/** Sets the widget's textFont variable **/
	public void setTextFont(Font newTextFont) { textFont = newTextFont; }
	
	/** Sets the button's CustomDraw object. **/
	public void setCustomDraw(CustomDraw newCustomDrawObject) { customDrawObject = newCustomDrawObject; }

	/** Creates a widget with default dimensions. In some cases, such as a scrollListBox or certain panels, the dimensions are specified by the object rather than directly by the user. **/
	public Widget() { 
		this(0, 0, 10, 10);
	}
	/** Creates a widgets with specified dimensions. **/
	public Widget(int x_, int y_, int w_, int h_) { 
		if (w < 0 || h < 0) { throw new IndexOutOfBoundsException("Size parameters must be non-negative"); }
		x=x_; 
		y=y_; 
		w=w_; 
		h=h_; 
		
		fillColor = DEFAULT_FILLCOLOR;
		borderColor = DEFAULT_BORDERCOLOR;
		textAreaColor = DEFAULT_TEXTAREACOLOR;
		textColor = DEFAULT_TEXTCOLOR;
		textFont = DEFAULT_TEXTFONT;
		
		enabled = true;
		visible = true;
		hovering = false;
		clicking = false;
		clicked = false;
		hasEntered = false;
		blocked = false;
	}
	
	/** Returns whether or not this widget intersects with another widget. Overwriting encouraged if alternate behavior is needed. **/
	public boolean intersectsWith(Widget other) {
		return intersectsWith(other.getX(), other.getY(), other.getWidth(), other.getHeight());
	}
	/** Returns whether or not this widget intersects with the specified bounding box. Overwriting encouraged if alternate behaviour is needed. **/
	public boolean intersectsWith(int x, int y, int w, int h) {
		return (this.x+this.w > x && x+w > this.x && this.y+this.h > y && y+h > this.y);
	}
	
	/** Returns whether the mouse's x and y coordinates are contained within the widgets bounds. Overwriting encourages if alternate behaviour is needed. **/
	public boolean containsMouse() {
		if (Mouse.getX() < x+w && Mouse.getX() > x && Mouse.getY() < y+h && Mouse.getY() > y) {
			return true && visible;
		} else {
			return false;
		}
	}
	/** Returns the widget's hovering variable. 
	 * <P> Hovering is defined as the mouse being contained by the widget, but with the mouse unpressed.**/
	public boolean isHovering() {
		return hovering;
	}
	/** Returns the widget's clicking variable.
	 * <P> Clicking is defined as the mouse being contained by the widget, with the mouse currently pressing down the button. **/
	public boolean isClicking() {
		return clicking;
	}
	/** Returns the widget's clicked variable.
	 * <P> Clicked is defined as the mouse being contained by the widget, with the mouse unpressed, but was clicking in the direct previous frame. **/
	public boolean isClicked() {
		return clicked;
	}
	
	/** Updates the widget's state relative to the mouse. This must be physically called in your widget's local Update() function for any mouse activity
	 * to register. **/
	protected void updateClickingState() {
		if (clicked) 
			clicked = false;
		if (blocked) {
			blocked = false;
			hovering = false;
			clicking = false;
			clicked = false;
		} else {
			if (containsMouse() && Mouse.isPressed() && hovering) {
				clicking = true && enabled && visible;
			} else if (containsMouse() && clicking && !Mouse.isPressed()) {
				clicked = true && enabled && visible;
				clicking = false;
			} else {
				clicking = false;
				clicked = false;
				if (containsMouse() && !Mouse.isPressed()) {
					hovering = true && visible;
				} else {
					hovering = false;
				}
			}
		}
	}
	
	/** Method which must be implemented by your widget. The status of the widget and it's info should be modified and regulated here every frame. **/
	public abstract void Update();
	/** Method which must be implemented by your widget. Any draw functions for your widget should be called here, and drawn every frame relative to the widget's status. **/
	public abstract void Draw();  
	
	/** Basic toString() function that returns the widget's class name, x and y coordinate, and width and height**/
	public String toString() {
		return this.getClass().getName() + " at position (" + x + "; " + y + ") of size " + w + "; " + h + ").";
	}
}
