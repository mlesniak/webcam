package com.mlesniak.webcam;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.*;
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
    private OpenCVFrameConverter.ToMat converter;

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

        converter = new OpenCVFrameConverter.ToMat();

        while (true) {
            Frame frame = grabber.grab();
            frame = processFrame(frame);
            canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight());
            canvas.showImage(frame);
        }
    }

    /**
     * Playground for frame processing.
     *
     * @param frame src frame
     * @return processed frame
     */
    private Frame processFrame(Frame frame) {
        opencv_core.Mat mat = converter.convert(frame);
        opencv_imgproc.rectangle(mat,
                new opencv_core.Point(50, 50),
                new opencv_core.Point(100, 100),
                new opencv_core.Scalar(255, 255, 0, 1));
        return converter.convert(mat);
    }
}
