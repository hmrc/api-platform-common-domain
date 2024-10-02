
# api-platform-common-domain

This library contains those types common to by api-platform-api-domain and api-platform-application-domain

The library is designed as a dependency for API Platform services and is subject to significant change without notice.

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

## Project structure

### api-platform-common-domain
Published - contains the common domain model classes for use by other code AND includes the common project classes that would otherwise need their own library.

### api-platform-common-domain-fixtures
Published - provides test fixtures for all classes in the common domain

### api-platform-common-domain-test
Tests the api platform common domain - done as a project to allow using test fixtures from test common domain in the testing

### common
Not published individually but contained within api-platform-common-domain
Code here can be used in repos depending on api-plaform-common-domain

### test-common
Not published individually but contained within api-platform-test-common-domain and used by this project for tests throughout.
Code here can be used in repos depending on api-plaform-common-test-domain
