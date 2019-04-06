import React, { Component } from 'react';
import { 
    View, 
    Text, 
    AsyncStorage, 
    ScrollView, 
    ToastAndroid 
} from 'react-native';
import { connect } from 'react-redux';
import Icon from 'react-native-vector-icons/FontAwesome';
import { 
    getListRoom,
    deleteRoom   
} from '../../actions';
import { ListItem, Divider, Button } from 'react-native-elements';
import Dialog from "react-native-dialog";

class Room extends Component {
    constructor(props){
        super(props);
        this.state = {
            listRoom: [],
            isDialog: false,
            itemSeleted: {}
        };
    }

    componentDidMount(){
        this.props._onFilter();
    }

    componentWillReceiveProps(newProps){
        const {room} = newProps;
        if(room && room.listData){
            this.state.listRoom = room.listData.data;
        }
        if(room && room.delete){
            this.setState({ isDialog: false });
            this.props._onFilter();
            ToastAndroid.show('Xóa thành công!', ToastAndroid.SHORT);
        }
    }

    _handlerPress(item){
        this.setState({ isDialog: true, itemSeleted: item });
    }

    _handleEdit(){
        this.setState({ isDialog: false });
        this.props.navigation.navigate('formRoomScreen', {data: this.state.itemSeleted});
    };

    _handleDelete(){
        this.props._onDelete(this.state.itemSeleted);
    }

    _handleAdd(){
        this.props.navigation.navigate('formRoomScreen');
    }

    render() {
        return (
            <View>
                <View style={{flexDirection: 'row', margin: 15}}>
                    <Text style={{fontSize: 20}}>Danh sách phòng </Text>
                    <Button containerStyle={{marginLeft: 30}}
                        icon={
                            <Icon
                            name="plus"
                            size={14}
                            color="white"
                            />
                        }
                        Title="Thêm mới"
                        onPress={()=>this._handleAdd()}
                        />
                </View>
                <Divider style={{ backgroundColor: 'blue' }} />
                <ScrollView>
                    {
                        this.state.listRoom.map((v, i) => (
                            <ListItem
                                key={i}
                                title={v.name}
                                badge={{ value: v.id}}
                                rightIcon={{name: 'arrow-forward'}}
                                bottomDivider={true}
                                onPress={()=> this._handlerPress(v)}
                            />
                        ))
                    }
                </ScrollView>
                <Dialog.Container visible={this.state.isDialog}>
                    <Dialog.Title>Thực hiện</Dialog.Title>
                    <Dialog.Button label="Sửa" onPress={()=>this._handleEdit()}/>
                    <Dialog.Button label="Xóa" onPress={()=>this._handleDelete()}/>
                </Dialog.Container>
            </View>
        );
    }
}

const mapDispatchToProps = dispatch => ({
    _onFilter: () => dispatch(getListRoom()),
    _onDelete: (param)=> dispatch(deleteRoom(param))
});

const mapStateToProps = state => {
    return {
        room: state.roomReducer
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(Room);