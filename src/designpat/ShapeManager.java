package designpat;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by HCH on 01-Jun-16.
 */
public class ShapeManager {
    private ArrayList<ExtShape> shapes;

    public ShapeManager(){
       shapes = new ArrayList<>();
    }

    public void addShape(ExtShape shape){
        shapes.add(shape);
    }

    public void removeShape() {
        shapes.remove(shapes.size()-1);
    }

    public void selectShape(ExtShape shape){
        shape.isSelected = true;
    }

    public void selectShapes(int oldX, int oldY, int currentX, int currentY) {
        Rectangle selection = new Rectangle(oldX, oldY, Math.abs(currentX - oldX), Math.abs(currentY - oldY));
        for (ExtShape extShape : shapes) {
            extShape.isSelected = selection.intersects(extShape.shape.getBounds()) || cursorInBounds(extShape.shape, currentX, currentY);
        }
    }

    public ArrayList<ExtShape> unselectShapes (ArrayList<ExtShape> shapes){
        for(ExtShape shape : shapes){
            shape.isSelected = false;
        }
        return shapes;
    }

    public ArrayList<ExtShape> getShapes(){
        return this.shapes;
    }

    public void drawShapes(Graphics2D g2d) {
        for(ExtShape extShape : shapes){
            g2d.draw(extShape.shape);
            if (extShape.isSelected == true) {
                g2d.fill(extShape.shape);
                drawHighlightSquares(g2d, extShape.shape.getBounds2D());
            }
        }
    }

    public void drawHighlightSquares(Graphics2D g2D, Rectangle2D r) {
        double x = r.getX();
        double y = r.getY();
        double w = r.getWidth();
        double h = r.getHeight();
        g2D.setPaint(Color.black);

        g2D.fill(new Rectangle.Double(x - 6.0, y - 6.0, 6.0, 6.0));
        g2D.fill(new Rectangle.Double(x + w + 1.0, y - 6.0, 6.0, 6.0));
        g2D.fill(new Rectangle.Double(x - 6.0, y + h + 1.0, 6.0, 6.0));
        g2D.fill(new Rectangle.Double(x + w + 1.0, y + h + 1.0, 6.0, 6.0));
    }

    public boolean cursorInBounds(Shape shape, int currentX, int currentY) {
        return shape.contains(currentX, currentY);
    }

    public void clear(){
        shapes.clear();
    }

    public void save() {
        try {
            File file = new File("output");
            FileOutputStream fos = new FileOutputStream(file);
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                unselectShapes(shapes);
                oos.writeObject(shapes);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void load() {
        try {
            FileInputStream fis = new FileInputStream("output");
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                shapes = (ArrayList<ExtShape>) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void dragShapes(int oldX, int oldY, int currentX, int currentY){
        for (ExtShape extShape : shapes) {
            Shape shape = extShape.shape;
            Point centerPoint = new Point(currentX - (shape.getBounds().width / 2), currentY - (shape.getBounds().height / 2));
            if (extShape.isSelected && cursorInBounds(extShape.shape, currentX,currentY)) {
                if (shape instanceof Rectangle) {
                    extShape.shape = new Rectangle(centerPoint.x, centerPoint.y, shape.getBounds().width, shape.getBounds().height);
                } else if (shape instanceof Ellipse2D) {
                    extShape.shape = new Ellipse2D.Double(centerPoint.x, centerPoint.y, shape.getBounds().width, shape.getBounds().height);
                }
            }
        }
    }

    public boolean resizeSquaresCheck(Shape shape, int oldX, int oldY) {
        double x = shape.getBounds().getX();
        double y = shape.getBounds().getY();
        double w = shape.getBounds().getWidth();
        double h = shape.getBounds().getHeight();
        Rectangle.Double rectNW = new Rectangle.Double(x - 6.0, y - 6.0, 6.0, 6.0);
        Rectangle.Double rectNE = new Rectangle.Double(x + w + 1.0, y - 6.0, 6.0, 6.0);
        Rectangle.Double rectSW = new Rectangle.Double(x - 6.0, y + h + 1.0, 6.0, 6.0);
        Rectangle.Double rectSE = new Rectangle.Double(x + w + 1.0, y + h + 1.0, 6.0, 6.0);

        if (cursorInBounds(rectNW, oldX, oldX)) {
            return true;
        } else if (cursorInBounds(rectNE, oldX, oldY)) {
            return true;
        } else if (cursorInBounds(rectSW, oldX, oldY)) {
            return true;
        } else if (cursorInBounds(rectSE, oldX, oldY)) {
            return true;
        } else return false;
    }

    public void resizeShape(int oldX, int oldY, int currentX, int currentY){
        for (ExtShape extShape : shapes) {
            Shape shape = extShape.shape;
            if(extShape.isSelected) {
                double newWidth = shape.getBounds().getWidth();
                double newHeight = extShape.shape.getBounds().getHeight();

                if (shape instanceof Rectangle) {
                    ((Rectangle) shape).setSize(currentX - ((Rectangle) shape).x, currentY - ((Rectangle) shape).y);
                    //extShape.shape = new Rectangle(extShape.shape.getBounds().x, extShape.shape.getBounds().y, (int)newWidth + Math.abs(currentX - oldX), (int)newHeight + Math.abs(currentY - oldY));
                } else if (shape instanceof Ellipse2D) {
                    ((Ellipse2D) shape).setFrame(((Ellipse2D) shape).getX(),((Ellipse2D) shape).getY(),currentX - ((Ellipse2D) shape).getX(),currentY - ((Ellipse2D) shape).getY());
                    //extShape.shape = new Ellipse2D.Double(((Ellipse2D) shape).getX(),((Ellipse2D) shape).getY(),currentX - newWidth, currentY -  newHeight) {
                }
            }
        }
    }
}
