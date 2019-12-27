package org.firstinspires.ftc.teamcode.JNI;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JNILoader {
    private final String libraryName;
    private boolean loaded;
    private List<String> testPaths;

    public JNILoader(String libraryName) {
        this.libraryName = libraryName;
        this.testPaths = new ArrayList();
        this.testPaths.add(getPlatformString() + "/" + this.getSharedLibraryFile());
    }

    public void load() {
        if (!this.loaded) {
            this.doLoad();
            this.loaded = true;
        }

    }

    public void addPath(String path) {
        this.testPaths.add(path);
    }

    public static String getPlatformString() {
        return getNormalizedOS() + getNormalizedArchitecture();
    }

    public String getSharedLibraryFile() {
        String os = getNormalizedOS();
        String prefix = os.equals("windows") ? "" : "lib";
        String suffix = os.equals("windows") ? ".dll" : (os.equals("osx") ? ".dylib" : ".so");
        return prefix + this.libraryName + suffix;
    }

    public static String getNormalizedOS() {
        String os = System.getProperty("os.name");
        if (os.startsWith("Win")) {
            return "windows";
        } else if (os.toLowerCase().contains("mac")) {
            return "osx";
        } else {
            return os.toLowerCase().contains("linux") ? "linux" : os.toLowerCase();
        }
    }

    public static String getNormalizedArchitecture() {
        String arch = System.getProperty("os.arch");
        if (!arch.equals("amd64") && !arch.equals("x86_64")) {
            return !arch.equals("i386") && !arch.equals("x86") ? arch.toLowerCase() : "x86";
        } else {
            return "x86-64";
        }
    }

    private void doLoad() {
        try {
            System.loadLibrary(this.libraryName);
        } catch (UnsatisfiedLinkError var9) {
            boolean trying = true;
            Iterator var3 = this.testPaths.iterator();

            while(var3.hasNext()) {
                String path = (String)var3.next();

                try {
                    this.tryLoadRelative(path);
                    trying = false;
                } catch (UnsatisfiedLinkError var8) {
                    try {
                        this.tryLoadJarResource(path);
                        trying = false;
                    } catch (UnsatisfiedLinkError | IOException var7) {
                    }
                }
            }

            if (trying) {
                throw new UnsatisfiedLinkError(this.libraryName + " could not be found at any location!");
            }
        }

    }

    public void tryLoadRelative(String path) throws UnsatisfiedLinkError {
        System.load((new File(path)).getAbsolutePath());
    }

    public void tryLoadJarResource(String path) throws IOException, UnsatisfiedLinkError {
        File f = this.extractJarResource(path);

        try {
            System.load(f.getAbsolutePath());
        } finally {
            if (f.exists()) {
                f.delete();
            }

        }

    }

    private File extractJarResource(String path) throws IOException {
        File output = File.createTempFile("jni_" + this.libraryName, this.getSharedLibraryFile());
        output.deleteOnExit();
        InputStream is = JNILoader.class.getResourceAsStream("/" + path);
        if (is == null) {
            throw new IOException("Resource /" + path + " does not exist!");
        } else {
            OutputStream os = new FileOutputStream(output);
            byte[] buffer = new byte[2048];

            int readBytes;
            try {
                while((readBytes = is.read(buffer)) != -1) {
                    os.write(buffer, 0, readBytes);
                }
            } finally {
                os.close();
                is.close();
            }

            return output;
        }
    }
}
