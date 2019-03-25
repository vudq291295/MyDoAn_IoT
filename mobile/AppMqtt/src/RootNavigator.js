import React, { Component } from 'react';
import {Text} from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { StackNavigator, DrawerNavigator } from 'react-navigation';
import Home from './screens/home/home';
import Category from './screens/category/category';
import Login from './screens/login/Login';
import {DrawerContent} from './components';

const Main = DrawerNavigator({
  Home: {
    screen: Home,
  },
  Category: {
    screen: Category
  }
},{
  contentComponent: DrawerContent
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

  // login stack
const LoginStack = StackNavigator({
  loginScreen: { screen: Login },
}, {
  headerMode: 'none'
})

const RootNavigator = StackNavigator(
  {
    loginStack: { screen: LoginStack },
    drawerStack: { screen: DrawerNavigation }
  },
  {
    headerMode: 'none',
    initialRouteName: 'drawerStack'
  }
);
export default RootNavigator;
