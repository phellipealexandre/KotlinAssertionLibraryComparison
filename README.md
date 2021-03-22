# Kotlin Assertion Library Comparison
The purpose of this repository is to serve as a case study for the various Kotlin 
assertion libraries we have available today. A comparison was conducted based on the following criteria:
- **Popularity**: Based on GitHub stars.
- **Documentation**: If I could find material on how to use and how to customize the library.
- **Speed**: If it took more time to execute compared a unit-test compared to other libraries.
- **Fluency / Kotlin idiomatic**: If it is easy to read and it is Kotlin-like
- **Customizability**: If it could be customized easily.
- **Type** (Match, Wrap, Extend)
    - **Match** -> assertion(expected, actual)
    - **Wrap** -> assert(actual).assertion(expected)
    - **Extend** -> actual.assert(expected)

Some of the criteria can be biased on personal subjectiveness and understanding. The results reflect my own opinion.

### Assertion libraries used in this study
- [JUnit](https://github.com/junit-team/junit4)
- [Kotlin Junit](https://github.com/JetBrains/kotlin/tree/master/libraries/kotlin.test)
- [Hamcrest](https://github.com/hamcrest/JavaHamcrest)
- [Hamkrest](https://github.com/npryce/hamkrest)
- [Google Truth](https://github.com/google/truth)
- [AssertJ](https://github.com/assertj/assertj-core)
- [AssertK](https://github.com/willowtreeapps/assertk)
- [Atrium](https://github.com/robstoll/atrium)
- [Expekt](https://github.com/winterbe/expekt)
- [Kluent](https://github.com/MarkusAmshove/Kluent)
- [Kotest assertion](https://github.com/kotest/kotest)
- [Strikt](https://github.com/robfletcher/strikt)

### Result

Lib | Popularity | Documentation | Performance | Fluency | Customizability | Type
--- | --- | --- | --- | --- | --- | --- 
**JUnit** | [![star this repo](https://img.shields.io/github/stars/junit-team/junit.svg)`*`](https://github.com/junit-team/junit4) | ✅ | ✅| ❌| ❌| Match
**Kotlin JUnit** | [![star this repo](https://img.shields.io/github/stars/JetBrains/kotlin.svg)`*`](https://github.com/JetBrains/kotlin/tree/master/libraries/kotlin.test) | ✅ | ✅| ❌| ❌| Match
**Hamcrest** | [![star this repo](https://img.shields.io/github/stars/hamcrest/JavaHamcrest.svg)](https://github.com/hamcrest/JavaHamcrest) | ✅ | ⚠️| ❌| ✅| Match
**Hamkrest** | [![star this repo](https://img.shields.io/github/stars/npryce/hamkrest.svg)](https://github.com/npryce/hamkrest) | ❌ | ⚠️| ✅| ✅| Match
**Google Truth** | [![star this repo](https://img.shields.io/github/stars/google/truth.svg)](https://github.com/google/truth) | ✅ | ⚠️| ❌| ✅| Wrap
**AssertJ** | [![star this repo](https://img.shields.io/github/stars/assertj/assertj-core.svg)](https://github.com/assertj/assertj-core) | ✅ | ❌| ✅| ✅| Wrap (can chain)
**AssertK** | [![star this repo](https://img.shields.io/github/stars/willowtreeapps/assertk.svg)](https://github.com/willowtreeapps/assertk) | ✅ | ✅| ✅| ✅| Wrap (can chain)
**Atrium** | [![star this repo](https://img.shields.io/github/stars/robstoll/atrium.svg)](https://github.com/robstoll/atrium) | ✅ | ❌| ❌| ✅| Wrap (can chain)
**Expekt** | [![star this repo](https://img.shields.io/github/stars/winterbe/expekt.svg)](https://github.com/winterbe/expekt) | ❌ | ✅| ❌| ❌| Extend (can chain)
**Kluent** | [![star this repo](https://img.shields.io/github/stars/MarkusAmshove/Kluent.svg)](https://github.com/MarkusAmshove/Kluent) | ❌ | ✅| ✅| ✅| Extend (can chain/infix)
**Kotest** | [![star this repo](https://img.shields.io/github/stars/kotest/kotest.svg)`*`](https://github.com/kotest/kotest) | ✅ | ⚠️| ✅| ✅| Extend (can chain/infix)
**Strikt** | [![star this repo](https://img.shields.io/github/stars/robfletcher/strikt.svg)](https://github.com/robfletcher/strikt) | ❌ | ❌| ✅| ✅| Wrap (can chain)

`*` This lib is part of a bigger library

### Winners
- **Hamcrest+Kotlin** JUnit for library of Match Type 🏆
- **AssertK** for library of Wrap Type 🏆
- **Kotest** for library of Extend Type 🏆
