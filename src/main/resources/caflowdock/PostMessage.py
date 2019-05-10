#
# Copyright 2019 XEBIALABS
#
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
#

import sys
import com.xhaus.jyson.JysonCodec as json
from sets import Set

http_success = Set([200, 201, 202])
contentType = "application/json"

def addToDictionary(dictionary, name, value):
    if value:
        dictionary[name] = value

def addMapToDictionary(dictionary, name, map):
    if map:
        dictionary[name] = {}
        for key in map.keys():
            if map[key]:
                dictionary[name][key] = map[key]

message = {
    "event" : "activity",
    "flow_token" : flowToken,
    "author" : {
        "name" : "XL Release"
    },
    "title" : messageTitle,
    "external_thread_id" : externalThreadId
}
addToDictionary(message, "body", messageBody)

thread = {}
addToDictionary(thread, "title", threadTitle)
addToDictionary(thread, "body", threadBody)
addToDictionary(thread, "external_url", threadExternalUrl)
threadFieldList = []
for key in threadFields.keys():
    if threadFields[key]:
        threadFieldList.append({"label" :key, "value" : threadFields[key]})
addToDictionary(thread, "fields", threadFieldList)
threadStatus = {}
addToDictionary(threadStatus, "value", threadStatusValue)
addToDictionary(threadStatus, "color", threadStatusColor)
addToDictionary(thread, "status", threadStatus)

addToDictionary(message, "thread", thread)

request = HttpRequest(flowdockServer)
response = request.post("/messages", json.dumps(message), contentType=contentType)
if response.status in http_success:
    print "Successful %s" % response.status
else:
    response.errorDump()
    sys.exit(1)
