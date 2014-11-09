package com.sprotect.view.activity.processsign;// MaiseyenkaDP gdfan 22.09.12 19:07

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.sprotect.R;
import com.sprotect.external.android.javacv.JavaCvImageUtils;
import com.sprotect.view.activity.CameraActivity;

import java.io.IOException;

import static com.googlecode.javacv.cpp.opencv_core.*;

/**
 * Process sign and Saves data to database
 */
public class ProcessSignActivity extends CameraActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.process_sign);

        ImageView signView = (ImageView) findViewById(R.id.signView);
        Bitmap sourceSign = getApp().getPhotoResult().getSourceSign();
        signView.setImageBitmap(sourceSign);
        EditText signIdText = (EditText) findViewById(R.id.signName);
        signIdText.setText(getApp().getSignId());
        signIdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                getApp().setSignId(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

        });

        Button saveSignButton = (Button) findViewById(R.id.saveSign);
        saveSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    /*
                    //for tests: save to file
                    String fileName = getApp().saveSignOnCard();

                    Toast.makeText(ProcessSignActivity.this, "Image saved to " + fileName, Toast.LENGTH_LONG).show();*/

                    //set UploadData
                    IplImage thresholdSign = fetchSignImage();

                    Toast.makeText(ProcessSignActivity.this,
                            getApp().saveSign(thresholdSign), Toast.LENGTH_LONG).show();


                } catch (IOException e) {
                    Toast.makeText(ProcessSignActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        Button checkSignButton = (Button) findViewById(R.id.checkSign);
        checkSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    IplImage thresholdSign = fetchSignImage();

                    String response = getApp().checkSign(thresholdSign);
                    showMessage(response);
                    /*Toast.makeText(ProcessSignActivity.this,
                            response, Toast.LENGTH_LONG).show();*/


                } catch (IOException e) {
                    Toast.makeText(ProcessSignActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

        if (!getResources().getBoolean(R.bool.debug)) {
            checkSignButton.performClick();
        }
    }

    private IplImage fetchSignImage() {
        IplImage thresholdSign = calcThresholdImage(getApp().getPhotoResult().getSourceSign());
        ImageView thresholdSignView = (ImageView) findViewById(R.id.thresholdSignView);
        thresholdSignView.setImageBitmap(JavaCvImageUtils.bgraImageToBitmap(JavaCvImageUtils.grayToBgraImage(thresholdSign)));

        if (getResources().getBoolean(R.bool.debug)) {
            TextView dataView = (TextView) findViewById(R.id.data);
            String data =
                    "thresholdSign width = " + thresholdSign.width() + "\n"
                    + "thresholdSign height = " + thresholdSign.height() + "\n";
           dataView.setText(data);
        }

        return thresholdSign;
    }

    private IplImage calcThresholdImage(Bitmap signBitmap) {
        float sparkleIncreaseCoefficient = 1;

        IplImage sourceImage = JavaCvImageUtils.bitmapToBgraImage(signBitmap);

        IplImage grayImage = cvCreateImage(cvGetSize(sourceImage), sourceImage.depth(), 1);
        IplImage thresholdImage = cvCreateImage(cvGetSize(sourceImage), sourceImage.depth(), 1);

        JavaCvImageUtils.createGrayImageBGRA(sourceImage, grayImage);
        JavaCvImageUtils.adaptiveThresholdImage(grayImage, thresholdImage);

        return thresholdImage;//JavaCvImageUtils.bgraImageToBitmap(JavaCvImageUtils.grayToBgraImage(thresholdImage));
    }

    private void showMessage(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage(message).show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        //textView.setTextSize(100);
    }
}
