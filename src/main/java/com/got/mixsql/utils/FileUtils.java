package com.got.mixsql.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static String readFromFile(String filename, String encoding) {
        try {
            InputStream in = new FileInputStream(filename);
            return readFromStream(in, encoding);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFromFile(String filename) {
        try {
            InputStream in = new FileInputStream(filename);
            return readFromStream(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFromClassPathResource(String resource, String encoding) {
        InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(resource);
        return readFromStream(in, encoding);
    }

    public static String readFromClassPathResource(String resource) {
        InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(resource);
        return readFromStream(in);
    }

    public static String readFromStream(InputStream in) {
        return readFromStream(in, "utf-8");
    }

    public static String readFromStream(InputStream in, String encoding) {
        try {
            byte[] b = new byte[in.available()];
            in.read(b);
            String text = new String(b, 0, b.length, encoding);
            return text;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveToFile(String filename, String text, String encoding) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(createFile(filename));
            byte[] b = text.getBytes(encoding);
            out.write(b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception ee) {

                }
            }
        }
    }

    public static List<String> allsubFiles(String path, String[] subfix) {
        List<String> files = new ArrayList<String>();
        searchSubFile(new File(path), subfix, files);
        return files;
    }

    private static void searchSubFile(File file, String[] subfix, List<String> files) {
        if (file.isFile()) {
            String filename = file.getAbsolutePath();
            if (subfix == null || subfix.length == 0) {
                files.add(filename);
                return;
            }
            String filesubfix = "";
            int index = filename.lastIndexOf('.');
            if (index != -1) {
                filesubfix = filename.substring(index + 1);
            }
            for (String sf : subfix) {
                if (filesubfix.equals(sf)) {
                    files.add(filename);
                }
            }
            return;
        }
        String[] names = file.list();
        if (names != null) {
            for (String name : names) {
                File subFile = new File(file.getAbsolutePath() + File.separatorChar + name);
                searchSubFile(subFile, subfix, files);
            }
        }
    }

    public static File createFile(String filename) {
        return createFile(filename, false);
    }

    public static File createDir(String filename) {
        return createFile(filename, true);
    }

    private static File createFile(String filename, boolean isDir) {
        File file = new File(filename);
        if (file.exists()) {
            return file;
        }
        createFile(file.getParent(), true);
        if (isDir) {
            file.mkdir();
        } else {
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }

    public static void copyFile(String from, String to) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(from);
            out = new FileOutputStream(createFile(to));
            byte[] buffer = new byte[8096];
            int read = -1;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {

            }
        }
    }

    public static void copy(String from, String to) {
        File fromFile = new File(from);
        if (fromFile.isFile()) {
            copyFile(from, to);
            return;
        }
        String[] subPaths = fromFile.list();
        if (subPaths == null || subPaths.length == 0) {
            return;
        }
        for (String subPath : subPaths) {
            String subfrom = from + File.separatorChar + subPath;
            String subto = to + File.separatorChar + subPath;
            copy(subfrom, subto);
        }
    }

}
