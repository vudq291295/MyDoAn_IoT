import React, { Component } from 'react';
import { BackHandler } from 'react-native';
import { connect } from 'react-redux';
import { addNavigationHelpers, NavigationActions } from 'react-navigation';
import { addListener } from './ultils/redux';
import RootNavigator from './RootNavigator';

class AppNavigatorState extends Component {

    componentDidMount() {
        BackHandler.addEventListener('hardwareBackPress', this.onBackButtonPressAndroid);
    }

    componentWillUnmount() {
        BackHandler.removeEventListener('hardwareBackPress', this.onBackButtonPressAndroid);
    }
    onBackButtonPressAndroid = () => {
        const { dispatch, nav } = this.props;
        
        if (nav.index === 0) {
            return false;
        }
        dispatch(NavigationActions.back());
        return true;
    };
    render() {
        const { dispatch, nav } = this.props;
        return (
            <RootNavigator
                navigation={addNavigationHelpers({
                    dispatch,
                    state: nav,
                    addListener
                })}
            />
        );
    }
}
const mapStateToProps = state => ({
    nav: state.nav,
});
export default connect(mapStateToProps)(AppNavigatorState);
