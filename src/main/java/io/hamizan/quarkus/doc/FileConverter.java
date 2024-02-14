/*
 * Copyright (C) 2024 hamizan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.hamizan.quarkus.doc;

import jakarta.enterprise.context.ApplicationScoped;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hamizan
 */
@ApplicationScoped
public class FileConverter {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final String outputPath = "/quarkus-doc/";
    private final int loadingBarLength = 60;
    private double loadingBarIncrement;
    private List<Future<Boolean>> futureList;
    
    public void writeToDocx (
            List<File> fileList, 
            String targetFolder
    ){
        if(!createTargetFolder(targetFolder + outputPath))
            throw new RuntimeException("Fail to create output directory");
        
        createFutureList(fileList, targetFolder);
        calculateLoadingBarIncrement(fileList.size());
        iterateFutureList();
    }

    public List<Future<Boolean>> getFutureList() {
        return futureList;
    }

    public double getLoadingBarIncrement() {
        return loadingBarIncrement;
    }
    
    private boolean createTargetFolder (String targetFolder){
        File f = new File(targetFolder);
        if(!f.isDirectory())
            f.mkdir();
        return f.exists();
    }
    
    private void createFutureList (
            List<File> fileList, 
            String targetFolder
    ){
        ExecutorService executor = Executors.newFixedThreadPool(fileList.size());
        futureList = new ArrayList<>();
        fileList.forEach(file -> {
            futureList.add(executor.submit(
                    new FileConverterRunnable(
                            file, 
                            targetFolder, 
                            outputPath)
            ));
        });
    }
    
    private void calculateLoadingBarIncrement (int fileListSize){
        loadingBarIncrement = loadingBarLength / (double) fileListSize;
    }
    
    private void iterateFutureList (){
        for(int i = 0; i < futureList.size(); i++){
            System.out.print(composeLoadingBar(i));
            try {
                // to demonstrate loading bar animation
                // remove for faster performance
                Thread.sleep(25);
                log.debug("status: {}", futureList.get(i).get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("error", e);
            }
        }
        // print next message in new line after loading bar completed
        System.out.println("");
    }
    
    private String composeLoadingBar (int counter){
        double finishDouble = (counter + 1) * loadingBarIncrement;
        double statusPercent = finishDouble / loadingBarLength * 100;
        int finishInteger = (int) Math.round(finishDouble);
        int remainingInteger = loadingBarLength - finishInteger;
        return "[" + "#".repeat(finishInteger)
                + "_".repeat(remainingInteger)
                + "] "
                + String.format("%.02f", statusPercent) + "%\r";
    }
}
