# v 2.2.12
- updated vert.x dependencies to v3.5.0
- better handling of auth with null username
- debug of auth with user/pass using wso2 as identity provider

# v 2.2.11
- added better support of sessions with prometheus metrics
- changed default auth verticle instances from 1 to 5

# v 2.2.10
- added support for prometheus metrics

# v 2.2.9
- bugfixing: retain with void or null tenant

# v 2.2.8
- topic based authorization

# v 2.2.7
- Resolved Out Of Memory: tested with 7000 msg/sec with 10 subscribers for about 2 month
- Fix null pointer exception during HAClient tests

# v 2.2.6
- compliance with mqtt paho integration suite for MQTT v 3.1.1 (Support for qos 1 and 2, will message, clean session, etc...)
- added a little http server to publish with a POST
    
        curl -XPOST \
        -d '{"test","snapshot"}' \
        -H "tenant: tenant.local" \
        "http://localhost:2883/pubsub/publish?channel=/tenant.local/channel/1"

- added authentication with clear user/pass on wso2 identity provider
- time eviction policy for retain messages
