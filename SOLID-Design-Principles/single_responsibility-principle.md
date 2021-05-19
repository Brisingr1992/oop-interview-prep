#### Single Responsibility Principle

- A class should have a single concern and if we include too many concerns with a class, it can lead to a god object which is an anti-pattern
- A typical example of a concern can be: A journal class's only concern should be things related to journal like reading / writing the entries inside.
- Like say we introduce persistance concern like saving to a database, loading from a file or URL to Journal class.
    1. In time the constant change can make this class unreadable and too dependent!
    2. If there are no other classes for persistance, it can lead to code duplication and also changing logic at several different places with one change.
- This principle helps with keeping the code DRY, manageable, easier to understand and easier to refactor!