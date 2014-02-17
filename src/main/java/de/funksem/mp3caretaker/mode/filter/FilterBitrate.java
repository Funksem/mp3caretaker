package de.funksem.mp3caretaker.mode.filter;

public class FilterBitrate extends BaseFilter
{
    private final int minBitrate;

    public FilterBitrate(String bitrateMinimum)
    {
        super();
        minBitrate = new Integer(bitrateMinimum);
    }

    @Override
    public boolean passes()
    {
        boolean isBitrateOk = (mp3File.getBitrate() >= minBitrate);
        if (!isBitrateOk)
        {
            LogWriter.addFilterBitrate(mp3File.getFilename() + " - Bitrate = "
                + mp3File.getBitrate() + " kbps ist kleiner als " + minBitrate + " kbps");
            incFilteredFiles();
        }
        return isBitrateOk;
    }
}
