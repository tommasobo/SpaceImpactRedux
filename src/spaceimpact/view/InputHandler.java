package spaceimpact.view;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.input.KeyCode;
import spaceimpact.utilities.Input;

public class InputHandler {

	private static InputHandler INPUTHANDLER = null;
	private boolean w = false;
	private boolean s = false;
	private boolean a = false;
	private boolean d = false;
	private boolean space = false;

	private InputHandler() {
	};

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
		final List<Input> defensiveList = new LinkedList<>();
		if (this.w) {
			defensiveList.add(Input.W);
		}
		if (this.s) {
			defensiveList.add(Input.S);
		}
		if (this.a) {
			defensiveList.add(Input.A);
		}
		if (this.d) {
			defensiveList.add(Input.D);
		}
		if (this.space) {
			defensiveList.add(Input.SPACE);
		}
		return defensiveList;
	}

	void emptyList() {
		this.w = false;
		this.s = false;
		this.a = false;
		this.d = false;
		this.space = false;
	}

	void press(final KeyCode code) {
		this.process(code, true);
	}

	void release(final KeyCode code) {
		this.process(code, false);
	}

	private void process(final KeyCode code, final boolean action) {
		if (code == KeyCode.W) {
			this.w = action;
		} else if (code == KeyCode.A) {
			this.a = action;
		} else if (code == KeyCode.S) {
			this.s = action;
		} else if (code == KeyCode.D) {
			this.d = action;
		} else if (code == KeyCode.SPACE) {
			this.space = action;
		}      
		
	}

}
