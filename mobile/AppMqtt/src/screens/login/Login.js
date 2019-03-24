import React, { PureComponent } from 'react';
import {
  Platform, StyleSheet, Text, Alert, View, TouchableOpacity, TextInput, Image
} from 'react-native';
import { NavigationActions } from 'react-navigation';
import { connect } from 'react-redux';
import Toast, {DURATION} from 'react-native-easy-toast';
import { WhiteSpace} from '@ant-design/react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Input, Card } from 'react-native-elements';
import styles from './styles';
import { login, setToken } from '../../actions';

class Login extends PureComponent {
  static navigationOptions = {
    header: null,
  };

  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      checkLogin: 0
    }
  }

  _onSubmit = () => {
    const { username, password } = this.state;
    if (username === '' || password === '') {
      this.refs.toast.show('Vui lòng nhập thông tin đăng nhập');
    } else {
      this.props.onLogin(username, password);
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Card title="Đăng nhập">
          <Input
            placeholder=' Tài khoản'
            leftIcon={
              <Icon
                name='user'
                size={24}
                color='grey'
              />
            }
            inputStyle={styles.txtInput}
            onChangeText={(username) => this.setState({ username: username })}
          />
          <Input
            placeholder=' Mật khẩu'
            leftIcon={
              <Icon
                name='key'
                size={24}
                color='grey'
              />
            }
            secureTextEntry={true}
            inputStyle={styles.txtInput}
            onChangeText={(password) => this.setState({ password: password })}
          />
           <WhiteSpace size="xl"/>
          <TouchableOpacity onPress={this._onSubmit} style={styles.btnLogin}>
            <Text style={styles.txtLogin}>Đăng nhập</Text>
          </TouchableOpacity>
        </Card>
        <Toast
            ref="toast"
            style={{backgroundColor:'#f4f4f4'}}
            position='top'
            positionValue={100}
            fadeInDuration={750}
            fadeOutDuration={1000}
            opacity={0.8}
            textStyle={{color:'red'}}
        />
      </View>
    );
  }
}

const mapDispatchToProps = dispatch => ({
  onLogin: (username, password) => dispatch(login(username, password)),
  // setToken:(token)=>dispatch(setToken(token)),
});

const mapStateToProps = state => {
  return {
    user: state.LoginReducer
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);