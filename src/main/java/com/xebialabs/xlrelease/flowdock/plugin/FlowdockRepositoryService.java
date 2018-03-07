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

import java.util.ArrayList;
import java.util.List;

import com.xebialabs.deployit.plugin.api.udm.ConfigurationItem;
import com.xebialabs.xlrelease.api.v1.ConfigurationApi;
import com.xebialabs.xlrelease.flowdock.plugin.exception.FlowdockNotConfiguredException;

/**
 * Created by jdewinne on 2/5/15.
 */
public class FlowdockRepositoryService {

    private List<FlowdockConfiguration> flowdockConfigurations = new ArrayList<>();
    
    private ConfigurationApi configurationApi;
    
    FlowdockRepositoryService(ConfigurationApi configurationApi) {
        this.configurationApi = configurationApi;
    }

    public List<FlowdockConfiguration> getFlowdockConfigurations() throws FlowdockNotConfiguredException {
        if (flowdockConfigurations.isEmpty()) {
            setFlowdockConfigurations();
        }

        return flowdockConfigurations;
    }

    public Boolean isFlowdockEnabled() throws FlowdockNotConfiguredException {
        if (flowdockConfigurations.isEmpty()) {
            setFlowdockConfigurations();
        }

        for (FlowdockConfiguration flowdockConfiguration : flowdockConfigurations) {
            if (flowdockConfiguration.isEnabled()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private void setFlowdockConfigurations() throws FlowdockNotConfiguredException {
        // Get flowdock properties
        final List<? extends ConfigurationItem> configurations = configurationApi.searchByTypeAndTitle("flowdock.configuration", null);
        if (configurations.size() > 0) {
            configurations.forEach(conf -> flowdockConfigurations.add(
                new FlowdockConfiguration(conf.getProperty("apiUrl"), conf.getProperty("flowToken"), conf.getProperty("enabled")))
            );
        } else {
            throw new FlowdockNotConfiguredException();
        }
    }


}
