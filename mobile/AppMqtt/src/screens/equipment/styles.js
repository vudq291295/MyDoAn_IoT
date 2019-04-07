import {
    StyleSheet, Dimensions
} from 'react-native';

const DEVICE_WIDTH = Dimensions.get('window').width;
const DEVICE_HEIGHT = Dimensions.get('window').height;
const styles = StyleSheet.create({
  txtInput:{
    padding: 10,
    marginTop: 2
  },
  btnSave:{
     width: DEVICE_WIDTH - 70,
     backgroundColor:'rgba(0,145,234,1)',
     padding:8,
     borderRadius: 20,
     marginTop:10
  },
  txtButton:{
    color:'#fff',
    textAlign:'center'
  }
  
});

export default styles;
