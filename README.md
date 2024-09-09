# levelcache

Levelcache is a thread-safe, dynamic multi-level caching system designed to efficiently manage data across various cache layers. It supports the dynamic addition of cache levels, hybrid eviction policies, and concurrent read/write operations, ensuring optimized data retrieval across levels.

Multilevel caching strategies ideally found in hardware systems (i.e. Computer Processors, CDNs, Web servers) to balance the trade-offs between speed, memory capacity, and cost. Each level stores data at different speeds and capacities, with higher levels (closer to the processor) being faster but smaller, and lower levels being slower but larger.

![level caching](doc/multi-level-caching.png)

The prototype requirements can be found [here](doc/requirements.md).

# Design considerations
The system's design emphasizes flexibility, efficiency, and scalability. A key aspect is the use of the builder pattern via `ConfigurationBuilder`, which allows for seamless customization of cache-related settings for each `LevelCache` instance. At the core of the system is the `CacheFactory`, which handles the creation and initialization of `LevelCache` instances based on the specified configurations. The `LevelCache` class offers a well-defined API, supporting operations like adding and removing cache levels, storing and retrieving data, displaying cache states, and performing bulk reads/writes. Each level is efficiently managed through `CacheUnit`, which holds the level's details and supports multiple eviction policies, making it highly extensible. The `CacheUnitProvider` supplies pre-configured `CacheUnit` instances with the appropriate eviction policy. This policy determines how the cache handles storage and key-value invalidation. Overall, the system is built for extensibility, with support for dynamic cache levels and eviction policies, and promotes loose coupling through the use of DefaultConfigBuilder.

# Prerequisite
* JDK 1.8
* Maven
* Junit 5

# Setup
```bash
# Get the code
$ git clone https://github.com/s-bose7/levelcache.git .
# Install maven dependencies
$ mvn clean install
# Run the tests
$ mvn test
```

# Example
```java
```

# Benchmarks