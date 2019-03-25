import React, { Component } from 'react';
import { View, Text, AsyncStorage } from 'react-native';
import { connect } from 'react-redux';

import Icon from 'react-native-vector-icons/FontAwesome';

const myIcon = (<Icon name="home" size={20} />)

class Category extends Component {
    render() {
        return (
            <View>
                <Text>Category</Text>
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
export default connect(mapStateToProps, mapDispatchToProps)(Category);