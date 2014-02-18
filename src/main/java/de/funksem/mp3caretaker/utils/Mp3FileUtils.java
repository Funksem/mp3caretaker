package de.funksem.mp3caretaker.utils;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class Mp3FileUtils
{
    public static final String EXTENSION_MP3 = "mp3";
    public static final String PATTERN_MP3 = "^[m|M][p|P]3?";

    public static boolean isMp3Extension(String extension)
    {
        return extension.matches(PATTERN_MP3);
    }

    public static Collection<File> getMp3Files(String path)
    {
        IOFileFilter mp3FileFilter = FileFilterUtils.suffixFileFilter(EXTENSION_MP3, IOCase.INSENSITIVE);
        return FileUtils.listFiles(new File(path), mp3FileFilter, TrueFileFilter.INSTANCE);
    }
}
