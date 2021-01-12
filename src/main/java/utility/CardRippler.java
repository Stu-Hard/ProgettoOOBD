package utility;

import com.jfoenix.controls.JFXRippler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CardRippler extends JFXRippler {
    private float radius;

    public CardRippler(Node control, float radius) {
        super(control);
        this.radius = radius;
        setRipplerFill(Color.GRAY);
    }

    @Override
    protected Node getMask() {
        Rectangle r = new Rectangle();
        r.setWidth(((Pane) control).getWidth());
        r.setHeight(((Pane) control).getHeight());
        r.setArcHeight(radius*2);
        r.setArcWidth(radius*2);
        return r;
    }
}
