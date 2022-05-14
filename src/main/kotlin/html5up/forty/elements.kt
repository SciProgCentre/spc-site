package html5up.forty

import kotlinx.html.*

internal fun FlowContent.fortyElements() {
    section {
        id = "one"
        div("inner") {
            header("major") {
                h1 { +"""Elements""" }
            }
            // Content
            h2 {
                id = "content"
                +"""Sample Content"""
            }
            p { +"""Praesent ac adipiscing ullamcorper semper ut amet ac risus. Lorem sapien ut odio odio nunc. Ac adipiscing nibh porttitor erat risus justo adipiscing adipiscing amet placerat accumsan. Vis. Faucibus odio magna tempus adipiscing a non. In mi primis arcu ut non accumsan vivamus ac blandit adipiscing adipiscing arcu metus praesent turpis eu ac lacinia nunc ac commodo gravida adipiscing eget accumsan ac nunc adipiscing adipiscing.""" }
            div("row") {
                div("col-6 col-12-small") {
                    h3 { +"""Sem turpis amet semper""" }
                    p { +"""Nunc lacinia ante nunc ac lobortis. Interdum adipiscing gravida odio porttitor sem non mi integer non faucibus ornare mi ut ante amet placerat aliquet. Volutpat commodo eu sed ante lacinia. Sapien a lorem in integer ornare praesent commodo adipiscing arcu in massa commodo lorem accumsan at odio massa ac ac. Semper adipiscing varius montes viverra nibh in adipiscing blandit tempus accumsan.""" }
                }
                div("col-6 col-12-small") {
                    h3 { +"""Magna odio tempus commodo""" }
                    p { +"""In arcu accumsan arcu adipiscing accumsan orci ac. Felis id enim aliquet. Accumsan ac integer lobortis commodo ornare aliquet accumsan erat tempus amet porttitor. Ante commodo blandit adipiscing integer semper orci eget. Faucibus commodo adipiscing mi eu nullam accumsan morbi arcu ornare odio mi adipiscing nascetur lacus ac interdum morbi accumsan vis mi accumsan ac praesent.""" }
                }
                // Break
                div("col-4 col-12-medium") {
                    h3 { +"""Interdum sapien gravida""" }
                    p { +"""Nunc lacinia ante nunc ac lobortis. Interdum adipiscing gravida odio porttitor sem non mi integer non faucibus ornare mi ut ante amet placerat aliquet. Volutpat eu sed ante lacinia sapien lorem accumsan varius montes viverra nibh in adipiscing blandit tempus accumsan.""" }
                }
                div("col-4 col-12-medium") {
                    h3 { +"""Faucibus consequat lorem""" }
                    p { +"""Nunc lacinia ante nunc ac lobortis. Interdum adipiscing gravida odio porttitor sem non mi integer non faucibus ornare mi ut ante amet placerat aliquet. Volutpat eu sed ante lacinia sapien lorem accumsan varius montes viverra nibh in adipiscing blandit tempus accumsan.""" }
                }
                div("col-4 col-12-medium") {
                    h3 { +"""Accumsan montes viverra""" }
                    p { +"""Nunc lacinia ante nunc ac lobortis. Interdum adipiscing gravida odio porttitor sem non mi integer non faucibus ornare mi ut ante amet placerat aliquet. Volutpat eu sed ante lacinia sapien lorem accumsan varius montes viverra nibh in adipiscing blandit tempus accumsan.""" }
                }
            }
            hr("major") {
            }
            // Elements
            h2 {
                id = "elements"
                +"""Elements"""
            }
            div("row gtr-200") {
                div("col-6 col-12-medium") {
                    // Text stuff
                    h3 { +"""Text""" }
                    p {
                        +"""This is Text"""
                    }
                    hr()
                    h2 { +"""Heading Level 2""" }
                    h3 { +"""Heading Level 3""" }
                    h4 { +"""Heading Level 4""" }
                    hr()
                    p { +"""Nunc lacinia ante nunc ac lobortis. Interdum adipiscing gravida odio porttitor sem non mi integer non faucibus ornare mi ut ante amet placerat aliquet. Volutpat eu sed ante lacinia sapien lorem accumsan varius montes viverra nibh in adipiscing blandit tempus accumsan.""" }
                    // Lists
                    h3 { +"""Lists""" }
                    div("row") {
                        div("col-6 col-12-small") {
                            h4 { +"""Unordered""" }
                            ul {
                                li { +"""Dolor etiam magna etiam.""" }
                                li { +"""Sagittis lorem eleifend.""" }
                                li { +"""Felis dolore viverra.""" }
                            }
                            h4 { +"""Alternate""" }
                            ul("alt") {
                                li { +"""Dolor etiam magna etiam.""" }
                                li { +"""Sagittis lorem eleifend.""" }
                                li { +"""Felis feugiat viverra.""" }
                            }
                        }
                        div("col-6 col-12-small") {
                            h4 { +"""Ordered""" }
                            ol {
                                li { +"""Dolor etiam magna etiam.""" }
                                li { +"""Etiam vel lorem sed viverra.""" }
                                li { +"""Felis dolore viverra.""" }
                                li { +"""Dolor etiam magna etiam.""" }
                                li { +"""Etiam vel lorem sed viverra.""" }
                                li { +"""Felis dolore viverra.""" }
                            }
                            h4 { +"""Icons""" }
                            ul("icons") {
                                li {
                                    a(classes = "icon brands fa-twitter") {
                                        href = "#"
                                        span("label") { +"""Twitter""" }
                                    }
                                }
                                li {
                                    a(classes = "icon brands fa-facebook-f") {
                                        href = "#"
                                        span("label") { +"""Facebook""" }
                                    }
                                }
                                li {
                                    a(classes = "icon brands fa-instagram") {
                                        href = "#"
                                        span("label") { +"""Instagram""" }
                                    }
                                }
                                li {
                                    a(classes = "icon brands fa-github") {
                                        href = "#"
                                        span("label") { +"""Github""" }
                                    }
                                }
                                li {
                                    a(classes = "icon brands fa-dribbble") {
                                        href = "#"
                                        span("label") { +"""Dribbble""" }
                                    }
                                }
                                li {
                                    a(classes = "icon brands fa-tumblr") {
                                        href = "#"
                                        span("label") { +"""Tumblr""" }
                                    }
                                }
                            }
                            ul("icons") {
                                li {
                                    a(classes = "icon brands alt fa-twitter") {
                                        href = "#"
                                        span("label") { +"""Twitter""" }
                                    }
                                }
                                li {
                                    a(classes = "icon brands alt fa-facebook-f") {
                                        href = "#"
                                        span("label") { +"""Facebook""" }
                                    }
                                }
                                li {
                                    a(classes = "icon brands alt fa-instagram") {
                                        href = "#"
                                        span("label") { +"""Instagram""" }
                                    }
                                }
                            }
                        }
                    }
                    h4 { +"""Definition""" }
                    dl {
                        dt { +"""Item1""" }
                        dd {
                            p { +"""Lorem ipsum dolor vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent. Lorem ipsum dolor.""" }
                        }
                        dt { +"""Item2""" }
                        dd {
                            p { +"""Lorem ipsum dolor vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent. Lorem ipsum dolor.""" }
                        }
                        dt { +"""Item3""" }
                        dd {
                            p { +"""Lorem ipsum dolor vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent. Lorem ipsum dolor.""" }
                        }
                    }
                    h4 { +"""Actions""" }
                    ul("actions") {
                        li {
                            a(classes = "button primary") {
                                href = "#"
                                +"""Default"""
                            }
                        }
                        li {
                            a(classes = "button") {
                                href = "#"
                                +"""Default"""
                            }
                        }
                    }
                    ul("actions small") {
                        li {
                            a(classes = "button primary small") {
                                href = "#"
                                +"""Small"""
                            }
                        }
                        li {
                            a(classes = "button small") {
                                href = "#"
                                +"""Small"""
                            }
                        }
                    }
                    div("row") {
                        div("col-6 col-12-small") {
                            ul("actions stacked") {
                                li {
                                    a(classes = "button primary") {
                                        href = "#"
                                        +"""Default"""
                                    }
                                }
                                li {
                                    a(classes = "button") {
                                        href = "#"
                                        +"""Default"""
                                    }
                                }
                            }
                        }
                        div("col-6 col-12-small") {
                            ul("actions stacked") {
                                li {
                                    a(classes = "button primary small") {
                                        href = "#"
                                        +"""Small"""
                                    }
                                }
                                li {
                                    a(classes = "button small") {
                                        href = "#"
                                        +"""Small"""
                                    }
                                }
                            }
                        }
                        div("col-6 col-12-small") {
                            ul("actions stacked") {
                                li {
                                    a(classes = "button primary fit") {
                                        href = "#"
                                        +"""Default"""
                                    }
                                }
                                li {
                                    a(classes = "button fit") {
                                        href = "#"
                                        +"""Default"""
                                    }
                                }
                            }
                        }
                        div("col-6 col-12-small") {
                            ul("actions stacked") {
                                li {
                                    a(classes = "button primary small fit") {
                                        href = "#"
                                        +"""Small"""
                                    }
                                }
                                li {
                                    a(classes = "button small fit") {
                                        href = "#"
                                        +"""Small"""
                                    }
                                }
                            }
                        }
                    }
                    h4 { +"""Pagination""" }
                    ul("pagination") {
                        li {
                            span("button small disabled") { +"""Prev""" }
                        }
                        li {
                            a(classes = "page active") {
                                href = "#"
                                +"""1"""
                            }
                        }
                        li {
                            a(classes = "page") {
                                href = "#"
                                +"""2"""
                            }
                        }
                        li {
                            a(classes = "page") {
                                href = "#"
                                +"""3"""
                            }
                        }
                        li {
                            span { +"""&hellip;""" }
                        }
                        li {
                            a(classes = "page") {
                                href = "#"
                                +"""8"""
                            }
                        }
                        li {
                            a(classes = "page") {
                                href = "#"
                                +"""9"""
                            }
                        }
                        li {
                            a(classes = "page") {
                                href = "#"
                                +"""10"""
                            }
                        }
                        li {
                            a(classes = "button small") {
                                href = "#"
                                +"""Next"""
                            }
                        }
                    }
                    // Blockquote
                    h3 { +"""Blockquote""" }
                    blockQuote { +"""Fringilla nisl. Donec accumsan interdum nisi, quis tincidunt felis sagittis eget tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan faucibus. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis.""" }
                    // Table
                    h3 { +"""Table""" }
                    h4 { +"""Default""" }
                    div("table-wrapper") {
                        table {
                            thead {
                                tr {
                                    th { +"""Name""" }
                                    th { +"""Description""" }
                                    th { +"""Price""" }
                                }
                            }
                            tbody {
                                tr {
                                    td { +"""Item1""" }
                                    td { +"""Ante turpis integer aliquet porttitor.""" }
                                    td { +"""29.99""" }
                                }
                                tr {
                                    td { +"""Item2""" }
                                    td { +"""Vis ac commodo adipiscing arcu aliquet.""" }
                                    td { +"""19.99""" }
                                }
                                tr {
                                    td { +"""Item3""" }
                                    td { +"""Morbi faucibus arcu accumsan lorem.""" }
                                    td { +"""29.99""" }
                                }
                                tr {
                                    td { +"""Item4""" }
                                    td { +"""Vitae integer tempus condimentum.""" }
                                    td { +"""19.99""" }
                                }
                                tr {
                                    td { +"""Item5""" }
                                    td { +"""Ante turpis integer aliquet porttitor.""" }
                                    td { +"""29.99""" }
                                }
                            }
                            tfoot {
                                tr {
                                    td {
                                        colSpan = "2"
                                    }
                                    td { +"""100.00""" }
                                }
                            }
                        }
                    }
                    h4 { +"""Alternate""" }
                    div("table-wrapper") {
                        table("alt") {
                            thead {
                                tr {
                                    th { +"""Name""" }
                                    th { +"""Description""" }
                                    th { +"""Price""" }
                                }
                            }
                            tbody {
                                tr {
                                    td { +"""Item1""" }
                                    td { +"""Ante turpis integer aliquet porttitor.""" }
                                    td { +"""29.99""" }
                                }
                                tr {
                                    td { +"""Item2""" }
                                    td { +"""Vis ac commodo adipiscing arcu aliquet.""" }
                                    td { +"""19.99""" }
                                }
                                tr {
                                    td { +"""Item3""" }
                                    td { +"""Morbi faucibus arcu accumsan lorem.""" }
                                    td { +"""29.99""" }
                                }
                                tr {
                                    td { +"""Item4""" }
                                    td { +"""Vitae integer tempus condimentum.""" }
                                    td { +"""19.99""" }
                                }
                                tr {
                                    td { +"""Item5""" }
                                    td { +"""Ante turpis integer aliquet porttitor.""" }
                                    td { +"""29.99""" }
                                }
                            }
                            tfoot {
                                tr {
                                    td {
                                        colSpan = "2"
                                    }
                                    td { +"""100.00""" }
                                }
                            }
                        }
                    }
                }
                div("col-6 col-12-medium") {
                    // Buttons
                    h3 { +"""Buttons""" }
                    ul("actions") {
                        li {
                            a(classes = "button primary") {
                                href = "#"
                                +"""Primary"""
                            }
                        }
                        li {
                            a(classes = "button") {
                                href = "#"
                                +"""Default"""
                            }
                        }
                    }
                    div("row") {
                        div("col-6 col-12-xsmall") {
                            ul("actions stacked") {
                                li {
                                    a(classes = "button large") {
                                        href = "#"
                                        +"""Large"""
                                    }
                                }
                                li {
                                    a(classes = "button") {
                                        href = "#"
                                        +"""Default"""
                                    }
                                }
                                li {
                                    a(classes = "button small") {
                                        href = "#"
                                        +"""Small"""
                                    }
                                }
                            }
                        }
                        div("col-6 col-12-xsmall") {
                            ul("actions stacked") {
                                li {
                                    a(classes = "button primary large") {
                                        href = "#"
                                        +"""Large"""
                                    }
                                }
                                li {
                                    a(classes = "button primary") {
                                        href = "#"
                                        +"""Default"""
                                    }
                                }
                                li {
                                    a(classes = "button primary small") {
                                        href = "#"
                                        +"""Small"""
                                    }
                                }
                            }
                        }
                    }
                    ul("actions fit") {
                        li {
                            a(classes = "button primary fit") {
                                href = "#"
                                +"""Fit"""
                            }
                        }
                        li {
                            a(classes = "button fit") {
                                href = "#"
                                +"""Fit"""
                            }
                        }
                    }
                    ul("actions fit small") {
                        li {
                            a(classes = "button primary fit small") {
                                href = "#"
                                +"""Fit + Small"""
                            }
                        }
                        li {
                            a(classes = "button fit small") {
                                href = "#"
                                +"""Fit + Small"""
                            }
                        }
                    }
                    ul("actions") {
                        li {
                            a(classes = "button primary icon solid fa-search") {
                                href = "#"
                                +"""Icon"""
                            }
                        }
                        li {
                            a(classes = "button icon solid fa-download") {
                                href = "#"
                                +"""Icon"""
                            }
                        }
                    }
                    ul("actions") {
                        li {
                            span("button primary disabled") { +"""Primary""" }
                        }
                        li {
                            span("button disabled") { +"""Default""" }
                        }
                    }
                    // Form
                    h3 { +"""Form""" }
                    form {
                        method =  FormMethod.post
                        action = "#"
                        div("row gtr-uniform") {
                            div("col-6 col-12-xsmall") {
                                input {
                                    type = InputType.text
                                    name = "demo-name"
                                    id = "demo-name"
                                    value = ""
                                    placeholder = "Name"
                                }
                            }
                            div("col-6 col-12-xsmall") {
                                input {
                                    type = InputType.email
                                    name = "demo-email"
                                    id = "demo-email"
                                    value = ""
                                    placeholder = "Email"
                                }
                            }
                            // Break
                            div("col-12") {
                                select {
                                    name = "demo-category"
                                    id = "demo-category"
                                    option {
                                        value = ""
                                        +"""- Category -"""
                                    }
                                    option {
                                        value = "1"
                                        +"""Manufacturing"""
                                    }
                                    option {
                                        value = "1"
                                        +"""Shipping"""
                                    }
                                    option {
                                        value = "1"
                                        +"""Administration"""
                                    }
                                    option {
                                        value = "1"
                                        +"""Human Resources"""
                                    }
                                }
                            }
                            // Break
                            div("col-4 col-12-small") {
                                input {
                                    type = InputType.radio
                                    id = "demo-priority-low"
                                    name = "demo-priority"
                                    checked = true
                                }
                                label {
                                    htmlFor = "demo-priority-low"
                                    +"""Low"""
                                }
                            }
                            div("col-4 col-12-small") {
                                input {
                                    type = InputType.radio
                                    id = "demo-priority-normal"
                                    name = "demo-priority"
                                }
                                label {
                                    htmlFor = "demo-priority-normal"
                                    +"""Normal"""
                                }
                            }
                            div("col-4 col-12-small") {
                                input {
                                    type = InputType.radio
                                    id = "demo-priority-high"
                                    name = "demo-priority"
                                }
                                label {
                                    htmlFor = "demo-priority-high"
                                    +"""High"""
                                }
                            }
                            // Break
                            div("col-6 col-12-small") {
                                input {
                                    type = InputType.checkBox
                                    id = "demo-copy"
                                    name = "demo-copy"
                                }
                                label {
                                    htmlFor = "demo-copy"
                                    +"""Email me a copy"""
                                }
                            }
                            div("col-6 col-12-small") {
                                input {
                                    type =  InputType.checkBox
                                    id = "demo-human"
                                    name = "demo-human"
                                    checked = true
                                }
                                label {
                                    htmlFor = "demo-human"
                                    +"""I am a human"""
                                }
                            }
                            // Break
                            div("col-12") {
                                textArea {
                                    name = "demo-message"
                                    id = "demo-message"
                                    placeholder = "Enter your message"
                                    rows = "6"
                                }
                            }
                            // Break
                            div("col-12") {
                                ul("actions") {
                                    li {
                                        input(classes = "primary") {
                                            type = InputType.submit
                                            value = "Send Message"
                                        }
                                    }
                                    li {
                                        input {
                                            type = InputType.reset
                                            value = "Reset"
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // Image
                    h3 { +"""Image""" }
                    h4 { +"""Fit""" }
                    span("image fit") {
                        img {
                            src = "images/pic03.jpg"
                            alt = ""
                        }
                    }
                    div("box alt") {
                        div("row gtr-50 gtr-uniform") {
                            div("col-4") {
                                span("image fit") {
                                    img {
                                        src = "images/pic08.jpg"
                                        alt = ""
                                    }
                                }
                            }
                            div("col-4") {
                                span("image fit") {
                                    img {
                                        src = "images/pic09.jpg"
                                        alt = ""
                                    }
                                }
                            }
                            div("col-4") {
                                span("image fit") {
                                    img {
                                        src = "images/pic10.jpg"
                                        alt = ""
                                    }
                                }
                            }
                            // Break
                            div("col-4") {
                                span("image fit") {
                                    img {
                                        src = "images/pic10.jpg"
                                        alt = ""
                                    }
                                }
                            }
                            div("col-4") {
                                span("image fit") {
                                    img {
                                        src = "images/pic08.jpg"
                                        alt = ""
                                    }
                                }
                            }
                            div("col-4") {
                                span("image fit") {
                                    img {
                                        src = "images/pic09.jpg"
                                        alt = ""
                                    }
                                }
                            }
                            // Break
                            div("col-4") {
                                span("image fit") {
                                    img {
                                        src = "images/pic09.jpg"
                                        alt = ""
                                    }
                                }
                            }
                            div("col-4") {
                                span("image fit") {
                                    img {
                                        src = "images/pic10.jpg"
                                        alt = ""
                                    }
                                }
                            }
                            div("col-4") {
                                span("image fit") {
                                    img {
                                        src = "images/pic08.jpg"
                                        alt = ""
                                    }
                                }
                            }
                        }
                    }
                    h4 { +"""Left &amp; Right""" }
                    p {
                        span("image left") {
                            img {
                                src = "images/pic09.jpg"
                                alt = ""
                            }
                        }
                        +"""Lorem ipsum dolor sit accumsan interdum nisi, quis tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget tempus vestibulum ante ipsum primis in faucibus magna blandit adipiscing eu felis iaculis."""
                    }
                    p {
                        span("image right") {
                            img {
                                src = "images/pic10.jpg"
                                alt = ""
                            }
                        }
                        +"""Lorem ipsum dolor sit accumsan interdum nisi, quis tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget tempus vestibulum ante ipsum primis in faucibus magna blandit adipiscing eu felis iaculis."""
                    }
                    // Box
                    h3 { +"""Box""" }
                    div("box") {
                        p { +"""Felis sagittis eget tempus primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget. tempus euismod. Magna sed etiam ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus lorem ipsum.""" }
                    }
                    // Preformatted Code
                    h3 { +"""Preformatted""" }
                    pre {
                        code {
                            +"""
                                i = 0;
                                while (!deck.isInOrder()) {
                                    print 'Iteration ' + i;
                                    deck.shuffle();
                                    i++;
                                }
                                
                                print 'It took ' + i + ' iterations to sort the deck.';
                            """.trimIndent()
                        }
                    }
                }
            }
        }
    }
}