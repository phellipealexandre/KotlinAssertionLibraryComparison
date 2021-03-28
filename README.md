# Kotlin Assertion Library Comparison
The purpose of this repository is to serve as a case study for the various Kotlin 
assertion libraries we have available today. A comparison was conducted based on the following criteria:
- **Popularity**: Based on GitHub stars and time of creation.
- **Documentation**: Quality of material found on how to use and how to customize the library.
- **Speed**: Time added to execute an unit-test assertion compared to other libraries.
- **Fluency / Kotlin idiomatic**: Readability and Kotlin-like style.
- **Customizability**: If it could be customized easily.

Some of the criteria can be biased on personal subjectiveness and understanding. The results reflect my own opinion.

### Types of assertion

- **Match** ```assertion(expected, actual)```
- **Wrap** ```assert(actual).assertion(expected)```
- **Extend** ```actual.assert(expected)``` or ```actual assert expected```

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

### Result Matchers

Lib | Popularity | Documentation | Performance | Fluency | Customizability | Type
--- | --- | --- | --- | --- | --- | --- 
**#1 Kotlin JUnit 🏆** | [![star this repo](https://img.shields.io/github/stars/JetBrains/kotlin.svg)`*`](https://github.com/JetBrains/kotlin/tree/master/libraries/kotlin.test) | ✅ | ✅| ❌| ❌| Match
**#2 JUnit 🏆** | [![star this repo](https://img.shields.io/github/stars/junit-team/junit.svg)`*`](https://github.com/junit-team/junit4) | ✅ | ✅| ❌| ❌| Match
**#3 Hamcrest 🏆** | [![star this repo](https://img.shields.io/github/stars/hamcrest/JavaHamcrest.svg)](https://github.com/hamcrest/JavaHamcrest) | ✅ | ⚠️| ❌| ✅| Match
**#4 Hamkrest 🏆** | [![star this repo](https://img.shields.io/github/stars/npryce/hamkrest.svg)](https://github.com/npryce/hamkrest) | ❌ | ⚠️| ✅| ✅| Match

### Result Wrappers

Lib | Popularity | Documentation | Performance | Fluency | Customizability | Type
--- | --- | --- | --- | --- | --- | --- 
**#1 AssertK 🏆** | [![star this repo](https://img.shields.io/github/stars/willowtreeapps/assertk.svg)](https://github.com/willowtreeapps/assertk) | ✅ | ✅| ✅| ✅| Wrap (can chain)
**#2 Google Truth 🏆** | [![star this repo](https://img.shields.io/github/stars/google/truth.svg)](https://github.com/google/truth) | ✅ | ⚠️| ❌| ✅| Wrap
**#3 AssertJ 🏆** | [![star this repo](https://img.shields.io/github/stars/assertj/assertj-core.svg)](https://github.com/assertj/assertj-core) | ✅ | ❌| ✅| ✅| Wrap (can chain)
**#4 Atrium 🏅** | [![star this repo](https://img.shields.io/github/stars/robstoll/atrium.svg)](https://github.com/robstoll/atrium) | ✅ | ❌| ❌| ✅| Wrap (can chain)
**#5 Strikt 🏅** | [![star this repo](https://img.shields.io/github/stars/robfletcher/strikt.svg)](https://github.com/robfletcher/strikt) | ❌ | ❌| ✅| ✅| Wrap (can chain)

### Result Extenders

Lib | Popularity | Documentation | Performance | Fluency | Customizability | Type
--- | --- | --- | --- | --- | --- | --- 
**#1 Kotest 🏆** | [![star this repo](https://img.shields.io/github/stars/kotest/kotest.svg)`*`](https://github.com/kotest/kotest) | ✅ | ⚠️| ✅| ✅| Extend (can chain/infix)
**#2 Kluent 🏆** | [![star this repo](https://img.shields.io/github/stars/MarkusAmshove/Kluent.svg)](https://github.com/MarkusAmshove/Kluent) | ❌ | ✅| ✅| ✅| Extend (can chain/infix)
**#3 Expekt 🏅** | [![star this repo](https://img.shields.io/github/stars/winterbe/expekt.svg)](https://github.com/winterbe/expekt) | ❌ | ✅| ❌| ❌| Extend (can chain)

### Notes
`*` This lib is part of a bigger library

✅ Consistent positive result.

❌ Consistent negative result (in my opinion and analysis).

⚠️ Inconclusive result.

🏆 Great Lib.

🏅 Good but use it with caution.
