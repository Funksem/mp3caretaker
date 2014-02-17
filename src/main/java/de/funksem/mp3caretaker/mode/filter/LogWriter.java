package de.funksem.mp3caretaker.mode.filter;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.funksem.mp3caretaker.utils.IOFiles;

public class LogWriter
{
    private static final Logger LOG = LoggerFactory.getLogger(LogWriter.class);

    public static final Logger LOGGER_RESULT_OK = LoggerFactory.getLogger("Result-Ok-Log");
    public static final Logger LOGGER_RESULT_NO_MP3 = LoggerFactory.getLogger("Result-Kein-Mp3-Log");
    public static final Logger LOGGER_RESULT_NO_ID3TAG = LoggerFactory.getLogger("Result-Kein-Id3Tag-Log");
    public static final Logger LOGGER_RESULT_UNCOMPLETE_ID3TAG = LoggerFactory
        .getLogger("Result-Unvollstaendig-Id3Tag-Log");
    public static final Logger LOGGER_RESULT_TECHNICAL_FAILURE = LoggerFactory
        .getLogger("Result-Technisch-Falsch-Log");

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String RESULT_FILE_BASE_DIR = "c:/temp";

    public static File createNewFile(String filename)
    {
        if (filename == null)
        {
            throw new IllegalArgumentException("filename could not be null");
        }
        return IOFiles.createNewFile(RESULT_FILE_BASE_DIR, filename);
    }

    public static void addFailureWithFile(String data)
    {
        LOGGER_RESULT_NO_MP3.info(data);
    }

    public static void addFilterNoTag(String data)
    {
        LOGGER_RESULT_NO_ID3TAG.info(data);
    }

    public static void addFilterMissingTags(String data)
    {
        LOGGER_RESULT_UNCOMPLETE_ID3TAG.info(data);
    }

    public static void addFilterBitrate(String data)
    {
        LOGGER_RESULT_TECHNICAL_FAILURE.info(data);
    }

    public static void addOk(String data)
    {
        LOGGER_RESULT_OK.info(data);
    }

    public static void addLine(File file, String data)
    {
        try
        {
            FileUtils.writeStringToFile(file, data + LINE_SEPARATOR, true);
        }
        catch (IOException e)
        {
            LOG.error("Writing string to file error", e);
        }
    }
}
