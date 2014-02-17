package de.funksem.mp3caretaker.mode.filter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.funksem.mp3caretaker.ConfigBean;
import de.funksem.mp3caretaker.Defines;

public final class Mp3FilterInitiator
{
    private static final Logger LOG = LoggerFactory.getLogger(Mp3FilterInitiator.class);

    private Mp3FilterInitiator()
    {
    }

    public static void execute(String sourceDirectory, ConfigBean configBean)
    {
        LOG.debug("Starting {}", Mp3FilterInitiator.class.getSimpleName());

        long start = System.currentTimeMillis();

        Mp3FilterVisitor musicVisitor = new Mp3FilterVisitor(configBean);
        try
        {
            System.out.println(Defines.LINE_SEPARATOR
                + "Starte mit der Analyse, dies kann mehrere Minuten dauern .." + Defines.LINE_SEPARATOR);

            Files.walkFileTree(Paths.get(sourceDirectory), musicVisitor);

            long duration = System.currentTimeMillis() - start;

            System.out.println("=====================    ERGEBNIS    ======================");
            System.out.println("Anzahl aller Dateien          = " + musicVisitor.getTotal());
            System.out.println("Anzahl aller MP3 Dateien      = " + musicVisitor.getMatches());
            System.out.println("Anzahl gefilteter MP3 Dateien = " + musicVisitor.getFilterMatches());
            System.out.println("Anzahl korrekter MP3 Dateien  = "
                + (musicVisitor.getMatches() - musicVisitor.getFilterMatches()));
            System.out.println("Gesamtdauer         = " + DurationFormatUtils.formatDurationHMS(duration));
            System.out.println("Dauer pro MP3 Datei = " + (duration / musicVisitor.getMatches()) + " ms");
        }
        catch (NoSuchFileException exc)
        {
            LOG.error("Directory '{}' does not exist", sourceDirectory);
            System.out.println("Quellverzeichnis existiert nicht: " + sourceDirectory);
        }
        catch (IOException e)
        {
            LOG.error("Fehler waehrend des Verzeichnisdurchlaufs", e);
        }
    }
}
