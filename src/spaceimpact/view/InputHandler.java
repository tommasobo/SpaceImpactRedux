package spaceimpact.view;

import java.util.LinkedList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import spaceimpact.utilities.Input;

public class InputHandler implements EventHandler<KeyEvent>, InputHandlerInterface{
    
    private static InputHandlerInterface INPUTHANDLER = null ;
    private final List<Input> listInput = new LinkedList<Input>();

    private InputHandler() {};
    
    public static InputHandlerInterface getInputHandler() {
        if (INPUTHANDLER == null) {
            synchronized ( InputHandler.class) {
                if (INPUTHANDLER == null) {
                    INPUTHANDLER = new InputHandler() ;
                }
            }
        }
        return INPUTHANDLER ;
    }
    
    @Override
    public List<Input> getInput() {
        final List<Input> defensiveListInput = new LinkedList<>(this.listInput);
        this.listInput.clear();
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
