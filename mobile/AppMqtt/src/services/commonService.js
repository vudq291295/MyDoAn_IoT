import axios from 'axios';
import url, { HOST} from '../ultils/constants/api';
import {AsyncStorage} from 'react-native';

var _service = axios.create({
  baseURL: HOST,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
});

export { _service } ;
