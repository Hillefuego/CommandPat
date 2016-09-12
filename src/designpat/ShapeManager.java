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
            if (extShape.isSelected && cursorInBounds(extShape.shape, currentX,currentY)) {
                Shape shape = extShape.shape;
                Point centerPoint = new Point(currentX - (shape.getBounds().width / 2), currentY - (shape.getBounds().height / 2));
                if (shape instanceof Rectangle) {
                    Rectangle rect = new Rectangle(centerPoint.x, centerPoint.y, shape.getBounds().width, shape.getBounds().height);
                    System.out.println(rect.getBounds().toString());
                    extShape.shape = rect;
                    System.out.println("OldX " + oldX + ",OldY " + oldY);
                    System.out.println("CurrentX " + currentX + ",CurrentY " + currentY);
                } else if (shape instanceof Ellipse2D) {
                    Rectangle rect = new Rectangle(centerPoint.x, centerPoint.y, shape.getBounds().width, shape.getBounds().height);
                    System.out.println(rect.getBounds().toString());
                    extShape.shape = new Ellipse2D.Double(rect.x, rect.y, rect.width, rect.height);
                    System.out.println("OldX " + oldX + ",OldY " + oldY);
                    System.out.println("CurrentX " + currentX + ",CurrentY " + currentY);
                }
            }
        }
    }
}
