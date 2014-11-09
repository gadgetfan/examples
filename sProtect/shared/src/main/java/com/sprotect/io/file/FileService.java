package com.sprotect.io.file;

import com.sprotect.model.entity.baseentity.BaseEntity;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

/**
 * service to work with files saved outside of DB
 */
public interface FileService {
    /**
     * saves file
     *
     * @param entity
     * @param fileName
     * @param inputStream
     */
    void saveStream(BaseEntity entity, String fileName, InputStream inputStream) throws IOException;

    /**
     * returns url string of saved file. Returns "" string if file doesn't exists
     *
     * @param entity
     * @param fileName
     * @return
     */
    //InputStream findStream(BaseEntity entity, String fileName) throws IOException;

    InputStream findStream(String entityName, Long id, String fileName) throws IOException;
}
