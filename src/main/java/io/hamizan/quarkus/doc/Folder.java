/*
 * Copyright (C) 2024 C00311
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

/**
 *
 * @author hamizan
 */
@ApplicationScoped
public class Folder {

    private File file;
    
    public File getFolder (){
        return this.file;
    }
    
    public boolean isFolderExist (String targetFolder){
        createFile(targetFolder);
        return file.exists();
    }
    
    private void createFile (String file){
        this.file = new File(file);
    }
}
