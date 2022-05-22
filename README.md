# Kotlin Assertion Library Comparison
The purpose of this repository is to serve as a case study for the various Kotlin
assertion libraries we have available today. A comparison was conducted based on the following criteria:
- **Popularity**: Based on GitHub stars and time of creation.
- **Documentation**: Quality of material found on how to use and how to customize the library.
- **Speed**: Time added to execute an unit-test assertion compared to other libraries.
- **Fluency / Kotlin idiomatic**: Readability and Kotlin-like style.
- **Customizability**: If it could be customized easily.

Some of the criteria in this report can be biased on my personal subjectiveness and understanding. The results reflect my own opinion and analysis.

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
- [Truthish](https://github.com/varabyte/truthish)

### Scenarios
All libraries were tested under a set of scenarios. You can find all of them in this codebase:
- **Scenario 1**: Perform assertion on a simple integer sum.
- **Scenario 2**: Perform a complex list assertion validating instance type, value and order.
- **Scenario 3**: Perform assertion on a ViewState element simulating an Android ViewModel with coroutines.

# Results
## Performance results
Performance was measured using the [Android Micro Benchmark](https://developer.android.com/topic/performance/benchmarking/microbenchmark-overview) 
library. For each scenario, the test configuration and action are the same, the only thing that changes is the assertion.

### Scenario 1 - Simple assert equals
Lib | test 1 (number assertion)
--- | ---
**JUnit** | 2.9 ns - 0 allocs
**Kotlin JUnit** | 3.0 ns - 0 allocs
**Hamcrest** | 15.3 ns - 2 allocs
**Hamkrest** | 17.4 ns - 2 allocs
**Truthish** | 17.7 ns - 2 allocs
**Kotest** | 26.4 ns - 1 allocs
**Expekt** | 82.0 ns - 5 allocs
**AssertJ** | 104 ns - 6 allocs
**AssertK** | 117 ns - 6 allocs
**Google Truth** | 128 ns - 6 allocs
**Kluent** | 147 ns - 4 allocs
**Atrium** | 388 ns - 17 allocs
**Strikt** | 1,259 ns - 37 allocs

### Scenario 2 - Complex list assertion
Lib | Test 1 (remove) | Test 2 (favorite only) | test 3 (no favorite) | test 4 (favorite and non-favorite)
--- | --- | --- | --- | ---
**JUnit** | 994 ns - 45 allocs | 878 ns - 37 allocs | 783 ns - 30 allocs | 1,075 ns - 50 allocs
**Kotlin JUnit** | 1,211 ns - 53 allocs | 1,072 ns - 45 allocs | 975 ns - 35 allocs | 1,417 ns - 62 allocs
**AssertK** | 1,668 ns - 72 allocs | 1,540 ns - 64 allocs | 1,026 ns - 41 allocs | 1,955 ns - 83 allocs
**Google Truth** | 2,443 ns - 112 allocs | 2,435 ns - 108 allocs | 1,107 ns -  43 allocs | 2,587 ns - 114 allocs
**Expekt** | 3,608 ns - 115 allocs | 3,427 ns - 107 allocs | 1,325 ns -  50 allocs | 4,841 ns - 146 allocs
**Truthish** | 4,720 ns - 73 allocs | 4,579 ns - 65 allocs | 4,424 ns - 55 allocs | 8,103 ns - 95 allocs
**Atrium** | 1,472 ns - 73 allocs | 23,952 ns - 858 allocs | 8,498 ns - 306 allocs | 34,618 ns - 1212 allocs
**Kluent** | 19,087 ns - 287 allocs | 18,010 ns - 278 allocs | 1,552 ns - 50 allocs | 28,013 ns - 402 allocs
**AssertJ** | 20,920 ns - 558 allocs | 20,822 ns - 550 allocs | 6,378 ns - 246 allocs | 29,909 ns - 791 allocs
**Kotest** | 38,969 ns - 663 allocs | 39,137 ns - 653 allocs | 38,209 ns - 638 allocs | 53,083 ns - 892 allocs
**Strikt** | 49,244 ns - 606 allocs | 48,000 ns - 598 allocs | 47,268 ns - 581 allocs | 50,931 ns - 666 allocs 
**Hamkrest** | 3,815,845 ns - 15997 allocs | 2,385,221 ns - 10074 allocs | 2,328,379 ns - 9643 allocs | 3,334,597 ns - 14147 allocs
**Hamcrest** | N/A | N/A | N/A | N/A

Note: Had troubles setting up latest hamcrest version on instrumented tests as it has conflicts with some
androidx test dependencies. Using the contains() method didn't work and trying to resolve the conflicts 
didn't work either

### Scenario 3 - Assertion with coroutines
Lib | Test 1 (success) | Test 2 (error)
--- | --- | ---
**JUnit** | 155 ns - 5 allocs | 22.7 ns - 1 allocs
**Hamcrest** | 181 ns - 7 allocs | 60.7 ns - 3 allocs
**Hamkrest** | 195 ns - 8 allocs | 75.5 ns - 4 allocs
**AssertK** | 213 ns - 11 allocs | 108 ns -  7 allocs
**Kotlin JUnit** | 235 ns - 8 allocs | 110 ns - 4 allocs
**Google Truth** | 407 ns - 17 allocs | 271 ns - 13 allocs
**Kotest** | 436 ns - 7 allocs | 326 ns - 3 allocs
**Truthish** | 551 ns - 9 allocs | 214 ns - 5 allocs
**Strikt** | 542 ns - 32 allocs | 452 ns - 28 allocs
**Expekt** | 1,007 ns - 33 allocs | 657 ns - 21 allocs
**AssertJ** | 304 ns - 14 allocs | 2,674 ns - 76 allocs
**Kluent** | 1,538 ns - 34 allocs | 1,150 ns - 22 allocs
**Atrium** | 3,725 ns  - 149 allocs | 1,903 ns  - 75 allocs

Note: Had trouble setting a benchmark that launches a coroutine (runTest). Did a slightly different
setup here just benchmarking the assertion and not the whole test

## Result Matchers

Lib | Popularity | Documentation | Performance | Fluency | Customizability | KMM
--- | --- | --- | --- | --- | --- | --- 
**#1 Kotlin JUnit 🏆** | [![star this repo](https://img.shields.io/github/stars/JetBrains/kotlin.svg)`*`](https://github.com/JetBrains/kotlin/tree/master/libraries/kotlin.test) | ✅ | ✅| ❌| ❌| ✅
**#2 JUnit 🏆** | [![star this repo](https://img.shields.io/github/stars/junit-team/junit.svg)`*`](https://github.com/junit-team/junit4) | ✅ | ✅| ❌| ❌| ❌
**#3 Hamcrest 🏅** | [![star this repo](https://img.shields.io/github/stars/hamcrest/JavaHamcrest.svg)](https://github.com/hamcrest/JavaHamcrest) | ✅ | ✅️| ❌| ✅| ❌
**#4 Hamkrest 🏅** | [![star this repo](https://img.shields.io/github/stars/npryce/hamkrest.svg)](https://github.com/npryce/hamkrest) | ❌ | ⚠️| ✅| ✅| ❌

### Result Wrappers

Lib | Popularity | Documentation | Performance | Fluency | Customizability | KMM
--- | --- | --- | --- | --- | --- | --- 
**#1 AssertK 🏆** | [![star this repo](https://img.shields.io/github/stars/willowtreeapps/assertk.svg)](https://github.com/willowtreeapps/assertk) | ✅ | ✅| ✅| ✅| ✅
**#2 Google Truth 🏆** | [![star this repo](https://img.shields.io/github/stars/google/truth.svg)](https://github.com/google/truth) | ✅ | ✅️| ❌| ✅| ❌
**#3 Truthish 🏅** | [![star this repo](https://img.shields.io/github/stars/robfletcher/strikt.svg)](https://github.com/varabyte/truthish) | ❌ | ⚠️| ✅| ✅| ✅
**#4 AssertJ 🏅** | [![star this repo](https://img.shields.io/github/stars/assertj/assertj-core.svg)](https://github.com/assertj/assertj-core) | ✅ | ❌| ✅| ✅| ❌
**#5 Atrium 🏅** | [![star this repo](https://img.shields.io/github/stars/robstoll/atrium.svg)](https://github.com/robstoll/atrium) | ✅ | ❌| ❌| ✅| ✅
**#6 Strikt 🏅** | [![star this repo](https://img.shields.io/github/stars/robfletcher/strikt.svg)](https://github.com/robfletcher/strikt) | ❌ | ❌| ✅| ✅| ❌

### Result Extenders

Lib | Popularity | Documentation | Performance | Fluency | Customizability | KMM
--- | --- | --- | --- | --- | --- | --- 
**#1 Kotest 🏆** | [![star this repo](https://img.shields.io/github/stars/kotest/kotest.svg)`*`](https://github.com/kotest/kotest) | ✅ | ⚠️| ✅| ✅| ✅
**#2 Kluent 🏆** | [![star this repo](https://img.shields.io/github/stars/MarkusAmshove/Kluent.svg)](https://github.com/MarkusAmshove/Kluent) | ❌ | ⚠️| ✅| ✅| ✅
**#3 Expekt 🏅** | [![star this repo](https://img.shields.io/github/stars/winterbe/expekt.svg)](https://github.com/winterbe/expekt) | ❌ | ✅| ❌| ❌| ❌

### Notes
`*` This lib is part of a bigger library

✅ Consistent positive result.

❌ Consistent negative result (in my opinion and analysis).

⚠️ Inconclusive result.

🏆 Great Lib.

🏅 Good but use it with caution.
