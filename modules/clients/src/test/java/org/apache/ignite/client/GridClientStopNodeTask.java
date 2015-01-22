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

package org.apache.ignite.client;

import org.apache.ignite.*;
import org.apache.ignite.compute.*;
import org.apache.ignite.resources.*;
import org.apache.ignite.internal.util.typedef.*;

import java.util.*;

import static org.apache.ignite.compute.ComputeJobResultPolicy.*;

/**
 * Stop node task, applicable arguments:
 * <ul>
 *     <li>node id (as string) to stop or</li>
 *     <li>node type (see start nodes task).</li>
 * </ul>
 */
public class GridClientStopNodeTask extends ComputeTaskSplitAdapter<String, Integer> {
    /** */
    @IgniteLoggerResource
    private transient IgniteLogger log;

    /** */
    @IgniteInstanceResource
    private transient Ignite ignite;

    /** {@inheritDoc} */
    @Override protected Collection<? extends ComputeJob> split(int gridSize, String arg) throws IgniteCheckedException {
        Collection<ComputeJob> jobs = new ArrayList<>();

        for (int i = 0; i < gridSize; i++)
            jobs.add(new StopJob(arg));

        return jobs;
    }

    /** {@inheritDoc} */
    @Override public ComputeJobResultPolicy result(ComputeJobResult res, List<ComputeJobResult> rcvd) throws IgniteCheckedException {
        ComputeJobResultPolicy superRes = super.result(res, rcvd);

        // Deny failover.
        if (superRes == FAILOVER)
            superRes = WAIT;

        return superRes;
    }

    /** {@inheritDoc} */
    @Override public Integer reduce(List<ComputeJobResult> results) throws IgniteCheckedException {
        int stoppedCnt = 0;

        for (ComputeJobResult res : results)
            if (!res.isCancelled())
                stoppedCnt+=(Integer)res.getData();

        return stoppedCnt;
    }

    /**
     * Stop node job it is executed on.
     */
    private static class StopJob extends ComputeJobAdapter {
        /** */
        private final String gridType;

        /** */
        @IgniteLoggerResource
        private IgniteLogger log;

        /** */
        @IgniteInstanceResource
        private Ignite ignite;

        /** */
        private StopJob(String gridType) {
            this.gridType = gridType;
        }

        /** {@inheritDoc} */
        @Override public Object execute() {
            log.info(">>> Stop node [nodeId=" + ignite.cluster().localNode().id() + ", name='" + ignite.name() + "']");

            String prefix = GridClientStartNodeTask.getConfig(gridType).getGridName() + " (";

            if (!ignite.name().startsWith(prefix)) {
                int stoppedCnt = 0;

                for (Ignite g : G.allGrids())
                    if (g.name().startsWith(prefix)) {
                        try {
                            log.info(">>> Grid stopping [nodeId=" + g.cluster().localNode().id() +
                                ", name='" + g.name() + "']");

                            G.stop(g.name(), true);

                            stoppedCnt++;
                        }
                        catch (IllegalStateException e) {
                            log.warning("Failed to stop grid.", e);
                        }
                    }

                return stoppedCnt;
            }

            return 0;
        }
    }
}
