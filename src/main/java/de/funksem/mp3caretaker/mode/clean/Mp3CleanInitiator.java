package de.funksem.mp3caretaker.mode.clean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mp3CleanInitiator
{
    private static final Logger LOG = LoggerFactory.getLogger(Mp3CleanInitiator.class);

    private Mp3CleanInitiator()
    {
    }

    public static void execute()
    {
        LOG.debug("Starting {}", Mp3CleanInitiator.class.getSimpleName());
        System.out.println("not implemented yet");
    }
}
