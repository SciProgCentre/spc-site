---
type: project
title: KMath
order: 1
language: en
image: 
  path: images/projects/KMath.png
  position: right
---

[![JetBrains Research](https://jb.gg/badges/research.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![DOI](https://zenodo.org/badge/129486382.svg)](https://zenodo.org/badge/latestdoi/129486382)
[![Maven Central](https://img.shields.io/maven-central/v/space.kscience/kmath-core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22space.kscience%22)

[Repository and documentation](https://github.com/mipt-npm/kmath)


Could be pronounced as `key-math`. The **K**otlin **Math**ematics library was initially intended as a Kotlin-based
analog to Python's NumPy library. Later we found that kotlin is much more flexible language and allows superior
architecture designs. In contrast to `numpy` and `scipy` it is modular and has a lightweight core. The `numpy`-like
experience could be achieved with [kmath-for-real](/kmath-for-real) extension module.

[Documentation site (**WIP**)](https://mipt-npm.github.io/kmath/)

## Publications and talks

* [A conceptual article about context-oriented design](https://proandroiddev.com/an-introduction-context-oriented-programming-in-kotlin-2e79d316b0a2)
* [Another article about context-oriented design](https://proandroiddev.com/diving-deeper-into-context-oriented-programming-in-kotlin-3ecb4ec38814)
* [ACAT 2019 conference paper](https://aip.scitation.org/doi/abs/10.1063/1.5130103)

## Goal

* Provide a flexible and powerful API to work with mathematics abstractions in Kotlin-multiplatform (JVM, JS and Native)
  .
* Provide basic multiplatform implementations for those abstractions (without significant performance optimization).
* Provide bindings and wrappers with those abstractions for popular optimized platform libraries.

## Non-goals

* Be like NumPy. It was the idea at the beginning, but we decided that we can do better in API.
* Provide the best performance out of the box. We have specialized libraries for that. Need only API wrappers for them.
* Cover all cases as immediately and in one bundle. We will modularize everything and add new features gradually.
* Provide specialized behavior in the core. API is made generic on purpose, so one needs to specialize for types, like
  for `Double` in the core. For that we will have specialization modules like `kmath-for-real`, which will give better
  experience for those, who want to work with specific types.