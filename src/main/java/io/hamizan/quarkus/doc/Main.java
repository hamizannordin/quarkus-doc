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

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hamizan
 */
public class Main {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Inject
    private FileFinder fileFinder;
    @Inject
    private FileConverter fileConverter;

    public void start(@Observes StartupEvent ev){
        Scanner scanner = new Scanner(System.in);
        
        log.info("Please input folder directory:");
        String targetFolder = scanner.nextLine();
        
        log.info("Please input file type (ext):");
        String fileType = scanner.nextLine();
        
        List<File> fileList = 
                fileFinder.findMatchFileTypeInTargetFolder(
                        targetFolder, 
                        fileType);
        
        fileConverter.writeToDocx(fileList, targetFolder);
        log.info("Completed!");
        
        Quarkus.asyncExit();
    }
    
    public void exit(@Observes ShutdownEvent ev) {
        log.info("quarkus-doc by hamizan :D");
    }
}
