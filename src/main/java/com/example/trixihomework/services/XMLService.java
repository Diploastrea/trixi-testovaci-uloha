package com.example.trixihomework.services;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public interface XMLService {
    void download(URL url, String destinationPath) throws IOException;
    void processFile(String path) throws IOException, XMLStreamException;
}
