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

package org.gridgain.grid.marshaller;

import org.apache.ignite.internal.util.typedef.internal.*;
import java.io.*;

/**
 * Externalizable bean for marshaller testing.
 */
class GridMarshallerExternalizableBean implements Externalizable {
    /** */
    private int i;

    /**
     * Required for {@link Externalizable}.
     */
    public GridMarshallerExternalizableBean() {
        // No=op.
    }

    /** {@inheritDoc} */
    @Override public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(i);
    }

    /** {@inheritDoc} */
    @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        i = in.readInt();
    }

    /** {@inheritDoc} */
    @Override public boolean equals(Object o) {
        assert o instanceof GridMarshallerExternalizableBean;

        return i == ((GridMarshallerExternalizableBean)o).i;
    }

    /** {@inheritDoc} */
    @Override public int hashCode() {
        return i;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(GridMarshallerExternalizableBean.class, this);
    }
}
