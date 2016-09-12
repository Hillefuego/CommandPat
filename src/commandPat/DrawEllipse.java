package commandPat;

import designpat.ExtShape;
import designpat.ShapeManager;

/**
 * Created by HCH on 01-Jun-16.
 */
public class DrawEllipse implements Command{
    private ShapeManager shapeManager;
    ExtShape extShape;

    public DrawEllipse(ShapeManager shapeManager, ExtShape extShape){
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
