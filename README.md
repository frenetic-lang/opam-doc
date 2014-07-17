# Frenetic API Documentation

This repository holds scripts to build and host Frenetic API documentation.

The hosted documentation is available here:

[http://arjunguha.github.io](http://arjunguha.github.io)

The scripts in this directory create a Docker image with everything needed to
build Frenetic and run it through `opam-doc` (see `Dockerfile`). Running the
image creates a container that builds OPAM doc on the Frenetic packages and
pushes them to a documentation repository.

# Installation

## Prerequisites

- Docker. The Docker that comes with Ubuntu 14.04 works fine. Follow the
  installation guide (including creating the symlink for Ubuntu):

  https://docs.docker.com/installation/

## Configure GitHub and Build Container

- `cd docker`

- Pick a GitHub Pages repository (e.g., arjunguha.github.io)

- `$ ssh-keygen -f key`

   do not set a passphrase

- Add the file `key.pub` created above to your Github account:

  https://github.com/settings/ssh

- `make`

- Edit the `REPO` and `OPAMDOC_BASE_URI` variables in `entrypoint.sh` to reflect
  the GitHub Pages repository you chose above.

## Build and Deploy Documentation (Manual)

    $ cd docker
    $ make manual

## Build and Deploy Documentation (Github Hook)

- Build the server
      
      $ sbt assembly

- Copy the server library to the server:

      $ scp target/scala-2.10/docs-assembly-0.1-SNAPSHOT.jar ...

- Install a Java runtime on the server.

- Install Docker on the server (both Ubuntu 14.04 and CentOS 6 will work)
  and *configure it to listen on TCP*.

- Create a file called `settings.conf` on the server, based on `example.conf`.
  You have to change *image* and *dockerUrl*

- Start the server:

      $ java docs-assembly-0.1-SNAPSHOT.jar settings.conf

- Configure Github a Github hook to post to `SERVER-URL/github-hook`. Detailed
  settings are unimportant. The system rebuilds on any post, at most once
  every 10 minutes.
