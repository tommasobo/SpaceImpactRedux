package spaceimpact.view;

import java.util.LinkedList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import spaceimpact.utilities.Input;

public class InputHandler implements EventHandler<KeyEvent> {
    
    private static InputHandler INPUTHANDLER = null ;
    private List<Input> listInput = null;

    private InputHandler() {};
    
    public static InputHandler getInputHandler() {
        if (INPUTHANDLER == null) {
            synchronized ( InputHandler.class) {
                if (INPUTHANDLER == null) {
                    INPUTHANDLER = new InputHandler() ;
                }
            }
        }
        return INPUTHANDLER ;
    }
    
    public List<Input> getInput() {
        if (this.listInput == null) {
            this.listInput = new LinkedList<>();
        }
        System.out.println(this.listInput);
        final List<Input> defensiveListInput = this.listInput;
        this.listInput = new LinkedList<>();
        return defensiveListInput;
    }
    
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
        case W:
            this.listInput.add(Input.W);
            break;
        case A:
            this.listInput.add(Input.A);
            break;
        case S:
            this.listInput.add(Input.S);
            break;
        case D:
            this.listInput.add(Input.D);
            break;
        case SPACE:
            this.listInput.add(Input.SPACE);
            break;
        default:
            break;
        }
    }
}
