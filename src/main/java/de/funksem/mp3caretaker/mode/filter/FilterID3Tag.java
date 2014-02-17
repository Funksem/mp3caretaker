package de.funksem.mp3caretaker.mode.filter;

public class FilterID3Tag extends BaseFilter
{

    public FilterID3Tag()
    {
        super();
    }

    @Override
    public boolean passes()
    {
        boolean isId3Tag = (mp3File.hasId3v1Tag() || mp3File.hasId3v2Tag());

        if (!isId3Tag)
        {
            LogWriter.addFilterNoTag(mp3File.getFilename());
            incFilteredFiles();
        }

        return isId3Tag;
    }
}
