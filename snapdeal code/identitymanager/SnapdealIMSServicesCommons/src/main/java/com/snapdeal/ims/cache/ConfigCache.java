package com.snapdeal.ims.cache;

import java.util.Map;

/**
 * This class is used for caching all configurable parameters.
 *
 * @author subhash
 *
 */
@Cache(name = "ConfigCache")
public class ConfigCache extends AbstractCache<String, Map<String, String>> {

}