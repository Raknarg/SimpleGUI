package simple.gui;

import java.awt.event.KeyEvent;

import simple.run.Mouse;

public class TextEntryBox extends TextBox {
	private String enteredText;
	private boolean textIsEntered;
	
	public String getEnteredText() { return enteredText; }
	public boolean hasTextEntered() { return textIsEntered; }
	
	public TextEntryBox() {
		this(0, 0, 10, 10);
	}
	public TextEntryBox(int x_, int y_, int w_, int h_) {
		super(x_, y_, w_, h_);		
		
		enteredText = "";
		textIsEntered = false;
	}
	
	@Override
	public void Update() {
		if (!enabled || !visible) 
			return;
		
		updateClickingState();
		textIsEntered = false;
		enteredText = "";
		
		if (isClicked()) {
			active = true;
		} else if (!containsMouse() && Mouse.isPressed()) {
			active = false;
		}
		if (newKeyCode != KEY_NOT_PRESSED) {
			if (active) {
				if (newKeyCode == KeyEvent.VK_BACK_SPACE) {
					if (text.length() > 0) {
						text = text.substring(0, text.length()-1);
					}
				} else if (newKeyCode == KeyEvent.VK_ENTER) {
					textIsEntered = true;
					enteredText = text;
					text = "";
				} else if (newKeyCode >= 32 && newKeyCode <= 126) {
					text += (char)newKeyCode;
				}
			}
			newKeyCode = KEY_NOT_PRESSED;
		}
	}
}
