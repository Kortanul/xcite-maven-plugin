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

// Verify that the processed source file has the expected result content.

def s = java.io.File.separator

def source = new File("target" + s + "it" + s + "xcite" + s + "target" + s + "xcite" + s + "source.txt")
def result = new File("target" + s + "it" + s + "xcite" + s + "resources" + s + "result.txt")

assert source.exists()
assert result.exists()
assert source.text.equals(result.text)
