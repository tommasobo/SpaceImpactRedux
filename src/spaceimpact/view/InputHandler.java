package spaceimpact.view;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.input.KeyCode;
import spaceimpact.utilities.Input;

public class InputHandler {

    private static InputHandler INPUTHANDLER = null;
    private final List<Input> inputList = new LinkedList<>();  

    private InputHandler() {};

    public static InputHandler getInputHandler() {
	if (InputHandler.INPUTHANDLER == null) {
	    synchronized (InputHandler.class) {
	        if (InputHandler.INPUTHANDLER == null) {
	            InputHandler.INPUTHANDLER = new InputHandler();
		}
	    }
	}
	return InputHandler.INPUTHANDLER;
    }
    
    public List<Input> getList() {
        return this.inputList;
    }
    
    public Input singleKey(KeyCode code) {
        switch (code) {
        case W:
            return Input.W;
        case A:
            return Input.A;
        case S:
            return Input.S;
        case D:
            return Input.D;
        case SPACE:
            return Input.SPACE;
        default:
            return Input.SPACE;
        }
    }
    
}
