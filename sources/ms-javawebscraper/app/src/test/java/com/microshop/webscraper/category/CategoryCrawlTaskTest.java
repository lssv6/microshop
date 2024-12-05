package com.microshop.webscraper.category;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import org.junit.jupiter.api.Test;

public class CategoryCrawlTaskTest {
    @Test
    void shouldReturnACorrectURI() {
        URI page4URI = URI.create("https://www.kabum.com.br/seguranca/-fechaduras?page_number=4");
        URI page5URI = URI.create("https://www.kabum.com.br/seguranca/-fechaduras?page_number=5");

        assertEquals(page5URI, CategoryCrawlTask.nextPageURI(page4URI));
    }
}
