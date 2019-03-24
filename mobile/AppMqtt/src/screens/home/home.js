import React, { Component } from 'react';
import { View, Text, AsyncStorage } from 'react-native';
import { connect } from 'react-redux';

import { NavigationActions } from 'react-navigation';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Button } from '@ant-design/react-native';


const myIcon = (<Icon name="home" size={20} />)

class Home extends Component {
    static navigationOptions = ({ navigation }) => {
        let tabBarIcon = myIcon;
        let header = null;
        return { tabBarIcon, header };
    }

    render() {
        return (
            <View>
                <Text>Hello mqtt</Text>
                <Button type="primary">primary</Button>
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