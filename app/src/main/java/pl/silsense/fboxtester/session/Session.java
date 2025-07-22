package pl.silsense.fboxtester.session;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import java.io.Serializable;
import java.util.List;

import pl.silsense.fboxtester.log.LogEntry;

public interface Session extends Serializable {

    @NonNull
    String getName();

    @NonNull
    DocumentFile getFile();

    void log(LogEntry logEntry);

    @NonNull
    List<LogEntry> readAll();
}

/*
* TODO:
* Przenieść stringi z Session manager activity do stringów w zasobach.
* Ulepszyć widok w Main Menu.
* Zrobić fragmenty dla loggera:
* - menu wyboru elementu
* - menu z nazwami błędów
* - wybór miejsca na ścianie
* Podpiąć fragmenty do aktywności loggera.
* Sprawdzić czy logger działa.
* Zrobić aktywność statystyk z:
* - menu wybieranie sesji
* - widok statystyk
* + przycisk w menu głównym do statystyk.
* Dodać wysyłanie przez UDP.
* */