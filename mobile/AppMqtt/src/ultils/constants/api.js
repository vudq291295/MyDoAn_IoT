export const HOST = 'http://14.160.26.174:6060/service-sh/'

const API = {
  LOGIN: 'oauth/token',
  USERINFO: 'userinfo',
  GETROOM: '/api/room/getAllRoom',
  INSERTROOM: '/api/room/insertRoom',
  UPDATEROOM: '/api/room/updateRoom',
  DELETEROOM: '/api/room/deleteRoom',
  GETALLROOM: '/api/equipment/getAllRoom',
  GETEQUIPMENTBYROOM: '/api/equipment/getEpuipmentByRoom',
  INSERTEQUIPMENT: '/api/equipment/insertEpuipment',
  UPDATEEQUIPMENT: '/api/equipment//updateEpuipment',
  DELETEEQUIPMENT: '/api/equipment//deleteEpuipment'
};
export default API;