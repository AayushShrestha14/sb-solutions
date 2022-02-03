package com.sb.solutions.core.jobs;

import com.sb.solutions.core.config.security.property.BackupProperties;
import com.sb.solutions.core.utils.file.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : Rujan Maharjan on  3/16/2020
 **/
@Component
@RequiredArgsConstructor
public class BackupDatabase {

    private static final Logger logger = LoggerFactory.getLogger(BackupDatabase.class);

    private final DataSource dataSource;

    private final String getOSPath;

    private final BackupProperties backupProperties;

    private static final String BACKUP_PROC_NAME = "db_backup";

    @Scheduled(cron = "0 0 20 * * ?")
    public void backup() {

        String procName = BACKUP_PROC_NAME;
        StoredProcedure storedProcedure = new GenericStoredProcedure();
        storedProcedure.setDataSource(dataSource);
        storedProcedure.setSql(procName);
        storedProcedure.setFunction(false);

        SqlParameter[] sqlParameters = {

                new SqlParameter("filePathName", Types.VARCHAR),
                new SqlParameter("db", Types.VARCHAR),


        };
        Path path = Paths.get(getBackupDir());
        if (!Files.exists(path)) {
            new File(getBackupDir()).mkdirs();
        }
        String fileName = this.databaseName() + System.currentTimeMillis() + ".BAK";
        String filePathName = getBackupDir() +"/"+ fileName;
        logger.info("backup dir {} ", filePathName);
        Map<String, Object> inp = new HashMap<>();
        inp.put("filePathName", filePathName);
        inp.put("db", this.databaseName());
        try {
            storedProcedure.setParameters(sqlParameters);
            storedProcedure.compile();
            storedProcedure.execute(inp);
        } catch (Exception e) {
            logger.error("error while backup database{}", e.getMessage());
        }
    }

    private String databaseName() {
        try {
            return dataSource.getConnection().getCatalog();
        } catch (SQLException r) {
            return null;
        }

    }

    //zip folder end of the month
    //@Scheduled(fixedRate = 10000)
    private void zipBackup() throws IOException {
        LocalDate localDate = LocalDate.now();
        String currentYearMonth =
                String.valueOf(localDate.getDayOfMonth()) + localDate.getMonth() + localDate.getYear();
        String destination =
                getBackupDir() + "/" + this.databaseName() + currentYearMonth + ".zip";
        FileUploadUtils.createZip(getBackupDir(), destination);
        logger.info("back up database succeed{}", currentYearMonth);
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void deletePreviousBackup() {
        ExecutorService executorService = getExecutorService();
        File rootFolder = new File(getBackupDir());
        if (rootFolder.exists()) {
            List<File> fileList = listFilesForDeletion(rootFolder);
            if (!fileList.isEmpty()) {
                fileList.forEach(file ->
                        executorService.submit(() ->
                                deleteBackupFile(file)));

            }
        } else {
            logger.info("folder does not exist {}", rootFolder);
        }

    }

    private List<File> listFilesForDeletion(final File folder) {
        List<File> fileList = new ArrayList<>();
        LocalDateTime cutOff = LocalDateTime.now().minusDays(backupProperties.getRollBack());
        logger.info("delete File before {} in folder {}", cutOff.toLocalDate(), folder);
        Instant instant = cutOff.atZone(ZoneId.systemDefault()).toInstant();
        long cutOffTimeInMS = instant.toEpochMilli();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForDeletion(fileEntry);
            } else {
                Path path = fileEntry.toPath();
                BasicFileAttributes attr;
                try {
                    attr = Files.readAttributes(path, BasicFileAttributes.class);

                    if (attr.creationTime().toMillis() < cutOffTimeInMS) {
                        fileList.add(fileEntry);
                    }


                } catch (IOException e) {
                    logger.error("unable to get  Date of File {}", e.getMessage());

                }

            }
        }

        return fileList;
    }

    private void deleteBackupFile(File file) {
        try {
            logger.info("deleting file {} created on {}", file.getAbsolutePath(), Files.getLastModifiedTime(file.toPath()));
            if (file.delete()) {
                logger.info("successFully deleted file");

            } else {
                logger.info("unable to delete file");
            }
        } catch (Exception e) {

        }
    }

    public ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(backupProperties.getThreadPool());
    }

    public String getBackupDir() {
        return getOSPath + "backup";
    }

}
