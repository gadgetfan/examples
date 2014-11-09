package com.sprotect.io.file;

import com.sprotect.model.entity.baseentity.BaseEntity;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Properties;

@Service
public class FileServiceImpl implements FileService {
    private static final String FILE_STORE_PATH;
    static { //TODO: class to load all properties is needed
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("/app.properties");
        try {
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        FILE_STORE_PATH = prop.getProperty("fileStorePath", "");
    }

    public FileServiceImpl() {
        init();
    }

    public void init() {
        createDirIfNotExists(FILE_STORE_PATH);
    }

    @Override
    public void saveStream(BaseEntity entity, String fileName, InputStream inputStream) throws IOException {
        if (isStoreFiles()) {
            if (entity != null && entity.getId() != null) {
                String absoluteFileName = getAbsoluteFileName(entity, fileName, true);
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(absoluteFileName);
                    IOUtils.copy(inputStream, fileOutputStream);
                } finally {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                }
            } else {
                throw new IOException("entity is null");
            }
        }
    }

    private String getAbsoluteFileName(BaseEntity entity, String fileName, boolean createDir) {
        StringBuilder absoluteFileName = new StringBuilder(FILE_STORE_PATH);
        absoluteFileName.append(entity.getClass().getSimpleName().toLowerCase());
        if (createDir) {
            createDirIfNotExists(absoluteFileName.toString());
        }

        absoluteFileName.append(File.separator);
        absoluteFileName.append(entity.getId());
        absoluteFileName.append(File.separator);
        if (createDir) {
            createDirIfNotExists(absoluteFileName.toString());
        }

        absoluteFileName.append(fileName);

        return absoluteFileName.toString();
    }

    /*@Override
    public InputStream findStream(BaseEntity entity, String fileName) throws IOException {
        if (entity != null && entity.getId() != null) {
            String absoluteFileName = getAbsoluteFileName(entity, fileName, false);

            return new FileInputStream(absoluteFileName);
        } else {
            throw new IOException("entity is null");
        }
    }*/

    @Override
    public InputStream findStream(String entityName, Long id, String fileName) throws IOException {
        if (isStoreFiles()) {
            String absoluteFileName = String.format("%s%s%s%d%s%s", FILE_STORE_PATH, entityName, File.separator, id, File.separator, fileName);
            return new FileInputStream(absoluteFileName);
        } else {
            return null;
        }
    }

    /**
     * creates one not created dir. Cannot create two or more consecutive dirs, that doesn't exists
     * @param dirString
     */
    private void createDirIfNotExists(String dirString) {
        if (isStoreFiles()) {
            File dir = new File(dirString);
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
    }

    public boolean isStoreFiles() {
        return !FILE_STORE_PATH.isEmpty();
    }

    /*public void init(ServletContext servletContext) {
        if (fileDir == null) {
            String contextPath = servletContext.getRealPath("/");
            //pathToOldFileDir = String.format("%sdb", contextPath);
            init(contextPath);
        }
    }*/

}
