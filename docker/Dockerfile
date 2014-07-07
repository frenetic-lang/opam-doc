FROM ubuntu:14.04
MAINTAINER Arjun Guha <arjun@cs.umass.edu>

RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y git make build-essential m4
RUN apt-get install -y ocaml ocaml-native-compilers opam \
  camlp4 camlp4-extra

RUN adduser --disabled-password --gecos "" frenetic

USER frenetic

ENV HOME /home/frenetic
ENV CAML_LD_LIBRARY_PATH /home/frenetic/.opam/system/lib/stublibs:/usr/lib/ocaml/stublibs
ENV PERL5LIB /home/frenetict/.opam/system/lib/perl5
ENV OCAML_TOPLEVEL_PATH /home/frenetic/.opam/system/lib/toplevel
ENV MANPATH /home/frenetic/.opam/system/man:
ENV PATH /home/frenetic/.opam/system/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin

WORKDIR /home/frenetic

RUN opam init -y

RUN echo "eval $(opam config env)" >> ~/.bashrc

RUN opam install -y oasis
RUN opam install -y core
RUN opam install -y async
RUN opam install -y uri stringext re
RUN opam install -y quickcheck
RUN opam install -y pa_ounit
RUN opam install -y cstruct
RUN opam install -y opam-doc
RUN opam install -y ocamlgraph
RUN opam install -y cmdliner
RUN opam doc core async || true
RUN opam repository add frenetic-opam https://github.com/frenetic-lang/opam-bleeding.git
RUN opam doc cstruct || true
RUN opam doc cmdliner || true
RUN opam doc ocamlgraph || true

RUN mkdir /home/frenetic/.ssh
ADD key /home/frenetic/.ssh/id_rsa
ADD key.pub /home/frenetic/.ssh/id_rsa.pub
ADD config /home/frenetic/.ssh/config

USER root
RUN chown -R frenetic:frenetic /home/frenetic/.ssh
RUN chmod -R go-rwx /home/frenetic/.ssh
USER frenetic
RUN ls -l /home/frenetic/.ssh

RUN git config --global user.name "Arjun Guha"
RUN git config --global user.email "arjun+doc-script@cs.umass.edu"
RUN git config --global push.default simple

ADD entrypoint.sh /home/frenetic/entrypoint.sh

ENTRYPOINT /home/frenetic/entrypoint.sh
