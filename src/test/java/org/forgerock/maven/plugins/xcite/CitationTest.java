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

import static org.assertj.core.api.Assertions.*;

import org.forgerock.maven.plugins.xcite.Citation;
import org.testng.annotations.Test;

@SuppressWarnings("javadoc")
public class CitationTest {

    @Test
    public void valueOfAndToString() {
        String valid = "[/path/to/script.sh:# start:# end]";
        Citation citation = Citation.valueOf(valid);
        assertThat(citation.toString()).isEqualTo(valid);
    }

    @Test
    public void missingBracketsFail() {
        String invalid = "Gobble dee gook";
        assertThat(Citation.valueOf(invalid)).isNull();
    }

    @Test
    public void missingPathFail() {
        String invalid = "[:Gobble dee gook]";
        assertThat(Citation.valueOf(invalid)).isNull();
    }

    @Test
    public void extraMarkerFail() {
        String invalid = "[/test:start:end:middle]";
        assertThat(Citation.valueOf(invalid)).isNull();
    }

    @Test
    public void nullStartDelimiter() {
        String nullStart = "[/test:]";    // Technically wrong, but tolerable.
        Citation citation = Citation.valueOf(nullStart);
        assertThat(citation.toString()).isEqualTo("[/test]");
    }

    @Test
    public void successiveDelimitersFail() {
        String invalid = "[/test::]";
        assertThat(Citation.valueOf(invalid)).isNull();
    }

    @Test
    public void differentDelimiter() {
        String valid = "[/path/to/script.sh%# start%# end]";
        Citation citation = Citation.valueOf(valid);
        assertThat(citation.toString()).isEqualTo(valid);
    }

    @Test
    public void pathConstructor() {
        Citation citation = new Citation("/test");
        assertThat(citation.toString()).isEqualTo("[/test]");
    }

    @Test
    public void pathDelimiterAndStartConstructor() {
        Citation citation = new Citation("/test", '%', "marker");
        assertThat(citation.toString()).isEqualTo("[/test%marker%marker]");
    }

    @Test
    public void checkWindowsAbsPath() {
        Citation citation = Citation.valueOf("[C:\\test%start%end]", "%");
        assertThat(citation.getPath()).isEqualTo("C:\\test");
    }
}
