# Kotlin Assertion Library Comparison
What is the best Kotlin assertion library? 

The purpose of this repository is to serve as a case study for the various Kotlin
assertion libraries we have available today. A comparison was conducted based on the following criteria:
- **Popularity**: Based on GitHub stars and time of creation.
- **Documentation**: Quality of material found on how to use and how to customize the library.
- **Performance**: Time added to execute an unit-test assertion compared to other libraries.
- **Fluency / Kotlin idiomatic**: Readability and Kotlin-like style.
- **Customizability**: If it could be customized easily.
- **Kotlin Multi Platform**: If it could be used in KMM projects.

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

# How to run the project
If you wish to run the microbenchmark, with an emulator running use the following command:

```
./gradlew assertionbenchmark:connectedCheck --info
```

If you wish just to run the unit tests, use the following command:

```
./gradlew benchmarkable:testDebugUnitTest
```

Although you can run the tests on the terminal, the results are best seen when running in the IDE. 

# Results
## Performance results
Performance was measured using the [Android Micro Benchmark](https://developer.android.com/topic/performance/benchmarking/microbenchmark-overview) 
library. For each scenario, the test configuration and action are the same, the only thing that changes is the assertion.

### Scenario 1 - Simple assert equals
Lib | test 1 (sum)
--- | ---
**JUnit** | 2.9 ns
**Kotlin JUnit** | 2.9 ns
**Truthish** | 14.8 ns
**Hamcrest** | 15.5 ns
**Hamkrest** | 18.5 ns
**Kotest** | 22.6 ns
**AssertJ** | 96.2 ns
**Expekt** | 100 ns
**AssertK** | 115 ns
**Google Truth** | 124 ns
**Kluent** | 150 ns
**Atrium** | 390 ns
**Strikt** | 1,250 ns

### Scenario 2 - Complex list assertion
Lib | Test 1 (remove) | Test 2 (favorite only) | test 3 (no favorite) | test 4 (favorite and non-favorite)
--- |-----------------|------------------------|----------------------| ---
**JUnit** | 988 ns          | 852 ns                 | 770 ns               | 1,113 ns
**Kotlin JUnit** | 1,142 ns        | 1,011 ns               | 933 ns               | 1,348 ns
**Truthish** | 1,462 ns        | 1,486 ns               | 1,331 ns             | 2,482 ns
**AssertK** | 1,658 ns        | 1,533 ns               | 1,050 ns             | 1,919 ns
**Google Truth** | 2,382 ns        | 2,371 ns               | 1,081 ns             | 2,428 ns
**Expekt** | 3,507 ns        | 3,361 ns               | 1,342 ns             | 4,725 ns
**Atrium** | 1,393 ns        | 24,086 ns              | 7,712 ns             | 34,373 ns
**AssertJ** | 11,545 ns       | 11,273 ns              | 6,333 ns             | 15,429
**Kluent** | 18,990 ns       | 17,689 ns              | 1,551 ns             | 27,798 ns
**Kotest** | 37,103 ns       | 32,497 ns              | 36,240 ns            | 49,885 ns
**Strikt** | 47,405 ns       | 47,209 ns              | 45,645 ns            | 49,009 ns 
**Hamkrest** | 3,783,240 ns    | 2,385,701 ns           | 2,297,980 ns         | 3,294,005 ns
**Hamcrest** | N/A             | N/A                    | N/A                  | N/A

Note: Had troubles setting up latest hamcrest version on instrumented tests as it has conflicts with some
androidx test dependencies. Using the contains() method didn't work and trying to resolve the conflicts 
didn't work either

### Scenario 3 - Assertion with coroutines
Lib | Test 1 (success) | Test 2 (error)
--- |------------------| ---
**JUnit** | 157 ns           | 23 ns
**Hamcrest** | 179 ns           | 61.8 ns
**Hamkrest** | 191 ns           | 79.6 ns
**AssertK** | 224 ns           | 98.3 ns
**Kotlin JUnit** | 235 ns           | 108 ns
**Google Truth** | 430 ns           | 247 ns
**Kotest** | 479 ns           | 326 ns
**Truthish** | 551 ns           | 214 ns
**Strikt** | 551 ns           | 452 ns
**Expekt** | 973 ns           | 685 ns
**AssertJ** | 271 ns           | 2,903 ns
**Kluent** | 1,555 ns         | 1,188 ns
**Atrium** | 4,432 ns         | 1,846 ns

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
**#3 Truthish 🏅** | [![star this repo](https://img.shields.io/github/stars/varabyte/truthish.svg)](https://github.com/varabyte/truthish) | ❌ | ⚠️| ❌| ✅| ✅
**#4 AssertJ 🏅** | [![star this repo](https://img.shields.io/github/stars/assertj/assertj-core.svg)](https://github.com/assertj/assertj-core) | ✅ | ❌| ✅| ✅| ❌
**#5 Atrium 🏅** | [![star this repo](https://img.shields.io/github/stars/robstoll/atrium.svg)](https://github.com/robstoll/atrium) | ✅ | ❌| ❌| ✅| ✅
**#6 Strikt 🏅** | [![star this repo](https://img.shields.io/github/stars/robfletcher/strikt.svg)](https://github.com/robfletcher/strikt) | ❌ | ❌| ✅| ✅| ❌

### Result Extenders

Lib | Popularity | Documentation | Performance | Fluency | Customizability | KMM
--- | --- | --- | --- | --- | --- | --- 
**#1 Kotest 🏆** | [![star this repo](https://img.shields.io/github/stars/kotest/kotest.svg)`*`](https://github.com/kotest/kotest) | ✅ | ⚠️| ✅| ✅| ✅
**#2 Kluent 🏆** | [![star this repo](https://img.shields.io/github/stars/MarkusAmshove/Kluent.svg)](https://github.com/MarkusAmshove/Kluent) | ❌ | ⚠️| ✅| ✅| ✅
**#3 Expekt 🏅** | [![star this repo](https://img.shields.io/github/stars/winterbe/expekt.svg)](https://github.com/winterbe/expekt) | ❌ | ⚠️| ❌| ❌| ❌

### Notes
`*` This lib is part of a bigger library

✅ Consistent positive result.

❌ Consistent negative result (in my opinion and analysis).

⚠️ Inconclusive result.

🏆 Great Lib.

🏅 Good but use it with caution.
