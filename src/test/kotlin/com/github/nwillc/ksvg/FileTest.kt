/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby
 * granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER
 * IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 * PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

import com.github.nwillc.ksvg.elements.SVG
import org.junit.jupiter.api.Test
import java.io.FileWriter

internal class FileTest {
    @Test
    internal fun testCirclesDiagonal() {
        val svg = SVG.svg(true) {
            height = "100"
            width = "100"
            defs {
                g {
                    id = "circle1"
                    circle {
                        r = "20"
                        fill = "blue"
                        strokeWidth = "2"
                        stroke = "red"
                    }
                }
            }
            for (i in 20..80 step 20)
                use {
                    x = i.toString()
                    y = i.toString()
                    href = "#circle1"
                }
        }

        FileWriter("build/tmp/circlesDiagonal.svg").use {
            svg.render(it, SVG.RenderMode.FILE)
        }
    }

    @Test
    internal fun testCodeMonkey() {
        val svg = SVG.svg(true) {
            height = "300"
            width = "300"
            style {
                body = """

                    .black-stroke { stroke: black; stroke-width: 2; }
                    .fur-color { fill: white; }

                """.trimIndent()
            }
            // Label
            text {
                x = "40"
                y = "50"
                body = "#CODE"
                fontFamily = "monospace"
                fontSize = "40px"
            }
            // Ears
            g {
                id = "ear"
                circle {
                    cssClass = "black-stroke fur-color"
                    cx = "100"
                    cy = "100"
                    r = "40"
                }
                circle {
                    cssClass = "black-stroke fur-color"
                    cx = "100"
                    cy = "100"
                    r = "28"
                }
            }
            use {
                x = "140"
                y = "-30"
                href = "#ear"
            }
            // Face
            circle {
                cssClass = "black-stroke"
                id = "face"
                cx = "180"
                cy = "140"
                r = "80"
                fill = "#aa450f"
            }
            // Eyes
            circle {
                cssClass = "black-stroke fur-color"
                id = "eye"
                cx = "160"
                cy = "95"
                r = "20"
            }
            use {
                x = "45"
                y = "-5"
                href = "#eye"
            }
            // Muzzle
            circle {
                cssClass = "black-stroke fur-color"
                cx = "195"
                cy = "178"
                r = "65"
            }
            // Nostrils
            circle {
                id = "nostril"
                cx = "178"
                cy = "138"
                r = "4"
                fill = "black"
            }
            use {
                x = "35"
                y = "-5"
                href = "#nostril"
            }
            // Mouth
            path {
                cssClass = "black-stroke"
                d = "M 150 150 C 100,250 305,260 230,140 C 205,190 165,170 150,150 Z"
                fill = "red"
            }
        }

        FileWriter("build/tmp/codeMonkey.svg").use {
            svg.render(it, SVG.RenderMode.FILE)
        }
    }
}
