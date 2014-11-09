package com.sprotect.view.activity.photographsign.overlayview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import com.sprotect.view.activity.photographsign.FindSignActivity;
import com.sprotect.model.camera.CameraManager;

/**
 * View overlays camera preview
 * 1) finders, that were found
 * 2) rectangle, where qr-code is searching
 * <p/>
 * Additionally searches for finders
 */
//TODO: to many functions for view. Extract finder's searching in other model
public class OverlayView extends View {
    public OverlayView(FindSignActivity context) {
        super(context);

        /*setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCameraManager().setFoundSign(true);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getCameraManager().takePicture();
                    }
                }, 1000);
            }
        });*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRoi(canvas);
        drawQrFinders(canvas);
    }

    private void drawRoi(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(getCameraManager().getPreviewRoiRect(), paint);

        /*if (context.getVideoPreviewData().getRoiBitmap() != null) {
            canvas.drawBitmap(context.getVideoPreviewData().getRoiBitmap(), 0, 0, paint);
        }*/
    }

    private void drawQrFinders(Canvas canvas) {
        /*Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        if (context.getVideoPreviewData().getQrFinder() != null) {
            for (ResultPoint point : context.getVideoPreviewData().getQrFinder().getPoints()) {
                canvas.drawCircle(point.getX(), point.getY(), 10, paint);
            }
        }*/
    }

    private CameraManager getCameraManager() {
        return ((FindSignActivity) getContext()).getCameraManager();
    }
}
