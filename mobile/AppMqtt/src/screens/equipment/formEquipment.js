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
    insertEquipment, 
    updateEquipment, 
} from '../../actions';
import styles from './styles';
import { Divider, Button, Input, Card } from 'react-native-elements';

class FormEquipment extends Component {
    static navigationOptions = ({navigation})=>{
        let headerTitle = "Thiết bị";
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
            id: 0,
            name: '',
            roomId: 0,
            portOutput: 0
        };
    }

    componentDidMount(){
        this.state = {
            isUpdate: false,
            id: 0,
            name: '',
            roomId: 0,
            portOutput: 0
        };
        var data = this.props.navigation.getParam('data');
        if(data){
            this.setState({id: data.id, name: data.name, portOutput: data.portOutput, roomId: data.roomId, isUpdate: true});
        }
    }

    componentWillReceiveProps(newProps){
        const {equipment} = newProps;
        if(equipment && equipment.insert){
            ToastAndroid.show('Thêm mới thành công!', ToastAndroid.SHORT);
            this.props.navigation.navigate('Equipment');
        }
        if(equipment && equipment.update){
            ToastAndroid.show('Sửa thành công!', ToastAndroid.SHORT);
            this.props.navigation.navigate('Equipment');
        }
    }

    _onInsert(){
        var param = {
            id: parseInt(this.state.id),
            name: this.state.name,
            roomId: parseInt(this.state.roomId),
            portOutput: parseInt(this.state.portOutput),
        }
        this.props.insert(param);
    }

    _onUpdate(){
        var param = {
            id: parseInt(this.state.id),
            name: this.state.name,
            roomId: parseInt(this.state.roomId),
            portOutput: parseInt(this.state.portOutput),
        }
        this.props.update(param);
    }

    renderUpdate(){
        return (
            <Card>
                <Input placeholder='id' value={this.state.id.toString()} 
                    onChangeText={(id) => this.setState({ id: id })}
                />
                <Input placeholder='Tên thiết bị' value={this.state.name}
                    onChangeText={(name) => this.setState({ name: name })}
                />
                <Input placeholder='Phòng' value={this.state.roomId.toString()}
                    onChangeText={(roomId) => this.setState({ roomId: roomId })}
                />
                <Input placeholder='Cổng ra' value={this.state.portOutput.toString()}
                    onChangeText={(portOutput) => this.setState({ portOutput: portOutput })}
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
                <Input placeholder='Tên thiết bị' 
                    onChangeText={(name) => this.setState({ name: name })}
                />
                <Input placeholder='Phòng' 
                    onChangeText={(roomId) => this.setState({ roomId: roomId })}
                />
                <Input placeholder='Cổng ra'
                    onChangeText={(portOutput) => this.setState({ portOutput: portOutput })}
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
    insert: (param)=> dispatch(insertEquipment(param)),
    update: (param)=> dispatch(updateEquipment(param))
});

const mapStateToProps = state => {
    return {
        equipment: state.equipmentReducer
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(FormEquipment);