package com.selectyour.model.probe.recognition;

import com.selectyour.model.probe.Probe;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * recognition service implementation
 */
@Service
public final class RecognitionServiceImpl implements RecognitionService {
    static private final String STRING_DATA_DELIMITER = ";";

    private MarkerRecognitionService recognitionService;

    public RecognitionServiceImpl() {
        recognitionService = new MarkerRecognitionService();
    }

    public String recognizeWindowFrame(BufferedImage bufferedImage) {
        String result = Probe.NO_WINDOW_FRAME_DATA;

        if (bufferedImage != null) {
            double[] coordinates = recognitionService.recognizeMarker(bufferedImage);
            if (coordinates != null) {
                //TODO: arch Объединить с соответствующей функцией WindowFrameList.getDataString()
                StringBuilder data = new StringBuilder();
                for (int i = 0; i < coordinates.length / 2; ++i) {
                    data.append((float) (coordinates[i * 2] / bufferedImage.getWidth()));
                    data.append(STRING_DATA_DELIMITER);
                    data.append((float) (coordinates[(i * 2) + 1] / bufferedImage.getHeight()));
                    data.append(STRING_DATA_DELIMITER);
                }

                result = data.toString();
            }
        }

        return result;
    }

}
