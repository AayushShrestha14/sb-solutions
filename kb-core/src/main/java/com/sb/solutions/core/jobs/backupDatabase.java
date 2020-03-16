package com.sb.solutions.core.jobs;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sb.solutions.core.constant.FilePath;

/**
 * @author : Rujan Maharjan on  3/16/2020
 **/
@Component
public class backupDatabase {

    private static final Logger logger = LoggerFactory.getLogger(backupDatabase.class);

    @Autowired
    private DataSource dataSource;

    @Value("${db}")
    private String dbName;


    @Scheduled(fixedRate = 2000)
    public void backup() {
        String procName = "db_backup";
        StoredProcedure storedProcedure = new GenericStoredProcedure();
        storedProcedure.setDataSource(dataSource);
        storedProcedure.setSql(procName);
        storedProcedure.setFunction(false);

        SqlParameter[] sqlParameters = {

            new SqlParameter("filePathName", Types.VARCHAR),
            new SqlParameter("db", Types.VARCHAR),


        };
        String filePath = FilePath.getOSPath() + "backup";
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            new File(filePath).mkdirs();
        }
        String fileName = dbName + System.currentTimeMillis() + ".BAK";
        String filePathName = filePath + File.separator + fileName;
        logger.info("backup dir{}", filePathName);
        Map<String, Object> inp = new HashMap<>();
        inp.put("filePathName", filePathName);
        inp.put("db", dbName);
        try {
            storedProcedure.setParameters(sqlParameters);
            storedProcedure.compile();
            storedProcedure.execute(inp);
        } catch (Exception e) {
            logger.error("error while backup database{}", e.getMessage());
        }
    }

}
