# Kotlin SVG DSL

I had a [Kotlin](https://kotlinlang.org/) microservice that needed to produce a simple heatmap chart on an HTML page to
a large audience very rapidly. This was an ad hoc DevOps requirement, so I didn't want to take on an involved Javascript
solution. Instead, I went with spitting out a half page of [inline SVG](https://www.w3schools.com/html/html5_svg.asp)
text generated by the server itself. This DSL made the server code trivial and has easily met the performance needs.

## Example

```kotlin

fun codeMonkey() {
    val svg = SVG.svg(true) {
         height = "300"
         width = "300"
         style {
             body = """

                 svg .black-stroke { stroke: black; stroke-width: 2; }
                 svg .fur-color { fill: white; }

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
         // Ears - example using a function because USE tag doesn't work in Safari
         ear(100, 100)
         ear(240, 70)
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
    
    private fun Container.ear(x: Int, y: Int) {
        circle {
            cssClass = "black-stroke fur-color"
            cx = x.toString()
            cy = y.toString()
            r = "40"
        }
        circle {
            cssClass = "black-stroke fur-color"
            cx = x.toString()
            cy = y.toString()
            r = "28"
        }
    }
    
```
Yields a code monkey:
![code monkey](./docs/images/codeMonkey.svg)
## Validation
Originally the attribute values were *appropriate* types, but eventually as support for percentage values etc. was 
added, and considering they are always represented as strings in SVG, the value type was changed to String. But that 
made errors like `length = "foo"` possible.  Therefore, attribute validation was added. Over time other validations
became apparent too. With validation on, while the SVG is created, things are checked, for example when an attribute 
string is assigned, if it can be properly validated, it is. Validation can be turned off for performance reasons. 

## About Inline vs File SVG
SVG is an XML tag based format. Those tags can be put into an `.svg` file, or in modern browsers 
appear directly inline in the HTML5. However some attributes and other details differ slightly between these modes. This
DSL is biased toward the inline representation because that's its origin, but it supports indicating the rendering mode
and in the limitted scenarios tested it works.

## A Limited Set of Elements
Currently only a small set of SVG Elements are supported. Adding more is straight forward, I just met my own needs, and 
so additions can be done by others, or as my needs increase.

## As Compared To kotlinx.html
Why did I write yet another SVG DSL when SVG is covered by the [kotlinx.html](https://github.com/Kotlin/kotlinx.html)?
Let's just say ... you try using it. I could not figure out how to use it based on the SVG specification. This package
is as close to a one to one mapping as I could make it.  So what if you want to combine them? Not a problem, just use
`unsafe/raw`:

```kotlin

 val svg = SVG.svg {
   // ....
 }
 System.out.appendHTML().html {
     body {
        unsafe {
            raw(svg.toString())
        }
     }
 }

```

## See Also

- [License](LICENSE.md)
- [Javadoc](https://nwillc.github.io/ksvg/javadoc)

-----
[![Coverage](https://codecov.io/gh/nwillc/ksvg/branch/master/graphs/badge.svg?branch=master)](https://codecov.io/gh/nwillc/ksvg)
[![license](https://img.shields.io/github/license/nwillc/ksvg.svg)](https://tldrlegal.com/license/-isc-license)
[![Travis](https://img.shields.io/travis/nwillc/ksvg.svg)](https://travis-ci.org/nwillc/ksvg)
[![Download](https://api.bintray.com/packages/nwillc/maven/ksvg/images/download.svg)](https://bintray.com/nwillc/maven/ksvg/_latestVersion)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e00d48ec86df4fb4a63e554729b1bf6e)](https://www.codacy.com/app/nwillc/ksvg?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=nwillc/ksvg&amp;utm_campaign=Badge_Grade)
