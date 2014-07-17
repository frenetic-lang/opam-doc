#!/bin/bash
set -x
set -e

export REPO=git@github.com:arjunguha/arjunguha.github.io.git
export OPAMDOC_BASE_URI=http://arjunguha.github.io

opam doc packet openflow topology frenetic || true

git clone $(REPO)
cd arjunguha.github.io
rsync -av --delete --exclude=".git" /home/frenetic/.opam/doc/doc/ .
git add --all

DATE=`date`
git commit -am "$DATE"
git push
