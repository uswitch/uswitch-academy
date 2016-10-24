# lab05 Destructuring

Welcome to the Clojure Training Lab number 5 about destructuring. In this lab we are going through a list of questions that you are supposed to solve by filling the blanks. This form of exercise is called "koans" style.

### Running the Koans

Run the koans via:

`lein koan run`

It's an auto-runner, so as you save your files with the correct answers. You'll see something like this:

    Now meditate on 01_destructuring.clj:3
    ---------------------
    Assertion failed!
    Destructuring is an arbiter: it breaks up arguments
    (= __ ((fn [[a b]] (str b a)) [:foo :bar]))

The output is telling you that you have a failing test in the file named
`01_destructuring.clj`, on line 3. So you just need to open that file up and make
it pass!  You'll always be filling in the blanks to make tests pass.
Sometimes there could be several correct answers (or even an infinite number):
any of them will work in these cases. Some tests will pass even if you replace
the blanks with whitespace (or nothing) instead of the expected answer. Make sure
you give one correct expression to replace each blank.

### Trying more things out

There's a REPL. Just run:

`lein repl` in another terminal window.

Here are some interesting commands you might try, once you're in a running REPL:

```clojure
(find-doc "vec")
(find-doc #"vec$")
(doc vec)
```

And if those still don't make sense:

```clojure
(doc doc)
(doc find-doc)
```

will show you what those commands mean.

You can exit the REPL with `CTRL-d` on any OS.
