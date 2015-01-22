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

import org.apache.ignite.marshaller.optimized.*;
import org.gridgain.grid.util.tostring.*;
import org.apache.ignite.internal.util.typedef.internal.*;

import javax.cache.processor.*;
import java.io.*;

/**
 * Implementation of {@link EntryProcessorResult}.
 */
public class CacheInvokeResult<T> implements EntryProcessorResult<T>, Externalizable, IgniteOptimizedMarshallable {
    /** */
    private static final long serialVersionUID = 0L;

    /** */
    @SuppressWarnings({"NonConstantFieldWithUpperCaseName", "JavaAbbreviationUsage", "UnusedDeclaration"})
    private static Object GG_CLASS_ID;

    /** */
    @GridToStringInclude
    private T res;

    /** */
    private Exception err;

    /**
     * Empty constructor required by {@link Externalizable}.
     */
    public CacheInvokeResult() {
        // No-op.
    }

    /**
     * @param res Computed result.
     */
    public CacheInvokeResult(T res) {
        assert res != null;

        this.res = res;
    }

    /**
     * @param err Exception thrown by {@link EntryProcessor#process(MutableEntry, Object...)}.
     */
    public CacheInvokeResult(Exception err) {
        this.err = err;
    }

    /** {@inheritDoc} */
    @Override public Object ggClassId() {
        return GG_CLASS_ID;
    }

    /** {@inheritDoc} */
    @Override public T get() throws EntryProcessorException {
        if (err != null) {
            if (err instanceof EntryProcessorException)
                throw (EntryProcessorException)err;

            throw new EntryProcessorException(err);
        }

        return res;
    }

    /** {@inheritDoc} */
    @Override public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(res);

        out.writeObject(err);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        res = (T)in.readObject();

        err = (Exception)in.readObject();
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(CacheInvokeResult.class, this);
    }
}
