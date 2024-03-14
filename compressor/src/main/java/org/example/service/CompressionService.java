package org.example.service;

import java.io.File;
import java.io.IOException;

public interface CompressionService {
    void compressDirectory(File srcDir, File outDir) throws IOException;
}


