# Machine Coding Question: Dynamic Multilevel Caching System

## Objective
Design and implement a dynamic multilevel caching system that efficiently manages data across multiple cache levels. The system should support dynamic addition of cache levels, eviction policies, and data retrieval across these levels.

## Requirements

### 1. Cache Levels
- The system should allow multiple cache levels (L1, L2, ..., Ln).
- Each cache level can have its own size (i.e., number of entries it can hold).
- Data should be retrieved from the highest-priority cache level (L1) first. If the data is not found in L1, it should be fetched from lower levels sequentially until found, or return a cache miss.

### 2. Eviction Policies
- Each cache level will have the same eviction policy.
- Implement support for at least one of these eviction policies:
  - **Least Recently Used (LRU)**: Evict the least recently accessed item.
  - **Least Frequently Used (LFU)**: Evict the least frequently accessed item.

### 3. Data Retrieval and Insertion
- **When retrieving data**:
  - If the data is found in any lower cache level, move the data up to higher cache levels (L1, L2, etc.), following the defined eviction policy.
  - If the data is not present in any cache, simulate fetching it from the main memory and store it in L1 cache.
  
- **When inserting new data**:
  - Always insert the data at the L1 cache level, and evict items if necessary.

### 4. Dynamic Cache Level Management
- The system should allow adding/removing cache levels at runtime.
- New cache levels should be able to specify their own size and eviction policy.

### 5. Concurrency
- Implement the system with thread-safety, allowing concurrent reads/writes from/to the cache.

### 6. Performance Considerations
- The system should ensure efficient lookups and minimize cache misses.
- Optimize data movement across levels when data is found in lower levels.
