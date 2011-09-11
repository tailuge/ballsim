/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.communications.layer.client;

import com.google.gwt.core.client.JavaScriptObject;

/** Represents a channel to receive messages from the server. */
public class Channel extends JavaScriptObject {
  protected Channel() {
  }

  /** Opens the channel and attaches the given listener. */
  public final native Socket open(SocketListener listener) /*-{
    var socket = this.open();
    socket.onopen = function(event) {
      listener.@org.communications.layer.client.SocketListener::onOpen()();
    };
    socket.onmessage = function(event) {
      listener.
      @org.communications.layer.client.SocketListener::onMessage(Ljava/lang/String;)
      (event.data);
    };
    socket.onerror = function(error) {
      listener.
      @org.communications.layer.client.SocketListener::onError(Lorg/communications/layer/client/SocketError;)
      (error);
    };
    socket.onclose = function(event) {
      listener.
      @org.communications.layer.client.SocketListener::onClose()();
    };
    return socket;
  }-*/;
}
