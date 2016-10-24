# lab02 Functional Programming Concepts

Welcome to the Clojure Training Lab number 2 about functional programming concepts. In this lab we have a few questions that you can answer just editing this README and a simple exercise.

## Questions

Given an expression like:

    ((fun1 operator) (fun2 operand) (fun3 operand))

* What evaluation process is this going to trigger?
* Is not important for now that fun1, fun2, fun3 aren't defined anywhere. Try to answer without jumping at the REPL first. Consider evaluation rules and order in Clojure (inner to outer, left to right). Also consider that anything in parenthesis in Clojure is a function call (unless specified otherwise).
* Bonus: what fun1 is expeted to return for this expression to work properly?

## Exercise

Write a function "flip" that takes a function f of two arguments (x,y) and return another function g of the inverted argument (y,x) instead. For example:

    $> ((flip /) 3 6)
    $> 2

Here's the output of a flip function that takes "/" (the division operation) and invert its operands to return 2 instead of 1/2. How such a function should be written? Write your answer in the file called `src/lab02/flip.clj`

It might help you to know that anonymous functions (also called lambdas) can be created in Clojure like this:

    (fn [x] (* x x))

which is the square(x) function although we didn't give it a name.
