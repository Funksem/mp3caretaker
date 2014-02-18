package de.funksem.mp3caretaker.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public final class IOFiles
{

    private IOFiles()
    {
    }

    public static File createNewFile(String parent, String child)
    {
        File file = new File(parent, child);
        if (file.exists())
        {
            boolean success = file.delete();
            if (!success)
            {
                return null;
            }
        }
        return file;
    }

    public static String createExtensionPattern(String extension)
    {
        String pattern = "*.{";
        if (!StringUtils.isBlank(extension))
        {
            pattern += extension;
        }
        pattern += "}";
        return pattern;
    }

    public static String createExtensionPattern(Set<String> extensions)
    {
        String pattern = "*.{";
        boolean firstLoop = true;
        for (String extension : extensions)
        {
            if (!firstLoop)
            {
                pattern += ",";
            }
            else
            {
                firstLoop = false;
            }
            pattern += extension;
        }
        pattern += "}";
        return pattern;
    }

    public static Path moveWithReplace(String source, String dest, boolean extensionToLowerCase)
        throws IOException
    {
        String destFilename = dest;
        if (extensionToLowerCase)
        {
            destFilename = FilenameUtils.removeExtension(dest)
                + FilenameUtils.getExtension(dest).toLowerCase();
        }
        return Files.move(Paths.get(source), Paths.get(destFilename), StandardCopyOption.REPLACE_EXISTING);
    }
}
