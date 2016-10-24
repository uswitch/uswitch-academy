# lab07 - testing

Welcome to the Clojure Training Lab number 7. In this lab we are going to test a calculator application which can execute the 4 basic arithmetic operation and square root of a number. You can test first or after, pick your style, but there should be at least one test each function. To help you out with interface, the 5 functions are already available in `src/lab07/calculcator.clj` but they are returning nil.

Please use the already available (and empty) `test/lab07/calculator_test.clj` namespace to start working on your tests. It is expected that the calculator, in case of very long decimal numbers, it will truncate the number at the 10th decimal while printing the number. As an example on how to achieve that, consider: `(format "%.10f" (double (/ 10 3)))`

## auto-test

To make your tester life easier, run `lein test-refresh` in a separate terminal after cd into `lab07`. It will start a process that watches your files for changes and trigger test re-run for a rapid feedback after a change.

## How to compute the square root

Let's forget for a moment about libraries and let's see if we can code our own square root function. The most common way is to use Newton's method of successive approximations, which says that whenever we have a guess y for the value of the square root of a number x, we can perform a simple manipulation to get a better guess (one closer to the actual square root) by averaging y with x/y. For example, we can compute the square root of 4 as follows. Suppose our initial guess is 1:

        1               (4/1) = 4                   ((4 + 1)/2) = 2.5

        2.5             (4/2.5) = 2.6666            ((2.6666 + 2.5)/2) = 2.0833

        2.0833          (4/2.0833) = 1.9200         ((1.9200 + 2.0833)/2) = 2.00165

        2.00165          ...                         ...

Continuing this way for a few iterations, you will obtain a good approximation of the square root. For instance, we could say that when:

        (defn good-enough? [guess x]
          (< (Math/abs (- (* guess guess) x)) 0.001))

returns true, then "guess" is a "good-enough" approximation of the square-root of x. How would you code such an algorithm in Clojure?

## Trying more things out

There's a REPL. Just run:

`lein repl`

in another terminal window.

Here are some interesting commands you might try, once you're in a running REPL:

        (find-doc "vec")
        (find-doc #"vec$")
        (doc vec)

And if those still don't make sense:

        (doc doc)
        (doc find-doc)

will show you what those commands mean.

You can exit the REPL with CTRL-d on any OS.
