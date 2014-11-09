package com.sprotect.io.web;

import java.io.IOException;

public interface DownloadedDataService {
    DownloadedData create(TransferredData transferredData) throws IOException;
}
