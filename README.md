reflection-matcher
==================

Compare two graph of objects and find differences

Supports:
* primitives
* nested objects with no depth limit
* primitive and object arrays
* arrays of arrays with no depth limit
* does not loops on cycles
* exclusion of some fields
* custom comparisons

The base comparison will check for null, check class equality and then use reflection to access members and compare them recursively.