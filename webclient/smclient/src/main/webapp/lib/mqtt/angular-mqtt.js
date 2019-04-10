/* Copyright (c) 2016 IO Systems

 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

( function() {
  "use strict";

  let default_config = {
    ip: "localhost",
    port: 1883,
    qos: 1,
    retain: true
  }

  angular.module('ngMqtt', []);

  angular.module('ngMqtt')
  .value('appName', 'ngMqtt')
  .value('author', 'Julien Ledun <j.ledun@iosystems.fr')
  .value('licence', 'MIT')
  .value('default_config', default_config)
  .provider('mqtt', mqttProvider)
  .run( run );

  function mqttProvider() {
    let self = this;
    self.conf = {};

    self.$get = ['default_config', function mqttFactory(default_config) {

      self.conf = self.conf || default_config;

      let client = {};
      self.mqtt = {};
      self.topics = {};
      
      client.connect = () => {

        self.mqtt = mqtt.connect(`ws:${self.conf.ip}:${self.conf.port}`);

        self.mqtt.on("message", (topic, message) => {
          angular.forEach( self.topics, ( cb, t ) => {
            if ( topic.indexOf( t ) !== -1 ) return cb( topic, message );
          });
        });
      };

      client.destroy = () => {
        if ( !self.mqtt ) return;
        self.mqtt.end();
      };

      client.send = (topic, message, qos, retain) => {
        qos = qos || self.conf.qos;
        retain = retain || self.conf.retain;
        self.mqtt.publish( topic, message, {qos: qos, retain: retain} );
      };
      client.publish = client.send;

      client.on = ( topic, cb ) => {
        topic = topic.replace('/#', '');
        self.topics[topic] = cb;
        self.mqtt.subscribe( `${topic}/#` );
      };

      client.connect();

      return client;

    }];

    self.client = function( config ) {
      self.conf = config;
    };

  };

  function run() {
    // function to be executed at launch
  };

})();
