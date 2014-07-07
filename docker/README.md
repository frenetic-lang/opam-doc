The scripts in this directory create a Docker image with everything needed to
build Frenetic and run it through `opam-doc` (see `Dockerfile`). Running the
image creates a container that builds OPAM doc on the Frenetic packages and
pushes them to a documentation repository.

Prerequisites
==============

- Docker. The Docker that comes with Ubuntu 14.04 works fine. Follow the
  installation guide (including creating the symlink for Ubuntu):

  https://docs.docker.com/installation/

Configure GitHub and Build Container
====================================

- Pick a GitHub Pages repository (e.g., arjunguha.github.io)

- `$ ssh-keygen -f key`

   do not set a passphrase

- Add the file `key.pub` created above to your Github account:

  https://github.com/settings/ssh

- `make`

- Edit the `REPO` and `OPAMDOC_BASE_URI` variables in `entrypoint.sh` to reflect
  the GitHub Pages repository you chose above.

Build and Deploy Documentation (Manual)
=========================================

    $ make manual

Build and Deploy Documentation (Automatic)
==========================================

Not yet ready.
