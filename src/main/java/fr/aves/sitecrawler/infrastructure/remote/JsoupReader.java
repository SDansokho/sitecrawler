package fr.aves.sitecrawler.infrastructure.remote;

import lombok.val;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JsoupReader implements Reader{

    public Document connectAndRead(String url) throws IOException {
        val doc = Jsoup.connect(url).get();
        return doc;
    }

    public List<Attribute> extractArretes(Document document, String targetElement) {
        val arretes = document.select(targetElement);
        return arretes.stream().map(arrete -> arrete.attribute("href")).toList();
    }


}
