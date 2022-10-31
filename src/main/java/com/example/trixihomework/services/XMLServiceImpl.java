package com.example.trixihomework.services;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Service
public class XMLServiceImpl implements XMLService {

    @Override
    public void download(URL url, String destinationPath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(destinationPath)) {
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
