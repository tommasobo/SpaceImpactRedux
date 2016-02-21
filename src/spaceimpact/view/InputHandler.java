package spaceimpact.view;

import java.util.Collection;
import java.util.Collections;
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
        this.inputList.removeAll(Collections.singleton(null));
        List<Input> defensiveList = this.inputList;
        return defensiveList;
    }
    
    public Input singleKey(KeyCode code) {
        if (code == KeyCode.W) {
            return Input.W;
        } else if (code == KeyCode.A) {
            return Input.A;
        } else if (code == KeyCode.S) {
            return Input.S;
        } else if (code == KeyCode.D) {
            return Input.D;
        } else if (code == KeyCode.SPACE) {
            return Input.SPACE;
        }
        return null;
    }
    
}
