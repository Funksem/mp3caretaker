package de.funksem.mp3caretaker.mode.filter;

import org.apache.commons.io.FilenameUtils;

import com.mpatric.mp3agic.Mp3File;

public abstract class BaseFilter
{
    public Mp3File mp3File;
    private int numOfFilteredFiles;

    public String getBaseName()
    {
        return FilenameUtils.getBaseName(mp3File.getFilename());
    }

    public void setMp3File(Mp3File mp3File)
    {
        this.mp3File = mp3File;
    }

    public void resetMp3File()
    {
        mp3File = null;
    }

    public int getNumOfFilteredFiles()
    {
        return numOfFilteredFiles;
    }

    public void incFilteredFiles()
    {
        numOfFilteredFiles++;
    }

    /**
     * @return <code>true</code> wenn Prüfung ok, sonst <code>false</code>
     */
    public abstract boolean passes();

    @Override
    public String toString()
    {
        return getClass().getSimpleName();
    }
}
