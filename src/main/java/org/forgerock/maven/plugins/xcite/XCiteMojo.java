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

package org.forgerock.maven.plugins.xcite;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.forgerock.maven.plugins.xcite.utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Copy quotes from source text files into target text files.
 */
@Mojo( name = "cite", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class XCiteMojo extends AbstractMojo {

    /**
     * Whether to escape XML characters in quotes.
     */
    @Parameter ( defaultValue = "false" )
    private boolean escapeXml;

    /**
     * Filter strings specifying files with citations to include.
     */
    @Parameter
    private String[] includes;

    /**
     * Indent quotes this number of spaces from the left margin.
     */
    @Parameter ( defaultValue = "0" )
    private int indent;

    /**
     * Whether to outdent quotes flush with the left margin.
     */
    @Parameter ( defaultValue = "false" )
    private boolean outdent;

    /**
     * Output directory for files with quotes.
     */
    @Parameter ( defaultValue = "${project.build.directory}/xcite" )
    private File outputDirectory;

    /**
     * Source directory for files with citations.
     */
    @Parameter ( defaultValue = "${basedir}/src/main" )
    private File sourceDirectory;

    /**
     * Replace citations with quotes in included files,
     * writing the resulting files in the output directory.
     *
     * @throws MojoExecutionException   Could not create output directory.
     * @throws MojoFailureException     Failed to perform replacements.
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (!outputDirectory.exists()) {
            if (!outputDirectory.mkdirs()) {
                throw new MojoExecutionException(
                        "Failed to create output directory: "
                                + outputDirectory.getPath());
            }
        }

        String[] files = FileUtils.getIncludedFiles(sourceDirectory, includes);
        Resolver resolver =
                new Resolver(outputDirectory, escapeXml, indent, outdent);
        try {
            resolver.resolve(sourceDirectory, files);
        } catch (IOException e) {
            throw new MojoFailureException(e.getMessage());
        }
    }
}
