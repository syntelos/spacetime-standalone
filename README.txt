
Spacetime Standalone Overview

  An in-memory implementation of spacetime package interfaces
    http://github.com/syntelos/spacetime
  using
    http://code.google.com/p/lxl

  This package presents additional application degrees of freedom than
  expressed in the spacetime interfaces.

  The principal class is Spacetime.  It extends an array list and
  implements the space time index.  It provides multiple feature sets
  for list and map applications.

  The STL class reads and writes Spacetime objects in the Face schema,
  demonstrating the list and xyzt map feature sets.  The STL write
  method is an example of consuming the Face schema, and the STL read
  method is an example of producing the Face schema.


Spacetime Standalone API

  List

    The lxl list API available via the super class of Spacetime
    permits unindexed list operations on the child Spacetime objects
    of a container Spacetime object.

  List index

    When used as an ordered list, the array list structure is indexed
    in the list order for unsigned integer keys.

  XY[Z[T]] index

    The get(Spacetime) and put(Spacetime) methods implemented in the
    Spacetime class permit two, three and four dimensional indexing of
    child Spacetime objects in a container Spacetime object.

  Enum index

    The get(Enum) and put(Enum,Spacetime) methods defined in the
    Spacetime class provide for an enumerated list of children
    contained in a container Spacetime object.

  Comparable index

    The get(Comparable) and put(Comparable,Spacetime) methods defined
    in the Spacetime class provide for a runtime indexed list of
    children contained in a container Spacetime object.


