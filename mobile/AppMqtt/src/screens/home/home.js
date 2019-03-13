import React, { Component } from 'react';
import { View, Text, AsyncStorage } from 'react-native';
import { connect } from 'react-redux';

import { NavigationActions } from 'react-navigation';
import Icon from 'react-native-vector-icons/FontAwesome';

import { Client, Message } from 'react-native-paho-mqtt';

const myIcon = (<Icon name="home" size={20} />)

const myStorage = {
    setItem: (key, item) => {
        myStorage[key] = item;
    },
    getItem: (key) => myStorage[key],
    removeItem: (key) => {
        delete myStorage[key];
    },
};

class Home extends Component {
    static navigationOptions = ({ navigation }) => {
        let tabBarIcon = myIcon;
        let header = null;
        return { tabBarIcon, header };
    }

    constructor() {
        const client = new Client({ uri: 'ws://iot.eclipse.org:80/ws', clientId: 'clientId', storage: myStorage });
    }

    componentDidMount() {
        // set event handlers
        client.on('connectionLost', (responseObject) => {
            if (responseObject.errorCode !== 0) {
                console.log(responseObject.errorMessage);
            }
        });
        client.on('messageReceived', (message) => {
            console.log(message.payloadString);
        });

        // connect the client
        client.connect()
            .then(() => {
                // Once a connection has been made, make a subscription and send a message.
                console.log('onConnect');
                return client.subscribe('World');
            })
            .then(() => {
                const message = new Message('Hello');
                message.destinationName = 'World';
                client.send(message);
            })
            .catch((responseObject) => {
                if (responseObject.errorCode !== 0) {
                    console.log('onConnectionLost:' + responseObject.errorMessage);
                }
            });
    }



    render() {
        return (
            <View>
                <Text>Hello mqtt</Text>
            </View>
        );
    }
}

const mapDispatchToProps = dispatch => ({
});

const mapStateToProps = state => {
    return {
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(Home);