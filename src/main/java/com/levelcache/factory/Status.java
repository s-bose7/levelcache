package com.levelcache.factory;

import com.levelcache.core.LevelCache;
/**
 * Enumeration of {@link LevelCache} statuses.
*/
public enum Status {
  /**
   * Uninitialized, indicates it is not ready for use.
   */
  UNINITIALIZED,

  /**
   * Maintenance, indicates exclusive access to allow for restricted operations.
   */
  MAINTENANCE,

  /**
   * Available, indicates it is ready for use.
   */
  AVAILABLE;

}
