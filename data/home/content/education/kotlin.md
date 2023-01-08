---
type: page
title: Introduction to scientific programming in Kotlin
pageName: SPC.education.kotlin
transformation: snark.basic
language: en
---

Recording of lectures in 2019 is available [here](https://www.youtube.com/playlist?list=PL4_hYwCyhAvZzRpbK4iTy9S6_OWZNEiVk).

## Lecturer
[Alexander Nozik](${resolvePageRef("team.index")}#nozik)

## Course purpose

As science develops, computer methods are becoming more and more important in the daily work of a scientist. In conducting an experiment, computer methods and tools are used at all stages of the work: planning the experiment, preparing the installation, collecting data, processing and publishing it. In such a situation, the quality of the programs used is beginning to play an important role. In addition, there is a need for specialists who understand both science and programming and who develop and improve software tools. 

Most students (and scientists) are more or less familiar with the basic tools of a programmer, for example, writing simple programs in Python. This is not enough for serious scientific development, so the course aims at a more advanced understanding of hardware, program structure and modern development tools.

As the main programming language we will use `Kotlin`, which appeared recently and managed to gain a large market share. Kotlin has several significant advantages as an initial language for advanced scientific programming:
* Strict typing, a clearly constructed system of types.
* High performance.
* Automatic memory management.
* Fully compatible with a huge number of Java libraries.
* Better toolkit.
* Extensive community.
* Possibility of commercial use.

## Format

The lessons are twice a week. 
* One is a lecture at MIPT campus. Lectures are dedicated to Kotlin language features and specifics of its application to science and industry problems. 
* The second one is practice. Practice is dedicated to work on students projects and additional material.

To get a mark student must complete 3 practical tasks during the semester. All tasks are accepted in form of Kotlin application of notebook and pass a code-review stage.

In this course we will learn to work in Kotlin language and apply it to scientific problems. We will focus on practical aspects and examples, so that no additional knowledge is needed to understand it. For practical examples we will use the development environment [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/).

All questions related to the course will be discussed in telegram [Kotlin at MIPT](https://t.me/kotlin_mipt).

## Content
1. **From hard to soft**
    1.  Program as a set of instructions. Evolution of programs.
    2.  Memory structure. Segmentation fault.
    3.  Programming paradigms. Genealogy of languages.
    4.  Virtual machines, byte-code.
    5.  Compilation and optimization.
    6.  Static and dynamic linking. Libraries.
    7.  Program structure. Entry points.
2. **Kotlin language**
    1.  Variables, classes and objects.
    2.  Control flow. Procedural and functional approach.
    3.  Loops.
    4.  Data structures and operations on them.
    5.  Properties and Delegates.
    6.  Parametric types.
    7.  Extensions.
    8.  Boxing.
    9.  Multiplatform projects.
3. **Program Architecture**
    1. Abstractions and interfaces.
    2. Basics of collective development with the help of modern tools.
    3. Ideology of object programming. Separation of behavior.
    4. Ideology of functional programming.
4. **Scientific programming**.
    1. Basics of numerical methods. The concept of numerical accuracy. Complexity of algorithms.
    2.  Numerical differentiation and integration.
    3.  Random Number Generators and Monte Carlo Modeling.
    4.  High-performance parallel and competitive computing.
    5.  The problem of I/O and the main methods to solve it.
    6.  Data collection systems. Protocols of data exchange.
    7.  Basics of work with big data.
    8.  Streaming data processing.

## Reporting

The offset is held in the form of a presentation based on the materials of an individual project. Interim reporting in the form of assignments is also provided.

## Recommended literature

* [Official documentation(en)](https://kotlinlang.org/docs/reference/)
* [Official documentation(ru)](https://kotlinlang.ru/)
* [Kotlin in action](https://dmkpress.com/catalog/computer/programming/java/978-5-97060-497-7/)
