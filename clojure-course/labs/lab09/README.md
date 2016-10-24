# lab09 Object Oriented Clojure

Welcome to the Clojure Training Lab number 9 about polymorphic dispatch in Clojure. This lab is again based on Koans.

### Running the Koans

Run the koans via:

`lein koan run`

It's an auto-runner, so as you save your files with the correct answers. You'll see something like this:

    Now meditate upon /me/prj/dm/internal-training/clj/labs/lab09/src/koans/01_runtime_polymorphism.clj:19
    ---------------------
    Assertion failed!
    Some functions can be used in different ways - with no arguments
    (= __ (hello))

The output is telling you that you have a failing test in the file named
`01_runtime_polymorphism.clj`, on line 19. So you just need to open that file up and make
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
