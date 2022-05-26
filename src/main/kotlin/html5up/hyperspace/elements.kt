package html5up.hyperspace

import kotlinx.html.*

fun FlowContent.elements() {
    h1("major") { +"""Elements""" }
    +"""<!-- Text -->"""
    section {
        h2 { +"""Text""" }
        p {
            +"""This is"""
            b { +"""bold""" }
            +"""and this is"""
            strong { +"""strong""" }
            +""". This is"""
            i { +"""italic""" }
            +"""and this is"""
            em { +"""emphasized""" }
            +""".
									This is"""
            sup { +"""superscript""" }
            +"""text and this is"""
            sub { +"""subscript""" }
            +"""text.
									This is"""
            // u { +"""underlined""" }
            +"""and this is code:"""
            code { +"""for (;;) { ... }""" }
            +""". Finally,"""
            a {
                href = "#"
                +"""this is a link"""
            }
            +"""."""
        }
        hr {
        }
        p { +"""Nunc lacinia ante nunc ac lobortis. Interdum adipiscing gravida odio porttitor sem non mi integer non faucibus ornare mi ut ante amet placerat aliquet. Volutpat eu sed ante lacinia sapien lorem accumsan varius montes viverra nibh in adipiscing blandit tempus accumsan.""" }
        hr {
        }
        h2 { +"""Heading Level 2""" }
        h3 { +"""Heading Level 3""" }
        h4 { +"""Heading Level 4""" }
        hr {
        }
        h3 { +"""Blockquote""" }
        blockQuote { +"""Fringilla nisl. Donec accumsan interdum nisi, quis tincidunt felis sagittis eget tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan faucibus. Vestibulum ante ipsum primis in faucibus lorem ipsum dolor sit amet nullam adipiscing eu felis.""" }
        h3 { +"""Preformatted""" }
        pre {
            code {
                +"""i = 0;

while (!deck.isInOrder()) {
    print 'Iteration ' + i;
    deck.shuffle();
    i++;
}

print 'It took ' + i + ' iterations to sort the deck.';"""
            }
        }
    }
    +"""<!-- Lists -->"""
    section {
        h2 { +"""Lists""" }
        div("row") {
            div("col-6 col-12-medium") {
                h3 { +"""Unordered""" }
                ul {
                    li { +"""Dolor pulvinar etiam.""" }
                    li { +"""Sagittis adipiscing.""" }
                    li { +"""Felis enim feugiat.""" }
                }
                h3 { +"""Alternate""" }
                ul("alt") {
                    li { +"""Dolor pulvinar etiam.""" }
                    li { +"""Sagittis adipiscing.""" }
                    li { +"""Felis enim feugiat.""" }
                }
            }
            div("col-6 col-12-medium") {
                h3 { +"""Ordered""" }
                ol {
                    li { +"""Dolor pulvinar etiam.""" }
                    li { +"""Etiam vel felis viverra.""" }
                    li { +"""Felis enim feugiat.""" }
                    li { +"""Dolor pulvinar etiam.""" }
                    li { +"""Etiam vel felis lorem.""" }
                    li { +"""Felis enim et feugiat.""" }
                }
                h3 { +"""Icons""" }
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
                }
            }
        }
        h2 { +"""Actions""" }
        div("row") {
            div("col-6 col-12-medium") {
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
            div("col-6 col-12-medium") {
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
    }
    +"""<!-- Table -->"""
    section {
        h2 { +"""Table""" }
        h3 { +"""Default""" }
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
                        td { +"""Item One""" }
                        td { +"""Ante turpis integer aliquet porttitor.""" }
                        td { +"""29.99""" }
                    }
                    tr {
                        td { +"""Item Two""" }
                        td { +"""Vis ac commodo adipiscing arcu aliquet.""" }
                        td { +"""19.99""" }
                    }
                    tr {
                        td { +"""Item Three""" }
                        td { +"""Morbi faucibus arcu accumsan lorem.""" }
                        td { +"""29.99""" }
                    }
                    tr {
                        td { +"""Item Four""" }
                        td { +"""Vitae integer tempus condimentum.""" }
                        td { +"""19.99""" }
                    }
                    tr {
                        td { +"""Item Five""" }
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
        h3 { +"""Alternate""" }
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
                        td { +"""Item One""" }
                        td { +"""Ante turpis integer aliquet porttitor.""" }
                        td { +"""29.99""" }
                    }
                    tr {
                        td { +"""Item Two""" }
                        td { +"""Vis ac commodo adipiscing arcu aliquet.""" }
                        td { +"""19.99""" }
                    }
                    tr {
                        td { +"""Item Three""" }
                        td { +"""Morbi faucibus arcu accumsan lorem.""" }
                        td { +"""29.99""" }
                    }
                    tr {
                        td { +"""Item Four""" }
                        td { +"""Vitae integer tempus condimentum.""" }
                        td { +"""19.99""" }
                    }
                    tr {
                        td { +"""Item Five""" }
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
    +"""<!-- Buttons -->"""
    section {
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
        ul("actions") {
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
            li {
                a(classes = "button fit small") {
                    href = "#"
                    +"""Fit + Small"""
                }
            }
        }
        ul("actions") {
            li {
                a(classes = "button primary icon solid fa-download") {
                    href = "#"
                    +"""Icon"""
                }
            }
            li {
                a(classes = "button icon solid fa-upload") {
                    href = "#"
                    +"""Icon"""
                }
            }
            li {
                a(classes = "button icon solid fa-save") {
                    href = "#"
                    +"""Icon"""
                }
            }
        }
        ul("actions") {
            li {
                span("button primary disabled") { +"""Disabled""" }
            }
            li {
                span("button disabled") { +"""Disabled""" }
            }
        }
    }
    +"""<!-- Form -->"""
    section {
        h2 { +"""Form""" }
        form {
            method = FormMethod.post
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
                        type = InputType.checkBox
                        id = "demo-human"
                        name = "demo-human"
                        checked = true
                    }
                    label {
                        htmlFor = "demo-human"
                        +"""Not a robot"""
                    }
                }
                div("col-12") {
                    textArea {
                        name = "demo-message"
                        id = "demo-message"
                        placeholder = "Enter your message"
                        rows = "6"
                    }
                }
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
    }
    +"""<!-- Image -->"""
    section {
        h2 { +"""Image""" }
        h3 { +"""Fit""" }
        div("box alt") {
            div("row gtr-uniform") {
                div("col-12") {
                    span("image fit") {
                        img {
                            src = "images/pic04.jpg"
                            alt = ""
                        }
                    }
                }
                div("col-4") {
                    span("image fit") {
                        img {
                            src = "images/pic01.jpg"
                            alt = ""
                        }
                    }
                }
                div("col-4") {
                    span("image fit") {
                        img {
                            src = "images/pic02.jpg"
                            alt = ""
                        }
                    }
                }
                div("col-4") {
                    span("image fit") {
                        img {
                            src = "images/pic03.jpg"
                            alt = ""
                        }
                    }
                }
                div("col-4") {
                    span("image fit") {
                        img {
                            src = "images/pic03.jpg"
                            alt = ""
                        }
                    }
                }
                div("col-4") {
                    span("image fit") {
                        img {
                            src = "images/pic01.jpg"
                            alt = ""
                        }
                    }
                }
                div("col-4") {
                    span("image fit") {
                        img {
                            src = "images/pic02.jpg"
                            alt = ""
                        }
                    }
                }
                div("col-4") {
                    span("image fit") {
                        img {
                            src = "images/pic02.jpg"
                            alt = ""
                        }
                    }
                }
                div("col-4") {
                    span("image fit") {
                        img {
                            src = "images/pic03.jpg"
                            alt = ""
                        }
                    }
                }
                div("col-4") {
                    span("image fit") {
                        img {
                            src = "images/pic01.jpg"
                            alt = ""
                        }
                    }
                }
            }
        }
        h3 { +"""Left &amp; Right""" }
        p {
            span("image left") {
                img {
                    src = "images/pic05.jpg"
                    alt = ""
                }
            }
            +"""Fringilla nisl. Donec accumsan interdum nisi, quis tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent. Donec accumsan interdum nisi, quis tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent."""
        }
        p {
            span("image right") {
                img {
                    src = "images/pic06.jpg"
                    alt = ""
                }
            }
            +"""Fringilla nisl. Donec accumsan interdum nisi, quis tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent. Donec accumsan interdum nisi, quis tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent tincidunt felis sagittis eget. tempus euismod. Vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing accumsan eu faucibus. Integer ac pellentesque praesent."""
        }
    }
}
