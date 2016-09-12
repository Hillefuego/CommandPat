/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designpat;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author HCH
 */
public class MainFrame {

    JFrame frame = new JFrame("Design Patterns");

    public MainFrame() {
        frame.setSize(500, 500);
        frame.setLocation(200, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        final DrawPad drawPad = new DrawPad();
        content.add(drawPad, BorderLayout.CENTER);

        JPanel toolbox = new JPanel();
        toolbox.setPreferredSize(new Dimension(100, 10));

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPad.clear();
            }
        });

        JButton rectangleButton = new JButton("Rectangle");
        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolboxControls.ellipse = false;
                toolboxControls.rectangle = true;
                toolboxControls.select = false;
            }

        });

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolboxControls.select = !toolboxControls.select;
                toolboxControls.ellipse = false;
                toolboxControls.rectangle = false;
            }
        });

        JButton ellipseButton = new JButton("Ellipse");

        ellipseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolboxControls.ellipse = true;
                toolboxControls.rectangle = false;
                toolboxControls.select = false;
            }
        });
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               drawPad.save();
            }
        });
        
         JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               drawPad.load();
            }
        });

        selectButton.setPreferredSize(new Dimension(100, 25));
        ellipseButton.setPreferredSize(new Dimension(100, 25));
        rectangleButton.setPreferredSize(new Dimension(100, 25));
        saveButton.setPreferredSize(new Dimension(100, 25));
        loadButton.setPreferredSize(new Dimension(100, 25));

        toolbox.add(ellipseButton);
        toolbox.add(rectangleButton);
        toolbox.add(selectButton);
        toolbox.add(saveButton);
        toolbox.add(loadButton);
        toolbox.add(clearButton);

        content.add(toolbox, BorderLayout.WEST);
    }
}
