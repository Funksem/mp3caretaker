package de.funksem.mp3caretaker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.digester3.Digester;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class ConfigReader
{
    private static final Logger LOG = LoggerFactory.getLogger(ConfigReader.class);
    private static final String DEFAULT_CONFIG_FILENAME = "mp3caretaker.xml";

    public static void main(String[] args)
    {
        readConfiguration();
    }

    public static ConfigBean readConfiguration()
    {
        return readConfiguration(DEFAULT_CONFIG_FILENAME);
    }

    public static ConfigBean readConfiguration(String fileName)
    {
        if (StringUtils.isBlank(fileName))
        {
            fileName = DEFAULT_CONFIG_FILENAME;
        }

        Digester digester = new Digester();
        digester.setValidating(false);
        addRules(digester);

        try (FileInputStream is = new FileInputStream(fileName))
        {
            return (ConfigBean) digester.parse(is);
        }
        catch (FileNotFoundException ex)
        {
            LOG.error("File '{}' does not exist", fileName);
        }
        catch (IOException | SAXException exc)
        {
            LOG.error("Error during reading config file", exc);
        }
        return null;
    }

    private static void addRules(Digester digester)
    {
        digester.addObjectCreate("musicfilter", ConfigBean.class);
        digester.addCallMethod("musicfilter/filterId3Tag", "setFilterId3TagActive", 1);
        digester.addCallParam("musicfilter/filterId3Tag/active", 0);
        digester.addCallMethod("musicfilter/filterBitrate", "setFilterBitrate", 2);
        digester.addCallParam("musicfilter/filterBitrate/active", 0);
        digester.addCallParam("musicfilter/filterBitrate/minimum", 1);
        digester.addCallMethod("musicfilter/filterTagsAvailable", "setFilterTagsAvailable", 1);
        digester.addCallParam("musicfilter/filterTagsAvailable/active", 0);
        digester.addCallMethod("musicfilter/filterTagsAvailable/mandatoryTags/mandatoryTag",
            "addMandatoryTag",
            1);
        digester.addCallParam("musicfilter/filterTagsAvailable/mandatoryTags/mandatoryTag", 0);
    }

    public static void logConfiguration(ConfigBean configBean)
    {
        if (configBean != null)
        {
            LOG.info("ConfigBean - Filter Id3-Tag active    = {}", configBean.getFilterId3TagActive());
            LOG.info("ConfigBean - Filter Bitrate active    = {}", configBean.getFilterBitrateActive());
            LOG.info("ConfigBean - Filter Tag-Entry active  = {}", configBean.getFilterTagsAvailableActive());
            LOG.info("ConfigBean - Bitrate minimum          = {} kbit/s",
                configBean.getFilterBitrateMinimum());
            LOG.info("ConfigBean - Name of tag entries      = {}", configBean.getMandatoryTags());
        }
    }
}
