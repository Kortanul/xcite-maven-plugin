/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * If applicable, add the following below this MPL 2.0 HEADER, replacing
 * the fields enclosed by brackets "[]" replaced with your own identifying
 * information:
 *     Portions Copyright [yyyy] [name of copyright owner]
 *
 *     Copyright 2014 ForgeRock AS
 *
 */

package org.forgerock.maven.plugins.xcite.utils;

import org.codehaus.plexus.util.DirectoryScanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Utility methods for handling files.
 */
public final class FileUtils {

    /**
     * Get the content of a file as an array of Strings.
     *
     * @param file          File containing the strings.
     * @return              The array of strings contained in the file.
     * @throws IOException  Failed to read the file.
     */
    public static ArrayList<String> getStrings(final File file)
            throws IOException {

        ArrayList<String> lines = new ArrayList<String>();
        if (file == null) {
            return lines;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();

        return lines;
    }

    /**
     * A subset of formats described in the documentation for the <a
     * href="http://www.docbook.org/tdg/en/html/imagedata.html">ImageData</a>
     * element.
     */
    private static String[] IMAGE_FILES = {"**/*.bmp",
            "**/*.eps",
            "**/*.gif",
            "**/*.jpeg",
            "**/*.jpg",
            "**/*.png",
            "**/*.svg",
            "**/*.tiff"};

    /**
     * Get a list of relative file paths based on inclusion patterns.
     *
     * @param baseDirectory     Where to look for files to include.
     * @param includes          Patterns specifying files to include.
     *                          If null, match all files
     *                          except files excluded by default.
     * @return                  Relative paths to files.
     */
    public static String[] getIncludedFiles(File baseDirectory, String[] includes) {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(baseDirectory);
        scanner.setIncludes(includes);

        // Exclude both image files and the defaults.
        String[] excludes = new String[IMAGE_FILES.length
                + DirectoryScanner.DEFAULTEXCLUDES.length];
        System.arraycopy(IMAGE_FILES, 0, excludes, 0, IMAGE_FILES.length);
        System.arraycopy(DirectoryScanner.DEFAULTEXCLUDES, 0,
                excludes, IMAGE_FILES.length, DirectoryScanner.DEFAULTEXCLUDES.length);
        scanner.setExcludes(excludes);

        scanner.scan();
        return scanner.getIncludedFiles();
    }
}
