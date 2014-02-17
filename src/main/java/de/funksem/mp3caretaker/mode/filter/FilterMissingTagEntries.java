package de.funksem.mp3caretaker.mode.filter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;

public class FilterMissingTagEntries extends BaseFilter
{
    private static final Logger LOG = LoggerFactory.getLogger(FilterMissingTagEntries.class);

    private final List<String> tagEntries;

    public FilterMissingTagEntries(List<String> mandatoryTags)
    {
        super();
        tagEntries = new ArrayList<String>(mandatoryTags);
    }

    @Override
    public boolean passes()
    {
        boolean isPassed = true;
        String errorLogEntry = mp3File.getFilename() + " - Missing Tags = ";

        ID3v1 id3v1Tag = mp3File.getId3v1Tag();
        ID3v2 id3v2Tag = mp3File.getId3v2Tag();

        for (String entry : tagEntries)
        {
            if (!(hasTag(id3v1Tag, entry) || hasTag(id3v2Tag, entry)))
            {
                errorLogEntry += (" " + entry + " ");
                isPassed = false;
            }
        }

        if (!isPassed)
        {
            LogWriter.addFilterMissingTags(errorLogEntry);
            incFilteredFiles();
        }

        return isPassed;
    }

    private boolean hasTag(ID3v1 id3tag, String getterType)
    {
        if ((id3tag == null) || StringUtils.isBlank(getterType))
        {
            return false;
        }

        String entry = getValueByMethodName(id3tag, getterType);

        return !StringUtils.isBlank(entry);
    }

    private String getValueByMethodName(ID3v1 id3tag, String getterType)
    {
        String entry = null;
        try
        {
            Method invokerMethod = id3tag.getClass().getMethod("get" + getterType, null);

            if (invokerMethod != null)
            {
                entry = (String) invokerMethod.invoke(id3tag);
            }
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e)
        {
            LOG.error("", e);
            entry = null;
        }
        return entry;
    }

}
