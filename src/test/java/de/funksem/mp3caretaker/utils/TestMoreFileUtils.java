package de.funksem.mp3caretaker.utils;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test(enabled = true)
public class TestMoreFileUtils
{

    @Test
    public void isCorrectMp3Extension()
    {
        String[] testExtensions = new String[] { "mp3", "MP3", "mP3", "Mp3" };

        for (String extension : testExtensions)
        {
            assertTrue(IOFiles.isMp3Extension(extension));
        }
    }

    @Test
    public void isFailureMp3Extension()
    {
        String[] testExtensions = new String[] { "mmp3", "MP4", "xxx", "mpp3", "mpg4", "mpeg4", " mp3",
                "mp3 ", " mp3 " };

        for (String extension : testExtensions)
        {
            assertFalse(IOFiles.isMp3Extension(extension));
        }
    }
}
