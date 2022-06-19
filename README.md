# Scientific programming center site

The site is [here](https://sciprog.center/)

## The mechanics

The site is a unique mix between static and dynamic content rendering. 
It uses [DataForge](https://github.com/mipt-npm/dataforge-core) and a prototype for SNARK project to load data lazily and 
render it with very low resource consumption.

The idea is the following:
* The file tree from [data](/data) directory is lazily loaded into a DataForge `DataSet`structure. Each element in a `DataSet` is a lazy data with eagerly loaded metadata. In case of files, metadata includes some file parameters like extensions and creation time a front matter YAML translated into DF `Meta`.
* The `DataSet` is transformed using parsers defined in `SnarkPlugin`. Parser transforms the bytearray-like file content into some typed object like Markdown or Html. Json/yaml files are transformed into `Meta`. All objects are lazy, they are not parsed and computed until they are read.
* There could be additional transformation for tree. For example severl blocks could be combined into one. Data could be loaded inside the page, etc.
* The resulting `DataSet` is loaded into a `PageContext`, which is used by a site template to render resulting pages.
* In the future, there will be utilities to automatically transform into a `DataSet`, loading templates as plugins.
* Once computed, the data is cached in-memory and is not recomputed until the underlying data is changed.
* A `PageContex` is loaded as a context receiver for all page rendering activities. It allows to seamlessly load data into the page, also it could be substituted on language change or other configuration changes. Also one could use `PageContext` to isolate different parts of site from each other.

## References

Currently we use two different designs from https://html5up.net/