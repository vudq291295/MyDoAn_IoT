import { StackNavigator, TabNavigator, createStackNavigator } from 'react-navigation';
import Home from './screens/home/home'

const MainNavigator = TabNavigator(
  {
    Home: { screen: Home },
    News: { screen: Home }
  },
  {
    headerMode: 'none',
    tabBarPosition: 'none',
    lazy: true,
    swipeEnabled: false,
    tabBarOptions: {
      activeTintColor: '#e91e63',
      showLabel: false,
      showIcon: true,
      scrollEnabled: false,
      style: {
        backgroundColor: '#198724'
      },
    }
  }
);
const RootNavigator = StackNavigator(
  {
    Main: {
      screen: MainNavigator
    },
  },
  {
    headerMode: 'none'
    //initialRouteName: 'Login',
  }
);
export default RootNavigator;
