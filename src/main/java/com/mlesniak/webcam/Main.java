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
    private FrameGrabber grabber;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setHeadless(false);
        app.run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        CanvasFrame canvas = new CanvasFrame("Webcam");
        canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        grabber = new OpenCVFrameGrabber("");
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

        double ratio = ((double) grabber.getImageWidth()) / grabber.getImageHeight();
        int width = 200;
        int height = (int) (width / ratio);
        int x = grabber.getImageWidth() - width;
        int y = grabber.getImageHeight() - height;

        opencv_core.Rect rect = new opencv_core.Rect(x, y, width, height);
        opencv_core.Mat roi = mat.apply(rect);

        opencv_core.Mat sub = new opencv_core.Mat();
        opencv_imgproc.resize(mat, sub, new opencv_core.Size(width, height));
        sub.copyTo(roi);

        return converter.convert(mat);
    }
}
