package com.jdfy.uploadcsvfile.service;

import com.jdfy.uploadcsvfile.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class CsvFileSplit {
    private static final Logger logger = LoggerFactory.getLogger(CsvFileSplit.class);

    public  boolean splitFile() throws IOException {
        logger.info("Spliting started");
        try {
            int split;
            File folder = new File("./src/main/resources/inputdata");                                //*** Location of your file
            int filecount = 0;
            logger.info("file is " + folder.listFiles());
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    filecount++;
                }
            }
            logger.info("Total source file count is :" + filecount + "\n");
            String path = folder.getAbsolutePath();
            File[] listOfFiles = folder.listFiles();
            for (int l = 0; l < listOfFiles.length; l++) {
                if (listOfFiles[l].isFile()) {
                    logger.info("File name Is :" + listOfFiles[l].getName());
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(path + "/" + listOfFiles[l].getName()));   // Read a souce file
                    String input;
                    int count = 0;
                    while ((input = bufferedReader.readLine()) != null) {
                        count++;
                    }
                    logger.info("File total rows count is: " + count);
                    split = count / Constants.LINE_COUNT;

                    int n = split, z = 0;
                    if (n != z) {
                        logger.info("Each splitted file line count is :" + split + "\n");
                        FileInputStream fstream = new FileInputStream(path + "/" + listOfFiles[l].getName());
                        DataInputStream in = new DataInputStream(fstream);
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        String strLine;
                        File dir = new File(path + "/" + "csvfile");
                        dir.mkdir();
                        for (int j = 0; j <= split; j++) {

                            File filefolder = new File(path + "/" + "csvfile");
                            String folderpath = filefolder.getAbsolutePath();
                            String filNam = Constants.FILE_NAME;
                            String fileNameWithoutExt = filNam.substring(0, filNam.indexOf('.'));
                            FileWriter fstream1 = new FileWriter(folderpath + "/" + fileNameWithoutExt + "_" + j + ".csv");
                            BufferedWriter out = new BufferedWriter(fstream1);
                            for (int i = 1; i <= Constants.LINE_COUNT; i++) {
                                strLine = br.readLine();
                                if (strLine != null) {
                                    out.write(strLine);
                                    if (i != Constants.LINE_COUNT) {
                                        out.newLine();
                                    }
                                }
                            }
                            out.close();
                        }
                        in.close();
                    } else {
                        logger.info("\n********** Mentioned this file have below - " + Constants.LINE_COUNT + " rows   ***************  " + listOfFiles[l].getName() + " \n");
                    }
                }
            }
            logger.info("\n Splitted CSV files stored location is :" + path);
            return true;
        } catch (Exception e) {
            logger.info("Failed to split, Please check with admin " +e.getMessage());
            return false;
        }
    }
}
