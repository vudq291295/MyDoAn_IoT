import React, { Component } from 'react';
import {Text} from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { StackNavigator, DrawerNavigator } from 'react-navigation';
import Home from './screens/home/home';
import Room from './screens/room/room';
import FormRoom from './screens/room/formRoom';
import Login from './screens/login/Login';
import {DrawerContent} from './components';

const Main = DrawerNavigator({
  Home: {
    screen: Home,
  },
  Room: {
    screen: Room
  },
  formRoomScreen: {
    screen: FormRoom
  }
},{
  contentComponent: DrawerContent,
  initialRouteName: 'Home',
});

const DrawerNavigation = StackNavigator({
  DrawerStack: { screen: Main }
  }, {
    headerMode: 'screen',
    navigationOptions: ({navigation}) => ({
      headerStyle: {backgroundColor: '#2089dc'},
      title: navigation.getParam('title', 'Trang chủ'),
      headerLeft: <Icon style={{'paddingLeft': 10}} name="align-justify" size={20} onPress={ () => navigation.navigate('DrawerToggle') } />
    })
  });

const RootNavigator = StackNavigator(
  {
    loginScreen: { screen: Login },
    drawerScreen: { screen: DrawerNavigation }
  },
  {
    headerMode: 'none',
    initialRouteName: 'loginScreen'
  }
);
export default RootNavigator;
