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

import io.quarkus.test.junit.QuarkusTest;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 *
 * @author hamizan
 */
@QuarkusTest
public class FileFinderTest {
    
    @Test
    public void testFindMatchFileTypeInTargetFolderFailFolderNotFound (){
        String folder = "folderA";
        String fileType = "typeA";
        
        Folder mockFolder = Mockito.mock(Folder.class);
        Mockito.when(mockFolder.isFolderExist(folder)).thenReturn(false);
        FileFinder fileFinder = new FileFinder(mockFolder);
        
        Exception exception = assertThrows(RuntimeException.class, () -> 
                fileFinder.findMatchFileTypeInTargetFolder(folder, fileType));
        
        assertTrue(exception.getMessage().equals("Folder not found"));
    }
    
    @Test
    public void testFindMatchFileTypeInTargetFolderFailFileTypeNotFound (){
        String folder = "folderB";
        String fileType = "typeB";
        
        Folder mockFolder = Mockito.mock(Folder.class);
        Mockito.when(mockFolder.isFolderExist(folder)).thenReturn(true);
        Mockito.when(mockFolder.getFolder()).thenReturn(new File("/User"));
        FileFinder fileFinder = new FileFinder(mockFolder);
        
        Exception exception = assertThrows(RuntimeException.class, () -> 
                fileFinder.findMatchFileTypeInTargetFolder(folder, fileType));
        
        assertTrue(exception.getMessage().equals("File type not found"));
    }
    
    @Test
    public void testFindMatchFileTypeInTargetFolderSuccess (){
        String folder = "folderC";
        String fileType = "xml";
        
        Folder mockFolder = Mockito.mock(Folder.class);
        Mockito.when(mockFolder.isFolderExist(folder)).thenReturn(true);
        Mockito.when(mockFolder.getFolder()).thenReturn(new File(System.getProperty("user.dir")));
        FileFinder fileFinder = new FileFinder(mockFolder);
        
        List<File> fileList = fileFinder.findMatchFileTypeInTargetFolder(folder, fileType);
        
        assertTrue(!fileList.isEmpty());
    }
}
