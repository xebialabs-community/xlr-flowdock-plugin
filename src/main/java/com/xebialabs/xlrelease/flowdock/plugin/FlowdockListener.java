/**
* Copyright 2018 XEBIALABS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.xebialabs.xlrelease.flowdock.plugin;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xebialabs.xlrelease.api.Inject;
import com.xebialabs.xlrelease.api.v1.IConfigurationApi;
import com.xebialabs.xlrelease.domain.events.ActivityLogEvent;
import com.xebialabs.xlrelease.events.AsyncSubscribe;
import com.xebialabs.xlrelease.events.XLReleaseEventListener;
import com.xebialabs.xlrelease.flowdock.plugin.exception.FlowdockException;
import com.xebialabs.xlrelease.flowdock.plugin.exception.FlowdockNotConfiguredException;

import static com.xebialabs.xlrelease.flowdock.plugin.TeamInboxMessage.fromActivityLogEvent;


/**
 * Created by jdewinne on 2/4/15.
 */
public class FlowdockListener implements XLReleaseEventListener {

    private final static Logger logger = LoggerFactory.getLogger(FlowdockListener.class);
    
    @Inject
    private IConfigurationApi configurationApi;

    @AsyncSubscribe
    public void sendUpdateToFlowdock(ActivityLogEvent event) {
        FlowdockRepositoryService flowdockRepositoryService = new FlowdockRepositoryService(configurationApi);
        try {
            if (flowdockRepositoryService.isFlowdockEnabled()) {
                // Get flowdock properties
                List<FlowdockConfiguration> flowdockConfigurations = flowdockRepositoryService.getFlowdockConfigurations();

                flowdockConfigurations.stream().filter(FlowdockConfiguration::isEnabled).forEach(conf -> {
                    // Send message to flowdock
                    FlowdockApi api = new FlowdockApi(conf);
                    TeamInboxMessage msg = fromActivityLogEvent(event);
                    try {
                        api.pushTeamInboxMessage(msg);
                    } catch (FlowdockException e) {
                        e.printStackTrace();
                    }
                    logger.info("Flowdock: Team Inbox notification sent successfully");
                });
            }
        } catch (FlowdockNotConfiguredException e) {
            // Do nothing, as Flowdock is not yet configured.
        }
    }
}
