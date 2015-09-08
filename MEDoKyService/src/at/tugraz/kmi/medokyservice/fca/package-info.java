/**
 * Formal Concept Analysis Module.
 * Requires a large stack for deserializing (-Xss64m is recommended) because of the way java handles deserialization.
 * @see <a href="http://bugs.sun.com/view_bug.do?bug_id=4152790">
 JDK-4152790 : StackOverFlowError serializing/deserializing a large graph of objects.</a>
 */
package at.tugraz.kmi.medokyservice.fca;