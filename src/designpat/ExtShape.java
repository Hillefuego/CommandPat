/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designpat;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

/**
 *
 * @author HCH
 */
public class ExtShape implements Serializable {
    
    public Shape shape;
    public boolean isSelected = false;

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        if(shape instanceof Ellipse2D){
            builder.append("ellipse " +
                    (int)((Ellipse2D) shape).getX() + " " +
                    (int)((Ellipse2D) shape).getY() + " " +
                    (int)((Ellipse2D) shape).getWidth() + " " +
                    (int)((Ellipse2D) shape).getWidth() + "\n");
        }
        if(shape instanceof Rectangle){
            builder.append("rectangle " +
                    (int)((Rectangle) shape).getX() + " " +
                    (int)((Rectangle) shape).getY() + " " +
                    (int)((Rectangle) shape).getWidth() + " " +
                    (int)((Rectangle) shape).getWidth() + "\n");
        }
        return builder.toString();
    }
}
