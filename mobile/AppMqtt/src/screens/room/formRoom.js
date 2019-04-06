import React, { Component } from 'react';
import { 
    View, 
    Text, 
    AsyncStorage, 
    ScrollView, 
    ToastAndroid,
    TouchableOpacity
} from 'react-native';
import { connect } from 'react-redux';
import Icon from 'react-native-vector-icons/FontAwesome';
import { 
    insertRoom, 
    updateRoom, 
} from '../../actions';
import styles from './styles';
import { Divider, Button, Input, Card } from 'react-native-elements';

class FormRoom extends Component {
    static navigationOptions = ({navigation})=>{
        let headerTitle = "Phòng";
        let headerStyle = {
          backgroundColor: '#2089dc'
        };
        let headerRight= (
            <Button
                onPress={() => navigation.goBack() }
                title="Quay lại"
                />
        );
        return { headerTitle, headerRight, headerStyle};
    };

    constructor(props){
        super(props);
        this.state = {
            isUpdate: false,
            id: '',
            name: '',
            chanel: '',
        };
    }

    componentDidMount(){
        this.state = {
            isUpdate: false,
            id: '',
            name: '',
            chanel: '',
        };
        var data = this.props.navigation.getParam('data');
        if(data){
            this.setState({id: data.id, name: data.name, chanel: data.chanel, isUpdate: true});
        }
    }

    componentWillReceiveProps(newProps){
        const {room} = newProps;
        if(room && room.insert){
            ToastAndroid.show('Thêm mới thành công!', ToastAndroid.SHORT);
            this.props.navigation.navigate('Room');
        }
        if(room && room.update){
            ToastAndroid.show('Sửa thành công!', ToastAndroid.SHORT);
            this.props.navigation.navigate('Room');
        }
    }

    _onInsert(){
        var param = {
            id: parseInt(this.state.id),
            name: this.state.name,
            chanel: parseInt(this.state.chanel),
        }
        this.props.insert(param);
    }

    _onUpdate(){
        var param = {
            id: parseInt(this.state.id),
            name: this.state.name,
            chanel: parseInt(this.state.chanel),
        }
        this.props.update(param);
    }

    renderUpdate(){
        return (
            <Card>
                <Input placeholder='id' value={this.state.id.toString()} 
                    onChangeText={(id) => this.setState({ id: id })}
                />
                <Input placeholder='Tên phòng' value={this.state.name}
                    onChangeText={(name) => this.setState({ name: name })}
                />
                <Input placeholder='Kênh' value={this.state.chanel.toString()}
                    onChangeText={(chanel) => this.setState({ chanel: chanel })}
                />
                <TouchableOpacity style={styles.btnSave} onPress={()=>this._onUpdate()}>
                    <Text style={styles.txtButton}>Lưu lại</Text>
                </TouchableOpacity>
            </Card>
        );
    }

    renderCreate(){
        return (
            <Card>
                <Input placeholder='id' 
                    onChangeText={(id) => this.setState({ id: id })}
                />
                <Input placeholder='Tên phòng' 
                    onChangeText={(name) => this.setState({ name: name })}
                />
                <Input placeholder='Kênh' 
                    onChangeText={(chanel) => this.setState({ chanel: chanel })}
                />
                <TouchableOpacity style={styles.btnSave} onPress={()=>this._onInsert()}>
                    <Text style={styles.txtButton}>Lưu lại</Text>
                </TouchableOpacity>
            </Card>
        );
    }

    render() {
        if(this.state.isUpdate){
            return (
                <View>{this.renderUpdate()}</View>
            )
        }
        else{
            return (
                <View>{this.renderCreate()}</View>
            )
        }
    }
}

const mapDispatchToProps = dispatch => ({
    insert: (param)=> dispatch(insertRoom(param)),
    update: (param)=> dispatch(updateRoom(param))
});

const mapStateToProps = state => {
    return {
        room: state.roomReducer
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(FormRoom);