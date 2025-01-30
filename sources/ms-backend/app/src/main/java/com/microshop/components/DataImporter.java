package com.microshop.components;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microshop.dto.dataimport.CategoryPageData;
import com.microshop.dto.dataimport.ProductPageData;
import com.microshop.service.DataImportationService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataImporter implements ApplicationRunner {
    public static Logger log = LoggerFactory.getLogger(DataImporter.class);

    private static final String OPTION_NAME = "initial_data_path";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DataImportationService dataImportationService;

    @Override
    public void run(ApplicationArguments args) {
        if (!args.containsOption(OPTION_NAME)) {
            log.warn("NO DATA FOLDER, CONTINUING AS A BACKEND.");
            return;
        }
        log.info("Data folder found!. Trying to fill database");
        List<String> options = args.getOptionValues(OPTION_NAME);
        String path = options.getFirst();

        File dumpPath = new File(path);

        File[] subfolders = dumpPath.listFiles();

        // Import all categories.
        Stream<File> firstPageFiles = Stream.of(subfolders).map(file -> file.listFiles()[0]);
        Stream<CategoryPageData> categoryPageDatas = getCategoryPageDataStream(firstPageFiles);

        categoryPageDatas.forEach(catPageData -> {
            dataImportationService.importCategoryData(catPageData);
        });

        // Import all products, manufacturers and selers.
        Stream<File> allFiles = Stream.of(subfolders).flatMap(file -> Stream.of(file.listFiles()));
        Stream<ProductPageData> productPageData = getCategoryPageDataStream(allFiles)
                .flatMap(catPageData -> (catPageData.getProducts() == null
                                || catPageData.getProducts().isEmpty())
                        ? Stream.empty()
                        : catPageData.getProducts().stream());
        productPageData.parallel().forEach(prodPageData -> dataImportationService.importProductData(prodPageData));

        log.info("Finished importing data!");
    }

    private Stream<CategoryPageData> getCategoryPageDataStream(Stream<File> pageFiles) {
        return pageFiles.map(file -> {
            try {
                return (CategoryPageData) objectMapper.readValue(file, CategoryPageData.class);
            } catch (DatabindException | StreamReadException exception) {
                log.error("Couldn't parse file=%s".formatted(file.toString()), exception);
            } catch (IOException ioException) {
                log.error("Couldn't parse file=%s".formatted(file.toString()), ioException);
            }
            return null;
        });
    }
}
