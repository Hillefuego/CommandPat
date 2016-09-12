/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designpat;

import commandPat.CommandManager;
import commandPat.DrawEllipse;
import commandPat.DrawRectangle;
import commandPat.SelectShapes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;


/**
 * @author HCH
 */
class DrawPad extends JComponent {

    Image image;
    ShapeManager shapeMan;
    CommandManager cmdMan;
    int currentX, currentY, oldX, oldY;

    public DrawPad() {
        shapeMan = new ShapeManager();
        cmdMan = new CommandManager();
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                dragShapes();
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            @SuppressWarnings("empty-statement")
            public void mouseReleased(MouseEvent e) {
                currentY = e.getY();
                currentX = e.getX();
                ExtShape newShape = new ExtShape();
                if (currentX < oldX) {
                    int tempX = currentX;
                    currentX = oldX;
                    oldX = tempX;
                }
                if (currentY < oldY) {
                    int tempY = currentY;
                    currentY = oldY;
                    oldY = tempY;
                }
                if (toolboxControls.rectangle == true) {
                    newShape.shape = new Rectangle(oldX, oldY, Math.abs(currentX - oldX), Math.abs(currentY - oldY));
                    cmdMan.newCommand(new DrawRectangle(shapeMan,newShape));
                }
                if (toolboxControls.ellipse == true) {
                    newShape.shape = new Ellipse2D.Double(oldX, oldY, Math.abs(currentX - oldX), Math.abs(currentY - oldY));
                    cmdMan.newCommand(new DrawEllipse(shapeMan,newShape));
                }
                if (toolboxControls.select == true) {
                    //Rectangle selection = new Rectangle(oldX, oldY, Math.abs(currentX - oldX), Math.abs(currentY - oldY));
                    cmdMan.newCommand(new SelectShapes(shapeMan,oldX, oldY, currentX, currentY));
                }
                repaint();
            }
        });
        Action undoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("undo");
                cmdMan.undoCommand();
                repaint();
            }
        };
        Action redoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("redo");
                cmdMan.redoCommand();
                repaint();
            }
        };

        getActionMap().put("Undo", undoAction);
        getActionMap().put("Redo", redoAction);

        InputMap[] inputMaps = new InputMap[]{
                getInputMap(JComponent.WHEN_FOCUSED),
                getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT),
                getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW),
        };
        for (InputMap i : inputMaps) {
            i.put(KeyStroke.getKeyStroke("control Z"), "Undo");
            i.put(KeyStroke.getKeyStroke("control Y"), "Redo");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            g2d.setPaint(Color.white);
            g2d.fillRect(0, 0, getSize().width, getSize().height);
            g2d.setPaint(Color.black);
            clear();

        } else {
            shapeMan.drawShapes(g2d);
        }
        //g.drawImage(image, 0, 0, null);
    }

    public void clear() {
        shapeMan.clear();
        repaint();
    }


    public void dragShapes() {
        shapeMan.dragShapes(oldX, oldY, currentX, currentY);
    }

    public boolean cursorInBounds(Shape shape) {
        return shape.contains(currentX, currentY);
    }

    public String resizeSquaresCheck(Shape shape) {
        double x = shape.getBounds().getX();
        double y = shape.getBounds().getY();
        double w = shape.getBounds().getWidth();
        double h = shape.getBounds().getHeight();
        Rectangle.Double rectNW = new Rectangle.Double(x - 6.0, y - 6.0, 6.0, 6.0);
        Rectangle.Double rectNE = new Rectangle.Double(x + w + 1.0, y - 6.0, 6.0, 6.0);
        Rectangle.Double rectSW = new Rectangle.Double(x - 6.0, y + h + 1.0, 6.0, 6.0);
        Rectangle.Double rectSE = new Rectangle.Double(x + w + 1.0, y + h + 1.0, 6.0, 6.0);

        if (cursorInBounds(rectNW)) {
            return "NW";
        } else if (cursorInBounds(rectNE)) {
            return "NE";
        } else if (cursorInBounds(rectSW)) {
            return "SW";
        } else if (cursorInBounds(rectSE)) {
            return "SE";
        } else return null;
    }

    public void save() {
        shapeMan.save();
        repaint();
    }

    public void load(){
        shapeMan.load();
        repaint();
    }
}
