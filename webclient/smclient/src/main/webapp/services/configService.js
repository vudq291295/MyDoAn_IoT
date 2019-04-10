define(['angular'], function () {
	var app = angular.module('configModule', []);

	app.factory('configService', function () {
		var hostname = window.location.hostname;
		var port = window.location.port;
		var rootUrl = 'http://' + hostname + ':' + port;
		//var rootUrlApi = 'http://localhost:2912';
		var rootUrlApi = 'http://14.160.26.174:6060/service-sh';
		if (!port) {
			rootUrl = 'http://' + hostname;
		}

		var result = {
			rootUrlWeb: rootUrl,
			rootUrlWebApi: rootUrlApi + '/api',
			apiServiceBaseUri: rootUrlApi,
			dateFormat: 'dd/MM/yyyy',
			delegateEvent: function ($event) {
				$event.preventDefault();
				$event.stopPropagation();
			},
			moment: require('moment')
		};

		result.buildLayouts = function (folder) {
			return this.rootUrlWeb + "/smclient/views/" + folder + "/";
		};

		result.buildUrl = function (folder, file) {
			var d = new Date();
			return this.rootUrlWeb + "/smclient/views/" + folder + "/" + file + ".html?time=" + d.getTime();
		};
		result.pageDefault = {
			totalItems: 0,
			itemsPerPage: 20,
			currentPage: 1,
			pageSize: 5,
			totalPage: 5,
			maxSize: 5
		};
		result.paramDefault = {
			IsAdvance: false,
			AdvanceData: {},
			PageSize: 5,
			Page: 5,
			OrderBy: '',
			OrderType: 'ASC',
			Keyword: '',
			summary: '',
		};
		result.filterDefault = {
			IsAdvance: false,
			AdvanceData: {},
			OrderBy: '',
			OrderType: 'ASC',
			Summary: ''
		};

		var label = {
			lblMessage: 'Thông báo',
			lblNotifications: 'Thông báo',
			lblindex: '',
			lblDetails: 'Thông tin',
			lblEdit: 'Cập nhập',
			lblCreate: 'Thêm',
			lbl: '',

			btnCreate: 'Thêm mới',
			btnEdit: 'Sửa',
			btnDelete: 'Xóa',
			btnRemove: 'Xóa',
			btnActive: 'Active',


			btnInactive: 'Inactive',
			btnToggle: 'Toggle',
			btnSaveAndKeep: 'Lưu và giữ lại',
			btnSaveAndPrint: 'Lưu và in phiếu',

			btnSearch: 'Tìm kiếm',
			btnRefresh: 'Làm mới',
			btnBack: 'Quay lại',
			btnClear: 'Xóa tất cả',
			btnCancel: 'Hủy',

			btnSave: 'Lưu lại',
			btnSubmit: 'Lưu',

			btnLogOn: 'Đăng nhập',
			btnLogOff: 'Đăng xuất',
			btnChangePassword: 'Đổi mật khẩu',

			btnSendMessage: 'Gửi tin nhắn',
			btnSendNotification: 'Gửi thông báo',
			btnNotifications: 'Thông báo',

			btnDisconnect: 'Hủy kết nối',
			btnDisconnectSession: 'Hủy kết nối',
			btnDisconnectAccount: 'Hủy mọi kết nối',

			btnUpload: 'Upload',
			btnUploadAll: 'Upload tất cả',
			btnFileCancel: 'Hủy',
			btnFileCancelAll: 'Hủy tất cả',
			btnFileRemove: 'Xóa',
			btnFileRemoveAll: 'Xóa tất cả',
			btn: '',

			btnImportExcel: 'Import từ file excel',
			btnExportExcel: 'Xuất ra file excel',

			btnCall: 'Call',
			btnChart: 'Biểu đồ',
			btnData: 'Số liệu',
			btnPrint: 'In phiếu',
			btnExit: 'Thoát',
			btnExportPDF: 'Kết xuất file PDF',
			btnExport: 'Kết xuất',

			btnPrintList: 'In DS',
			btnPrintDetailList: 'In DS chi tiết',
			btnSend: 'DS duyệt',
			btnApproval: 'Duyệt',
			btnComplete: 'Hoàn thành',
			btnAddInfo: 'Bổ sung'
		};

		result.label = label;

		return result;
	}
	);
	return app;
});
