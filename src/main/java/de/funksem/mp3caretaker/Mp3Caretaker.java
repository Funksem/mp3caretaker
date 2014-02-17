package de.funksem.mp3caretaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.funksem.mp3caretaker.mode.clean.Mp3CleanInitiator;
import de.funksem.mp3caretaker.mode.filter.Mp3FilterInitiator;
import de.funksem.mp3caretaker.mode.show.Mp3ShowInitiator;

public class Mp3Caretaker
{
    private static final Logger LOG = LoggerFactory.getLogger(Mp3Caretaker.class);

    private static Options options = new Options();

    //CHECKSTYLE:OFF
    static
    {
        options.addOption("h", "help", false, "Gibt Hilfe aus");
        options.addOption("c", "configfile", true, "Name der Konfigurationsdatei");
        options.addOption("s", "sourcedir", true, "Quellverzeichnis der MP3-Dateien");
    }

    // CHEKCSTYLE:ON

    /**
     * ++++++++++++++ M A I N ++++++++++++++
     */
    public static void main(String[] args)
    {
        LOG.info("\n=================================\nSTARTING MP3Caretaker\n=================================\n");

        showHeader();

        CommandLine cli = parseCommandLine(args);

        callRightMethod(cli);
    }

    private static CommandLine parseCommandLine(String[] args)
    {
        CommandLine cli = null;
        CommandLineParser parser = new PosixParser();
        try
        {
            cli = parser.parse(options, args);
        }
        catch (ParseException e)
        {
            LOG.error("Konnte Kommandozeile nicht parsen: {}", e.getMessage());
            System.exit(1);
        }

        if ((cli == null) || cli.hasOption('h') || checkCommandLine(cli))
        {
            showHelpAndExit();
        }
        return cli;
    }

    private static void callRightMethod(CommandLine cli)
    {
        Mode mode = getMode(cli);

        final String sourceDirectory = cli.getOptionValue('s');
        final ConfigBean configBean = readConfigBean(cli.getOptionValue('c'));

        LOG.info("Starting with mode: {}", mode);
        LOG.info("Source directory  : {}", sourceDirectory);
        ConfigReader.logConfiguration(configBean);

        switch (mode)
        {
            case CLEAN:
                Mp3CleanInitiator.execute();
            break;
            case FILTER:
                Mp3FilterInitiator.execute(sourceDirectory, configBean);
            break;
            case SHOW:
                Mp3ShowInitiator.execute();
            break;
            default:
                LOG.error("Mode wurde nicht erkannt");
            break;
        }
    }

    private static ConfigBean readConfigBean(String fileName)
    {
        String configFileName = fileName;
        if (StringUtils.isBlank(fileName))
        {
            configFileName = "./mp3caretaker.xml";
        }
        if (Files.isRegularFile(Paths.get(configFileName)))
        {
            return ConfigReader.readConfiguration(configFileName);
        }
        return null;
    }

    private static boolean checkCommandLine(CommandLine cli)
    {
        boolean error = false;

        Mode mode = getMode(cli);
        if (mode == null)
        {
            LOG.error("Es wurde kein Modus angegeben");
            error = true;
        }

        if (!cli.hasOption('c'))
        {
            LOG.debug("Es ist keine Konfigurationsdatei angegeben, starte mit Default-Datei");
        }

        if (!cli.hasOption('s'))
        {
            LOG.error("Es ist kein Quellverzeicnis angegeben");
            error = true;
        }

        return error;
    }

    private static Mode getMode(CommandLine cli)
    {
        String[] args = cli.getArgs();
        if (args.length > 0)
        {
            return Mode.fromString(args[0]);
        }
        return null;
    }

    private static void showHelpAndExit()
    {
        System.out.println();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("mp3caretaker <MODUS> <OPTIONS>" + Defines.LINE_SEPARATOR, options);
        System.out.println();
        System.exit(0);
    }

    private static void showHeader()
    {
        try
        {
            IOUtils.copy(Mp3Caretaker.class.getResourceAsStream("/header.txt"), System.out);
        }
        catch (IOException e)
        {
            LOG.warn("Programm Header kann nicht angezeigt werden", e);
        }
    }
}
