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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hamizan
 */
@ApplicationScoped
public class FileFinder {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Folder folder;

    public FileFinder(Folder folder) {
        this.folder = folder;
    }
    
    public List<File> findMatchFileTypeInTargetFolder (
            String targetFolder, 
            String fileType
    ){
        log.debug("processing: {}", targetFolder);
        
        if(!folder.isFolderExist(targetFolder))
            throw new RuntimeException("Folder not found");
        
        log.info("Searching...");
        
        List<File> fileList = new ArrayList<>();
        iterateFolder("." + fileType, folder.getFolder(), fileList);
        
        if(fileList.isEmpty())
            throw new RuntimeException("File type not found");
        
        log.info("File found: {}", fileList.size());
        
        return fileList;
    }
    
    private void iterateFolder (
            String fileType,
            File file, 
            List<File> fileList
    ){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                iterateFolder(fileType, f, fileList);
            }
        }
        if(file.isFile() && file.getName().endsWith(fileType)){
            fileList.add(file);
        }
    }
}
