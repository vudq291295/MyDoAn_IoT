import React, { Component } from 'react';
import {Text, View} from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { ListItem, Divider  } from 'react-native-elements'

const list = [
  {
    title: 'Trang chủ',
    icon: 'home',
    path: 'Home'
  },
  {
    title: 'Danh sách phòng',
    icon: 'home',
    path: 'Room'
  },
  {
    title: 'Danh sách thiết bị',
    icon: 'extension',
    path: 'Equipment'
  },
  {
    title: 'Đăng xuất',
    icon: 'exit-to-app',
    path: 'loginScreen'
  }
]

export class DrawerContent extends Component {
    constructor(props){
      super(props);
    }

    _redirect(item){
      try {
        this.props.navigation.navigate(item.path, {title: item.title});
      } catch (error) {
        console.log('error', error);
      }
    }

    render(){
      return (
        <View style={{'backgroundColor': '#dbdcdd'}}>
          {
            list.map((item, i) => (
              <View key={i}>
                <ListItem
                  key={i}
                  title={item.title}
                  leftIcon={{ name: item.icon }}
                  rightIcon={{name: 'arrow-forward'}}
                  onPress= {()=>{ this._redirect(item)}}
                />
                <Divider style={{ backgroundColor: 'grey' }} />
              </View>
            ))
          }
        </View>
      );
    }
  }