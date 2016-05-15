package com.mlesniak.webcam;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

/**
 * Entry point.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@SpringBootApplication
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setHeadless(false);
        app.run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        CanvasFrame canvas = new CanvasFrame("Webcam");
        canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        FrameGrabber grabber = new OpenCVFrameGrabber("");
        grabber.start();
        while (true) {
            Frame frame = grabber.grab();
            canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight());
            canvas.showImage(frame);
        }
    }
}
