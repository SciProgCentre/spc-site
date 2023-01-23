---
type: project
title: VisionForge
order: 30
language: en
image:  
    path: images/projects/muon-monitor.png
    position: right
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
- Scales up to few hundred thousand elements
- Camera move, rotate, zoom-in and zoom-out
- Scene graph as an object tree with property editor
- Settings export and import
- Multiple platform support

## An introduction video

<iframe width="100%" height="315" src="https://www.youtube.com/embed/lzsF0NuhS6g" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>