# Code Coverage

## Learning Goals

- Use IntelliJ to perform code coverage analysis of Junit tests

## Code Along

Fork and clone this lesson.  We will use IntelliJ
to measure the degree of coverage of Junit tests.

## Introduction

Consider the following code, which has an error in it.
The programmer copied the assignment statement from the `if` into
the `else` and forgot to update the variable from `x` to `y`.

```java
public class BuggyExample1 {

    public static int smallest(int x, int y){
        int smallest ;
        if (x < y) {
            smallest = x;
        }
        else {
            smallest = x;  //error, should be y
        }
        return smallest;

    }

}
```

The Junit class to test the method is shown below.
The test method calls the `BuggyExample1.smallest` static method
one time with parameters `4` and `10`.

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BuggyExample1Test {

    @Test
    public void smallest() {
        assertEquals(4, BuggyExample1.smallest(4, 10));
    }

}
```

- Right-click on the `BuggyExample1Test` class and select `run` to
  execute the test.

![run](https://curriculum-content.s3.amazonaws.com/6676/coverage/run.png)

The Junit test passes, but does this mean our code
is correct? Since we know there is a bug in the code, obviously this test
is not sufficient for finding it.  Let's see how code coverage techniques
can help.

## Statement Coverage

**Statement Coverage** requires enough tests to force every executable
statement in the source code to be executed at least once.  **Line coverage**
is very similar to statement coverage, requiring every executable line
of code to be executed.  While there are slight differences between
line and statement coverage, for our purposes we will treat them as the same.

The `smallest` method has 4 executable lines of code numbered 5, 6, 9 and 11:

![smallest method with line numbers](https://curriculum-content.s3.amazonaws.com/6676/coverage/smallest_method.png)

If we view the method as a flowchart, we see there are two possible
execution paths through the code:

- A path executing lines 5, 6, 11 (x < y is true)
- A path executing lines 5, 9, 11 (x < y is false)


Our Junit test called `BuggyExample1.smallest(4, 10)`, which executed
the `true` branch resulting in path `5, 6, 11`.  However, the test
does not cause the `false` branch (i.e. the `else` clause) to
execute the incorrect assignment statement at line `9`.

![buggy flowchart1](https://curriculum-content.s3.amazonaws.com/6676/coverage/buggy_flowchart1.png)

### IntelliJ Coverage Runner

IntelliJ comes bundled with a code coverage runner that measures
the percent of class, function (method), and line coverage.

Right-click on the `BuggyExample1Test` Junit class and select `Run BuggyExample1Test with Coverage`.

![run with coverage step 0](https://curriculum-content.s3.amazonaws.com/6676/coverage/run_coverage0.png)

A new view is displayed containing the coverage metrics.
The Junit class `BuggyExample1Test` calls methods
in class `BuggyExample1` but not `BuggyExample2`:

- 100% of classes in `BuggyExample1` were executed (the `BuggyExample1` class itself).
- 100% of methods in `BuggyExample1` were executed (the `smallest` method).
- 75% of executable lines in `BuggyExample1` were executed (lines `5, 6, 11`).


![run with coverage step 1](https://curriculum-content.s3.amazonaws.com/6676/coverage/run_coverage1.png)

We can view the executed lines by double-clicking on the `BuggyExample1`
class in the coverage view:

![run with coverage step 2](https://curriculum-content.s3.amazonaws.com/6676/coverage/run_coverage2.png)

Lines 5, 6, and 11 highlighted in green were executed, while line 9 highlighted in red was not.

We did not achieve 100% line coverage, so we should add more tests.
Update the Junit test method to add another call where the first parameter
is larger than the second:

```java
@Test
public void smallest() {
  assertEquals(4, BuggyExample1.smallest(4, 10));
  assertEquals(8, BuggyExample1.smallest(20, 8));
}
```

We'll rerun the test by selecting `Run BuggyExample1Test with Coverage`.
Confirm that 100% line coverage is achieved.  Notice also
that the test fails since the expected value `8` does not match
the actual value returned from the method `20`.  The
failed test tells us there is an error in the method that we need to fix.


![run with coverage step 3](https://curriculum-content.s3.amazonaws.com/6676/coverage/run_coverage3.png)


Let's fix the assignment statement in the `else` branch:

```java
  public static int smallest(int x, int y){
      int smallest ;
      if (x < y) {
          smallest = x;
      }
      else {
          smallest = y;  
      }
      return smallest;

  }
```

Now the Junit test should pass, and we have 100% line coverage.

![run](https://curriculum-content.s3.amazonaws.com/6676/coverage/run.png)


## Branch Coverage

Let's look at another example that shows we may still have errors
in our code even if the Junit tests execute every line.

```java
public class BuggyExample2 {

    public static boolean isEven(int x) {
        boolean result = true;   //Error, should be false
        if (x % 2 == 0)
            result = true;
        return result;
    }

}
```

The `isEven` method returns a boolean value indicating whether the parameter
value is an even number. The `result` local variable is incorrectly initialized
to `true` rather than `false`.  The method should return `false`
if the parameter is odd, but the method always returns `true` due to
the incorrect initialization.

The Junit test class `BuggyExample2Test` calls the method once, passing
an even number `8` as a parameter.

```java
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuggyExample2Test {
    @Test
    public void isEven() {
        assertTrue(BuggyExample2.isEven(8));
    }

}
```

Right-click on the Junit class `BuggyExample2Test` and select `Run BuggyExample2Test with Coverage`.

Notice we achieve 100% line coverage of the `BuggyExample2` class and the test
passes.

![run coverage step 4](https://curriculum-content.s3.amazonaws.com/6676/coverage/run_coverage4.png)

Since we know where is an error in the code, this tells us we need a stronger
measure of coverage to find our error.

Let's consider the possible execution paths through the `isEven` method.
The method has 4 executable lines of code numbered 4, 5, 6, and 7:

![iseven method with line numbers](https://curriculum-content.s3.amazonaws.com/6676/coverage/iseven_method.png)

If we view the method as a flowchart, we see there are two possible
execution paths through the code:

- A path executing lines 4, 5, 6, 7 (x % 2 == 0 is true)
- A path executing lines 4, 5, 7 (x % 2 == 0 is false)

![buggy flowchart2](https://curriculum-content.s3.amazonaws.com/6676/coverage/buggy_flowchart2.png)


**Branch coverage** ensures the tests cause all branches
of all control structures in a program to be executed at least once.
Branch coverage requires at least one test that causes the boolean condition
in the `if` statement to evaluate to `true`, and at least one test that causes it to
evaluate to `false`.

### Jacoco Coverage Runner

The default IntelliJ coverage runner does not include a branch coverage metric.
However, there is another runner named **Jacoco** (Java Code Coverage) that does.

Right-click on the `BuggyExample2Test` class and select "Modify Run Configuration".

![modify run configuration](https://curriculum-content.s3.amazonaws.com/6676/coverage/modify_run.png)

Click on the link titled "Modify Options".

![modify options](https://curriculum-content.s3.amazonaws.com/6676/coverage/modify_options.png)

Select "Specify alternative coverage runner".

![alternative runner](https://curriculum-content.s3.amazonaws.com/6676/coverage/alternative_runner.png)

Select "Jacoco" and then press the "OK" button.

![jacoco runner](https://curriculum-content.s3.amazonaws.com/6676/coverage/jacoco_runner.png)

Right-click on the Junit class `BuggyExample2Test` and select `Run BuggyExample2Test with Coverage`.
The coverage view now includes an additional metric for branch coverage.
We can see only the test only coverage 50% of branches since the
test never executes the branch where `x % 2 == 0` is false.

![branch coverage step 0](https://curriculum-content.s3.amazonaws.com/6676/coverage/branch_coverage0.png)


Notice line 5 (the if condition) is not a dark red, since the line was executed.
However, it is a light red to indicate not every branch was executed.


We did not achieve 100% branch coverage, so we should add more tests.
Update the Junit test method to add another call where the parameter
is an odd number:

```java
@Test
public void isEven() {
    assertTrue(BuggyExample2.isEven(8));
    assertFalse(BuggyExample2.isEven(17));
}
```

We'll rerun the test by selecting `Run BuggyExample1Test with Coverage`.
Confirm that 100% branch coverage is achieved.  Notice also
that the test fails since the expected value `false` does not match
the actual value returned from the method `true`.  The
failed test tells us there is an error in the method that we need to fix.

![branch coverage step#1](https://curriculum-content.s3.amazonaws.com/6676/coverage/branch_coverage1.png)

Let's fix the initialization error in `BuggyExample2`:

```java
public class BuggyExample2 {

    public static boolean isEven(int x) {
        boolean result = false;  
        if (x % 2 == 0)
            result = true;
        return result;
    }
}
```

If we rerun the test with coverage, we see the test passes, and we have 100% line and branch coverage.

![branch coverage step2](https://curriculum-content.s3.amazonaws.com/6676/coverage/branch_coverage2.png)

Does 100% branch coverage ensure we've found all structural errors?
Unfortunately, no. We would have to test all paths through a program,
which is not possible in most cases.

## Conclusion

White-box testing focusing on testing the structure of a program.
Code coverage is a white-box testing technique that measures

White box testing techniques are based on code coverage,
which measures how much source code is executed by the tests.
This lesson demonstrated how to use IntelliJ's coverage runners for
different types of coverage:

- **Function coverage:** The tests cause each function (method) to be executed at least once.
- **Statement Coverage:** The tests cause all executable statements in a program to be executed at least once.
- **Branch Coverage:** The tests cause all branches of all control structures in a program to be executed at least once.

## Resources

- [IntelliJ Code Coverage](https://www.jetbrains.com/help/idea/code-coverage.html.com)
- [Code Coverage](https://en.wikipedia.org/wiki/Code_coverage)
