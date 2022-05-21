---
type: project
title: Controls-kt
order: 10
language: en
image:  images/projects/controls-demo.png
---
[![JetBrains Research](https://jb.gg/badges/research.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)

[Repository and documentation](https://github.com/mipt-npm/controls.kt)

Controls.kt is a data acquisition framework (work in progress). It is based on DataForge, a software framework for automated data processing. This repository contains a prototype of API and simple implementation
of a slow control system, including a demo.

Controls.kt uses some concepts and modules of DataForge,
such as `Meta` (immutable tree-like structure) and `Meta` (which
includes a scalar value, or a tree of values, easily convertable to/from JSON
if needed).

## Materials and publications

* Video - [A general overview seminar](https://youtu.be/LO-qjWgXMWc)
* Video - [A seminar about the system mechanics](https://youtu.be/wES0RV5GpoQ)
* Article - [A Novel Solution for Controlling Hardware Components of Accelerators and Beamlines](https://www.preprints.org/manuscript/202108.0336/v1)

### Features
Among other things, you can:
- Describe devices and their properties.
- Collect data from devices and execute arbitrary actions supported by a device.
- Property values can be cached in the system and requested from devices as needed, asynchronously.
- Connect devices to event bus via bidirectional message flows.