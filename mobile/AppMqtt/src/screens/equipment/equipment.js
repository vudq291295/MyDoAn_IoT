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
    getAllEquipment,
    deleteEquipment   
} from '../../actions';
import { ListItem, Divider, Button } from 'react-native-elements';
import Dialog from "react-native-dialog";

class Equipment extends Component {
    constructor(props){
        super(props);
        this.state = {
            listEquipment: [],
            isDialog: false,
            itemSeleted: {}
        };
    }

    componentDidMount(){
        this.props._onFilter();
    }

    componentWillReceiveProps(newProps){
        const {equipment} = newProps;
        console.log('equipment', equipment);
        if(equipment && equipment.listData){
            this.state.listEquipment = equipment.listData.data;
        }
        if(equipment && equipment.delete){
            this.setState({ isDialog: false });
            this.props._onFilter();
            ToastAndroid.show('Xóa thành công!', ToastAndroid.SHORT);
        }
    }

    _handlerPress(item){
        this.setState({ isDialog: true, itemSeleted: item });
    }

    _handleCancel(){
        this.setState({ isDialog: false });
    }

    _handleEdit(){
        this.setState({ isDialog: false });
        this.props.navigation.navigate('formEquipmentScreen', {data: this.state.itemSeleted});
    };

    _handleDelete(){
        this.props._onDelete(this.state.itemSeleted);
    }

    _handleAdd(){
        this.props.navigation.navigate('formEquipmentScreen');
    }

    render() {
        return (
            <View>
                <View style={{flexDirection: 'row', margin: 15}}>
                    <Text style={{fontSize: 20}}>Danh sách thiết bị </Text>
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
                        this.state.listEquipment.map((v, i) => (
                            <ListItem
                                key={i}
                                title={v.name +" - Phòng: "+ v.roomId }
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
                    <Dialog.Button label="Đóng" onPress={()=>this._handleCancel()}/>
                </Dialog.Container>
            </View>
        );
    }
}

const mapDispatchToProps = dispatch => ({
    _onFilter: () => dispatch(getAllEquipment()),
    _onDelete: (param)=> dispatch(deleteEquipment(param))
});

const mapStateToProps = state => {
    return {
        equipment: state.equipmentReducer
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(Equipment);