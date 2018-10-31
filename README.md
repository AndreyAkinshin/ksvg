# Kotlin SVG DSL

I had a [Kotlin](https://kotlinlang.org/) microservice that needed to produce a simple heatmap chart on an HTML page to
a large audience very rapidly. This was an ad hoc DevOps requirement, so I didn't want to take on an involved Javascript
solution. Instead, I went with spitting out a half page of [inline SVG](https://www.w3schools.com/html/html5_svg.asp)
text generated by the server itself. This DSL made the server code trivial and has easily met the performance needs.

## Example

```kotlin
    val svg = SVG.svg(true) {
        height = "300"
        width = "300"
        // Ears
        g {
            id = "ear"
            circle {
                cx = "100"
                cy = "100"
                r = "40"
                stroke = "black"
                strokeWidth = "2"
                fill = "white"
            }
            circle {
                cx = "100"
                cy = "100"
                r = "28"
                stroke = "black"
                strokeWidth = "2"
                fill = "white"
            }
        }
        use {
            x = "140"
            y = "-30"
            href = "#ear"
        }
        // Face
        circle {
            id = "face"
            cx = "180"
            cy = "140"
            r = "80"
            stroke = "black"
            strokeWidth = "2"
            fill = "#aa450f"
        }
        // Eyes
        circle {
            id = "eye"
            cx = "160"
            cy = "95"
            r = "20"
            stroke = "black"
            strokeWidth = "2"
            fill = "white"
        }
        use {
            x = "45"
            y = "-5"
            href = "#eye"
        }
        // Muzzle
        circle {
            cx = "195"
            cy = "178"
            r = "65"
            stroke = "black"
            strokeWidth = "2"
            fill = "white"
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
            d = "M 150 150 C 100,250 305,260 230,140 C 205,190 165,170 150,150 Z"
            fill = "red"
            stroke = "black"
            strokeWidth = "2"
        }
    }
        
    System.out.println("<html><body>$svg</body></html>")
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

## See Also

- [License](LICENSE.md)
- [Javadoc](https://nwillc.github.io/ksvg/javadoc)

-----
[![Coverage](https://codecov.io/gh/nwillc/ksvg/branch/master/graphs/badge.svg?branch=master)](https://codecov.io/gh/nwillc/ksvg)
[![license](https://img.shields.io/github/license/nwillc/ksvg.svg)](https://tldrlegal.com/license/-isc-license)
[![Travis](https://img.shields.io/travis/nwillc/ksvg.svg)](https://travis-ci.org/nwillc/ksvg)
[![Download](https://api.bintray.com/packages/nwillc/maven/ksvg/images/download.svg)](https://bintray.com/nwillc/maven/ksvg/_latestVersion)
