package commandPat;

import designpat.ShapeManager;

/**
 * Created by HCH on 01-Jun-16.
 */
public class ResizeShape implements Command{
    private ShapeManager shapeManager;
    private int currentX;
    private int currentY;
    private int oldX;
    private int oldY;

    public ResizeShape(ShapeManager shapeManager, int oldX, int oldY, int currentX, int currentY){
        this.shapeManager = shapeManager;
        this.currentX = currentX;
        this.currentY = currentY;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    @Override
    public void execute() {
        shapeManager.resizeShape(oldX, oldY, currentX,currentY);
    }

    @Override
    public void undo() {

    }
}
