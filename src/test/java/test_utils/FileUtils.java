package test_utils;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Clara on 8/2/17.
 * Collection of utility methods for working with files
 */

public class FileUtils {
    
    // DO NOT do anything destructive.
    // Just in case student modifies test and deletes some other file on their file system.
    // (Expected worst case is that a file will be moved unexpectedly).
    
    private static String tempDirectoryName = "temporary_directory_for_test_files_verify_no_important_files_are_here_then_you_may_delete";
    
    public static void ensureTempExists() throws IOException {
        
        // Create temp directory to contain test files, if it does not exist.
        
        try {
            
            // Does the directory exist? If so, do nothing.
            File f = new File(tempDirectoryName);
            
            if (f.exists()) {
                // must have created it already.
                return;
            }
            
            else {
                java.nio.file.Files.createDirectory(new File(tempDirectoryName).toPath());
            }
            
        } catch (IOException e) {
            // Error creating directory.
            System.err.println("Unable to create directory to store temporary files. PLEASE REPORT THIS ERROR TO CLARA");
            throw e;
        }
        
        
        
    }
    
    
    public static void moveToTemporaryTestFolder(String filename) {
        
        File file = new File(filename);
        File tempDir = new File(tempDirectoryName, filename);
        
        try {
            ensureTempExists();
            Files.move(file, tempDir);
        } catch (IOException e) {
            System.out.println("Tried to move a temporary file to the temporary directory: " + tempDirectoryName +
                    "\n but an error occurred: " + e.getMessage() +
                    "\n If this file exists, you may delete it.");
        }
    }
    
    
    
    /** Create a unique filename for temporary test files.
     * If prefix is null, it will be of the format
     * temp_testing_file_you_may_delete_2dcf5b24-d2de-4e01-8ce4-d12c398cbbbe.txt
     * or, if prefix = "PREFIX"
     * temp_testing_file_you_may_delete_PREFIX_2dcf5b24-d2de-4e01-8ce4-d12c398cbbbe.txt*/
    public static String uniqueFilename(String prefix) {
        
        String tempMsg = "temp_testing_file_you_may_delete";
        String tempPrefix = (prefix == null) ? tempMsg : tempMsg + "_" + prefix;
        return tempPrefix + "_" + UUID.randomUUID().toString() + ".data";
    }
    
    
    
    
}
