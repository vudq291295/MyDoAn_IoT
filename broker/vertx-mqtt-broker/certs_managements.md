# Certs management

## Create a root CA (One time only)

    openssl genrsa -out rootCA_torename.key 2048
    openssl req -x509 -new -nodes -key rootCA_torename.key -days 1024 -out rootCA_torename.pem

## Create a cert to use in main broker

    TENANT="main"
    mkdir $TENANT
    openssl genrsa -out $TENANT/$TENANT.key 2048
    openssl req -new -key $TENANT/$TENANT.key -out $TENANT/$TENANT.csr
    openssl x509 -req -in $TENANT/$TENANT.csr -CA ./rootCA.pem -CAkey ./rootCA.key -CAcreateserial -out $TENANT/$TENANT.crt -days 500 -sha256
    openssl pkcs8 -topk8 -inform PEM -outform PEM -in $TENANT/$TENANT.key -out $TENANT/$TENANT.pkcs8.key -nocrypt

Pay attention to common-name parameter, use "localhost" for local tests

    Common Name (eg, YOUR name) []:lcoalhost

If your broker websocket bridge is exposed throught dns as "http://mqtt.acme.it:7007", use "acme.it"

    Common Name (eg, YOUR name) []:mqtt.acme.it


### Config

    {
      "brokers": [
        {
          "tcp_port": 1883,
          "socket_idle_timeout": 120
        }
      ],
      "bridge_server": {
        "websocket_enabled": true,
        "socket_idle_timeout": 120,
        "local_bridge_port": 7007,
        "ssl_cert_key": "/opt/CA/dcfilippetti/dcfilippetti.pkcs8.key",
        "ssl_cert": "/opt/CA/dcfilippetti/dcfilippetti.crt",
        "ssl_trust": "/opt/CA/rootCA.pem"
      }
    }

## Create a cert to use locally (tenant)

    TENANT="tenant1"
    mkdir $TENANT
    openssl genrsa -out $TENANT/$TENANT.key 2048
    openssl req -new -key $TENANT/$TENANT.key -out $TENANT/$TENANT.csr
    openssl x509 -req -in $TENANT/$TENANT.csr -CA ./rootCA.pem -CAkey ./rootCA.key -CAcreateserial -out $TENANT/$TENANT.crt -days 500 -sha256
    openssl pkcs8 -topk8 -inform PEM -outform PEM -in $TENANT/$TENANT.key -out $TENANT/$TENANT.pkcs8.key -nocrypt

### Config

    {
      "brokers": [
        {
          "tcp_port": 1884,
          "socket_idle_timeout": 120
        }
      ],
      "bridge_client": {
        "websocket_enabled": true,
        "socket_idle_timeout": 120,
        "remote_bridge_host": "localhost",
        "remote_bridge_port": 7007,
        "ssl_cert_key": "/opt/CA/t1.com/t1.com.pkcs8.key",
        "ssl_cert": "/opt/CA/t1.com/t1.com.crt",
        "ssl_trust": "/opt/CA/rootCA.pem"
      }
    }

the property "remote_bridge_tenant" will not be used if ssl_* properties are present.
The tenant name is in field CN of the certificate.