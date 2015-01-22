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

package org.gridgain.grid.kernal.processors.cache.datastructures;

import org.gridgain.grid.kernal.processors.cache.*;
import org.apache.ignite.internal.util.typedef.internal.*;

import java.io.*;

/**
 * Queue header key.
 */
public class GridCacheQueueHeaderKey implements Externalizable, GridCacheInternal {
    /** */
    private static final long serialVersionUID = 0L;

    /** */
    private String name;

    /**
     * Required by {@link Externalizable}.
     */
    public GridCacheQueueHeaderKey() {
        // No-op.
    }

    /**
     * @param name Queue name.
     */
    public GridCacheQueueHeaderKey(String name) {
        this.name = name;
    }

    /**
     * @return Queue name.
     */
    public String queueName() {
        return name;
    }

    /** {@inheritDoc} */
    @Override public void writeExternal(ObjectOutput out) throws IOException {
        U.writeString(out, name);
    }

    /** {@inheritDoc} */
    @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = U.readString(in);
    }

    /** {@inheritDoc} */
    @Override public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        GridCacheQueueHeaderKey queueKey = (GridCacheQueueHeaderKey)o;

        return name.equals(queueKey.name);
    }

    /** {@inheritDoc} */
    @Override public int hashCode() {
        return name.hashCode();
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(GridCacheQueueHeaderKey.class, this);
    }
}
