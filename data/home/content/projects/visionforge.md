---
type: project
title: VisionForge
order: 30
language: en
image:  images/projects/muon-monitor.png
---

[![JetBrains Research](https://jb.gg/badges/research.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![DOI](https://zenodo.org/badge/174502624.svg)](https://zenodo.org/badge/latestdoi/174502624)

[![Slack](https://img.shields.io/badge/slack-channel-green?logo=slack)](https://kotlinlang.slack.com/archives/CEXV2QWNM)

[Repository and documentation](https://github.com/mipt-npm/visionforge)

The main framework's use case for now is 3D visualization for particle physics experiments.
Other applications including 2D plots are planned for the future.

The project is developed as a [Kotlin multiplatform](https://kotlinlang.org/docs/reference/multiplatform.html)
application, currently targeting browser JavaScript and JVM.

## Features

- 3D visualization of complex experimental set-ups
- Event display such as particle tracks, etc.
- Scales up to few hundred thousands of elements
- Camera move, rotate, zoom-in and zoom-out
- Scene graph as an object tree with property editor
- Settings export and import
- Multiple platform support