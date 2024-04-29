package fr.aves.sitecrawler.infrastructure.remote;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public interface Reader {
    Cloneable connectAndRead(String url) throws IOException;
    List<Attribute> extractArretes(Document document, String targetElement);
}
