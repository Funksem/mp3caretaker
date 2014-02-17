package de.funksem.mp3caretaker;

enum Mode
{
    CLEAN, FILTER, SHOW;

    // CHECKSTYLE:OFF
    // Viele Return Anweisungen sind hier ok 
    static Mode fromString(String string)
    {
        switch (string.toLowerCase())
        {
            case "clean":
                return CLEAN;
            case "filter":
                return FILTER;
            case "show":
                return SHOW;
            default:
                return null;
        }
    }
    // CHECKSTYLE:ON
}
