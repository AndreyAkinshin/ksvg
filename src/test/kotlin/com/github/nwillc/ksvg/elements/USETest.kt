/*
 * Copyright 2019 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL
 * WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL
 * THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR
 * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

package com.github.nwillc.ksvg.elements

import com.github.nwillc.ksvg.getTestLogger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.org.lidalia.slf4jtest.LoggingEvent.warn
import uk.org.lidalia.slf4jtest.TestLoggerFactory
import java.util.Arrays.asList

internal class USETest : HasSvg(true) {
    private var logger = getTestLogger<USE>()

    @BeforeEach
    fun setup() {
        TestLoggerFactory.clear()
    }

    @Test
    fun `generate a warning about safari on use tag with validation true`() {
        svg.validation = true
        svg.use {}
        assertThat(logger.loggingEvents).containsAll(
            asList(warn("The use tags href has compatibility issues with Safari.")))
    }

    @Test
    fun `not generate a warning for use when validation false`() {
        svg.validation = false
        svg.use {}
        assertThat(logger.loggingEvents).isEmpty()
    }
}
