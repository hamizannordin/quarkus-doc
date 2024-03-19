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

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.concurrent.Callable;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author hamizan
 */
public class FileConverterRunnable implements Callable<Boolean> {
    
    private final File file;
    private final String parentFolder;
    private final String outputFolder;
    private final String outputFileType = ".docx";
    private final String replaceDirectorySymbol = "_";
    private final String windowsDirectorySymbol = "\\";
    private final String linuxDirectorySymbol = "/";
    private final String fontType = "Consolas";
    private final int fontSize = 9;
    private String exception;

    public FileConverterRunnable(
            File file, 
            String parentFolder,
            String outputFolder
    ){
        this.file = file;
        this.parentFolder = parentFolder;
        this.outputFolder = outputFolder;
    }
    
    @Override
    public Boolean call() {
        try {
            return composeDoc();
        } catch (Exception e) {
            this.exception = e.toString();
            return false;
        }
    }

    public String getException() {
        return exception;
    }

    private boolean composeDoc () throws Exception {
        
        try (XWPFDocument doc = new XWPFDocument()) {
            
            XWPFParagraph p1 = doc.createParagraph();
            p1.setAlignment(ParagraphAlignment.LEFT);

            XWPFRun r1 = p1.createRun();
            r1.setFontSize(fontSize);
            r1.setFontFamily(fontType);

            Scanner fileScanner = new Scanner(file);

            while(fileScanner.hasNextLine()){
                r1.setText(fileScanner.nextLine());
                r1.addBreak();
            }
            
            return writeToFolder(doc);
        }
    }
    
    private boolean writeToFolder (XWPFDocument doc) throws Exception {
        String nextPath = file.getPath().substring(
                parentFolder.length() + 1)
                .replace(windowsDirectorySymbol, replaceDirectorySymbol)
                .replace(linuxDirectorySymbol, replaceDirectorySymbol);
        try (FileOutputStream out =
                new FileOutputStream(
                        parentFolder 
                                + outputFolder 
                                + nextPath 
                                + outputFileType)){
            doc.write(out);
            return true;
        }
    }
}
