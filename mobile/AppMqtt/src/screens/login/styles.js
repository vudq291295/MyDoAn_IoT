import {
    StyleSheet, Dimensions
} from 'react-native';

const DEVICE_WIDTH = Dimensions.get('window').width;
const DEVICE_HEIGHT = Dimensions.get('window').height;
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.8,
    shadowRadius: 2,
    borderRadius: 2,
  },
  title:{
    fontSize:30,
    color:'red'
  },
  txtInput:{
    padding: 10,
    marginTop: 2
  },
  btnLogin:{
     width: DEVICE_WIDTH - 40,
     backgroundColor:'rgba(0,145,234,1)',
     padding:8,
     borderRadius: 20,
     marginTop:2
 
  },
  txtLogin:{
    color:'#fff',
    textAlign:'center'
  }
  
});

export default styles;
