package com.example.trixihomework.services;

import java.net.URL;

public interface XMLService {
    void download(URL url, String destinationPath);
    void processFile(String path);
}
