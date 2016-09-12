package commandPat;

import designpat.ExtShape;
import designpat.ShapeManager;

/**
 * Created by HCH on 01-Jun-16.
 */
public class SelectShapes implements Command {
    private ShapeManager shapeManager;
    int oldX;
    int oldY;
    int currentX;
    int currentY;

    public SelectShapes(ShapeManager shapeManager, int oldX, int oldY, int currentX, int currentY){
        this.shapeManager = shapeManager;
        this.oldX = oldX;
        this.oldY = oldY;
        this.currentX = currentX;
        this.currentY = currentY;
    }

    @Override
    public void execute() {
        shapeManager.selectShapes(oldX,  oldY,  currentX,  currentY);
    }

    @Override
    public void undo() {
        shapeManager.unselectShapes(shapeManager.getShapes());
    }
}
