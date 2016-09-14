package FileIO;

import designpat.ExtShape;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by HCH on 18-Jun-16.
 */

public class FileReader {

    public List<ExtShape> readFile() throws IOException {
        List<String> readList = new ArrayList<>();
        List<ExtShape> fileGroup = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get("shapesFile"))) {

            readList = stream.collect(Collectors.toList());
            if(readList.size()>0);
            readList.remove(0);
            //System.out.print(stream.findFirst().filter(line->line.contains("group")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        fileGroup = parseToGroup(readList, fileGroup);
        return fileGroup;
    }



    public List<ExtShape> parseToGroup(List<String> readList, List<ExtShape> group){

        while(!readList.isEmpty()){
            String currentLine = readList.get(0);
            if(currentLine.contains("rectangle")) {
                List<String> shapeCoords = Arrays.asList(currentLine.trim().split(" "));
                Rectangle2D rect = new Rectangle(Integer.parseInt(shapeCoords.get(1)),
                        Integer.parseInt(shapeCoords.get(2)),
                        Integer.parseInt(shapeCoords.get(3)),
                        Integer.parseInt(shapeCoords.get(4)));
                ExtShape extShape = new ExtShape();
                extShape.shape = rect;
                group.add(extShape);
                readList.remove(currentLine);
            }
            if(currentLine.contains("ellipse")) {
                List<String> shapeCoords = Arrays.asList(currentLine.trim().split(" "));
                Ellipse2D ellipse = new Ellipse2D.Float(Integer.parseInt(shapeCoords.get(1)),
                        Integer.parseInt(shapeCoords.get(2)),
                        Integer.parseInt(shapeCoords.get(3)),
                        Integer.parseInt(shapeCoords.get(4)));
                //Ellipse2D ellipse = new Ellipse2D.Float((int)shapeCoords.get(1),(int)shapeCoords.get(2),(int)shapeCoords.get(3),(int)shapeCoords.get(4));
                ExtShape extShape = new ExtShape();
                extShape.shape = ellipse;
                group.add(extShape);
                readList.remove(currentLine);
            }
        }
        // }

        return group;
    }

    private int whiteSpaceCounter(String line){
        int spaceCount = 0;
        for (int i = 0; i <= line.length() && line.charAt(i) == ' '; i++) {
            spaceCount ++;
        }

        return spaceCount;
    }
}
