---
type: project
title: Muo(no)graphy
order: 2
language: en
image:  images/projects/muon_coupling.png
---

# Geological gas storage monitoring and leaks prevention

## Overview

Various industrial processes in geological formations could present safety and environmental risks including groundwater contamination. Non-aqueous phase liquids such as chlorinated hydrocarbons and oil, but also super-critical CO2 and other compressed gas have low solubility in brine. Their migration, especially due to external forces, must be thoroughly monitored in order to avoid long-time pollution of freshwater aquifers in the subsurface. In the case of geological storage sites, any leaks would be also detrimental for the performance of the capture system itself.
We are developing a realistic 3D evolution model for such leakage incidents, a detection technique based on muography, and a materials design platform for membrane capturing technology.

## Applications

Carbon capture systems are devised to reduce greenhouse gas emissions while
relying on fossil fuels to produce energy. After capture and compression, super-
critical CO2 is injected into a permeable sandstone capped by low-permeability
layer acting as a seal. This type of fluid injection might cause pressure build-up,
temperature change and rock structure deformation/fracturing. Hence our aim
is to design a system capable to detect a leakage of CO2 through a potential
high permeability corridor in the cap-rock layer. Detecting such breakthroughs
as early as possible is crucial to prevent further pollution of subsurface layers
and assess storage exploitation.

Subsurface renewable energy storage for hydrogen, methane or compressed
air is another example that shares many of the challenges in breakthrough
monitoring tasks. Other sources of renewable energy such as solar panels
and wind farms are known to be intermittent. Hence, one has to either build a
storage for energy or use a continuous clean energy source in addition. The idea
is to compress and store air, hydrogen or other gases underground while demand
is low and then exploit them to generate electricity similarly to traditional gas
turbine plants. Obviously any leakage from such sites would undermine the
whole energy generation system but also pose safety risks for the environment
around.

Another application is provided by the enhanced oil recovery technology that
uses CO2 to extract hard-to-reach oil. Nowadays, researchers and practitioners
are trying to find more environment-friendly and economically feasible alterna-
tives. One of them is microbial enhanced oil recovery in which by-products of
microorganisms activity are used to extract residual oil left in the reservoir after
a primary pass. While biological processes bring additional modelling issues, we
should be able to cover such scenarios as well.

Other interesting directions to explore involve environmental control and
survey tasks for geothermal energy, mountain hydrology and well disposal to mention a few.

Regarding geological storage, we are further considering double porosity, thermal effects, electrokinetic flow, geomechanics and faults simulations which are crucial to supervise dangerous seismic activity.

## Our approach

Modelling of substances migration in porous media with low solubility in water has been addressed by a vast literature. The use of atmospheric muons to monitor underground fluid saturation levels has been also studied, specifically for the case of carbon sequestration.

However, the low-contrast and possibly noisy muon flux measurements require accurate and realistic modelling of the main physical processes for the inverse problem behind monitoring.

Moreover, first order sensitivity information for control parameters is needed to improve the analysis, as was demonstrated using approximate and simplified dynamics for both the CO2
plume evolution and the muon flux computation.

We address those issues by incorporating a differentiable programming paradigm into the implementation of the physics with detailed simulations.

We model the two-phase flow with capillary barrier effect in heterogeneous
porous media for which we rely on the mixed-hybrid finite element method
(MHFEM). We develop adjoint sensitivity methods in the context of MHFEM.
We perform muon transport building upon the Backward Monte-Carlo (BMC)
scheme. Re-using the spatial discretisation from MHFEM and we perform sensitivity computations with respect to the media density and saturation levels. Finally, we put everything together to design a system for detecting CO2 leakage through the cap-rock layer in GCS sites.

For capture technologies, we note that rotor & amine scrubbing requires large installations but membranes are compact, absorbing CO2 at relatively low pressures and ambient temperature. Therefore, membranes are more suitable to capture leaks but improvement on the technology is required as only 60% effectiveness is attained as of now.

To design the materials that would improve membrane's capture performance, we build a platform incorporating differentiable programming into ab initio molecular dynamics leading to performant high-throughput computational sceening system for new materials identification.