package utility;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

// classe utile a spostare le finestre trascinandole per evitare riscrittura di codice. Si accettano consigli per cambiarle nome
public class WindowDragger {

    private double xOffset = 0.0;
    private double yOffset = 0.0;

    public void setOffset(MouseEvent e){
        Window win = ((Pane) e.getSource()).getScene().getWindow();
        win.requestFocus();
        xOffset = win.getX() - e.getScreenX();
        yOffset = win.getY() - e.getScreenY();
    }
    public void moveWindow(MouseEvent e) {
        Window win = ((Pane) e.getSource()).getScene().getWindow();
        win.setX(e.getScreenX() + xOffset);
        win.setY(e.getScreenY() + yOffset);
    }
}
