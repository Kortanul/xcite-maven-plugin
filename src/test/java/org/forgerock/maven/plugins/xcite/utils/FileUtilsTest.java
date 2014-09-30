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

import static org.assertj.core.api.Assertions.*;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("javadoc")
public class FileUtilsTest {

    @Test
    public void readTemporaryFile() throws IOException {
        ArrayList<String> content = new ArrayList<String>();
        content.add("Hello world");
        content.add("Hello again");

        File tempFile = File.createTempFile(getClass().getName(), ".txt");
        org.apache.commons.io.FileUtils.writeLines(tempFile, content);

        ArrayList<String> fileContent = FileUtils.getStrings(tempFile);
        assertThat(fileContent).isEqualTo(content);

        tempFile.deleteOnExit();
    }

    @Test
    public void checkIncludedFiles() throws IOException {
        File temporaryTxtFile = File.createTempFile("test", ".txt");
        File temporaryTmpFile = File.createTempFile("test", null);

        File baseDirectory = temporaryTmpFile.getParentFile();

        String[] includes = new String[1];
        includes[0] = "**/*.txt";

        String[] files = FileUtils.getIncludedFiles(baseDirectory, includes);

        assertThat(files).contains(temporaryTxtFile.getName());
        assertThat(files).doesNotContain(temporaryTmpFile.getName());

        temporaryTmpFile.deleteOnExit();
        temporaryTmpFile.deleteOnExit();
    }

    @Test
    public void nullIncludesMatchesAllFiles() throws IOException {
        File temporaryTxtFile = File.createTempFile("test", ".txt");
        File temporaryTmpFile = File.createTempFile("test", null);

        File baseDirectory = temporaryTmpFile.getParentFile();

        String[] files = FileUtils.getIncludedFiles(baseDirectory, null);

        assertThat(files).contains(temporaryTxtFile.getName());
        assertThat(files).contains(temporaryTmpFile.getName());

        temporaryTmpFile.deleteOnExit();
        temporaryTmpFile.deleteOnExit();
    }
}
