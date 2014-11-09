package com.sprotect.io.web;

import com.sprotect.SharedConstants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Uploads UploadData
 */
public class DataUploader {
    public static String uploadDataToServer(String servletUrl, UploadData uploadData)
            throws IOException {

        if (!checkUploadData(uploadData).isEmpty()) {
            return checkUploadData(uploadData);
        }

        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        HttpPost httpPost = new HttpPost(servletUrl);

        MultipartEntity entity = new MultipartEntity();

        TransferredData transferredData = uploadData.toTransferredData();

        /*File file = new File(fileName);
        ContentBody sourceSignContent = new FileBody(file, "image/png");
        entity.addPart(SharedConstants.HTTP_SOURCE_SIGN_PART_NAME, sourceSignContent);*/

        if (transferredData.getSignName() != null) {
            entity.addPart(SharedConstants.HTTP_SIGN_ID_PART_NAME, new StringBody(transferredData.getSignName()));
        }

        ContentBody sourceSignContent =
                new InputStreamBody(transferredData.getSourceSign(), "image/png", "sourceSignContent.png");
        entity.addPart(SharedConstants.HTTP_SOURCE_SIGN_PART_NAME, sourceSignContent);

        ContentBody thresholdSignContent =
                new InputStreamBody(transferredData.getThresholdSign(), "text/plain", "thresholdContent.txt");
        entity.addPart(SharedConstants.HTTP_THRESHOLD_SIGN_PART_NAME, thresholdSignContent);

        entity.addPart(SharedConstants.HTTP_PHOTO_METHOD_ID_PART_NAME, new StringBody(transferredData.getPhotoMethodId()));

        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity resEntity = response.getEntity();

        String responseResult = response.getStatusLine().toString();
        if (resEntity != null) {
            responseResult = EntityUtils.toString(resEntity);
        }
        if (resEntity != null) {
            resEntity.consumeContent();
        }

        httpClient.getConnectionManager().shutdown();

        return responseResult;
    }

    private static String checkUploadData(UploadData uploadData) {
        if (uploadData == null) {
            return "uploadData == null";
        } else {
            if (!uploadData.check().isEmpty()) {
                return uploadData.check();
            }
        }

        return "";
    }
}
