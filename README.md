# XL Release Flowdock plugin

[![Build Status][xlr-flowdock-plugin-travis-image] ][xlr-flowdock-plugin-travis-url]
[![Codacy][xlr-flowdock-plugin-codacy-image] ][xlr-flowdock-plugin-codacy-url]
[![Code Climate][xlr-flowdock-plugin-code-climate-image] ][xlr-flowdock-plugin-code-climate-url]
[![License: MIT][xlr-flowdock-plugin-license-image] ][xlr-flowdock-plugin-license-url]
[![Github All Releases][xlr-flowdock-plugin-downloads-image] ]()

[xlr-flowdock-plugin-travis-image]: https://travis-ci.org/xebialabs-community/xlr-flowdock-plugin.svg?branch=master
[xlr-flowdock-plugin-travis-url]: https://travis-ci.org/xebialabs-community/xlr-flowdock-plugin
[xlr-flowdock-plugin-codacy-image]: https://api.codacy.com/project/badge/Grade/0fc277a894eb4cf1b286fcdd5e770768
[xlr-flowdock-plugin-codacy-url]: https://www.codacy.com/app/joris-dewinne/xlr-flowdock-plugin
[xlr-flowdock-plugin-code-climate-image]: https://codeclimate.com/github/xebialabs-community/xlr-flowdock-plugin/badges/gpa.svg
[xlr-flowdock-plugin-code-climate-url]: https://codeclimate.com/github/xebialabs-community/xlr-flowdock-plugin
[xlr-flowdock-plugin-license-image]: https://img.shields.io/badge/License-MIT-yellow.svg
[xlr-flowdock-plugin-license-url]: https://opensource.org/licenses/MIT
[xlr-flowdock-plugin-downloads-image]: https://img.shields.io/github/downloads/xebialabs-community/xlr-flowdock-plugin/total.svg

## Preface

This document describes the functionality provided by the XL Release Flowdock plugin.

See the [XL Release Documentation](https://docs.xebialabs.com/xl-release/) for background information on XL Release and release orchestration concepts.

## Overview

The XL Release Flowdock plugin is a XL Release plugin that adds capability for sending message to a Flowdock inbox.

## Requirements

* **Requirements**
	* v1.0.0-v1.3.0:  **XL Release 6.0.x - 7.2.x**
	* v1.4.0: **XL Release 8+**
	* Event-based notifications are not supported in **XL Release 7.5.x and 7.6.x**.

## Installation

* Copy the latest JAR file from the [releases page](https://github.com/xebialabs-community/xlr-flowdock-plugin/releases) into the `XL_RELEASE_SERVER/plugins` directory.
* Restart the XL Release server.

## Usage

### Event based notifications

1. Go to `Settings - Shared configuration - Flowdock: Configuration`.
2. Add a new configuration.
3. Provide Title (can be anything), API url (eg: https://api.flowdock.com), Flow token (see your Flow configuration in Flowdock) and enable or disable the Flowdock configuration.
4. Each time XL Release stores something into the Activity logs, this will also be send to Flowdock. 
5. The SendTeamInboxMessage task can also use this configuration item.

![Flowdock configuration](images/Flowdock_configuration.png?raw=true "Flowdock configuration")
   

### Flowdock server

For the Post Message task, configure a Flowdock Server.  
1. Go to `Settings - Shared configuration - Flowdock: Server`.  
2. Add a new server.  
3. Provide Title (can be anything) and API url (eg: https://api.flowdock.com).

![Flowdock server](images/Flowdock_server.png?raw=true "Flowdock server")

## Tasks

### Send Team Inbox Message

Send a specific message to a Team Inbox.  This task should be configured with a Flowdock configuration and a flow token that is not associated with a source.

![Flowdock send team inbox message](images/Flowdock_send_team_inbox_message.png?raw=true "Flowdock send team inbox message")

### Post Message  

Send a specific message to a flow (inbox) using the Messages API.  This task should be configured with a Flowdock server and a flow token that is associated with a source.  See <https://www.flowdock.com/api/integration-getting-started>.

![Flowdock post message](images/Flowdock_post_message.png?raw=true "Flowdock post message")
