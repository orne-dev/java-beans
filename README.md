# Orne Bean utilities

Provides utilities for development of POJO Java beans.

## Status

[![License][status.license.badge]][status.license]
[![Latest version][status.maven.badge]][status.maven]
[![Javadoc][status.javadoc.badge]][javadoc]
[![Maven site][status.site.badge]][site]

| Latest Release | Develop |
| :------------: | :-------------: |
| [![Build Status][status.latest.ci.badge]][status.latest.ci] | [![Build Status][status.dev.ci.badge]][status.dev.ci] |
| [![Coverage][status.latest.cov.badge]][status.latest.cov] | [![Coverage][status.dev.cov.badge]][status.dev.cov] |

## Usage

The binaries can be obtained from [Maven Central][status.maven] with the
`dev.orne:beans` coordinates:

```xml
<dependency>
  <groupId>dev.orne</groupId>
  <artifactId>beans</artifactId>
  <version>0.5.0</version>
</dependency>
```

## Features

### Identities

Abstraction mechanism for entity identities on beans, also known as
"Primary ID" or "Primary Key".

See [Maven site page][site identities] for further information.

## References

Annotation based mechanism to declare and validate bean based references to
unique entity references.

See [Maven site page][site references] for further information.

## Converters

Adds extra supported conversions to Apache Commons BeanUtils.

See [Maven site page][site converters] for further information.

## Further information

For further information refer to the [Javadoc][javadoc]
and [Maven Site][site].

[site]: https://orne-dev.github.io/java-beans/
[site identities]: https://orne-dev.github.io/java-beans/indentities.html
[site references]: https://orne-dev.github.io/java-beans/references.html
[site converters]: https://orne-dev.github.io/java-beans/converters.html
[javadoc]: https://javadoc.io/doc/dev.orne/beans
[status.license]: http://www.gnu.org/licenses/gpl-3.0.txt
[status.license.badge]: https://img.shields.io/github/license/orne-dev/java-beans
[status.maven]: https://search.maven.org/artifact/dev.orne/beans
[status.maven.badge]: https://img.shields.io/maven-central/v/dev.orne/beans.svg?label=Maven%20Central
[status.javadoc.badge]: https://javadoc.io/badge2/dev.orne/beans/javadoc.svg
[status.site.badge]: https://img.shields.io/website?url=https%3A%2F%2Forne-dev.github.io%2Fjava-beans%2F
[status.latest.ci]: https://github.com/orne-dev/java-beans/actions/workflows/release.yml
[status.latest.ci.badge]: https://github.com/orne-dev/java-beans/actions/workflows/release.yml/badge.svg?branch=master
[status.latest.cov]: https://sonarcloud.io/dashboard?id=orne-dev_java-beans
[status.latest.cov.badge]: https://sonarcloud.io/api/project_badges/measure?project=orne-dev_java-beans&metric=coverage
[status.dev.ci]: https://github.com/orne-dev/java-beans/actions/workflows/build.yml
[status.dev.ci.badge]: https://github.com/orne-dev/java-beans/actions/workflows/build.yml/badge.svg?branch=develop
[status.dev.cov]: https://sonarcloud.io/dashboard?id=orne-dev_java-beans&branch=develop
[status.dev.cov.badge]: https://sonarcloud.io/api/project_badges/measure?project=orne-dev_java-beans&metric=coverage&branch=develop
