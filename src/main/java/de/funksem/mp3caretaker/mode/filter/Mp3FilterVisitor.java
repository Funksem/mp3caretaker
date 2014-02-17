package de.funksem.mp3caretaker.mode.filter;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import de.funksem.mp3caretaker.ConfigBean;
import de.funksem.mp3caretaker.Defines;
import de.funksem.mp3caretaker.utils.IOFiles;

public class Mp3FilterVisitor extends SimpleFileVisitor<Path>
{
    private final PathMatcher matcher;
    private final Mp3FilterWorker musicWorker;
    int totalFiles;
    int numOfMatches;
    int numOfFilterMatches;

    public Mp3FilterVisitor(ConfigBean configBean)
    {
        String pattern = IOFiles.createExtensionPattern(Defines.EXTENSION_MP3);
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        musicWorker = new Mp3FilterWorker(configBean);
    }

    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
    {
        ++totalFiles;

        Path name = file.getFileName();
        if ((name != null) && matcher.matches(name))
        {
            numOfMatches++;

            if (!musicWorker.run(file))
            {
                numOfFilterMatches++;
            }
        }
        return FileVisitResult.CONTINUE;
    }

    public int getTotal()
    {
        return totalFiles;
    }

    public int getMatches()
    {
        return numOfMatches;
    }

    public int getFilterMatches()
    {
        return numOfFilterMatches;
    }
}
