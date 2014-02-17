package de.funksem.mp3caretaker.mode.filter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import de.funksem.mp3caretaker.ConfigBean;

public class Mp3FilterWorker
{
    private static final Logger LOG = LoggerFactory.getLogger(Mp3FilterWorker.class);

    String fileName;
    List<BaseFilter> filters = new ArrayList<BaseFilter>();
    ConfigBean conf;

    public Mp3FilterWorker(ConfigBean configBean)
    {
        super();
        conf = configBean;
        initFilter();
    }

    /**
     * @param file MP3 file
     * @return <code>true</code> wenn MP3 Datei alle Filter fehlerfrei durchlaufen hat, sonst
     *         <code>false</code>
     */
    public boolean run(Path file)
    {
        fileName = file.toString();

        try
        {
            final Mp3File mp3File = new Mp3File(fileName);

            LOG.info("Processing file \"{}\"", mp3File.getFilename());

            for (BaseFilter mFilter : filters)
            {
                mFilter.setMp3File(mp3File);

                if (!mFilter.passes())
                {
                    return false;
                }
            }
        }
        catch (UnsupportedTagException | InvalidDataException | IOException exc)
        {
            LogWriter.addFailureWithFile(fileName + " - " + exc.getMessage());
            return false;
        }

        // .. alle Filter durchlaufen, ab hier alles ok.

        LogWriter.addOk(fileName);
        return true;
    }

    public void initFilter()
    {
        if (conf.isFilterId3TagActive())
        {
            filters.add(new FilterID3Tag());
        }
        if (conf.isFilterBitrateActive())
        {
            filters.add(new FilterBitrate(conf.getFilterBitrateMinimum()));
        }
        if (conf.isFilterTagsAvailable())
        {
            filters.add(new FilterMissingTagEntries(conf.getMandatoryTags()));
        }
        LOG.info("Added filter(s): {}", filters);
    }
}
