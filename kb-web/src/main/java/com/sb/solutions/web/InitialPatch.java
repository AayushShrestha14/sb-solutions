package com.sb.solutions.web;

import java.io.File;
import java.net.URL;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

/**
 * @author Rujan Maharjan on 7/12/2019
 */

@Component
public final class InitialPatch {

    private static final String GENERALPATCHFOLDER = "/general_patch";

    private static final Logger logger = LoggerFactory.getLogger(InitialPatch.class);

    private InitialPatch() {
    }

    public static void inital(String baseServerFolder, DataSource dataSource) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(baseServerFolder.substring(1) + GENERALPATCHFOLDER);
        String path = url.getPath();

        File[] listOfFiles = new File(path).listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                ClassPathResource dataResource = new ClassPathResource(
                    baseServerFolder + GENERALPATCHFOLDER + File.separator + listOfFiles[i]
                        .getName());
                ResourceDatabasePopulator populator = new ResourceDatabasePopulator(dataResource);
                logger.info("executing query {}", listOfFiles[i].getName());
                populator.execute(dataSource);
            }
        }
    }

}
