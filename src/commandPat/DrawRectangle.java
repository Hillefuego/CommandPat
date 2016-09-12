package commandPat;

import designpat.ExtShape;
import designpat.ShapeManager;

/**
 * Created by HCH on 01-Jun-16.
 */
public class DrawRectangle implements Command {
    private ShapeManager shapeManager;
    ExtShape extShape;

    public DrawRectangle(ShapeManager shapeManager, ExtShape extShape){
        this.shapeManager = shapeManager;
        this.extShape = extShape;
    }

    @Override
    public void execute() {
        shapeManager.addShape(this.extShape);
    }

    @Override
    public void undo() {
        shapeManager.removeShape();
    }
}
