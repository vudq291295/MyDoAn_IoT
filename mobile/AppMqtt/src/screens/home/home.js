import React, { Component } from 'react';
import { View, Text, AsyncStorage, ScrollView } from 'react-native';
import { connect } from 'react-redux';
import { 
    getAllEquipment
} from '../../actions';
import { NavigationActions } from 'react-navigation';
import Icon from 'react-native-vector-icons/FontAwesome';
import ToggleSwitch from 'toggle-switch-react-native'
import { Flex, WhiteSpace, List } from '@ant-design/react-native';
import { MqttClient } from '@eyezon/react-native-mqtt'
const Item = List.Item;

const uri = 'wss://192.168.1.99:1884/';
const username = 'vudq'; 
const password = '1';

const clientId = Math.floor(Math.random() * 1000) + '';

class Home extends Component {
    constructor(props){
        super(props);
        this.state = {
            lstData: [],
            isOnBlueToggleSwitch: false,
        };
    }

    componentDidMount(){
        this.props._filterData();
        MqttClient.connect({ 
            uri, 
            username, 
            password, 
            clientId },
        ()=>{
          console.log('Woo! Mqtt connected!!')
        });
    }

    componentWillReceiveProps(newPops){
        const { equipment } = newPops;
        if(equipment && equipment.listData){
            equipment.listData.data.forEach(element => {
                element.isOn = false;
            });
            this.state.lstData = this._fitlterObject(equipment.listData.data)
        }
    }

    _fitlterObject(list){
        var group_to_values = list.reduce(function (obj, item) {
            obj[item. roomId] = obj[item. roomId] || [];
            obj[item. roomId].push(item);
            return obj;
        }, {});
        
        var groups = Object.keys(group_to_values).map(function (key) {
            return { roomId: key, equipment: group_to_values[key]};
        });

        return groups;
    }

    _onToggle(item, toggle){
        item.isOn = toggle;
        this.setState({lstData: this.state.lstData});
    }

    renderContent(equipments){
        return equipments.map((item, key) => {
            return (
                <Item key={key}
                    extra={
                        <View>
                            <ToggleSwitch
                                onColor="#2196F3"
                                offColor="grey"
                                isOn={item.isOn}
                                size='small'
                                onToggle={isOnToggle => {
                                    this._onToggle(item, isOnToggle);
                                }}
                                />
                        </View>
                        }
                >
                    {item.name}
                </Item>
            );
        });
    }

    render() {
        return (
            <ScrollView
                style={{ flex: 1 }}
                automaticallyAdjustContentInsets={false}
                showsHorizontalScrollIndicator={false}
                showsVerticalScrollIndicator={false}
            >
                {
                    this.state.lstData.map((item, i) => (
                        <List renderHeader={'Phòng : '+ item.roomId} key={i}>
                            {this.renderContent(item.equipment)}
                        </List>
                    ))
                }
            </ScrollView>
        );
    }
}

const mapDispatchToProps = dispatch => ({
    _filterData: () => dispatch(getAllEquipment()),
});

const mapStateToProps = state => {
    return {
        equipment: state.equipmentReducer
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(Home);