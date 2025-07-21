package pl.silsense.fboxtester.session;

import androidx.documentfile.provider.DocumentFile;

import java.io.Serializable;

public interface Session extends Serializable {

    String getName();

    DocumentFile getFile();
}

/*
* TODO:
* Przenieść stringi z Session manager activity do stringów w zasobach.
* Zmienić system nazw plików sesji, ustalić jak działa nazwa sesji.
* Ulepszyć widok w Main Menu.
* Przebudować LogEntry itd.
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