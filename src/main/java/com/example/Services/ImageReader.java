package com.example.Services;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class ImageReader {
    public ImageIcon createImageIcon(String fileName, int width, int height) {
        InputStream stream = getClass().getResourceAsStream("/" + fileName);
        if (stream != null) {
            try {
                Image img = ImageIO.read(stream).getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Couldn't find file: " + fileName);
        }
        return null;
    }
}
