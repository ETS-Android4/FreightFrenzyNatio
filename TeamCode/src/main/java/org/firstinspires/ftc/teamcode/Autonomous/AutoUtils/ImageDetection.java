package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import com.acmerobotics.dashboard.FtcDashboard;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

public class  ImageDetection {
    //TODO: REFACTOOOOOR
    public static OpenCvCamera camera;
    public enum DuckPosition{
        Left,
        Middle,
        Right
    }
    public static DuckPosition duckPosition;

    public static void initialize() {
        int cameraMonitorViewId = Hardware.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", Hardware.hardwareMap.appContext.getPackageName());
        WebcamName webcamName = Hardware.hardwareMap.get(WebcamName.class, "Webcam 1");
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        camera.setPipeline(new Pipeline());

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(camera,60);
            }
            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

    }

    public static void updatePosition(DuckPosition position){
        duckPosition = position;
    }

    static class Pipeline extends OpenCvPipeline {
        int yOffset = 20;
        Point leftRegionTopLeft = new Point(0+10,60 + yOffset);
        Point leftRegionBottomRight = new Point(105-50,100 + yOffset);

        Point middleRegionTopLeft = new Point(106+30, 38 + yOffset);
        Point middleRegionBottomRight = new Point(210-30, 78 + yOffset);

        Point rightRegionTopLeft = new Point(211+55, 22 + yOffset);
        Point rightRegionBottomRight = new Point(319-5, 62 + yOffset);

        Mat leftSubmat;
        Mat middleSubmat;
        Mat rightSubmat;

        double leftMeanGreen;
        double middleMeanGreen;
        double rightMeanGreen;

        double maxMeanGreen;

        DuckPosition position;

        @Override
        public void init(Mat firstFrame) {
            leftSubmat=firstFrame.submat(new Rect(leftRegionTopLeft, leftRegionBottomRight));
            middleSubmat=firstFrame.submat(new Rect(middleRegionTopLeft, middleRegionBottomRight));
            rightSubmat=firstFrame.submat(new Rect(rightRegionTopLeft, rightRegionBottomRight));
        }

        @Override
        public Mat processFrame(Mat input) {

            Imgproc.rectangle(input, leftRegionTopLeft, leftRegionBottomRight, new Scalar(0, 255, 0), 4);
            Imgproc.rectangle(input, middleRegionTopLeft, middleRegionBottomRight, new Scalar(0, 255, 0), 4);
            Imgproc.rectangle(input, rightRegionTopLeft, rightRegionBottomRight, new Scalar(0, 255, 0), 4);

            leftMeanGreen = Core.mean(leftSubmat).val[1];
            middleMeanGreen = Core.mean(middleSubmat).val[1];
            rightMeanGreen = Core.mean(rightSubmat).val[1];

            maxMeanGreen = leftMeanGreen;
            position = DuckPosition.Left;
            if (middleMeanGreen > maxMeanGreen){
                maxMeanGreen = middleMeanGreen;
                position = DuckPosition.Middle;
            }
            if (rightMeanGreen > maxMeanGreen){
                maxMeanGreen = rightMeanGreen;
                position = DuckPosition.Right;
            }

            updatePosition(position);
            Hardware.telemetry.addData("CaruselPositionX", Trajectories.caruselPosition);
            Hardware.telemetry.addData("gapPose", Trajectories.gapPose);
            Hardware.telemetry.addData("startPose", PoseStorage.startPosition);
            Hardware.telemetry.addData("Left Green Amount", leftMeanGreen);
            Hardware.telemetry.addData("Middle Green Amount", middleMeanGreen);
            Hardware.telemetry.addData("Right Green Amount", rightMeanGreen);
            Hardware.telemetry.addData("Detected Case: ", position);
            Hardware.telemetry.update();

            return input;
        }

    }
}
