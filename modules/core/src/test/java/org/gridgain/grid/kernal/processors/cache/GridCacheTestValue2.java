/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gridgain.grid.kernal.processors.cache;

import org.apache.ignite.cache.query.*;
import org.apache.ignite.internal.util.typedef.internal.*;
import java.io.*;

/**
 * Second test value.
 */
public class GridCacheTestValue2 implements Serializable {
    /** */
    @GridCacheQuerySqlField
    private String val;

    /**
     *
     */
    public GridCacheTestValue2() {
        /* No-op. */
    }

    /**
     *
     * @param val Value.
     */
    public GridCacheTestValue2(String val) {
        this.val = val;
    }

    /**
     *
     * @return Value.
     */
    public String getValue() {
        return val;
    }

    /**
     *
     * @param val Value.
     */
    public void setValue(String val) {
        this.val = val;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(GridCacheTestValue2.class, this);
    }
}
