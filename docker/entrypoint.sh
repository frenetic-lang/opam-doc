#!/bin/bash
set -x
set -e

export REPO=git@github.com:frenetic-lang/api.git
export OPAMDOC_BASE_URI=http://frenetic-lang.github.io/api/

opam doc packet openflow topology frenetic || true

cd /home/frenetic/.opam/doc/doc
find . -name "*.html" | xargs -n 1 sed -i 's/\/js/http:\/\/frenetic-lang.github.io\/api\/js/g'
find . -name "*.html" | xargs -n 1 sed -i 's/href="\/css/href="http:\/\/frenetic-lang.github.io\/api\/css/g'
find . -name "*.html" | xargs -n 1 sed -i 's/href="\/"/href="http:\/\/frenetic-lang.github.io\/api"/g'

cd /home/frenetic
git clone -b gh-pages $REPO
cd api
rsync -av --delete --exclude=".git" /home/frenetic/.opam/doc/doc/ .
git add --all

DATE=`date`
git commit -am "$DATE"
git push
