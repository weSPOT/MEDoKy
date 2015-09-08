
<!-- saved from url=(0051)http://colibri-java.googlecode.com/svn/trunk/README -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"><style type="text/css"></style></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">
== Colibi-Java ==

Colibri-Java is an implementation of Formal Concept Analysis in Java.
Formal concept analysis is an algebraic theory for binary relation,
which can be represented as cross tables. It identifies all maximal
rectangles in such a table; these rectangles from a hierarchy, the
so-called concept lattice. The concept lattice gives insight into the
original cross table.

The implementation of formal concept analysis is designed as a library
but includes a small demo application that computes the concept lattice
for a relation read from a file.

== Overview ==

* `colibri/`    Java implementation
* `doc/`        Papers

The `doc/` directory contains Daniel Götzmann's Bachelor Thesis (in
German) about this implementation. In addition, a paper by Christian
Lindig explains the idea how to use formal concept analysis to mine data
for rules and exceptions from these rules. The implementation provides
iterators for this purpose.

== See Also ==

A similar library like this but implemented in Objective Caml can be 
found at http://code.google.com/p/colibri-ml/. An older and less
advanced implementation in C can be found at
http://code.google.com/p/colibri-concepts/.

== Author ==

Daniel Götzmann     &lt;dngoetzmann@googlemail.com&gt;
Christian Lindig    &lt;lindig@gmail.com&gt;

== Copyright ==

Colibri-Java is published unter the GNU General Public License 2.0.


</pre></body><style type="text/css">embed[type*="application/x-shockwave-flash"],embed[src*=".swf"],object[type*="application/x-shockwave-flash"],object[codetype*="application/x-shockwave-flash"],object[src*=".swf"],object[codebase*="swflash.cab"],object[classid*="D27CDB6E-AE6D-11cf-96B8-444553540000"],object[classid*="d27cdb6e-ae6d-11cf-96b8-444553540000"],object[classid*="D27CDB6E-AE6D-11cf-96B8-444553540000"]{	display: none !important;}</style></html>