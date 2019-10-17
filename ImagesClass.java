/*
 * Copyright (c) 2019. Created By Tiefseetauchner
 * https://github.com/Tiefseetauchner
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImagesClass {
    public static void encodeTextInImage(String text, int width, int height) throws IOException {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        File image = new File("rsc/imageLol.png");
        int pixel = 0;
        int length = text.length();
        for (int i = 0; i < length; i += 2, pixel++) {
            int color;
            color = text.charAt(i);
            color <<= 16;
            if (i + 1 == length) {
                break;
            }
            color += text.charAt(i + 1);
            int x = pixel % width;
            int y = pixel / width;
            img.setRGB(x, y, color);
        }
        ImageIO.write(img, "png", image);
    }

    public static void encodeTextInImage(Path path, int width, int height) throws IOException {
        try (
                BufferedReader in = Files.newBufferedReader(path,
                        Charset.forName("UTF-8")
                )
        ) {
            String line;
            StringBuilder text = new StringBuilder();
            while ((line = in.readLine()) != null) {
                text.append(line).append(System.lineSeparator());
            }
            encodeTextInImage(text.toString(), width, height);
        }
    }

    public static void decodeTextInImage(String imagePaths) throws IOException {
        BufferedImage in = ImageIO.read(new File(imagePaths));
        int width = in.getWidth();
        int height = in.getHeight();
        int length = height * width;
        int pixel = 0;
        for (int i = 0; i < length; i += 2, pixel++) {
            int color = in.getRGB(pixel % width, pixel / width);
            if (color == 0) {
                break;
            }
            char c1 = (char) ((color >> 16) & 0xffff);
            char c2 = (char) (color & 0xffff);
            System.out.print(c1 + "" + c2);
        }
        System.out.println("Finished");
    }
}