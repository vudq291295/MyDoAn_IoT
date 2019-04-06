import React, { Component } from 'react';
import { View, Text, AsyncStorage, ScrollView } from 'react-native';
import { connect } from 'react-redux';
import Icon from 'react-native-vector-icons/FontAwesome';
import { getListRoom } from '../../actions';
import { ListItem } from 'react-native-elements';

class Room extends Component {
    constructor(props){
        super(props);
        this.state = {
            listRoom: [],
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
    }

    _handlerLongPress(item){
        console.log('item', item);
    }

    render() {
        return (
            <View>
                <ScrollView>
                {
                    this.state.listRoom.map((v, i) => (
                        <ListItem
                            key={i}
                            title={v.name}
                            badge={{ value: v.id}}
                            rightIcon={{name: 'arrow-forward'}}
                            bottomDivider={true}
                            onLongPress={()=> this._handlerLongPress(v)}
                        />
                    ))
                }
            </ScrollView>
            </View>
        );
    }
}

const mapDispatchToProps = dispatch => ({
    _onFilter: () => dispatch(getListRoom())
});

const mapStateToProps = state => {
    return {
        room: state.roomReducer
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(Room);