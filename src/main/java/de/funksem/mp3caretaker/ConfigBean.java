package de.funksem.mp3caretaker;

import java.util.ArrayList;
import java.util.List;

public class ConfigBean
{
    private String filterId3TagActive;
    private String filterBitrateActive;
    private String filterMinimum;
    private String filterTagsAvailable;
    private List<String> mandatoryTags = new ArrayList<String>();

    public ConfigBean()
    {
    }

    public List<String> getMandatoryTags()
    {
        return mandatoryTags;
    }

    public void setMandatoryTags(List<String> mandatoryTags)
    {
        this.mandatoryTags = mandatoryTags;
    }

    public void addMandatoryTag(String mandatoryTag)
    {
        mandatoryTags.add(mandatoryTag);
    }

    public void setFilterId3TagActive(String active)
    {
        filterId3TagActive = active;
    }

    public String getFilterId3TagActive()
    {
        return filterId3TagActive;
    }

    public boolean isFilterId3TagActive()
    {
        return Boolean.parseBoolean(filterId3TagActive);
    }

    public void setFilterBitrate(String active, String minimum)
    {
        filterBitrateActive = active;
        filterMinimum = minimum;
    }

    public String getFilterBitrateActive()
    {
        return filterBitrateActive;
    }

    public boolean isFilterBitrateActive()
    {
        return Boolean.parseBoolean(filterBitrateActive);
    }

    public String getFilterBitrateMinimum()
    {
        return filterMinimum;
    }

    public void setFilterTagsAvailable(String active)
    {
        filterTagsAvailable = active;
    }

    public String getFilterTagsAvailableActive()
    {
        return filterTagsAvailable;
    }

    public boolean isFilterTagsAvailable()
    {
        return Boolean.parseBoolean(filterTagsAvailable);
    }

    @Override
    public String toString()
    {
        return "[ Mp3Caretaker - ConfigBean ]";
    }
}
