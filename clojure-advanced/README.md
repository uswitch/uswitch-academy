## Clojure Advanced Workshop

### Setup

Install pandoc (On MacOS: `brew install pandoc`)
Install gems: `bundle install`

### How

Modify `clojure-advanced.md` and then generate slides.html with:

    pandoc -t html5 --template=template-revealjs.html --no-highlight --section-divs --variable theme="beige" --variable transition="linear" clojure-advanced.md -o slides.html

Alternatively, run `guard` and have `pandoc` running automatically on file changes.
